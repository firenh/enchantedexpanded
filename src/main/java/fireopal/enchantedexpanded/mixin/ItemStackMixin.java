package fireopal.enchantedexpanded.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.Multimap;

import fireopal.enchantedexpanded.enchantments.EEEnchantments;
import fireopal.enchantedexpanded.enchantments.GrowthEnchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Inject(
        method = "getAttributeModifiers",
        at = @At("TAIL"),
        cancellable = true
    )
    private void getAttributeModifiers(EquipmentSlot slot, CallbackInfoReturnable<Multimap<EntityAttribute, EntityAttributeModifier>> cir) {
        if (((ItemStack) (Object) this).getItem() instanceof ArmorItem && ((ArmorItem) ((ItemStack) (Object) this).getItem()).getSlotType() == slot) {
            Multimap<EntityAttribute, EntityAttributeModifier> multimap = cir.getReturnValue();

            int growthLevel = EnchantmentHelper.getLevel(EEEnchantments.GROWTH, (ItemStack) (Object) this);

            if (growthLevel > 0) {
                multimap.put(
                    EntityAttributes.GENERIC_MAX_HEALTH,
                    new EntityAttributeModifier(GrowthEnchantment.GROWTH_ATTRIBUTE_UUID.get(slot), "enchantedexpanded:growth", growthLevel, EntityAttributeModifier.Operation.ADDITION)
                );

                cir.setReturnValue(multimap);
            }
        }
    }
}
