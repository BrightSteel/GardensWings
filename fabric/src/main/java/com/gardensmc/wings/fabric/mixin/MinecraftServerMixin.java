package com.gardensmc.wings.fabric.mixin;

import com.gardensmc.wings.fabric.schedule.FabricScheduler;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@SuppressWarnings("unused")
@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

    @Inject(at = @At("HEAD"), method = "tick")
    public void onTick(BooleanSupplier shouldKeepTicking, CallbackInfo callbackInfo) {
        FabricScheduler.INSTANCE.tick();
    }
}
