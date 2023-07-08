package fireopal.enchantedexpanded.gameplay;

import java.util.List;

import fireopal.enchantedexpanded.enchantments.EEEnchantments;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.EnchantRandomlyLootFunction;
import net.minecraft.util.Identifier;

public class AddToLootTable {
    private static final List<EELootTableModification> MODIFICATIONS = List.of(
        new EELootTableModification(LootTables.WOODLAND_MANSION_CHEST, EEEnchantments.MAGNITUDE, 0.1f),
        new EELootTableModification(LootTables.STRONGHOLD_LIBRARY_CHEST, EEEnchantments.GROWTH, 0.33f),
        new EELootTableModification(LootTables.NETHER_BRIDGE_CHEST, EEEnchantments.BRUTALITY, 0.25f),
        new EELootTableModification(LootTables.PILLAGER_OUTPOST_CHEST, EEEnchantments.VELOCITY, 0.45f)
    );

    public static final Identifier NETHER_FORTRESS_LOOT_TABLE_ID = new Identifier("chests/nether_bridge");

    public static void init() {
        for (EELootTableModification modification : MODIFICATIONS) {
            LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
                if (source.isBuiltin() && modification.lootTableId().equals(id)) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                        .with(
                            ItemEntry.builder(Items.BOOK)
                                .conditionally(RandomChanceLootCondition.builder(modification.chance()))
                                .apply(new EnchantRandomlyLootFunction.Builder().add(modification.enchantment()))
                        );
            
                    tableBuilder.pool(poolBuilder);
                }
            });
        }
    }

    private static record EELootTableModification(Identifier lootTableId, Enchantment enchantment, float chance) {}
}
