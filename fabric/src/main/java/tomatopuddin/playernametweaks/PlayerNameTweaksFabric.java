package tomatopuddin.playernametweaks;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class PlayerNameTweaksFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        PlayerNameTweaks.init();
        ServerLifecycleEvents.SERVER_STARTED.register(PlayerNameTweaks::serverStarted);
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> PlayerNameTweaks.registerCommand(dispatcher));
    }
}
