package fireopal.enchantedexpanded.enchantments;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class FreezingEnchantment extends EEEnchantment {
    protected FreezingEnchantment(Rarity weight, EquipmentSlot[] slotTypes) {
        super(weight, EnchantmentTarget.CROSSBOW, slotTypes);
    }

    @Override
    public int getMinPower(int level) {
        return 25;
    }

    @Override
    public int getMaxPower(int level) {
        return 30;
    }
}
