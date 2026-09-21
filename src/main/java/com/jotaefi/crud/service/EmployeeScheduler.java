package com.jotaefi.crud.service;

import com.jotaefi.crud.entity.EmployeeEntity;
import com.jotaefi.crud.entity.EmployeeStatusHistory;
import com.jotaefi.crud.enums.EmployeeStatus;
import com.jotaefi.crud.repository.EmployeeRepository;
import com.jotaefi.crud.repository.EmployeeStatusHistoryRepository;
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
    private final EmployeeStatusHistoryRepository employeeStatusHistoryRepository;

    @Scheduled(fixedRate = 300000)
    public void checkEmployee(){
        checkVacations();
    }

    @Transactional
    private void checkVacations() {
        LocalDateTime limit = LocalDateTime.now().minusMinutes(5);

        List<EmployeeStatusHistory> histories = employeeStatusHistoryRepository.findByNewStatusAndChangeAtBefore(EmployeeStatus.FERIAS, limit);

        for (EmployeeStatusHistory h : histories) {
            EmployeeEntity employee = h.getEmployee();

            EmployeeStatusHistory lastHistory = employeeStatusHistoryRepository.findTopByEmployeeIdOrderByChangeAtDesc(employee.getId())
                    .orElse(null);

            if (lastHistory != null
                    && lastHistory.getNewStatus() == EmployeeStatus.FERIAS
                    && lastHistory.getChangeAt().isBefore(limit)) {

                EmployeeStatusHistory history = new EmployeeStatusHistory(
                        null,
                        EmployeeStatus.FERIAS,
                        EmployeeStatus.ATIVO,
                        LocalDateTime.now(),
                        employee
                );

                employeeStatusHistoryRepository.save(history);
                employee.setStatus(EmployeeStatus.ATIVO);
            }
            employeeRepository.save(employee);
        }
    }
}
