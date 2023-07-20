package fireopal.enchantedexpanded.util;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fireopal.enchantedexpanded.EnchantedExpanded;
import fireopal.enchantedexpanded.enchantments.EEEnchantment;

public class Config {
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    //Config Default Values

    public String CONFIG_VERSION_DO_NOT_TOUCH_PLS = EnchantedExpanded.VERSION;
    public boolean logWhenLoaded = false;
    public Map<String, EEEnchantment.Config> enchantment_configs = getDefaultConfigs();

    public static Map<String, EEEnchantment.Config> getDefaultConfigs() {
        Map<String, EEEnchantment.Config> map = new HashMap<>();

        map.put("adrenaline", new EEEnchantment.Config(true, true, false, true));
        map.put("alluring", new EEEnchantment.Config(true, true, true, true));
        map.put("blazing", new EEEnchantment.Config(true, true, true, true));
        map.put("brutality", new EEEnchantment.Config(true, false, true, true));
        map.put("detonation", new EEEnchantment.Config(false, false, false, false));
        map.put("freezing", new EEEnchantment.Config(true, false, true, true));
        map.put("frost_aspect", new EEEnchantment.Config(true, false, true, true));
        map.put("growth", new EEEnchantment.Config(true, true, true, true));
        map.put("harvest", new EEEnchantment.Config(true, false, true, true));
        map.put("launching", new EEEnchantment.Config(true, false, true, true));
        map.put("magic_protection", new EEEnchantment.Config(true, false, true, true));
        map.put("magnitude", new EEEnchantment.Config(true, true, true, true));
        map.put("velocity", new EEEnchantment.Config(true, false, true, true));
        map.put("withering", new EEEnchantment.Config(true, true, false, false));

        return map;
    }

    public static Config init() {
        Config config = null;

        try {
            Path configPath = Paths.get("", "config", "enchanted_expanded.json");

            if (Files.exists(configPath)) {
                config = gson.fromJson(
                    new FileReader(configPath.toFile()),
                    Config.class
                );

                if (!config.CONFIG_VERSION_DO_NOT_TOUCH_PLS.equals(EnchantedExpanded.VERSION)) {
                    config.CONFIG_VERSION_DO_NOT_TOUCH_PLS = EnchantedExpanded.VERSION;

                    BufferedWriter writer = new BufferedWriter(
                        new FileWriter(configPath.toFile())
                    );

                    writer.write(gson.toJson(config));
                    writer.close();
                }

            } else {
                config = new Config();
                Paths.get("", "config").toFile().mkdirs();

                BufferedWriter writer = new BufferedWriter(
                    new FileWriter(configPath.toFile())
                );

                writer.write(gson.toJson(config));
                writer.close();
            }


        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return config;
    }
}
