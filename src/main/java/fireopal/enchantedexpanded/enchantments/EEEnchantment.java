package fireopal.enchantedexpanded.enchantments;

import eu.pb4.polymer.core.api.utils.PolymerObject;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class EEEnchantment extends Enchantment implements PolymerObject {
    protected EEEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
    }

    public String getName() {
        return null;
    }

    public boolean isEnabled() {
        return true;
    }
    
    // public EnchantmentProperties defaultProperties() {
    //     Map<String, EnchantmentProperty<?>> properties = new HashMap<>();

    //     return new EnchantmentProperties(true, properties);
    // }
}
