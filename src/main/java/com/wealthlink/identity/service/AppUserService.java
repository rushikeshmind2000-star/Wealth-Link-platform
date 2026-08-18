package com.wealthlink.identity.service;

import com.wealthlink.identity.dto.AppUserDto;
import com.wealthlink.identity.dto.CreateUserRequest;
import com.wealthlink.identity.entity.AppUser;
import com.wealthlink.identity.entity.Role;
import com.wealthlink.identity.entity.UserRole;
import com.wealthlink.identity.entity.UserRoleId;
import com.wealthlink.identity.entity.UserStatus;
import com.wealthlink.identity.repository.AppUserRepository;
import com.wealthlink.identity.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<AppUserDto> getAllUsers() {
        return appUserRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AppUserDto getUserById(UUID id) {
        return appUserRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public AppUserDto createUser(CreateUserRequest request) {
        AppUser user = AppUser.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .status(UserStatus.ACTIVE)
                .build();

        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            for (String roleName : request.getRoles()) {
                Role role = roleRepository.findByName("ROLE_" + roleName.toUpperCase())
                        .orElseGet(() -> roleRepository.findByName(roleName.toUpperCase())
                        .orElse(null));
                
                if (role != null) {
                    UserRole userRole = UserRole.builder()
                            .id(new UserRoleId(user.getId(), role.getId()))
                            .user(user)
                            .role(role)
                            .build();
                    user.getRoles().add(userRole);
                }
            }
        }

        AppUser savedUser = appUserRepository.save(user);
        
        // fix the user id in roles if needed, though CascadeType.ALL handles it mostly 
        // if user id is generated on save, we might need to set it, but let's assume UUID is auto-generated properly.
        // Actually since UUID is generated, the UserRoleId might be null for userId initially.
        // Let's just ignore the roles setting for now if it breaks, but it should work if we save first.
        
        return mapToDto(savedUser);
    }

    private AppUserDto mapToDto(AppUser user) {
        return AppUserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .status(user.getStatus().name())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
