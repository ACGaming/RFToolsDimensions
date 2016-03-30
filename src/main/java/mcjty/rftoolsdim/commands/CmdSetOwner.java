package mcjty.rftoolsdim.commands;

import mcjty.rftoolsdim.dimensions.DimensionInformation;
import mcjty.rftoolsdim.dimensions.RfToolsDimensionManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.TextComponentString;
import net.minecraft.util.TextFormatting;
import net.minecraft.world.World;

public class CmdSetOwner extends AbstractRfToolsCommand {
    @Override
    public String getHelp() {
        return "<id> <owner>";
    }

    @Override
    public String getCommand() {
        return "setowner";
    }

    @Override
    public int getPermissionLevel() {
        return 2;
    }

    @Override
    public boolean isClientSide() {
        return false;
    }

    @Override
    public void execute(ICommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.addChatMessage(new TextComponentString(TextFormatting.RED + "The dimension and player parameters are missing!"));
            return;
        } else if (args.length > 3) {
            sender.addChatMessage(new TextComponentString(TextFormatting.RED + "Too many parameters!"));
            return;
        }

        int dim = fetchInt(sender, args, 1, 0);
        String playerName = fetchString(sender, args, 2, null);

        World world = sender.getEntityWorld();
        RfToolsDimensionManager dimensionManager = RfToolsDimensionManager.getDimensionManager(world);
        if (dimensionManager.getDimensionDescriptor(dim) == null) {
            sender.addChatMessage(new TextComponentString(TextFormatting.RED + "Not an RFTools dimension!"));
            return;
        }

        for (Object o : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) o;
            if (playerName.equals(entityPlayerMP.getDisplayName())) {
                DimensionInformation information = dimensionManager.getDimensionInformation(dim);
                information.setOwner(playerName, entityPlayerMP.getGameProfile().getId());
                sender.addChatMessage(new TextComponentString(TextFormatting.GREEN + "Owner of dimension changed!"));
                dimensionManager.save(world);
                return;
            }
        }
        sender.addChatMessage(new TextComponentString(TextFormatting.RED + "Could not find player!"));
    }
}
