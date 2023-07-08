package fireopal.enchantedexpanded.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import fireopal.enchantedexpanded.enchantments.EEEnchantments;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

@Mixin(FlintAndSteelItem.class)
public class FlintAndSteelItemMixin {
    @Inject(
        method = "useOnBlock",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"
        ),
        slice = @Slice(
            from = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/block/AbstractFireBlock;canPlaceAt(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z"
            ),
            to = @At("TAIL")
        ),
        cancellable = true
    )
    private void injectUseOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        if (EnchantmentHelper.getLevel(EEEnchantments.BLAZING, context.getStack()) > 0) { 
            PlayerEntity playerEntity = context.getPlayer();
            BlockPos blockPos = context.getBlockPos();
            World world = context.getWorld();
            BlockPos blockPos2 = blockPos.offset(context.getSide());
            
            for (Direction dir : Direction.values()) {
                BlockPos blockPos3 = blockPos2.offset(dir);

                if (AbstractFireBlock.canPlaceAt(world, blockPos3, context.getHorizontalPlayerFacing())) {
                    world.setBlockState(blockPos3, AbstractFireBlock.getState(world, blockPos3), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
                    world.emitGameEvent((Entity)playerEntity, GameEvent.BLOCK_PLACE, blockPos);
                }
            }
        }
    }
}
