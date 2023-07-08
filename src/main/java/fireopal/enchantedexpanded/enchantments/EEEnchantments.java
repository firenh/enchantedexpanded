package fireopal.enchantedexpanded.enchantments;

import fireopal.enchantedexpanded.EnchantedExpanded;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class EEEnchantments {
    private static Enchantment register(String id, Enchantment enchantment) {
        // EnchantedExpanded.LOGGER.info("Registering Enchantment " + EnchantedExpanded.MODID + ":" + id);
        return Registry.register(Registries.ENCHANTMENT, EnchantedExpanded.id(id), enchantment);
    }

    public static void init() {
        new EEEnchantments();
    }

    public static final EquipmentSlot[] ARMOR_SLOTS = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    public static final Enchantment FROST_ASPECT = register("frost_aspect", new FrostAspectEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
    public static final Enchantment WITHERING = register("withering", new WitheringEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));
    public static final Enchantment MAGIC_PROTECTION = register("magic_protection", new MagicProtectionEnchantment(Enchantment.Rarity.COMMON, EnchantmentTarget.ARMOR, ARMOR_SLOTS));
    public static final Enchantment ALLURING = register("alluring", new AlluringEnchantment(Enchantment.Rarity.COMMON, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));
    public static final Enchantment BRUTALITY = register("brutality", new BrutalityEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND));
    public static final Enchantment HARVEST = register("harvest", new HarvestEnchantment(Enchantment.Rarity.COMMON, EquipmentSlot.MAINHAND));
    public static final Enchantment LAUNCHING = register("launching", new LaunchingEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND));
    public static final Enchantment FREEZING = register("freezing", new FreezingEnchantment(Enchantment.Rarity.RARE, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}));
    public static final Enchantment DETONATION = register("detonation", new DetonationEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));
    public static final Enchantment VELOCITY = register("velocity", new VelocityEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.CROSSBOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}));
    public static final Enchantment BLAZING = register("blazing", new BlazingEnchantment(Enchantment.Rarity.COMMON, EnchantmentTarget.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}));
    public static final Enchantment MAGNITUDE = register("magnitude", new MagnitudeEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));
    public static final Enchantment GROWTH = register("growth", new GrowthEnchantment(Enchantment.Rarity.VERY_RARE, ARMOR_SLOTS));
    public static final Enchantment ADRENALINE = register("adrenaline", new AdrenalineEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentTarget.ARMOR_CHEST, EquipmentSlot.CHEST));
}
