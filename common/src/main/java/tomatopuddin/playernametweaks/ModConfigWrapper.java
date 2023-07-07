package tomatopuddin.playernametweaks;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ModConfigWrapper {
    private static final Pattern dangerousPlayerNamePattern = Pattern.compile("^[@'\"]|\\s");

    public final ModConfig config;
    private final boolean isDedicatedServer;

    private final List<Pattern> illegalPlayerNamePatterns;
    private final HashSet<UUID> whiteList;
    private final List<Function<String, Component>> checkers;

    public ModConfigWrapper(ModConfig config, boolean isDedicatedServer) {
        this.config = config;
        this.isDedicatedServer = isDedicatedServer;

        illegalPlayerNamePatterns = Arrays.stream(config.illegalPatterns).map(str -> Pattern.compile(str, Pattern.CASE_INSENSITIVE)).collect(Collectors.toList());
        whiteList = Arrays.stream(config.whiteList).map(UUID::fromString).collect(Collectors.toCollection(HashSet::new));
        checkers = List.of(this::checkTooLongPlayerName, this::checkIllegalPlayerName, this::checkDangerousPlayerName);
    }

    public void checkPlayerName(UUID uuid, String name, Consumer<Component> blockAction) {
        if (whiteList.contains(uuid))
            return;

        checkers.stream()
                .map(checker -> checker.apply(name))
                .filter(Objects::nonNull)
                .findFirst()
                .ifPresent(blockAction);
    }

    private @Nullable Component checkDangerousPlayerName(String name) {
        if (!dangerousPlayerNamePattern.matcher(name).find())
            return null;
        if (!config.blockDangerous) {
            PlayerNameTweaks.LOGGER.warn("Player name \"{}\" is dangerous!", name);
            return null;
        }
        return new TranslatableComponent("playernametweaks.hint.dangerous").withStyle(ChatFormatting.YELLOW);
    }

    private @Nullable Component checkIllegalPlayerName(String name) {
        if (!isDedicatedServer)
            return null;
        if (illegalPlayerNamePatterns.stream().noneMatch(pattern -> pattern.matcher(name).find()))
            return null;
        return new TranslatableComponent("playernametweaks.hint.illegal").withStyle(ChatFormatting.YELLOW);
    }

    private @Nullable Component checkTooLongPlayerName(String name) {
        if (name.length() <= config.maxLength)
            return null;
        return new TranslatableComponent("playernametweaks.hint.tooLong", config.maxLength).withStyle(ChatFormatting.YELLOW);
    }
}
