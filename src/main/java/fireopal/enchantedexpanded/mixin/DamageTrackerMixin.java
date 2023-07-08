package fireopal.enchantedexpanded.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fireopal.enchantedexpanded.enchantments.AdrenalineEnchantment;
import fireopal.enchantedexpanded.enchantments.EEEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTracker;

@Mixin(DamageTracker.class)
public class DamageTrackerMixin {
    @Shadow @Final
    private LivingEntity entity;

    @Shadow
    private int ageOnLastDamage;

    @Inject(
        method = "onDamage",
        at = @At("HEAD")
    )
    private void onDamage(DamageSource damageSource, float originalHealth, CallbackInfo ci) {        
        if (EnchantmentHelper.getLevel(EEEnchantments.ADRENALINE, entity.getEquippedStack(EquipmentSlot.CHEST)) > 0 && entity.age - ageOnLastDamage > AdrenalineEnchantment.COOLDOWN_TICKS) {
            // EnchantedExpanded.LOGGER.info("Age gap: " + (entity.age - ageOnLastDamage));
            
            AdrenalineEnchantment.doAdrenalineEnchantment(entity, EnchantmentHelper.getLevel(EEEnchantments.ADRENALINE, entity.getEquippedStack(EquipmentSlot.CHEST)));
        }
    }
}
