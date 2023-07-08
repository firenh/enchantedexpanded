package fireopal.enchantedexpanded.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import fireopal.enchantedexpanded.gameplay.OnAttack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;

@Mixin(MobEntity.class)
public class MobEntityMixin {
    @Inject(method = "tryAttack", at = @At("HEAD"))
    private void tryAttack(Entity target, CallbackInfoReturnable<Boolean> cir) {
        OnAttack.onAttack(((MobEntity)(Object)this), target);
    }
}
