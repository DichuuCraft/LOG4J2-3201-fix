package com.hadroncfy.log4j2_3201_fix.mixin;

import com.hadroncfy.log4j2_3201_fix.Util;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.main.Main;

@Mixin(Main.class)
public class MixinMain {
    @Inject(method = "main", at = @At("HEAD"))
    private static void onStart(String[] args, CallbackInfo ci) {
        Util.disableLogLookups();
    }
}
