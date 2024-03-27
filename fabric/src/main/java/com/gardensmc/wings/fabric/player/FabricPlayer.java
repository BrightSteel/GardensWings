package com.gardensmc.wings.fabric.player;

import com.gardensmc.wings.common.player.GardensPlayer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class FabricPlayer extends GardensPlayer {

    private final ServerPlayerEntity serverPlayer;

    public FabricPlayer(ServerPlayerEntity serverPlayer) {
        super(serverPlayer.getUuid(), serverPlayer.getGameProfile().getName());
        this.serverPlayer = serverPlayer;
    }

    @Override
    public boolean isGliding() {
        return false;
    }

    @Override
    public void setGliding(boolean isGliding) {

    }

    @Override
    public boolean hasWingsEquipped() {
        return false;
    }

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }

    @Override
    public void spawnParticle(int count) {

    }

    @Override
    public void playSound() {

    }

    @Override
    public void setVelocityMultiplier(double multiplier) {

    }

    @Override
    public boolean isChestplateSlotEmpty() {
        return false;
    }

    @Override
    public void addWingsToChestplateSlot() {

    }

    @Override
    public void sendMessage(String message) {
        serverPlayer.sendMessage(Text.of(message));
    }

    @Override
    public float getPitch() {
        return 0;
    }
}
