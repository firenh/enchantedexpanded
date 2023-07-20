package fireopal.enchantedexpanded.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;

public class VelocityEnchantment extends EEEnchantment {
    protected VelocityEnchantment(String name, Rarity weight, EnchantmentTarget type, EquipmentSlot... slot) {
        super(name, weight, type, slot);
    }
    
    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public int getMinPower(int level) {
        return 10 + (level - 1) * 7;
    }

    @Override
    public int getMaxPower(int level) {
        return this.getMinPower(level) + 10;
    }

    @Override
    public boolean canAccept(Enchantment other) {
        return other != Enchantments.PIERCING && other != Enchantments.MULTISHOT && other != Enchantments.QUICK_CHARGE; 
    }
}
