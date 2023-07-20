package fireopal.enchantedexpanded.mixin;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import fireopal.enchantedexpanded.enchantments.AlluringEnchantment;
import fireopal.enchantedexpanded.enchantments.EEEnchantment;
import fireopal.enchantedexpanded.enchantments.EEEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @Inject(method = "getKnockback", at = @At("RETURN"), cancellable = true)
    private static void getKnockback(LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(
            cir.getReturnValue() - (AlluringEnchantment.getKnockbackFactor() * EnchantmentHelper.getEquipmentLevel(EEEnchantments.ALLURING, entity))
        );
    }

    @Inject(method = "getLevel(Lnet/minecraft/enchantment/Enchantment;Lnet/minecraft/item/ItemStack;)I", at = @At("HEAD"), cancellable = true)
    private static void getLevel(Enchantment enchantment, ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if (enchantment instanceof EEEnchantment && !((EEEnchantment) enchantment).getConfig().isEnabled()) {
            cir.setReturnValue(0);
        }
    }

    // @Redirect(
    //     method = "getPossibleEntries",
    //     at = @At(
    //         value = "FIELD",
    //         target = "Lnet/minecraft/enchantment/EnchantmentHelper;getPossibleEntries(ILnet/minecraft/item/ItemStack;Z)Ljava/util/List;"
    //     )
    // )

    @Inject(
        method = "getPossibleEntries",
        at = @At("RETURN"),
        cancellable = true
    )
    private static void getPossibleEntries(int power, ItemStack stack, boolean treasureAllowed, CallbackInfoReturnable<List<EnchantmentLevelEntry>> cir) {
        List<EnchantmentLevelEntry> list = cir.getReturnValue();

        // EnchantedExpanded.LOGGER.info("Possible enchantments for " + stack.getTranslationKey() + ": " + list.stream().map(e -> e.enchantment.getTranslationKey()).toList());
        
        List<EnchantmentLevelEntry> newList = new ArrayList<>(); 
        
        for (EnchantmentLevelEntry entry : list) {
            // EnchantedExpanded.LOGGER.info("\nEnchantment Entry: " + entry.enchantment.getTranslationKey() + " " + entry.level);

            boolean bl = false;
            
            if (stack.isOf(Items.BOOK)) {
                bl = true;
                // EnchantedExpanded.LOGGER.info("isOf book");
            }

            if (stack.isOf(Items.ENCHANTED_BOOK)) {
                bl = true;
                // EnchantedExpanded.LOGGER.info("isOf enchanted_book");
            }

            if (entry.enchantment.isAcceptableItem(stack)) {
                bl = true;
                // EnchantedExpanded.LOGGER.info("isAcceptableItem " + stack.getTranslationKey());
            }

            if (bl) {
                newList.add(entry);
            }
        }

        cir.setReturnValue(newList);
    }
    
    // @Inject(
    //     method = "generateEnchantments",
    //     at = @At("RETURN"),
    //     cancellable = true
    // )
    // private static void generateEnchantments(Random random, ItemStack stack, int level, boolean treasureAllowed, CallbackInfoReturnable<List<EnchantmentLevelEntry>> cir) {
    //     EnchantedExpanded.LOGGER.info("New Set");
        
    //     List<EnchantmentLevelEntry> list = cir.getReturnValue();

    //     if (!stack.isOf(Items.ENCHANTED_BOOK)) {
    //         list = list.stream().filter(entry -> entry.enchantment.isAcceptableItem(stack)).toList();
    //     }
    //     Set<Enchantment> set = new HashSet<>(); 

    //     for (int i = 0; i < list.size(); i+= 1) {
    //         EnchantmentLevelEntry entry = list.get(i);

    //         EnchantedExpanded.LOGGER.info("entry: " + entry.enchantment.getTranslationKey() + " " + entry.level);

    //         if (!set.contains(entry.enchantment)) {
    //             set.add(entry.enchantment);
    //         } else {
    //             list.remove(i);
    //             i -= 1;
    //             EnchantedExpanded.LOGGER.info("removed from list");
    //         }
    //     }

    //     cir.setReturnValue(list);
    // }
}
