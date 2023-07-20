package fireopal.enchantedexpanded.enchantments;

import java.util.Objects;

import eu.pb4.polymer.core.api.utils.PolymerObject;
import fireopal.enchantedexpanded.EnchantedExpanded;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class EEEnchantment extends Enchantment implements PolymerObject {
    private String name;
    private Config config;

    protected EEEnchantment(String name, Rarity rarity, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(rarity, type, slotTypes);

        this.name = name;
        config = getConfig();
    }

    public Config getConfig() {
        if (!Objects.isNull(config)) {
            // EnchantedExpanded.LOGGER.info(getName() + ": " + config.toString());
            return config;
        }

        Config config = EnchantedExpanded.getConfig().enchantment_configs.get(getName());

        if (!Objects.isNull(config)) {
            // EnchantedExpanded.LOGGER.info(getName() + ": " + config.toString());
            return config;
        }

        EnchantedExpanded.LOGGER.warn("Enchantment `" + getName() + "` is missing config options; force-disabling enchantment");
        return new Config(false, true, false, false);
    }

    public boolean isAvailableForRandomSelection() {
        return config.isEnabled();
    }

    public boolean isAvailableForEnchantedBookOffer() {
        return config.isTreasure();
    }

    public boolean isTreasure() {
        // EnchantedExpanded.LOGGER.info("Enchantment Name: " + getName() + " Return val: " + config.isTreasure());
        return config.isTreasure();
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return true;
    }

    public static class Config {
        private final boolean enabled;
        private final boolean treasure;
        private final boolean enchantedBookOffer;
        private final boolean availableFandomSelection;

        public Config(boolean enabled, boolean treasure, boolean enchantedBookOffer, boolean availableFandomSelection) {
            this.enabled = enabled;
            this.treasure = treasure;
            this.enchantedBookOffer = enchantedBookOffer;
            this.availableFandomSelection = availableFandomSelection;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public boolean isTreasure() {
            return treasure;
        }

        public boolean isEnchantedBookOffer() {
            return enchantedBookOffer;
        }
    }
}
