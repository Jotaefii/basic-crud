package com.jotaefi.crud.service;

import com.jotaefi.crud.dto.request.CardUpdateDTO;
import com.jotaefi.crud.dto.response.CardResponseDTO;
import com.jotaefi.crud.entity.CardsEntity;
import com.jotaefi.crud.exception.NotFoundException;
import com.jotaefi.crud.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    @Transactional(readOnly = true)
    public CardResponseDTO findCardByEmployeeId(Long employeeId) {
        CardsEntity card = cardRepository.findByEmployee_Id(employeeId)
                .orElseThrow(() -> new NotFoundException("Funcionário não encontrado"));

        return new CardResponseDTO(
                card.getId(),
                card.getEmployee().getUser().getName(),
                card.getSalary(),
                card.getFoodAid(),
                card.getTransportationAid(),
                card.getHealthAssistence()
        );
    }

    @Transactional
    public CardResponseDTO updateCard(CardUpdateDTO request, Long employeeId) {
        CardsEntity card = cardRepository.findByEmployee_Id(employeeId)
                .orElseThrow(() -> new NotFoundException("Funcionário não encontrado"));

        if (request.salary() != null) {
            card.setSalary(request.salary());
        }

        if (request.food() != null) {
            card.setFoodAid(request.food());
        }

        if (request.transport() != null) {
            card.setTransportationAid(request.transport());
        }

        if (request.health() != null) {
            card.setHealthAssistence(request.health());
        }

        cardRepository.save(card);

        return new CardResponseDTO(
                card.getId(),
                card.getEmployee().getUser().getName(),
                card.getSalary(),
                card.getFoodAid(),
                card.getTransportationAid(),
                card.getHealthAssistence()
        );
    }
}
