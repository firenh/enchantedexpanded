package fireopal.enchantedexpanded.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class AlluringEnchantment extends EEEnchantment {
    protected AlluringEnchantment(String name, Rarity weight, EnchantmentTarget type, EquipmentSlot... slot) {
        super(name, weight, type, slot);
    }

    public static int getKnockbackFactor() {
        return 2;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinPower(int level) {
        return 10 + 5 * level;
    }

    @Override
    public int getMaxPower(int level) {
        return 25 + level * 5;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (level > 0 && target instanceof LivingEntity) {
            ((LivingEntity)target).takeKnockback(level * 0.25f, -MathHelper.sin(user.getYaw() * ((float)Math.PI / 180)), MathHelper.cos(user.getYaw() * ((float)Math.PI / 180)));
        }
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return other != Enchantments.KNOCKBACK && super.canAccept(other);
    }
}
