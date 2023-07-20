package fireopal.enchantedexpanded.enchantments;

import fireopal.enchantedexpanded.EnchantedExpanded;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class BlazingEnchantment extends EEEnchantment {
    public static boolean isEnabled = EnchantedExpanded.getConfig().enchantment_configs.get("blazing").isEnabled();

    protected BlazingEnchantment(String name, Rarity weight, EnchantmentTarget type, EquipmentSlot... slot) {
        super(name, weight, type, slot);
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.isOf(Items.FLINT_AND_STEEL);
    }

    @Override
    public int getMinPower(int level) {
        return 0;
    }

    @Override
    public int getMaxPower(int level) {
        return 30;
    }

    
}
