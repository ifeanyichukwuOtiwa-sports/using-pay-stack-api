package io.wtech.usingpaystack.service.api;

import io.wtech.usingpaystack.model.AppUser;

import java.util.UUID;

public interface AppUserService {
    AppUser getUserById(UUID userUuid);
}
