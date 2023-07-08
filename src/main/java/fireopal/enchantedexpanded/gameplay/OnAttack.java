package fireopal.enchantedexpanded.gameplay;

import fireopal.enchantedexpanded.enchantments.EEEnchantments;
import fireopal.enchantedexpanded.enchantments.FrostAspectEnchantment;
import fireopal.enchantedexpanded.enchantments.WitheringEnchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class OnAttack {
    public static void onAttack(LivingEntity attacker, Entity target) {
        frostAspect(attacker, target);
        withering(attacker, target);
        alluring(attacker, target);
        atrophy(attacker, target);
    }

    public static void frostAspect(LivingEntity attacker, Entity target) {
        int frostAspectLevel = EnchantmentHelper.getEquipmentLevel(EEEnchantments.FROST_ASPECT, attacker);
        
        if (frostAspectLevel > 0) {
            int frozenTicks = FrostAspectEnchantment.getFrozenTicks(frostAspectLevel);

            if (frozenTicks > target.getFrozenTicks()) target.setFrozenTicks(frozenTicks);
            FrostAspectEnchantment.spawnFrozenParticles(target, target.getEntityWorld());
        }
    }

    public static void withering(LivingEntity attacker, Entity target) {
        int level = EnchantmentHelper.getEquipmentLevel(EEEnchantments.WITHERING, attacker);
        
        if (level > 0) {
            WitheringEnchantment.effect(level, attacker, target);
            World world = attacker.getWorld();

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

    private static void alluring(LivingEntity attacker, Entity target) {
        int level = EnchantmentHelper.getEquipmentLevel(EEEnchantments.ALLURING, attacker);
        
        if (level > 0 && target instanceof LivingEntity && ((LivingEntity) target).hurtTime == 0) {
            ((LivingEntity)target).takeKnockback(0.75 + level * 0.75f, -MathHelper.sin(attacker.getYaw() * ((float)Math.PI / 180)), MathHelper.cos(attacker.getYaw() * ((float)Math.PI / 180)));
        }
    }

    private static void atrophy(LivingEntity attacker, Entity target) {
        int level = EnchantmentHelper.getEquipmentLevel(EEEnchantments.BRUTALITY, attacker);

        if (level > 0 && target instanceof LivingEntity && ((LivingEntity) target).hurtTime == 0) {
            attacker.damage(attacker.getWorld().getDamageSources().magic(), level * 3f);
            World world = attacker.getWorld();

            if (world instanceof ServerWorld) {
                ((ServerWorld) world).spawnParticles(
                    ParticleTypes.DAMAGE_INDICATOR, 
                    attacker.getX(), 
                    attacker.getEyeY(), 
                    attacker.getZ(), 
                    level, 
                    0, 
                    0, 
                    0, 
                    0.5
                );
            }
        }
    }
}
