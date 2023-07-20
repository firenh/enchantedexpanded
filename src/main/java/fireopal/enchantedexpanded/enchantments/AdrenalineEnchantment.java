package fireopal.enchantedexpanded.enchantments;

import fireopal.enchantedexpanded.EnchantedExpanded;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class AdrenalineEnchantment extends EEEnchantment {
    public static final int COOLDOWN_TICKS = 2 * 60 * 20;
    protected AdrenalineEnchantment(String name, Rarity weight, EnchantmentTarget type, EquipmentSlot... slot) {
        super(name, weight, type, slot);
    }

    public static void doAdrenalineEnchantment(LivingEntity user, int level) {

        int bonus = level - 1;
        spawnParticles(user, user.getWorld());
        // EnchantedExpanded.LOGGER.info("excite: " + (user.getWorld().getTime() - ((LivingEntityAccessor) user).getLastDamageTime()));
        
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, (3 + bonus * 2) * 20, 0));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION,  (3 + (level - 1) * 2) * 20, 1));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, (3 + (level - 1) * 2) * 20, 0));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, (3 + (level - 1) * 2) * 20, 0));
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    // private static void addStatusEffect(LivingEntity user, StatusEffect effect, int duration, int amplifier) {
    //     if (user.getStatusEffect(effect).getAmplifier() < amplifier) {
    //         user.addStatusEffect(new StatusEffectInstance(effect, duration, amplifier, false, true, true, user.getStatusEffect(effect))));
    //     }
    // }

    public static void spawnParticles(Entity e, World world) {
        if (world instanceof ServerWorld && e instanceof LivingEntity && ((LivingEntity) e).hurtTime == 0) {
            ((ServerWorld) world).spawnParticles(
                ParticleTypes.SPORE_BLOSSOM_AIR, 
                e.getX(), 
                (e.getY() + e.getEyeY()) / 2, 
                e.getZ(), 
                10, 
                1, 
                1, 
                1, 
                0
            );
        }
    }
    
}
