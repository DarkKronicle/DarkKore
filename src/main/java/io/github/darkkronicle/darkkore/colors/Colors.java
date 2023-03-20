package io.github.darkkronicle.darkkore.colors;

import com.electronwill.nightconfig.core.file.FileConfig;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.darkkronicle.darkkore.DarkKore;
import io.github.darkkronicle.darkkore.config.ConfigHolder;
import io.github.darkkronicle.darkkore.config.impl.ConfigObject;
import io.github.darkkronicle.darkkore.config.impl.FileObject;
import io.github.darkkronicle.darkkore.config.impl.NightFileObject;
import io.github.darkkronicle.darkkore.util.Color;
import io.github.darkkronicle.darkkore.util.FileUtil;
import lombok.Getter;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.InputStreamReader;
import java.util.*;

public class Colors implements ConfigHolder {

    private static final Colors INSTANCE = new Colors();

    public static Colors getInstance() {
        return INSTANCE;
    }

    @Getter
    private final Map<String, Color> colors = new HashMap<>();

    @Getter
    private final Map<String, Color> defaultColors = new HashMap<>();

    @Getter
    private final Map<String, Palette> palettes = new HashMap<>();

    private String defaultPalette = "";

    private Colors() {}

    public File getFile() {
        return FileUtil.getConfigDirectory().toPath().resolve("colors.toml").toFile();
    }

    public void load() {
        colors.clear();
        defaultColors.clear();
        palettes.clear();

        JsonObject object = null;
        try {
            object = (JsonObject) JsonParser.parseReader(new InputStreamReader(FileUtil.getResource("basic_colors.json")));
        } catch (Exception e) {
            DarkKore.LOGGER.log(Level.ERROR, "Could not load basic colors!", e);
        }
        if (object != null) {
            for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
                defaultColors.put(entry.getKey().toLowerCase(Locale.ROOT), hexToSimple(entry.getValue().getAsString()));
            }
        }

        // Get file or create if it doesn't exist
        File file = getFile();
        if (!file.exists()) {
            try {
                org.apache.commons.io.FileUtils.copyInputStreamToFile(FileUtil.getResource("colors.toml"), file);
            } catch (Exception e) {
                // Rip
                DarkKore.LOGGER.log(Level.ERROR, "Colors could not be loaded correctly!", e);
                return;
            }
        }

        // Use night-config toml parsing
        FileObject night = new NightFileObject(FileConfig.of(file));
        night.load();
        ConfigObject config = night.getConfig();

        // Assign colors
        Optional<ConfigObject> customColors = config.getOptional("color");
        if (customColors.isPresent()) {
            for (Map.Entry<String, Object> entry : customColors.get().getValues().entrySet()) {
                this.colors.put(entry.getKey().toLowerCase(Locale.ROOT), hexToSimple((String) entry.getValue()));
            }
        }

        Optional<ConfigObject> palettes = config.getOptional("palettes");
        if (palettes.isPresent()) {
            // Nested configuration
            for (Map.Entry<String, Object> entry : palettes.get().getValues().entrySet()) {
                ArrayList<Color> colors = new ArrayList<>();
                for (String c : (List<String>) entry.getValue()) {
                    if (this.colors.containsKey(c)) {
                        // Allow color reference
                        colors.add(this.colors.get(c));
                    } else {
                        colors.add(hexToSimple(c));
                    }
                }
                this.palettes.put(entry.getKey(), new Palette(colors));
            }
        }

        // Set default
        Optional<String> defaultPalette = config.getOptional("default_palette");
        defaultPalette.ifPresent(s -> this.defaultPalette = s);
        night.close();
    }

    @Override
    public void save() {
        // TODO implement some way to configure in game
    }

    /**
     * Get's the default palette specified by the user.
     *
     * @return Palette user specified
     */
    public Palette getDefault() {
        if (this.palettes.containsKey(defaultPalette)) {
            return this.palettes.get(defaultPalette);
        }
        DarkKore.LOGGER.log(Level.WARN, "Default Palette " + defaultPalette + " does not exist!");
        return this.palettes.values().toArray(new Palette[0])[0];
    }

    /**
     * Get's a palette by name. If it doesn't exist, an empty optional is returned
     *
     * @param name Name of the palette from colors.toml
     * @return Palette
     */
    public Optional<Palette> get(String name) {
        Palette palette = palettes.get(name);
        if (palette != null) {
            return Optional.of(palette);
        }
        return Optional.empty();
    }

    private static Color hexToSimple(String string) {
        if (string.length() != 7 && string.length() != 9 && string.length() != 4) {
            // Not #ffffff (so invalid!)
            DarkKore.LOGGER.log(Level.WARN, "Color " + string + " isn't formatted correctly! (#ffffff) (#ffffffff)");
            return new Color(255, 255, 255, 255);
        }
        string = string.substring(1).toLowerCase(Locale.ROOT);
        if (string.length() == 3) {
            string = string.substring(0, 1).repeat(2) + string.substring(1, 2).repeat(2) + string.substring(2, 3).repeat(2);
        }
        try {
            int red = Integer.parseInt(string.substring(0, 2), 16);
            int green = Integer.parseInt(string.substring(2, 4), 16);
            int blue = Integer.parseInt(string.substring(4, 6), 16);
            int alpha = 255;
            if (string.length() == 8) {
                alpha = Integer.parseInt(string.substring(6), 16);
            }
            return new Color(red, green, blue, alpha);
        } catch (Exception e) {
            DarkKore.LOGGER.log(Level.WARN, "Couldn't convert " + string + " into a color!", e);
        }
        return new Color(255, 255, 255, 255);
    }

    public Optional<Color> getColor(String key) {
        key = key.toLowerCase(Locale.ROOT).replaceAll(" ", "_");
        if (colors.containsKey(key)) {
            return Optional.of(colors.get(key));
        }
        if (defaultColors.containsKey(key)) {
            return Optional.of(defaultColors.get(key));
        }
        return Optional.empty();
    }

    public Color getColorOrWhite(String key) {
        return getColor(key).orElse(new Color(255, 255, 255, 255));
    }

    public static class Palette {

        @Getter private final List<Color> colors;

        public Palette(List<Color> colors) {
            this.colors = colors;
        }
    }
}
