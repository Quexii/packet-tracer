package eu.shoroa.packettracer.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ActionResult;

public interface NetworkOutgoingCallback {
    Event<NetworkOutgoingCallback> EVENT = EventFactory.createArrayBacked(NetworkOutgoingCallback.class,
        (l) -> (packet) -> {
            for (NetworkOutgoingCallback listener : l) {
                ActionResult result = listener.call(packet);

                if(result != ActionResult.PASS) {
                    return result;
                }
            }

        return ActionResult.PASS;
    });

    ActionResult call(Packet<?> packet);
}