package com.gardensmc.wings.fabric.mixin;

import com.gardensmc.wings.common.listener.PlayerGlideListener;
import com.gardensmc.wings.fabric.player.FabricPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin  {

    @Inject(at = @At("HEAD"), method = "startFallFlying", cancellable = true)
    public void onStartGliding(CallbackInfo callbackInfo) {
        PlayerEntity playerEntity = (PlayerEntity) ((Object) this);
        if (playerEntity instanceof ServerPlayerEntity serverPlayer) {
            PlayerGlideListener glideListener = new PlayerGlideListener(new FabricPlayer(serverPlayer));
            glideListener.callListener();
            if (glideListener.isShouldCancel()) {
                callbackInfo.cancel();
            }
        }
    }
}
