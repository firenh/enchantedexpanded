package fireopal.enchantedexpanded.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import fireopal.enchantedexpanded.duck.DuckPersistentProjectileEntity;
import fireopal.enchantedexpanded.enchantments.EEEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {
    @Inject(method = "createArrow", at = @At("RETURN"), cancellable = true)
    private static void createArrow(World world, LivingEntity entity, ItemStack crossbow, ItemStack arrow, CallbackInfoReturnable<PersistentProjectileEntity> cir) {
        int freezingLevel = EnchantmentHelper.getLevel(EEEnchantments.FREEZING, crossbow);
        int velocityLevel = EnchantmentHelper.getLevel(EEEnchantments.VELOCITY, crossbow);
        PersistentProjectileEntity arrowEntity = cir.getReturnValue();

        if (freezingLevel != 0 && !(world.getDimension().ultrawarm())) {
            ((DuckPersistentProjectileEntity)arrowEntity).setFreezingLevel(freezingLevel);
            arrowEntity.setDamage(arrowEntity.getDamage() / Math.pow(2, freezingLevel));
            arrowEntity.setCritical(false);
        }

        if (velocityLevel != 0) {
            Vec3d velocity = arrowEntity.getVelocity().multiply(1 + ((double) velocityLevel / 3.0));
            ((DuckPersistentProjectileEntity) arrowEntity).setVelocityLevel(velocityLevel);

            arrowEntity.setVelocity(velocity);
            // System.out.println("Velocity Arrow; Old Velocity: " + arrowEntity.getVelocity() + " | New Velocity: " + velocity);
        }

        cir.setReturnValue(arrowEntity);
    }

    // @Inject(
    //     method = "shoot",
    //     at = @At(
    //         value = "INVOKE",
    //         target = "Lnet/minecraft/world/ModifiableWorld;spawnEntity(Lnet/minecraft/entity/Entity;)Z"
    //     ),
    //     locals = LocalCapture.CAPTURE_FAILHARD
    // )
    // private static void shoot_addArrowVelocity(
    //     World world, LivingEntity shooter, Hand hand, ItemStack crossbow, ItemStack projectile, float soundPitch, 
    //     boolean creative, float speed, float divergence, float simulated, CallbackInfo ci, PersistentProjectileEntity projectileEntity
    // ) {
    //     int velocityLevel = EnchantmentHelper.getLevel(EEEnchantments.VELOCITY, crossbow);
        
    //     if (velocityLevel > 0) {
    //         projectileEntity.setVelocity(projectileEntity.getVelocity().multiply(1 + (((double) velocityLevel) / 3.0)));
    //         // projectileEntity = projectileEntity;
    //     }
    // } 

    @ModifyArg(method = "shoot", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"), index = 0)
    private static Entity modifyShoot(Entity projectile) {
        if (!(projectile instanceof PersistentProjectileEntity)) return projectile;
        
        int velocityLevel = ((DuckPersistentProjectileEntity) ((PersistentProjectileEntity) projectile)).getVelocityLevel();
        // System.out.println("Old velocity: " + projectile.getVelocity());

        if (velocityLevel > 0) {
            projectile.setVelocity(projectile.getVelocity().multiply(1 + (((double) velocityLevel) / 3.0)));
        }

        // System.out.println("New velocity: " + projectile.getVelocity());
        return projectile;
    }
}
