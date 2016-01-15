package mcjty.rftoolsdim.dimensions.dimlets.types;

import mcjty.rftoolsdim.dimensions.DimletConfiguration;
import mcjty.rftoolsdim.dimensions.description.SkyDescriptor;
import mcjty.rftoolsdim.dimensions.dimlets.DimletKey;
import mcjty.rftoolsdim.dimensions.dimlets.DimletObjectMapping;
import mcjty.rftoolsdim.dimensions.dimlets.DimletRandomizer;
import mcjty.rftoolsdim.dimensions.DimensionInformation;
import mcjty.rftoolsdim.dimensions.types.SkyType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SkyDimletType implements IDimletType {
    private static final String CATEGORY_TYPE = "type_sky";

    private static int rarity = DimletRandomizer.RARITY_0;
    private static int baseCreationCost = 100;
    private static int baseMaintainCost = 1;
    private static int baseTickCost = 1;

    @Override
    public String getName() {
        return "Sky";
    }

    @Override
    public String getOpcode() {
        return "s";
    }

    @Override
    public String getTextureName() {
        return "skyDimlet";
    }

    @Override
    public void setupFromConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_TYPE, "Settings for the sky dimlet type");
        rarity = cfg.get(CATEGORY_TYPE, "rarity", rarity, "Default rarity for this dimlet type").getInt();
        baseCreationCost = cfg.get(CATEGORY_TYPE, "creation.cost", baseCreationCost, "Dimlet creation cost (how much power this dimlets adds during creation time of a dimension)").getInt();
        baseMaintainCost = cfg.get(CATEGORY_TYPE, "maintenance.cost", baseMaintainCost, "Dimlet maintenance cost (how much power this dimlet will use up to keep the dimension running)").getInt();
        baseTickCost = cfg.get(CATEGORY_TYPE, "tick.cost", baseTickCost, "Dimlet tick cost (how long it takes to make a dimension with this dimlet in it)").getInt();
    }

    @Override
    public int getRarity() {
        return rarity;
    }

    @Override
    public int getCreationCost() {
        return baseCreationCost;
    }

    @Override
    public int getMaintenanceCost() {
        return baseMaintainCost;
    }

    @Override
    public int getTickCost() {
        return baseTickCost;
    }

    @Override
    public boolean isModifier() {
        return false;
    }

    @Override
    public boolean isModifiedBy(DimletType type) {
        return false;
    }

    @Override
    public float getModifierCreateCostFactor(DimletType modifierType, DimletKey key) {
        return 1.0f;
    }

    @Override
    public float getModifierMaintainCostFactor(DimletType modifierType, DimletKey key) {
        return 1.0f;
    }

    @Override
    public float getModifierTickCostFactor(DimletType modifierType, DimletKey key) {
        return 1.0f;
    }

    @Override
    public boolean isInjectable() {
        return true;
    }

    @Override
    public void inject(DimletKey key, DimensionInformation dimensionInformation) {
        SkyDescriptor.Builder builder = new SkyDescriptor.Builder();
        builder.combine(dimensionInformation.getSkyDescriptor());
        SkyDescriptor newDescriptor = DimletObjectMapping.getSky(key);
        if (newDescriptor.specifiesFogColor()) {
            builder.resetFogColor();
        }
        if (newDescriptor.specifiesSkyColor()) {
            builder.resetSkyColor();
        }
        if (dimensionInformation.isPatreonBitSet(Patreons.PATREON_DARKCORVUS)) {
            builder.skyType(SkyType.SKY_STARS3);
        }
        builder.combine(newDescriptor);
        dimensionInformation.setSkyDescriptor(builder.build());
    }

    @Override
    public void constructDimension(List<Pair<DimletKey, List<DimletKey>>> dimlets, Random random, DimensionInformation dimensionInformation) {
        dimlets = DimensionInformation.extractType(DimletType.DIMLET_SKY, dimlets);
        if (dimlets.isEmpty()) {
            if (random.nextFloat() < DimletConfiguration.randomSpecialSkyChance) {
                // If nothing was specified then there is random chance we get random sky stuff.
//                List<DimletKey> skyIds = new ArrayList<>(DimletObjectMapping.idToSkyDescriptor.keySet());
//                for (int i = 0 ; i < 1+random.nextInt(3) ; i++) {
//                    DimletKey key = skyIds.get(random.nextInt(skyIds.size()));
//                    List<DimletKey> modifiers = Collections.emptyList();
//                    dimlets.add(Pair.of(key, modifiers));
//                }
                // @todo
            }

            if (random.nextFloat() < DimletConfiguration.randomSpecialSkyChance) {
                List<DimletKey> bodyKeys = new ArrayList<>();
//                for (DimletKey key : DimletObjectMapping.idToSkyDescriptor.keySet()) {
//                    if (DimletObjectMapping.celestialBodies.contains(key)) {
//                        bodyKeys.add(key);
//                    }
//                }
                // @todo

//                for (int i = 0 ; i < random.nextInt(3) ; i++) {
//                    DimletKey key = bodyKeys.get(random.nextInt(bodyKeys.size()));
//                    List<DimletKey> modifiers = Collections.emptyList();
//                    dimlets.add(Pair.of(key, modifiers));
//                }
            }
        }

        SkyDescriptor.Builder builder = new SkyDescriptor.Builder();
        for (Pair<DimletKey, List<DimletKey>> dimletWithModifiers : dimlets) {
            DimletKey key = dimletWithModifiers.getKey();
            builder.combine(DimletObjectMapping.getSky(key));
        }
        if (dimensionInformation.isPatreonBitSet(Patreons.PATREON_DARKCORVUS)) {
            builder.skyType(SkyType.SKY_STARS3);
        }
        dimensionInformation.setSkyDescriptor(builder.build());
    }

    @Override
    public String[] getInformation() {
        return new String[] { "Control various features of the sky", "like sky color, fog color, celestial bodies, ..." };
    }

    @Override
    public DimletKey attemptDimletCrafting(ItemStack stackController, ItemStack stackMemory, ItemStack stackEnergy, ItemStack stackEssence) {
        return null;
    }
}
