package com.jotaefi.crud.service;

import com.jotaefi.crud.dto.response.AdminProfileResponseDTO;
import com.jotaefi.crud.dto.response.CardSummaryDTO;
import com.jotaefi.crud.dto.response.EmployeeProfileResponseDTO;
import com.jotaefi.crud.dto.response.ProfileResponse;
import com.jotaefi.crud.entity.EmployeeEntity;
import com.jotaefi.crud.entity.UsersEntity;
import com.jotaefi.crud.exception.NotFoundException;
import com.jotaefi.crud.repository.EmployeeRepository;
import com.jotaefi.crud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    public ProfileResponse getMyProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        UsersEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        Period period = Period.between(user.getRegistrationDate().toLocalDate(), LocalDate.now());
        String timeAtCompany = period.getYears() + " anos, " + period.getMonths() + " meses e " + period.getDays() + " dias";

        if (isAdmin) {
            return new AdminProfileResponseDTO(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getRegistrationDate(),
                    timeAtCompany
            );
        }

        EmployeeEntity employee = employeeRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NotFoundException("Dados do funcionário não encontrado"));

        CardSummaryDTO cardSummary = new CardSummaryDTO(
                employee.getCard().getId(),
                employee.getCard().getSalary(),
                employee.getCard().getFoodAid(),
                employee.getCard().getTransportationAid(),
                employee.getCard().getHealthAssistence()
        );

        return new EmployeeProfileResponseDTO(
                employee.getId(),
                user.getName(),
                user.getEmail(),
                user.getRegistrationDate(),
                timeAtCompany,
                employee.getDepartment().getName(),
                employee.getStatus().name(),
                cardSummary
        );
    }
}
