package tomatopuddin.playernametweaks;
import com.google.common.collect.Lists;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(PlayerNameTweaksMod.MODID)
@Mod.EventBusSubscriber
public class PlayerNameTweaksMod {
    public static final String MODID = "playernametweaks";
    public static final Logger LOGGER = LogManager.getLogger(PlayerNameTweaksMod.class);

    private static ModConfigWrapper config;
    private static MinecraftServer server;

    public PlayerNameTweaksMod() {
        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        config = new ModConfigWrapper(AutoConfig.getConfigHolder(ModConfig.class).getConfig(), false);
    }

    @SubscribeEvent
    public static void onServerStaring(FMLServerStartedEvent event) {
        server = event.getServer();
        config = new ModConfigWrapper(AutoConfig.getConfigHolder(ModConfig.class).getConfig(), server.isDedicatedServer());
    }

    @SubscribeEvent
    public static void onCommandRegistering(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("playernametweaks")
                .requires(source -> source.hasPermissionLevel(4))
                .then(Commands.literal("reload")
                        .executes(context -> {
                            me.shedaniel.autoconfig.ConfigHolder<ModConfig> holder = AutoConfig.getConfigHolder(ModConfig.class);
                            if (!holder.load()) {
                                context.getSource().sendErrorMessage(new StringTextComponent("Failed to reload config.").mergeStyle(TextFormatting.RED));
                                return 1;
                            }

                            config = new ModConfigWrapper(holder.getConfig(), server.isDedicatedServer());
                            context.getSource().sendFeedback(new StringTextComponent("Successfully reloaded config.").mergeStyle(TextFormatting.GREEN), false);
                            checkPlayers();

                            return 0;
                        })));
    }

    public static ModConfigWrapper getConfig() {
        return config;
    }

    private static void checkPlayers() {
        for (ServerPlayerEntity player : Lists.newArrayList(server.getPlayerList().getPlayers())) {
            config.checkPlayerName(player.getGameProfile().getId(), player.getGameProfile().getName(),
                    hint -> player.connection.disconnect(hint));
        }
    }
}
