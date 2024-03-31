package com.gardensmc.wings.fabric.player;

import com.gardensmc.wings.common.GardensWings;
import com.gardensmc.wings.common.particle.GardensParticle;
import com.gardensmc.wings.common.player.GardensPlayer;
import com.gardensmc.wings.common.util.ChatUtil;
import com.gardensmc.wings.fabric.particle.ParticleAdapter;
import com.gardensmc.wings.fabric.wings.WingsFactory;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;

public class FabricPlayer extends GardensPlayer {

    private final int CHEST_PLATE_SLOT = 2;
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
        ItemStack chestPlate = serverPlayer.getInventory().armor.get(CHEST_PLATE_SLOT);
        if (chestPlate.getItem() != Items.ELYTRA) {
            return false;
        }
        NbtCompound nbt = chestPlate.getNbt();
        return nbt != null && nbt.contains(GardensWings.WINGS_IDENTIFIER);
    }

    @Override
    public boolean hasPermission(String permission) {
        return serverPlayer.hasPermissionLevel(4) || Permissions.check(serverPlayer, permission);
    }

    @Override
    public void spawnParticle(GardensParticle gardensParticle) {
        serverPlayer.getServerWorld().spawnParticles(
                ParticleAdapter.adaptParticle(gardensParticle.getParticleType()),
                serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(),
                gardensParticle.getCount(),
                0, 0, 0,
                gardensParticle.getSpeed()
        );
    }

    @Override
    public void playSound(float volume) {
        serverPlayer.getServerWorld().playSound(
                null,
                serverPlayer.getBlockPos(),
                SoundEvents.ENTITY_DRAGON_FIREBALL_EXPLODE,
                SoundCategory.PLAYERS,
                volume,
                1.0f
        );
    }

    @Override
    public void setVelocityMultiplier(double multiplier) {
        serverPlayer.setVelocity(getDirection().normalize().multiply(multiplier));
        serverPlayer.velocityModified = true; // sync to client
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
        return serverPlayer.getInventory().armor.get(CHEST_PLATE_SLOT) == ItemStack.EMPTY;
    }

    @Override
    public void addWingsToChestplateSlot() {
        serverPlayer.getInventory().armor.set(CHEST_PLATE_SLOT, WingsFactory.createWings());
    }

    @Override
    public void addWingsToInventory() {
        serverPlayer.getInventory().offerOrDrop(WingsFactory.createWings());
    }

    @Override
    public void sendMessage(String message) {
        serverPlayer.sendMessage(ChatUtil.translateMiniMessage(message));
    }

    @Override
    public float getPitch() {
        return serverPlayer.getPitch();
    }
}
