package com.jotaefi.crud.service;

import com.jotaefi.crud.dto.response.AdminProfileResponseDTO;
import com.jotaefi.crud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public AdminProfileResponseDTO getMyProfile(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Period period = Period.between(user.getRegistrationDate().toLocalDate(), LocalDate.now());
        String timeAtCompany = period.getYears() + " anos, " + period.getMonths() + " meses e " + period.getDays() + " dias";

        return new AdminProfileResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRegistrationDate(),
                timeAtCompany
        );
    }
}
