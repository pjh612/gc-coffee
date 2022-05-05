package com.kdt.hairsalon.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@EqualsAndHashCode
public class Designer {
    private final UUID id;
    private final String name;
    private Position position;
    private final LocalDateTime joinedAt;

    public Designer(UUID id, String name, Position position, LocalDateTime joinedAt) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.joinedAt = joinedAt.withNano(0);
    }
}
