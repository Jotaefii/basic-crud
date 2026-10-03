package com.jotaefi.crud.service;

import com.jotaefi.crud.entity.EmployeeEntity;
import com.jotaefi.crud.entity.UserStatusHistory;
import com.jotaefi.crud.enums.UserStatus;
import com.jotaefi.crud.repository.EmployeeRepository;
import com.jotaefi.crud.repository.UserStatusHistoryRepository;
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
    private final UserStatusHistoryRepository userStatusHistoryRepository;

    @Scheduled(fixedRate = 300000)
    public void checkEmployee(){
        checkVacations();
    }

    @Transactional
    private void checkVacations() {
        LocalDateTime limit = LocalDateTime.now().minusMinutes(5);

        List<UserStatusHistory> histories = userStatusHistoryRepository.findByNewStatusAndChangeAtBefore(UserStatus.FERIAS, limit);

        for (UserStatusHistory h : histories) {
            EmployeeEntity employee = h.getEmployee();

            UserStatusHistory lastHistory = userStatusHistoryRepository.findTopByUserIdOrderByChangeAtDesc(employee.getId())
                    .orElse(null);

            if (lastHistory != null
                    && lastHistory.getNewStatus() == UserStatus.FERIAS
                    && lastHistory.getChangeAt().isBefore(limit)) {

                UserStatusHistory history = new UserStatusHistory(
                        null,
                        UserStatus.FERIAS,
                        UserStatus.ATIVO,
                        LocalDateTime.now(),
                        employee
                );

                userStatusHistoryRepository.save(history);
                employee.setStatus(UserStatus.ATIVO);
            }
            employeeRepository.save(employee);
        }
    }
}
