package eu.shoroa.packettracer.mixin.client;

import eu.shoroa.packettracer.ui.UI;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Inject(at = @At("TAIL"), method = "<init>")
    private void init(CallbackInfo info) {
        UI.INSTANCE.init();
    }
}