package com.hadroncfy.log4j2_3201_fix.mixin;

import com.hadroncfy.log4j2_3201_fix.Util;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.MinecraftServer;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {
    @Inject(method = "run", at = @At(
        value = "INVOKE_ASSIGN",
        target = "Lnet/minecraft/server/MinecraftServer;setupServer()Z"
    ))
    private void onRun(CallbackInfo ci){
        Util.disableLogLookups();
    }
}
