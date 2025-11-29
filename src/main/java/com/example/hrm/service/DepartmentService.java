package com.example.hrm.service;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.dto.DepartmentDTO;
import com.example.hrm.model.Department;
import com.example.hrm.repository.DepartmentRepository;
import com.example.hrm.request.DepartmentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public GeneralResponse<?> getAll(){
        List<DepartmentDTO> list = departmentRepository.findAll()
                                                        .stream()
                                                        .map(DepartmentDTO::new)
                                                        .collect(Collectors.toList());

        if(list.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Empty", null);
        }
        return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Departments", list);
    }

    public GeneralResponse<?> create(DepartmentRequest request){
        if(departmentRepository.existsByIdOrName(request.getId(), request.getName())){
            return new GeneralResponse<>(HttpStatus.CONFLICT.value(), "Existed: {Id or Name}", null);
        }

        Department department = new Department();
        department.setId(request.getId());
        department.setName(request.getName());
        department.setDescribe(request.getDescribe());

        departmentRepository.save(department);

        DepartmentDTO dto = new DepartmentDTO(department);

        return new GeneralResponse<>(HttpStatus.OK.value(), "Success", dto);
    }

    public GeneralResponse<?> update(String id, String name, String describe){
        Optional<Department> findResult = departmentRepository.findById(id);
        if(findResult.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(),"Not Found id", null );
        }

        Department department = findResult.get();
        department.setName(name);
        department.setDescribe(describe);

        departmentRepository.save(department);
        return new GeneralResponse<>(HttpStatus.NO_CONTENT.value(), "Success", null);
    }
}
