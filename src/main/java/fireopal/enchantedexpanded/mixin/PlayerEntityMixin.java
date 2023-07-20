package fireopal.enchantedexpanded.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fireopal.enchantedexpanded.duck.DuckPlayerEntity;
import fireopal.enchantedexpanded.enchantments.EEEnchantments;
import fireopal.enchantedexpanded.enchantments.MagnitudeEnchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements DuckPlayerEntity {
    private int ee_magnitudeCooldown = 0;

    @Shadow
    private ItemStack selectedItem;
    
    @Override
    public int getMagnitudeCooldown() {
        return ee_magnitudeCooldown;
    }

    @Override
    public void setMagnitudeCooldown(int cooldown) {
        this.ee_magnitudeCooldown = cooldown;
    }

    @Inject(
        method = "tick",
        at = @At("TAIL")
    )
    private void tick(CallbackInfo ci) {
        ee_magnitudeCooldown -= ee_magnitudeCooldown > 0 ? 1 : 0;
    }

    @Inject(
        method = "resetLastAttackedTicks",
        at = @At("HEAD")
    )
    private void inject_resetLastAttackedTicks(CallbackInfo ci) {
        if (EnchantmentHelper.getLevel(EEEnchantments.MAGNITUDE, selectedItem) > 0) {
            MagnitudeEnchantment.onUseMagnitude((PlayerEntity) (Object) this, selectedItem);
        }
    }

    // @Inject(
    //     method = "tick",
    //     at = @At("HEAD")
    // )
    // private void inject_tick(CallbackInfo ci) {
    //     if (((PlayerEntity)(Object)this).getLastAttackTime()  EnchantmentHelper.getLevel(EEEnchantments.MAGNITUDE, selectedItem) > 0) {
    //         MagnitudeEnchantment.onUseMagnitude((PlayerEntity) (Object) this);
    //     }
    // }
}
