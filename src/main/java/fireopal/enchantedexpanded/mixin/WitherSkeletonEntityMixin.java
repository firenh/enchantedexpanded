package fireopal.enchantedexpanded.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fireopal.enchantedexpanded.enchantments.EEEnchantments;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.item.ItemStack;

@Mixin(WitherSkeletonEntity.class)
public class WitherSkeletonEntityMixin {
    @Inject(method = "dropEquipment", at = @At("HEAD"))
    private void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops, CallbackInfo ci) {
        ItemStack itemStack = ((WitherSkeletonEntity)(Object)this).getEquippedStack(EquipmentSlot.MAINHAND);
        
        if (EnchantmentTarget.WEAPON.isAcceptableItem((itemStack.getItem()))) {
            itemStack.addEnchantment(EEEnchantments.WITHERING, 1);
        }

        ((WitherSkeletonEntity)(Object)this).equipStack(EquipmentSlot.MAINHAND, itemStack);
    }
}
