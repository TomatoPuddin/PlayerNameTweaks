package tomatopuddin.playernametweaks;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

@Mod(PlayerNameTweaks.MODID)
@EventBusSubscriber
public class PlayerNameTweaksNeoForge {
    public PlayerNameTweaksNeoForge() {
        PlayerNameTweaks.init();
    }

    @SubscribeEvent
    public static void onCommandRegistering(RegisterCommandsEvent event) {
        PlayerNameTweaks.registerCommand(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onServerStaring(ServerStartedEvent event) {
        PlayerNameTweaks.serverStarted(event.getServer());
    }
}
