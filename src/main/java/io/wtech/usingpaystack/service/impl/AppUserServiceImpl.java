package io.wtech.usingpaystack.service.impl;

import io.wtech.usingpaystack.model.AppUser;
import io.wtech.usingpaystack.repository.AppUserRepository;
import io.wtech.usingpaystack.service.api.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;
    @Override
    public AppUser getUserById(final UUID userUuid) {
        return appUserRepository.findById(userUuid).orElseThrow();
    }

    public AppUser save(final AppUser user) {
        return appUserRepository.save(user);
    }
}
