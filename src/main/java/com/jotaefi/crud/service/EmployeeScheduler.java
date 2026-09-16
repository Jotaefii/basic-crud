package com.jotaefi.crud.service;

import com.jotaefi.crud.entity.EmployeeEntity;
import com.jotaefi.crud.enums.EmployeeStatus;
import com.jotaefi.crud.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeScheduler {

    private final EmployeeRepository employeeRepository;

    @Scheduled(fixedRate = 300000)
    public void checkEmployee(){
        checkVacations();
    }

    @Transactional
    private void checkVacations() {
        LocalDateTime limit = LocalDateTime.now().minusMinutes(5);

        List<EmployeeEntity> employees = employeeRepository.findByStatusAndStatusChangeBefore(EmployeeStatus.FERIAS, limit);

        for (EmployeeEntity employee : employees) {
            employee.setStatus(EmployeeStatus.ATIVO);
            employee.setStatusChange(null);
        }

        employeeRepository.saveAll(employees);
    }
}
