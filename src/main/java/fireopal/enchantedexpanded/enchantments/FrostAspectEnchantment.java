package fireopal.enchantedexpanded.enchantments;

import eu.pb4.polymer.core.api.utils.PolymerObject;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.FireAspectEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class FrostAspectEnchantment extends FireAspectEnchantment implements PolymerObject {
    protected FrostAspectEnchantment(Rarity weight, EquipmentSlot[] slotTypes) {
        super(weight, slotTypes);
    }

    protected FrostAspectEnchantment(Rarity weight, EquipmentSlot slotTypes) {
        super(weight, slotTypes);
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return other != Enchantments.FIRE_ASPECT && super.canAccept(other);
    }

    public static int getFrozenTicks(int level) {
        return 150 + level * 6 * 40;
    } 

    public static void spawnFrozenParticles(Entity e, World world) {
        if (world instanceof ServerWorld && e instanceof LivingEntity && ((LivingEntity) e).hurtTime == 0) {
            ((ServerWorld) world).spawnParticles(
                ParticleTypes.SNOWFLAKE, 
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
