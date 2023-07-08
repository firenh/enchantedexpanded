package fireopal.enchantedexpanded.enchantments;

import java.util.List;

import eu.pb4.polymer.core.api.utils.PolymerObject;
import net.minecraft.enchantment.KnockbackEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class LaunchingEnchantment extends KnockbackEnchantment implements PolymerObject {
    protected LaunchingEnchantment(Rarity weight, EquipmentSlot mainhand) {
        super(weight, mainhand);
    }

    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof AxeItem;
    }

    public int getMaxLevel() {
      return 3;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (target instanceof LivingEntity) {
            LivingEntity livingTarget = (LivingEntity) target;
            Vec3d velocity = livingTarget.getVelocity();
            velocity = new Vec3d(-velocity.x, velocity.y + 0.135 * level, -velocity.z);


            if (target instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity) user).networkHandler.sendPacket(new ExplosionS2CPacket(0, -256, 0, 0, List.of(), velocity));
            } else {
                livingTarget.setVelocity(velocity);
            }
            
            spawnParticles(livingTarget, livingTarget.getWorld());
        }
    }

    private static void spawnParticles(Entity e, World world) {
        if (world instanceof ServerWorld && e instanceof LivingEntity && ((LivingEntity) e).hurtTime == 0) {
            ((ServerWorld) world).spawnParticles(
                ParticleTypes.CLOUD, 
                e.getX(), 
                e.getY(),
                e.getZ(), 
                3, 
                0.2, 
                0.2, 
                0.2, 
                0
            );
        }
    }
}
