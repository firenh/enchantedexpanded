package fireopal.enchantedexpanded.enchantments;

import java.util.UUID;

import com.google.common.collect.ImmutableMap;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.EquipmentSlot;

public class GrowthEnchantment extends EEEnchantment {
    public static final ImmutableMap<EquipmentSlot, UUID> GROWTH_ATTRIBUTE_UUID = ImmutableMap.of(
        EquipmentSlot.HEAD, new UUID(-6421228739104998949L, -2604566457587412309L),
        EquipmentSlot.CHEST, new UUID(8380594599542067499L, 5184728070634523481L),
        EquipmentSlot.LEGS, new UUID(-2536208089593323313L, -5287105864511806650L),
        EquipmentSlot.FEET, new UUID(-5076417669345956011L, 6566427467804455003L)
    );

    
    protected GrowthEnchantment(Rarity weight, EquipmentSlot[] slotTypes) {
        super(weight, EnchantmentTarget.ARMOR, slotTypes);
    }
    
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinPower(int level) {
        return 15 + 5 * level;
    }

    @Override
    public int getMaxPower(int level) {
        return getMinPower(level + 1) + level * 2;
    }

    public boolean isTreasure() {
        return true;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return false;
    }

    @Override
    public boolean canAccept(Enchantment other) {
        return !(other instanceof ProtectionEnchantment);
    }
}
