package fireopal.enchantedexpanded.enchantments;

import fireopal.enchantedexpanded.EnchantedExpanded;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class EEEnchantments {
    private static Enchantment register(EEEnchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, EnchantedExpanded.id(enchantment.getName()), enchantment);
    }

    public static void init() {
        new EEEnchantments();
    }

    public static final EquipmentSlot[] ARMOR_SLOTS = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    public static final Enchantment FROST_ASPECT = register(new FrostAspectEnchantment("frost_aspect", Enchantment.Rarity.RARE, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));
    public static final Enchantment WITHERING = register(new WitheringEnchantment("withering", Enchantment.Rarity.VERY_RARE, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));
    public static final Enchantment MAGIC_PROTECTION = register(new MagicProtectionEnchantment("magic_protection", Enchantment.Rarity.COMMON, EnchantmentTarget.ARMOR, ARMOR_SLOTS));
    public static final Enchantment ALLURING = register(new AlluringEnchantment("alluring", Enchantment.Rarity.COMMON, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));
    public static final Enchantment BRUTALITY = register(new BrutalityEnchantment("brutality", Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));
    public static final Enchantment HARVEST = register(new HarvestEnchantment("harvest", Enchantment.Rarity.COMMON, EnchantmentTarget.DIGGER, EquipmentSlot.MAINHAND));
    public static final Enchantment LAUNCHING = register(new LaunchingEnchantment("launching", Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));
    public static final Enchantment FREEZING = register(new FreezingEnchantment("freezing", Enchantment.Rarity.RARE, EnchantmentTarget.CROSSBOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}));
    public static final Enchantment DETONATION = register(new DetonationEnchantment("detonation", Enchantment.Rarity.VERY_RARE, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));
    public static final Enchantment VELOCITY = register(new VelocityEnchantment("velocity", Enchantment.Rarity.UNCOMMON, EnchantmentTarget.CROSSBOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}));
    public static final Enchantment BLAZING = register(new BlazingEnchantment("blazing", Enchantment.Rarity.COMMON, EnchantmentTarget.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}));
    public static final Enchantment MAGNITUDE = register(new MagnitudeEnchantment("magnitude", Enchantment.Rarity.VERY_RARE, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));
    public static final Enchantment GROWTH = register(new GrowthEnchantment("growth", Enchantment.Rarity.VERY_RARE, EnchantmentTarget.BREAKABLE, ARMOR_SLOTS));
    public static final Enchantment ADRENALINE = register(new AdrenalineEnchantment("adrenaline", Enchantment.Rarity.VERY_RARE, EnchantmentTarget.ARMOR_CHEST, EquipmentSlot.CHEST));
}
