package mcjty.rftoolsdim.blocks.workbench;

import mcjty.lib.container.GenericGuiContainer;
import mcjty.lib.gui.Window;
import mcjty.lib.gui.events.ButtonEvent;
import mcjty.lib.gui.layout.PositionalLayout;
import mcjty.lib.gui.widgets.Button;
import mcjty.lib.gui.widgets.*;
import mcjty.lib.gui.widgets.Panel;
import mcjty.lib.network.Argument;
import mcjty.rftoolsdim.RFToolsDim;
import mcjty.rftoolsdim.dimensions.dimlets.DimletKey;
import mcjty.rftoolsdim.dimensions.dimlets.KnownDimletConfiguration;
import mcjty.rftoolsdim.items.ModItems;
import mcjty.rftoolsdim.network.RFToolsDimMessages;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class GuiDimletWorkbench extends GenericGuiContainer<DimletWorkbenchTileEntity> {
    public static final int WORKBENCH_WIDTH = 200;
    public static final int WORKBENCH_HEIGHT = 224;

    private EnergyBar energyBar;
    private Button extractButton;
    private ImageLabel progressIcon;

    private static final ResourceLocation iconLocation = new ResourceLocation(RFToolsDim.MODID, "textures/gui/dimletworkbench.png");
    private static final ResourceLocation iconGuiElements = new ResourceLocation(RFToolsDim.MODID, "textures/gui/guielements.png");

    public GuiDimletWorkbench(DimletWorkbenchTileEntity dimletWorkbenchTileEntity, DimletWorkbenchContainer container) {
        super(RFToolsDim.instance, RFToolsDimMessages.INSTANCE, dimletWorkbenchTileEntity, container, RFToolsDim.GUI_MANUAL_DIMENSION, "create");

        xSize = WORKBENCH_WIDTH;
        ySize = WORKBENCH_HEIGHT;
    }

    @Override
    public void initGui() {
        super.initGui();

        int maxEnergyStored = tileEntity.getMaxEnergyStored(EnumFacing.DOWN);
        energyBar = new EnergyBar(mc, this).setVertical().setMaxValue(maxEnergyStored).setLayoutHint(new PositionalLayout.PositionalHint(8, 142, 8, 54)).setShowText(false);
        energyBar.setValue(tileEntity.getCurrentRF());

        progressIcon = new ImageLabel(mc, this).setImage(iconGuiElements, 4 * 16, 16);
        progressIcon.setLayoutHint(new PositionalLayout.PositionalHint(135, 6, 16, 16));

        extractButton = new Button(mc, this).setText("Extract").setLayoutHint(new PositionalLayout.PositionalHint(36, 7, 55, 14)).addButtonEvent(
                new ButtonEvent() {
                    @Override
                    public void buttonClicked(Widget parent) {
                        extractDimlet();
                    }
                }
        ).setTooltips("Deconstruct a dimlet into its parts");

        Widget toplevel = new Panel(mc, this).setBackground(iconLocation).setLayout(new PositionalLayout()).addChild(extractButton).addChild(energyBar).addChild(progressIcon);
        toplevel.setBounds(new Rectangle(guiLeft, guiTop, xSize, ySize));

        window = new Window(this, toplevel);
    }

    private void extractDimlet() {
        Slot slot = inventorySlots.getSlot(DimletWorkbenchContainer.SLOT_INPUT);
        if (slot.getStack() != null) {
            ItemStack itemStack = slot.getStack();
            if (ModItems.knownDimletItem.equals(itemStack.getItem())) {
                DimletKey key = KnownDimletConfiguration.getDimletKey(itemStack);
                if (!KnownDimletConfiguration.isCraftable(key)) {
//                    Achievements.trigger(Minecraft.getMinecraft().thePlayer, Achievements.smallBits);
                    sendServerCommand(RFToolsDimMessages.INSTANCE, DimletWorkbenchTileEntity.CMD_STARTEXTRACT);
                }
            }
        }
    }

    private void enableButtons() {
        boolean enabled = false;
        Slot slot = inventorySlots.getSlot(DimletWorkbenchContainer.SLOT_INPUT);
        if (slot.getStack() != null) {
            ItemStack itemStack = slot.getStack();
            if (ModItems.knownDimletItem.equals(itemStack.getItem())) {
                DimletKey key = KnownDimletConfiguration.getDimletKey(itemStack);
                if (!KnownDimletConfiguration.isCraftable(key)) {
                    enabled = true;
                }
            }
        }
        extractButton.setEnabled(enabled);
    }



    @Override
    protected void drawGuiContainerBackgroundLayer(float v, int i, int i2) {
        enableButtons();

        int extracting = tileEntity.getExtracting();
        if (extracting == 0) {
            progressIcon.setImage(iconGuiElements, 4 * 16, 16);
        } else {
            progressIcon.setImage(iconGuiElements, (extracting % 4) * 16, 16);
        }

        drawWindow();

        energyBar.setValue(tileEntity.getCurrentRF());

        tileEntity.requestRfFromServer(RFToolsDim.MODID);
        tileEntity.requestExtractingFromServer();
    }
}
