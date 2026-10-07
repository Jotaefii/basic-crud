package com.jotaefi.crud.service;

import com.jotaefi.crud.dto.request.EmployeeCreateDTO;
import com.jotaefi.crud.dto.request.EmployeeUpdateDTO;
import com.jotaefi.crud.dto.response.EmployeeProfileResponseDTO;
import com.jotaefi.crud.dto.response.EmployeeResponseDTO;
import com.jotaefi.crud.entity.*;
import com.jotaefi.crud.enums.UserStatus;
import com.jotaefi.crud.exception.BadRequestException;
import com.jotaefi.crud.exception.EmployeeAlreadyTurnedOffException;
import com.jotaefi.crud.exception.NotFoundException;
import com.jotaefi.crud.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final RolesRepository rolesRepository;
    private final CardRepository cardRepository;
    private final DepartmentRepository departmentRepository;
    private final UserStatusHistoryRepository userStatusHistoryRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public EmployeeResponseDTO createEmployee(EmployeeCreateDTO request) {

        DepartmentEntity department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new NotFoundException("Departamento não encontrado"));

        if (userRepository.existsByEmail(request.email())) {
            throw new BadRequestException("Email já cadastrado");
        }

        RolesEntity role = rolesRepository.findByName("ROLE_EMPLOYEE")
                .orElseThrow(() -> new NotFoundException("Permissão não encontrada"));

        UsersEntity user = UsersEntity.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .roles(new HashSet<>(Set.of(role)))
                .build();

        userRepository.saveAndFlush(user);

        EmployeeEntity employee = EmployeeEntity.builder()
                .user(user)
                .status(UserStatus.ATIVO)
                .department(department)
                .build();

        employeeRepository.save(employee);

        CardsEntity card = CardsEntity.builder()
                .salary(request.salary())
                .employee(employee)
                .build();

        cardRepository.save(card);

        employee.setCard(card);
        user.setEmployee(employee);

        return toResponse(employee);
    }

    @Transactional(readOnly = true)
    public Page<EmployeeResponseDTO> findAll(Pageable pageable) {

        List<UserStatus> statuses = List.of(UserStatus.ATIVO, UserStatus.FERIAS);

        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.ASC, "user.name")
        );

        return employeeRepository.findByStatusIn(statuses, sortedPageable)
                .map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public EmployeeResponseDTO findEmployeeById(Long employeeId) {

        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Funcionário não encontrado"));

        return toResponse(employee);
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponseDTO> findByDepartmentId(Long departmentId) {

        departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NotFoundException("Departamento não encontrado"));

        List<UserStatus> statuses = List.of(UserStatus.ATIVO, UserStatus.FERIAS);

        return employeeRepository.findByDepartmentIdAndStatusIn(departmentId, statuses, Sort.by("user.name"))
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public EmployeeResponseDTO updateEmployee(EmployeeUpdateDTO request, Long employeeId) {
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Funcionário não encontrado"));

        if (employee.getStatus() == UserStatus.DESLIGADO) {
            if (request.status() == UserStatus.ATIVO) {

                UserStatusHistory history = new UserStatusHistory(
                        null,
                        employee.getStatus(),
                        UserStatus.ATIVO,
                        LocalDateTime.now(),
                        employee
                );

                userStatusHistoryRepository.save(history);

                employee.setStatus(UserStatus.ATIVO);

                employeeRepository.save(employee);

                return toResponse(employee);

            } else {
                throw new EmployeeAlreadyTurnedOffException("Você não pode alterar valores de um funcionário desligado até ele ser ativo novamente.");
            }
        }

        UsersEntity user = employee.getUser();

        if (request.name() != null) {
            user.setName(request.name());
        }

        if (request.email() != null && !request.email().equals(user.getEmail())) {
            if (userRepository.existsByEmail(request.email())) {
                throw new BadRequestException("Email já cadastrado");
            }
            user.setEmail(request.email());
        }

        if (request.salary() != null) {
            employee.getCard().setSalary(request.salary());
        }

        if (request.departmentId() != null) {
            DepartmentEntity department = departmentRepository.findById(request.departmentId())
                    .orElseThrow(() -> new NotFoundException("Departamento não encontrado"));

            employee.setDepartment(department);
        }

        if (request.status() != null && request.status() != employee.getStatus()) {
            UserStatus oldStatus = employee.getStatus();
            UserStatus newStatus = request.status();

            UserStatusHistory history = new UserStatusHistory();

            history.setOldStatus(oldStatus);
            history.setNewStatus(newStatus);
            history.setChangeAt(LocalDateTime.now());
            history.setEmployee(employee);

            userStatusHistoryRepository.save(history);

            employee.setStatus(newStatus);
        }

        employeeRepository.save(employee);

        return toResponse(employee);
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponseDTO> findAllByStatus (UserStatus status) {
        return employeeRepository.findByStatus(status, Sort.by("user.name")).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponseDTO> findByName(String employeeName) {
        List<UserStatus> statuses = List.of(UserStatus.ATIVO, UserStatus.FERIAS);

        return employeeRepository.findByUserNameContainingIgnoreCaseAndStatusIn(employeeName, statuses, Sort.by("user.name"))
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public EmployeeProfileResponseDTO getMyProfile(String email) {

        UsersEntity user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new NotFoundException("Usuário não encontrado"));

        EmployeeEntity employee = employeeRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new NotFoundException("Funcionário não encontrado"));

        return toProfileResponse(employee);
    }

    private EmployeeProfileResponseDTO toProfileResponse(EmployeeEntity employee) {

        UsersEntity user = employee.getUser();
        CardsEntity card = employee.getCard();

        Period period = Period.between(user.getRegistrationDate().toLocalDate(), LocalDate.now());
        String timeAtCompany = period.getYears() + " anos, " + period.getMonths() + " meses e " + period.getDays() + " dias";

        return new EmployeeProfileResponseDTO(
                employee.getId(),
                user.getName(),
                user.getEmail(),
                user.getRegistrationDate(),
                timeAtCompany,
                employee.getDepartment().getName(),
                employee.getStatus().name(),
                card.getSalary()
        );
    }


    private EmployeeResponseDTO toResponse(EmployeeEntity employee) {

        UsersEntity user = employee.getUser();
        CardsEntity card = employee.getCard();

        return EmployeeResponseDTO.builder()
                .id(employee.getId())
                .name(user.getName())
                .email(user.getEmail())
                .cardId(card.getId())
                .departmentName(employee.getDepartment().getName())
                .status(employee.getStatus())
                .registrationDate(user.getRegistrationDate())
                .build();
    }
}
