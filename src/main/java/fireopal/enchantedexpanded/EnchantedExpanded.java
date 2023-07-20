package fireopal.enchantedexpanded;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// import fireopal.enchantedexpanded.gameplay.AddToLootTable;
import fireopal.enchantedexpanded.util.Config;
import fireopal.enchantedexpanded.enchantments.EEEnchantments;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class EnchantedExpanded implements ModInitializer {
	public static final String MODID = "enchanted_expanded";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	public static final String VERSION = "1.0.0";

	private static Config config;

	@Override
	public void onInitialize() {
		loadConfigFromFile();
		EEEnchantments.init();
		// AddToLootTable.init();
		LOGGER.info("Expanding Enchantments... Done!");
	}

	public static Identifier id(String id) {
		return new Identifier(MODID, id);
	}

	public static Config getConfig() {
		return config;
	}

	public static void loadConfigFromFile() {
        config = Config.init();
        if (config.logWhenLoaded) LOGGER.info("Loaded config for " + MODID);
    }
}
