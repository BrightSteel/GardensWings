package com.gardensmc.wings.fabric.mixin;

import com.gardensmc.wings.common.listener.PlayerGlideListener;
import com.gardensmc.wings.fabric.player.FabricPlayer;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "startFallFlying", cancellable = true)
    public void onStartGliding(CallbackInfo callbackInfo) {
        ServerPlayerEntity serverPlayer = getServer() == null ? null : getServer().getPlayerManager().getPlayer(uuid);
        if (serverPlayer != null) {
            PlayerGlideListener glideListener = new PlayerGlideListener(new FabricPlayer(serverPlayer));
            glideListener.callListener();
            if (glideListener.isShouldCancel()) {
                callbackInfo.cancel();
            }
        }
    }
}
