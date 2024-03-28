package com.gardensmc.wings.fabric.mixin;

import com.gardensmc.wings.common.listener.PlayerElytraCollisionListener;
import com.gardensmc.wings.common.listener.PlayerFallDamageListener;
import com.gardensmc.wings.common.listener.type.Listener;
import com.gardensmc.wings.fabric.player.FabricPlayer;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(at = @At("HEAD"), method = "onDamage", cancellable = true)
    public void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> ci) {
        // this should be a valid cast...
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
