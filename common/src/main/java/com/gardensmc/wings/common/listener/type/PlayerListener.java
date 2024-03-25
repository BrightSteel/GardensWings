package com.gardensmc.wings.common.listener.type;

import com.gardensmc.wings.common.player.GardensPlayer;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class PlayerListener implements Listener {

    protected final GardensPlayer player;
    public boolean shouldCancel;

    protected PlayerListener(GardensPlayer player) {
        this.player = player;
    }
}
