package fireopal.enchantedexpanded.util;

import fireopal.enchantedexpanded.EnchantedExpanded;
import net.minecraft.block.Block;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class EETags {
    private static TagKey<Block> block(String id) {
        return TagKey.of(RegistryKeys.BLOCK, EnchantedExpanded.id(id));
    }

    private static TagKey<DamageType> damageType(String id) {
        return TagKey.of(RegistryKeys.DAMAGE_TYPE, EnchantedExpanded.id(id));
    }

    public static final TagKey<Block> MELTS_FREEZING_ARROWS = block("melts_freezing_arrows");

    public static final TagKey<DamageType> MAGIC_DAMAGE_TYPE = damageType("magic");

    
}
