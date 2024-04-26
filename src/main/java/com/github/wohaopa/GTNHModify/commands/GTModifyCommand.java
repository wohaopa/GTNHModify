package com.github.wohaopa.GTNHModify.commands;

import java.util.Arrays;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;

import com.github.wohaopa.GTNHModify.DumperThread;
import com.github.wohaopa.GTNHModify.GT_Recipes;

public class GTModifyCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "gtnhmodify";
    }

    @Override
    public List<String> getCommandAliases() {
        return Arrays.asList("gtm");
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "gtnhmodify.cmd.echo.usage";
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        return Arrays.asList("print", "dump", "hand");
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length != 1) {
            sender.addChatMessage(new ChatComponentText("未知指令！"));
            return;
        }

        switch (args[0]) {
            case "print":
                int size = GT_Recipes.recipes.size();
                ChatComponentText text = new ChatComponentText("共有：" + size + "配方");
                sender.addChatMessage(text);
                break;
            case "dump":

                DumperThread dumperThread = null;
                try {
                    dumperThread = new DumperThread((str) -> {
                        sender.addChatMessage(new ChatComponentText(str));
                        return null;
                    });
                    sender.addChatMessage(new ChatComponentText("导出开始！"));
                    dumperThread.start();
                } catch (Exception e) {
                    sender.addChatMessage(new ChatComponentText("请勿重复导出！"));
                }

                break;
            case "hand":
                if (sender instanceof EntityPlayerMP playerMP) {
                    ItemStack itemStack = playerMP.getHeldItem();
                    String msg = "null";
                    if (itemStack != null && itemStack.getItem() != null) {

                        msg = Item.itemRegistry.getNameForObject(itemStack.getItem());
                    }
                    sender.addChatMessage(new ChatComponentText(msg));
                }
                break;
            default:
                sender.addChatMessage(new ChatComponentText("未知指令！"));
        }
    }
}
