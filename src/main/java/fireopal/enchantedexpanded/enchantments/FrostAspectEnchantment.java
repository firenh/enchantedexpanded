package fireopal.enchantedexpanded.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class FrostAspectEnchantment extends EEEnchantment {
    protected FrostAspectEnchantment(String name, Rarity weight, EnchantmentTarget type, EquipmentSlot... slot) {
        super(name, weight, type, slot);
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return other != Enchantments.FIRE_ASPECT && super.canAccept(other);
    }

    public static int getFrozenTicks(int level) {
        return 150 + level * 6 * 40;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        frostAspect(user, target, level);
    }
    
    public static void frostAspect(LivingEntity user, Entity target, int level) {
        if (level > 0) {
            int frozenTicks = FrostAspectEnchantment.getFrozenTicks(level);

            if (frozenTicks > target.getFrozenTicks()) target.setFrozenTicks(frozenTicks);
            spawnFrozenParticles(target, user.getWorld());
        } 

    }

    public static void spawnFrozenParticles(Entity target, World world) {
        if (world instanceof ServerWorld) {
                ((ServerWorld) world).spawnParticles(
                    ParticleTypes.SNOWFLAKE, 
                    target.getX(), 
                    (target.getEyeY() + target.getY()) / 2.0, 
                    target.getZ(), 
                    5, 
                    0.25, 
                    (target.getEyeY() - target.getY()) / 2.0, 
                    0.25, 
                    0
                );
            }
    }

    public int getMinPower(int level) {
        return 10 + 20 * (level - 1);
    }

    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
    }

    public int getMaxLevel() {
        return 2;
    }
}
