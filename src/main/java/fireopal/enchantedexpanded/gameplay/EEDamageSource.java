package fireopal.enchantedexpanded.gameplay;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
// import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class EEDamageSource {
    public static final RegistryKey<DamageType> BRUTALITY = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("brutality"));

    private final DamageSource brutalityDamageSource = new DamageSource(this.registry.entryOf(key));
}
