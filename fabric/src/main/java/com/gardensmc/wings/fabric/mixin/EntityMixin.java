package com.gardensmc.wings.fabric.mixin;

import com.gardensmc.wings.common.listener.PlayerSneakJumpListener;
import com.gardensmc.wings.common.listener.PlayerSneakListener;
import com.gardensmc.wings.fabric.player.FabricPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.entity.EntityLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityLike {

    @Shadow private World world;

    @Inject(at = @At("TAIL"), method = "setSneaking")
    public void onPlayerSneak(boolean sneaking, CallbackInfo ci) {
        if (!isPlayer()) return;
        // not sure why I can't cast this directly to a player, but this works and is inexpensive
        MinecraftServer server = world.getServer();
        ServerPlayerEntity serverPlayer = server == null ? null : server.getPlayerManager().getPlayer(getUuid());
        if (serverPlayer != null && sneaking) { // this fires on both crouch and un-crouch, let's just call on crouch
            new PlayerSneakListener(new FabricPlayer(serverPlayer)).callListener();
        }
    }

    @Inject(at = @At("TAIL"), method = "move")
    public void onEntityMove(MovementType movementType, Vec3d movement, CallbackInfo ci) {
        if (!isPlayer()) return;
        if (movementType == MovementType.SELF && movement.y > 0) { // potential jump movement
            double velocity = Math.round(movement.getY() * 100d) / 100d;
            if (velocity == 0.42) { // jump velocity
                MinecraftServer server = world.getServer();
                ServerPlayerEntity serverPlayer = server == null ? null : server.getPlayerManager().getPlayer(getUuid());
                if (serverPlayer != null && serverPlayer.isSneaking()) {
                    new PlayerSneakJumpListener(new FabricPlayer(serverPlayer)).callListener();
                }
            }
        }
    }
}

