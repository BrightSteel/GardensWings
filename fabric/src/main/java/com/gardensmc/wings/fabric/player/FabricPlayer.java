package com.gardensmc.wings.fabric.player;

import com.gardensmc.wings.common.player.GardensPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class FabricPlayer extends GardensPlayer {

    private final ServerPlayerEntity serverPlayer;

    public FabricPlayer(ServerPlayerEntity serverPlayer) {
        super(serverPlayer.getUuid(), serverPlayer.getGameProfile().getName());
        this.serverPlayer = serverPlayer;
    }

    @Override
    public boolean isGliding() {
        return serverPlayer.isFallFlying();
    }

    @Override
    public void setGliding(boolean isGliding) {
        if (isGliding) {
            serverPlayer.startFallFlying();
        } else {
            serverPlayer.stopFallFlying();
        }
    }

    @Override
    public boolean hasWingsEquipped() {
        // todo make this actually check for wings
        return serverPlayer.getInventory().armor.get(2).getItem() == Items.ELYTRA;
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
        System.out.println("Doing");
        serverPlayer.setVelocity(getDirection().normalize().multiply(multiplier));
        serverPlayer.velocityModified = true;
    }

    // copied from spigot
    // todo - move to util
    public Vec3d getDirection() {

        double rotX = serverPlayer.getYaw();
        double rotY = serverPlayer.getPitch();

        double y = (-Math.sin(Math.toRadians(rotY)));

        double xz = Math.cos(Math.toRadians(rotY));

        double x = (-xz * Math.sin(Math.toRadians(rotX)));
        double z = (xz * Math.cos(Math.toRadians(rotX)));

        return new Vec3d(x, y, z);
    }

    @Override
    public boolean isChestplateSlotEmpty() {
        return serverPlayer.getInventory().armor.get(2) == ItemStack.EMPTY;
    }

    @Override
    public void addWingsToChestplateSlot() {
        // todo actually make wings
        ItemStack wings = new ItemStack(Items.ELYTRA, 1);
        serverPlayer.getInventory().armor.set(2, wings);
    }

    @Override
    public void sendMessage(String message) {
        serverPlayer.sendMessage(Text.of(message));
    }

    @Override
    public float getPitch() {
        return serverPlayer.getPitch();
    }
}
