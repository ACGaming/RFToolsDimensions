package mcjty.rftoolsdim.dimensions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;

public class DamageSourcePowerLow extends DamageSource {
    public DamageSourcePowerLow(String damageType) {
        super(damageType);
        setDamageBypassesArmor();
        setDamageIsAbsolute();
    }

    @Override
    public IChatComponent getDeathMessage(EntityLivingBase entity) {
        String s = "death.dimension.powerfailure";
        return new ChatComponentTranslation(s, entity.getName());
    }
}
