package com.kdt.hairsalon.controller.api.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateCustomerRequest {
    UUID id;

    String comment;

    public UpdateCustomerRequest(UUID id, String comment) {
        this.id = id;
        this.comment = comment;
    }
}
