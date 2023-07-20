package fireopal.enchantedexpanded.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class WitheringEnchantment extends EEEnchantment {
    protected WitheringEnchantment(String name, Rarity weight, EnchantmentTarget type, EquipmentSlot... slot) {
        super(name, weight, type, slot);
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

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        withering(user, target, level);
    }

    public static void withering(LivingEntity user, Entity target, int level) {
        if (level > 0) {
            WitheringEnchantment.effect(level, user, target);
            World world = user.getWorld();

            if (world instanceof ServerWorld) {
                ((ServerWorld) world).spawnParticles(
                    ParticleTypes.ASH, 
                    target.getX(), 
                    (target.getEyeY() + target.getY()) / 2.0, 
                    target.getZ(), 
                    level * 15, 
                    0.25, 
                    (target.getEyeY() - target.getY()) / 2.0, 
                    0.25, 
                    1
                );
            }
        }
    }

    public static void effect(int level, Entity attacker, Entity target) {
        if (target instanceof LivingEntity) {
            ((LivingEntity) target).addStatusEffect(
                    new StatusEffectInstance(StatusEffects.WITHER, 100 - (level - 1) * 30, level), attacker);
        }
    }
}
