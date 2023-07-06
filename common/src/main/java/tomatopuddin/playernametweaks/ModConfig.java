package tomatopuddin.playernametweaks;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

import java.util.UUID;
import java.util.regex.Pattern;

@Config(name = PlayerNameTweaks.MODID)
public class ModConfig implements ConfigData {

    @Comment("Block dangerous player names that start with @ or quotation mark or contain blank characters.\nSuch names probably lead to fatal issues or be exploited maliciously.")
    public Boolean blockDangerous = true;

    @Comment("Block player names that match these regular expressions(case insensitive).\nOnly available on dedicated servers.")
    public String[] illegalPatterns = new String[]{"illegal-name.*?"};

    @Comment("Experimental! Back up your world before modifying the value.")
    @ConfigEntry.BoundedDiscrete(min = 1, max = 32767)
    public int maxLength = 16;

    @Comment("Player UUID. For example \"24c105a6-1c7f-414e-99ed-8d9ede0721f0\"")
    public String[] whiteList = new String[0];

    @Override
    public void validatePostLoad() throws ValidationException {
        if (illegalPatterns == null)
            throw new ValidationException("illegalPlayerNamePatterns is null");
        for (var regex : illegalPatterns) {
            try {
                Pattern.compile(regex);
            } catch (Exception e) {
                throw new ValidationException("Regex \"" + regex + "\" is invalid", e);
            }
        }

        if (whiteList == null)
            throw new ValidationException("whiteList is null");
        for (var uuid : whiteList) {
            try {
                UUID.fromString(uuid);
            } catch (Exception e) {
                throw new ValidationException("UUID \"" + uuid + "\" is invalid", e);
            }
        }

        if (maxLength < 1)
            throw new ValidationException("maxLength is shorter than 1");
        if (maxLength > 32767)
            throw new ValidationException("maxLength is longer than 32767");
    }
}
