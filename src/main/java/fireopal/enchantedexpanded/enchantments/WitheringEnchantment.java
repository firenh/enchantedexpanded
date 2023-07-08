package fireopal.enchantedexpanded.enchantments;

import eu.pb4.polymer.core.api.utils.PolymerObject;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.FireAspectEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class WitheringEnchantment extends FireAspectEnchantment implements PolymerObject {
    protected WitheringEnchantment(Rarity weight, EquipmentSlot[] slotTypes) {
        super(weight, slotTypes);
    }

    protected WitheringEnchantment(Rarity weight, EquipmentSlot slotTypes) {
        super(weight, slotTypes);
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return other != Enchantments.FIRE_ASPECT && other != EEEnchantments.FROST_ASPECT && super.canAccept(other);
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

    public static void effect(int level, Entity attacker, Entity target) {
        if (target instanceof LivingEntity) {
            ((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 100 - (level - 1) * 30, level), attacker);
        }
    }
}
