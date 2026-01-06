package com.example.hrm.docuseal;

import com.example.hrm.model.Contract;
import com.example.hrm.model.Employee;
import com.example.hrm.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class DocusealApiService {
    private final RestTemplate restTemplate;
    private final EmployeeRepository employeeRepository;

    @Value("${docuseal.role}")
    private String docusealRole;

    @Value("${docuseal.template_id}")
    private String template_id;

    @Value("${port.docuseal}")
    private String docuSealUrl;

    @Value("${docuseal.API_KEY}")
    private String docuSealKey;

    public DocuSeal getSubmission(String submissionId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", docuSealKey);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<DocuSeal> response = restTemplate.exchange( docuSealUrl + "submissions/" + submissionId,
                HttpMethod.GET, requestEntity, DocuSeal.class );
        return response.getBody();
    }

    public DocuSeal createSubmission(String idAdmin, Contract contract) {
        Map<String, Object> payload = buildDocusealPayload(idAdmin, contract);
        return callDocusealApi(payload);
    }

    private DocuSeal callDocusealApi(Map<String, Object> payload) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Auth-Token", docuSealKey);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(payload, headers);
        ResponseEntity<DocuSeal[]> response = restTemplate.postForEntity( docuSealUrl + "submissions",
                requestEntity, DocuSeal[].class );
        DocuSeal[] submissions = response.getBody();
        if (submissions != null && submissions.length > 0) {
            return submissions[0]; }
        else {
            throw new RuntimeException("Docuseal API returned empty array");
        }
    }






    private Map<String, Object> buildDocusealPayload(String idAdmin, Contract contract) {
        Employee employee = contract.getEmployee();

        Employee admin = employeeRepository.findById(idAdmin)
                .orElseThrow(() -> new RuntimeException("Not Found Employee with Id:" + idAdmin));

        // Xử lý date_end có thể null
        String dateEnd = contract.getDateEnd() != null
                ? contract.getDateEnd().toString()
                : ""; // hoặc "" nếu muốn để trống


        // Tạo values bằng HashMap
        Map<String, Object> values = new HashMap<>();

        //Tiêu đề
        values.put("contractId", contract.getId());
        values.put("dateCreate", contract.getDateCreate().toString());

        //Bên A
        values.put("adminName", admin.getName());
        values.put("adminNation", admin.getNation());
        values.put("adminPhone", admin.getPhone());

        //Bên B
        values.put("employeeName", employee.getName());
        values.put("employeeBirth", employee.getBirthDate().toString());
        values.put("employeeNation", employee.getNation());
        values.put("employeeIdentification", employee.getIdentification().getId());
        values.put("employeePhone", employee.getPhone());
        values.put("employeeAddress", getAddress(employee));

        //Điều 1.
        values.put("position", contract.getPosition());

        //Điều 2
        values.put("typeContract", contract.getTypeContract().getName());
        values.put("term", String.valueOf(contract.getTerm()));
        values.put("dateBegin", contract.getDateBegin().toString());
        values.put("dateEnd", dateEnd);

        //Điều 3:
        values.put("salary", String.valueOf(contract.getSalary()));
        values.put("note", contract.getNote());

        // Tạo fields readonly từ values
        List<Map<String, Object>> fields = new ArrayList<>();
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            Map<String, Object> field = new HashMap<>();
            field.put("name", entry.getKey());
            field.put("default_value", entry.getValue());
            field.put("readonly", true);
            fields.add(field);
        }

        // Thêm field chữ ký
        fields.add(Map.of("name", "dateSign", "readonly", false));
        fields.add(Map.of("name", "employeeSignature", "readonly", false));

        // Tạo submitter
        Map<String, Object> submitter = new HashMap<>();
        submitter.put("email", employee.getEmail());
        submitter.put("name", employee.getName());
        submitter.put("role", docusealRole);
        submitter.put("values", values);
        submitter.put("fields", fields);

        // Tạo payload
        Map<String, Object> payload = new HashMap<>();
        payload.put("template_id", template_id);
        payload.put("submitters", List.of(submitter));
        payload.put("send_email", true);
        payload.put("order", "preserved");

        return payload;
    }



    private String getAddress(Employee employee) {
        return employee.getAddresses().stream()
                .filter(addr -> "Thường trú".equalsIgnoreCase(addr.getAddressType()))
                .findFirst()
                .map(addr -> String.format("%s, %s, %s, %s",
                        addr.getStreet(),
                        addr.getWard(),
                        addr.getDistrict(),
                        addr.getProvince()))
                .orElse("Không có địa chỉ thường trú");
    }


}
