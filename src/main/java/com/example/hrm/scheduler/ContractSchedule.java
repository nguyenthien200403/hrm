package com.example.hrm.scheduler;

import com.example.hrm.docuseal.DocusealApiService;
import com.example.hrm.model.Contract;
import com.example.hrm.docuseal.DocuSeal;
import com.example.hrm.repository.ContractRepository;
import com.example.hrm.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ContractSchedule {
    @Autowired
    private ContractService service;

    @Autowired
    private ContractRepository repository;

    @Autowired
    private DocusealApiService docusealApiService;

    @Scheduled(cron = "0 0 * * * *")
    public void checkDocusealStatus() {
        List<Contract> contracts = repository.findByStatusIn(List.of("sent", "in_progress"));
        for (Contract contract : contracts) {
            DocuSeal submission = docusealApiService.getSubmission(contract.getId());
            if ("completed".equals(submission.getStatus())) {
                // cập nhật ngày ký từ Docuseal
                LocalDateTime dateSign = LocalDateTime.parse(submission.getCompletedAt());

                // gọi confirmContract để xử lý lương và lưu
                service.confirm(contract.getId(), dateSign);
            } else if ("declined".equals(submission.getStatus())) {
                contract.setStatus("declined");
                repository.save(contract);
            }
        }
    }

}
