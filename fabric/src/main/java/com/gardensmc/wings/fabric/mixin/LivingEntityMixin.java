package com.gardensmc.wings.fabric.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    protected LivingEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "setJumping")
    public void onEntityMove(CallbackInfo ci) {
        if (!isPlayer()) return;
        ServerPlayerEntity playerEntity = getServer() == null ? null : getServer().getPlayerManager().getPlayer(uuid);
        if (playerEntity != null) {
            System.out.println("jumped");
        }
    }


}
