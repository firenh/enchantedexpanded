package fireopal.enchantedexpanded.enchantments;

import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;

public class BrutalityEnchantment extends EEEnchantment {
    protected BrutalityEnchantment(Rarity weight, EquipmentSlot slotTypes) {
        super(weight, EnchantmentTarget.WEAPON, new EquipmentSlot[]{slotTypes});
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
      return stack.getItem() instanceof AxeItem ? true : super.isAcceptableItem(stack);
    }

    @Override
    public boolean canAccept(Enchantment other) {
        // EnchantedExpanded.LOGGER.info("canAccept: " + other.getTranslationKey());
        return (!(other instanceof DamageEnchantment)) && (!(other instanceof BrutalityEnchantment));
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public float getAttackDamage(int level, EntityGroup group) {
        return level * 2.25f;
    }

    @Override
    public int getMinPower(int level) {
        return 10 + (level - 1) * 4;
    }

    @Override
    public int getMaxPower(int level) {
        return this.getMinPower(level) + 15;
    }
}
