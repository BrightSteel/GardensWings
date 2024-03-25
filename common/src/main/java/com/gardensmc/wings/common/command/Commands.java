package com.gardensmc.wings.common.command;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused") // registered commands look unused unless they're called elsewhere
public class Commands {

    @Getter
    private static final Set<GardensCommand> commands = new HashSet<>();

    private static final GardensCommand wingsCommand = new WingsCommand();

    public static void addCommandToRegistry(GardensCommand command) {
        commands.add(command);
    }
}
