package tomatopuddin.playernametweaks;

import com.mojang.brigadier.CommandDispatcher;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class PlayerNameTweaks {
    public static final String MODID = "playernametweaks";
    public static final Logger LOGGER = LogManager.getLogger(PlayerNameTweaks.class);

    private static ModConfigWrapper config;
    private static MinecraftServer server;

    public static void init() {
        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
    }

    public static void serverStarted(MinecraftServer server) {
        PlayerNameTweaks.server = server;
        PlayerNameTweaks.config = new ModConfigWrapper(AutoConfig.getConfigHolder(ModConfig.class).getConfig(), server.isDedicatedServer());
    }

    public static void registerCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("playernametweaks")
                .requires(source -> source.hasPermission(4))
                .then(Commands.literal("reload")
                        .executes(context -> {
                            var holder = AutoConfig.getConfigHolder(ModConfig.class);
                            if (!holder.load()) {
                                context.getSource().sendFailure(new TextComponent("Failed to reload config.").withStyle(ChatFormatting.RED));
                                return 1;
                            }

                            config = new ModConfigWrapper(holder.getConfig(), server.isDedicatedServer());
                            context.getSource().sendSuccess(new TextComponent("Successfully reloaded config.").withStyle(ChatFormatting.GREEN), false);
                            checkPlayers();

                            return 0;
                        })));
    }

    public static ModConfigWrapper getConfig() {
        return config;
    }

    private static void checkPlayers() {
        for (ServerPlayer player : List.copyOf(server.getPlayerList().getPlayers())) {
            config.checkPlayerName(player.getGameProfile().getId(), player.getGameProfile().getName(),
                    hint -> player.connection.disconnect(hint));
        }
    }
}
