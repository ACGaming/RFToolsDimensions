package mcjty.rftools.api.screens;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public interface IClientScreenModule {
    public enum TransformMode {
        NONE,
        TEXT,
        TEXTLARGE,
        ITEM
    }

    TransformMode getTransformMode();

    int getHeight();

    void render(IModuleRenderHelper renderHelper, FontRenderer fontRenderer, int currenty, Object[] screenData, float factor);

    void mouseClick(World world, int x, int y, boolean clicked);

    void createGui(IModuleGuiBuilder guiBuilder);

    void setupFromNBT(NBTTagCompound tagCompound, int dim, int x, int y, int z);

    // Return true if this module needs server data.
    boolean needsServerData();
}
