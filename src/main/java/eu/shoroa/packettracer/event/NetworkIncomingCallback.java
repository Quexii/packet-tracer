package eu.shoroa.packettracer.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ActionResult;

public interface NetworkIncomingCallback {
    Event<NetworkIncomingCallback> EVENT = EventFactory.createArrayBacked(NetworkIncomingCallback.class,
        (l) -> (packet, packetListener) -> {
            for (NetworkIncomingCallback listener : l) {
                ActionResult result = listener.call(packet, packetListener);

                if(result != ActionResult.PASS) {
                    return result;
                }
            }

        return ActionResult.PASS;
    });

    ActionResult call(Packet<?> packet, PacketListener listener);
}