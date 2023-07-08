package fireopal.enchantedexpanded.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fireopal.enchantedexpanded.duck.DuckPersistentProjectileEntity;
import fireopal.enchantedexpanded.enchantments.EEEnchantments;
import fireopal.enchantedexpanded.enchantments.FrostAspectEnchantment;
import fireopal.enchantedexpanded.util.EETags;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

@Mixin(PersistentProjectileEntity.class)
public class PersistentProjectileEntityMixin implements DuckPersistentProjectileEntity {
    // protected PersistentProjectileEntityMixin(EntityType<? extends PersistentProjectileEntity> type, double x, double y,
    //         double z, World world) {
    //     super(type, x, y, z, world);
    // }

    private int freezingLevel = 0;
    private int velocityLevel = 0;

    @Override
    public void setFreezingLevel(int freezingLevel) {
        this.freezingLevel = freezingLevel;
    }

    @Override
    public int getFreezingLevel() {
        return freezingLevel;
    }

    @Override
    public void setVelocityLevel(int velocityLevel) {
        this.velocityLevel = velocityLevel;
    }

    @Override
    public int getVelocityLevel() {
        return velocityLevel;
    }
    
    @Inject(method = "tick", at = @At("HEAD")) 
    private void tick(CallbackInfo ci) {
        if (getFreezingLevel() > 0) {
            World world = ((PersistentProjectileEntity) (Object) this).getEntityWorld();
            
            if (
                world.getDimension().ultrawarm()
                || world.getBlockState(((PersistentProjectileEntity) (Object) this).getBlockPos()).isIn(EETags.MELTS_FREEZING_ARROWS)
            ) {
                setFreezingLevel(0);
            } else if (world instanceof ServerWorld) {
                ((ServerWorld) world).spawnParticles(
                    ParticleTypes.SNOWFLAKE, 
                    ((PersistentProjectileEntity) (Object) this).getX(), 
                    ((PersistentProjectileEntity) (Object) this).getY(), 
                    ((PersistentProjectileEntity) (Object) this).getZ(), 
                    1,
                    0, 
                    0, 
                    0, 
                    0
                );
            }
        }

        if (((PersistentProjectileEntity) (Object) this).getFireTicks() >= 0) {
            setFreezingLevel(0);
        }
    }

    @Inject(method = "onHit", at = @At("HEAD"))
    private void onHit(LivingEntity target, CallbackInfo ci) {
        if (getFreezingLevel() > 0 && target.getFireTicks() < 0) {
            target.setFrozenTicks(150 + getFreezingLevel() * 6 * 40);

            FrostAspectEnchantment.spawnFrozenParticles(target, target.getEntityWorld());
        }
    }

    @Inject(method = "applyEnchantmentEffects", at = @At("HEAD"))
    private void applyEnchantmentEffects(LivingEntity entity, float damageModifier, CallbackInfo ci) {
        int level = EnchantmentHelper.getEquipmentLevel(EEEnchantments.VELOCITY, entity);
        if (level > 0) ((PersistentProjectileEntity)(Object)this).setDamage(((PersistentProjectileEntity)(Object)this).getDamage() + (double)level * 0.5 + 0.5);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putByte("FreezingLevel", (byte) this.getFreezingLevel());
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        this.setFreezingLevel(nbt.getByte("FreezingLevel"));
    }
}
