package eu.shoroa.packettracer.client;

import eu.shoroa.packettracer.event.NetworkIncomingCallback;
import eu.shoroa.packettracer.event.NetworkOutgoingCallback;
import eu.shoroa.packettracer.hud.Handler;
import eu.shoroa.packettracer.viewer.Viewer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class PacketTracerClient implements ClientModInitializer {
    public static KeyBinding keyStartRecording;
    public static KeyBinding keyStopRecording;
    public static KeyBinding keyOpenGui;

    @Override
    public void onInitializeClient() {
        keyStartRecording = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.packettracer.startrecording",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                "category.packettracer.packetcontrol"
        ));
        keyStopRecording = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.packettracer.stoptrecording",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                "category.packettracer.packetcontrol"
        ));
        keyOpenGui = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.packettracer.opengui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_J,
                "category.packettracer.packetcontrol"
        ));

        Viewer.Companion.init();

        ClientTickEvents.END_CLIENT_TICK.register(Handler.INSTANCE::tick);
        HudRenderCallback.EVENT.register(Handler.INSTANCE::render);
        NetworkIncomingCallback.EVENT.register(Handler.INSTANCE::packetIN);
        NetworkOutgoingCallback.EVENT.register(Handler.INSTANCE::packetOUT);
    }
}
