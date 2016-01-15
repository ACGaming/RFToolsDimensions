package mcjty.rftoolsdim.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public abstract class AbstractRfToolsCommand implements RfToolsCommand {

    protected String fetchString(ICommandSender sender, String[] args, int index, String defaultValue) {
        try {
            return args[index];
        } catch (ArrayIndexOutOfBoundsException e) {
            return defaultValue;
        }
    }

    protected boolean fetchBool(ICommandSender sender, String[] args, int index, boolean defaultValue) {
        boolean value;
        try {
            value = Boolean.valueOf(args[index]);
        } catch (NumberFormatException e) {
            value = false;
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Parameter is not a valid boolean!"));
        } catch (ArrayIndexOutOfBoundsException e) {
            return defaultValue;
        }
        return value;
    }

    protected int fetchInt(ICommandSender sender, String[] args, int index, int defaultValue) {
        int value;
        try {
            value = Integer.parseInt(args[index]);
        } catch (NumberFormatException e) {
            value = 0;
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Parameter is not a valid integer!"));
        } catch (ArrayIndexOutOfBoundsException e) {
            return defaultValue;
        }
        return value;
    }

    protected float fetchFloat(ICommandSender sender, String[] args, int index, float defaultValue) {
        float value;
        try {
            value = Float.parseFloat(args[index]);
        } catch (NumberFormatException e) {
            value = 0.0f;
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Parameter is not a valid real number!"));
        } catch (ArrayIndexOutOfBoundsException e) {
            return defaultValue;
        }
        return value;
    }

    @Override
    public boolean isClientSide() {
        return false;
    }
}
