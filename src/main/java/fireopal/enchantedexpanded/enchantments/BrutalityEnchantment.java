package fireopal.enchantedexpanded.enchantments;

import fireopal.enchantedexpanded.EnchantedExpanded;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class BrutalityEnchantment extends EEEnchantment {
    protected BrutalityEnchantment(String name, Rarity weight, EnchantmentTarget type, EquipmentSlot... slot) {
        super(name, weight, type, slot);
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
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (level > 0 && target instanceof LivingEntity) {
            user.damage(user.getWorld().getDamageSources().magic(), level * 2f);
            World world = user.getWorld();

            if (world instanceof ServerWorld) {
                ((ServerWorld) world).spawnParticles(
                    ParticleTypes.DAMAGE_INDICATOR, 
                    user.getX(), 
                    user.getEyeY(), 
                    user.getZ(), 
                    level, 
                    0, 
                    0, 
                    0, 
                    0.5
                );
            }
        }
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
        return level * 2f;
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
