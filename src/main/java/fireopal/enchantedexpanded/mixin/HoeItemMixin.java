package fireopal.enchantedexpanded.mixin;

import java.util.Collection;
import java.util.Objects;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import fireopal.enchantedexpanded.enchantments.EEEnchantments;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(HoeItem.class)
public class HoeItemMixin {
    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    private void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        ItemStack stack = context.getStack();
        PlayerEntity player = context.getPlayer();
        BlockState state;
        Item item;

        if (
            EnchantmentHelper.get(stack).containsKey(EEEnchantments.HARVEST) &&
            (
                (state = world.getBlockState(pos)).getBlock() instanceof CropBlock ||
                state.getBlock() instanceof NetherWartBlock
            ) &&
            (
                player.isCreative() ||
                player.getInventory().containsAny(
                    s -> s.isOf(
                        state.getBlock().getPickStack(world, pos, state).getItem()
                    )
                )
            )
        ) {
            Collection<Property<?>> properties = state.getProperties();
            Property<Integer> property = null;
            int max = 0;

            for (Property<?> p : properties) {
                if (p instanceof IntProperty && p.getName().equals("age")) {
                    property = ((IntProperty) p);

                    for (int i : ((IntProperty) p).getValues()) {
                        if (i > max) max = i;
                    }

                    break;
                }
            }

            // EnchantedExpanded.LOGGER.info("Property: " + property);

            if (!(Objects.isNull(property)) && state.get(property) == max) {
                // EnchantedExpanded.LOGGER.info("Property Value: " + state.get(property));

                item = state.getBlock().getPickStack(world, pos, state).getItem();
                
                if (!player.isCreative()) {     
                    player.getInventory().remove(s -> s.isOf(item), 1, player.getInventory());
                } 

                world.breakBlock(pos, true, player);
                world.setBlockState(pos, state.with(property, 0));
                cir.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }
}
