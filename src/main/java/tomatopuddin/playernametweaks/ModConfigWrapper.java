package tomatopuddin.playernametweaks;

import com.google.common.collect.Lists;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;
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
    private final List<Function<String, ITextComponent>> checkers;

    public ModConfigWrapper(ModConfig config, boolean isDedicatedServer) {
        this.config = config;
        this.isDedicatedServer = isDedicatedServer;

        illegalPlayerNamePatterns = Arrays.stream(config.illegalPatterns).map(str -> Pattern.compile(str, Pattern.CASE_INSENSITIVE)).collect(Collectors.toList());
        whiteList = Arrays.stream(config.whiteList).map(UUID::fromString).collect(Collectors.toCollection(HashSet::new));
        checkers = Lists.newArrayList(this::checkTooLongPlayerName, this::checkIllegalPlayerName, this::checkDangerousPlayerName);
    }

    public void checkPlayerName(UUID uuid, String name, Consumer<ITextComponent> blockAction) {
        if (whiteList.contains(uuid))
            return;

        checkers.stream()
                .map(checker -> checker.apply(name))
                .filter(Objects::nonNull)
                .findFirst()
                .ifPresent(blockAction);
    }

    private @Nullable ITextComponent checkDangerousPlayerName(String name) {
        if (!dangerousPlayerNamePattern.matcher(name).find())
            return null;
        if (!config.blockDangerous) {
            PlayerNameTweaksMod.LOGGER.warn("Player name \"{}\" is dangerous!", name);
            return null;
        }
        return new TranslationTextComponent("playernametweaks.hint.dangerous").mergeStyle(TextFormatting.YELLOW);
    }

    private @Nullable ITextComponent checkIllegalPlayerName(String name) {
        if (!isDedicatedServer)
            return null;
        if (illegalPlayerNamePatterns.stream().noneMatch(pattern -> pattern.matcher(name).find()))
            return null;
        return new TranslationTextComponent("playernametweaks.hint.illegal").mergeStyle(TextFormatting.YELLOW);
    }

    private @Nullable ITextComponent checkTooLongPlayerName(String name) {
        if (name.length() <= config.maxLength)
            return null;
        return new TranslationTextComponent("playernametweaks.hint.tooLong", config.maxLength).mergeStyle(TextFormatting.YELLOW);
    }
}
