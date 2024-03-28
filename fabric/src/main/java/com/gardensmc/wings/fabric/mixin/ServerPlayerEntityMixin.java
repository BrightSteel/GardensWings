package com.gardensmc.wings.fabric.mixin;

import com.gardensmc.wings.common.listener.PlayerElytraCollisionListener;
import com.gardensmc.wings.common.listener.PlayerFallDamageListener;
import com.gardensmc.wings.common.listener.type.Listener;
import com.gardensmc.wings.fabric.player.FabricPlayer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
@SuppressWarnings({"DataFlowIssue", "unused"})
public abstract class ServerPlayerEntityMixin {

    @Inject(at = @At("HEAD"), method = "damage", cancellable = true)
    public void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> ci) {
        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) ((Object) this);
        source.getTypeRegistryEntry().getKey().ifPresent(damageTypeRegistryKey -> {
            FabricPlayer fabricPlayer = new FabricPlayer(serverPlayer);
            Listener listener;
            if (damageTypeRegistryKey == DamageTypes.FALL) {
                listener = new PlayerFallDamageListener(fabricPlayer);
            } else if (damageTypeRegistryKey == DamageTypes.FLY_INTO_WALL) {
                listener = new PlayerElytraCollisionListener(fabricPlayer);
            } else {
                return;
            }
            listener.callListener();
            if (listener.isShouldCancel()) {
                ci.setReturnValue(false);
            }
        });
    }
}
