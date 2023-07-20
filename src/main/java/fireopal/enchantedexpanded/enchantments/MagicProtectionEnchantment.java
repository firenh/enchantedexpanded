package fireopal.enchantedexpanded.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;

public class MagicProtectionEnchantment extends EEEnchantment {
    protected MagicProtectionEnchantment(String name, Rarity weight, EnchantmentTarget type, EquipmentSlot... slot) {
        super(name, weight, type, slot);
    }

    @Override
    public int getMinPower(int level) {
        return 10 + (level - 1) * 5;
    }

    @Override
    public int getMaxPower(int level) {
        return this.getMinPower(level) + 5;
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public boolean canAccept(Enchantment other) {
        return !(other instanceof ProtectionEnchantment && other != Enchantments.FEATHER_FALLING) && super.canAccept(other);
    }

    @Override
    public int getProtectionAmount(int level, DamageSource source) {
        // EnchantedExpanded.LOGGER.info(source.getName());
        if (source.isOf(DamageTypes.MAGIC) || source.isOf(DamageTypes.INDIRECT_MAGIC)) {
            // EnchantedExpanded.LOGGER.info("level: " + level);

            return 6 * level;
        }
        return 0;
    }
}
