package com.gardensmc.wings.common.command.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PlayerNotFoundException extends IllegalArgumentException {
    private String username;
}
