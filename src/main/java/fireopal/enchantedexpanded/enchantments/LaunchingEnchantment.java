package fireopal.enchantedexpanded.enchantments;

import java.util.List;

import net.minecraft.enchantment.EnchantmentTarget;
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

public class LaunchingEnchantment extends EEEnchantment {
    protected LaunchingEnchantment(String name, Rarity weight, EnchantmentTarget type, EquipmentSlot... slot) {
        super(name, weight, type, slot);
    }

    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof AxeItem;
    }

    public int getMaxLevel() {
        return 3;
    }

    public int getMinPower(int level) {
        return 5 + 20 * (level - 1);
    }

    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
    
        if (target instanceof LivingEntity) {
            LivingEntity livingTarget = (LivingEntity) target;
            Vec3d velocity = livingTarget.getVelocity();
            velocity = new Vec3d(-velocity.x, velocity.y + 0.1 * level, -velocity.z);

            if (target instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity) user).networkHandler
                        .sendPacket(new ExplosionS2CPacket(0, -256, 0, 0, List.of(), velocity));
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
                    0);
        }
    }
}
