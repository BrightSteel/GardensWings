package com.gardensmc.wings.fabric.mixin;

import com.gardensmc.wings.common.listener.PlayerSneakJumpListener;
import com.gardensmc.wings.common.listener.PlayerSneakListener;
import com.gardensmc.wings.fabric.player.FabricPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.entity.EntityLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(at = @At("TAIL"), method = "setSneaking")
    public void onPlayerSneak(boolean sneaking, CallbackInfo ci) {
        Entity entity = (Entity) ((Object) this);
        if (entity instanceof ServerPlayerEntity serverPlayer && sneaking) { // this fires on both crouch and un-crouch, let's just call on crouch
            new PlayerSneakListener(new FabricPlayer(serverPlayer)).callListener();
        }
    }

    @Inject(at = @At("TAIL"), method = "move")
    public void onEntityMove(MovementType movementType, Vec3d movement, CallbackInfo ci) {
        Entity entity = (Entity) ((Object) this);
        if (entity instanceof ServerPlayerEntity serverPlayer && serverPlayer.isSneaking()) {
            if (movementType == MovementType.SELF && movement.y > 0) { // potential jump movement
                double velocity = Math.round(movement.getY() * 100d) / 100d;
                if (velocity == 0.42) { // jump velocity
                    new PlayerSneakJumpListener(new FabricPlayer(serverPlayer)).callListener();
                }
            }
        }
    }
}

