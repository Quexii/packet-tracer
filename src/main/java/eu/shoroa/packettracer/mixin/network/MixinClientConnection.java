package eu.shoroa.packettracer.mixin.network;

import eu.shoroa.packettracer.event.NetworkIncomingCallback;
import eu.shoroa.packettracer.event.NetworkOutgoingCallback;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class MixinClientConnection {
    @Inject(method = "handlePacket", at = @At("HEAD"), cancellable = true)
    private static <T extends PacketListener> void handlePacketIncoming(Packet<T> packet, PacketListener listener, CallbackInfo ci) {
        ActionResult result = NetworkIncomingCallback.EVENT.invoker().call(packet, listener);

        if(result == ActionResult.FAIL) {
            ci.cancel();
        }
    }

    @Inject(method = "send(Lnet/minecraft/network/packet/Packet;)V", at = @At("HEAD"), cancellable = true)
    public void handlePacketOutgoing(Packet<?> packet, CallbackInfo ci) {
        ActionResult result = NetworkOutgoingCallback.EVENT.invoker().call(packet);

        if(result == ActionResult.FAIL) {
            ci.cancel();
        }
    }
}
