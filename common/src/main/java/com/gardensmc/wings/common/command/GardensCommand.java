package com.gardensmc.wings.common.command;

import com.gardensmc.wings.common.command.exception.NoPermissionException;
import com.gardensmc.wings.common.player.GardensPlayer;
import lombok.Getter;

@Getter
public abstract class GardensCommand {
    
    private final String name;
    protected final String permissionNode;

    public GardensCommand(String name, String permission) {
        this.name = name;
        this.permissionNode = permission;
        Commands.addCommandToRegistry(this);
    }

    public void execute(GardensPlayer executor, String[] args) {
        if (!executor.hasPermission(permissionNode)) {
            throw new NoPermissionException(permissionNode);
        }
    }

}
