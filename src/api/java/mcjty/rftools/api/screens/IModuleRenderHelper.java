package mcjty.rftools.api.screens;

import mcjty.rftools.api.screens.data.IModuleDataContents;
import net.minecraft.client.gui.FontRenderer;

/**
 * Some convenience methods to help render screen modules
 */
public interface IModuleRenderHelper {

    void renderLevel(FontRenderer fontRenderer, int xoffset, int currenty, IModuleDataContents screenData, String label, boolean hidebar, boolean hidetext, boolean showpct, boolean showdiff,
                     int poscolor, int negcolor,
                     int gradient1, int gradient2, FormatStyle formatStyle);

    String format(String in, FormatStyle style);

}
