package tomatopuddin.playernametweaks;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(PlayerNameTweaks.MODID)
@Mod.EventBusSubscriber
public class PlayerNameTweaksForge {
    public PlayerNameTweaksForge() {
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
