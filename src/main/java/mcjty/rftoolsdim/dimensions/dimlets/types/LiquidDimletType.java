package mcjty.rftoolsdim.dimensions.dimlets.types;

import mcjty.rftoolsdim.dimensions.dimlets.DimletKey;
import mcjty.rftoolsdim.dimensions.dimlets.DimletRandomizer;
import mcjty.rftoolsdim.dimensions.DimensionInformation;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.config.Configuration;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Random;

public class LiquidDimletType implements IDimletType {

    @Override
    public String getName() {
        return "Liquid";
    }

    @Override
    public String getOpcode() {
        return "L";
    }

    @Override
    public String getTextureName() {
        return "liquidDimlet";
    }

    @Override
    public void setupFromConfig(Configuration cfg) {
    }

    @Override
    public boolean isModifier() {
        return true;
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
        return false;
    }

    @Override
    public void inject(DimletKey key, DimensionInformation dimensionInformation) {

    }

    @Override
    public void constructDimension(List<Pair<DimletKey, List<DimletKey>>> dimlets, Random random, DimensionInformation dimensionInformation) {
        // As a modifier this is handled in the dimlet that is being modified.
    }

    @Override
    public String[] getInformation() {
        return new String[] { "This is a modifier for terrain, lake, or liquid orbs.", "Put these dimlets BEFORE the thing you want", "to change." };
    }

    private static boolean isValidLiquidEssence(ItemStack stackEssence, NBTTagCompound essenceCompound) {
//        Block essenceBlock = BlockTools.getBlock(stackEssence);
//
//        if (essenceBlock != DimletConstructionSetup.liquidAbsorberBlock) {
//            return false;
//        }
//        if (essenceCompound == null) {
//            return false;
//        }
//        int absorbing = essenceCompound.getInteger("absorbing");
//        int blockID = essenceCompound.getInteger("liquid");
//        if (absorbing > 0 || blockID == -1) {
//            return false;
//        }
        return true;
    }

    private static DimletKey findLiquidDimlet(NBTTagCompound essenceCompound) {
//        int blockID = essenceCompound.getInteger("liquid");
//        for (Map.Entry<DimletKey, Block> entry : DimletObjectMapping.idToFluid.entrySet()) {
//            if (entry.getValue() != null) {
//                int id = Block.blockRegistry.getIDForObject(entry.getValue());
//                if (blockID == id) {
//                    return entry.getKey();
//                }
//            }
//        }
        return null;
    }

    @Override
    public DimletKey attemptDimletCrafting(ItemStack stackController, ItemStack stackMemory, ItemStack stackEnergy, ItemStack stackEssence) {
        if (!isValidLiquidEssence(stackEssence, stackEssence.getTagCompound())) {
            return null;
        }
        DimletKey liquidDimlet = findLiquidDimlet(stackEssence.getTagCompound());
        if (liquidDimlet == null) {
            return null;
        }
        if (!DimletCraftingTools.matchDimletRecipe(liquidDimlet, stackController, stackMemory, stackEnergy)) {
            return null;
        }
        return liquidDimlet;
    }
}
