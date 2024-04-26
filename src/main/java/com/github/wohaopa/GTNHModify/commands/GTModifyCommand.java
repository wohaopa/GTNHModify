package com.github.wohaopa.GTNHModify.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;

import com.github.wohaopa.GTNHModify.DumperThread;
import com.github.wohaopa.GTNHModify.GT_Recipes;
import com.github.wohaopa.GTNHModify.IconDumper;

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

    List<String> subCmd = Arrays.asList("print", "dump", "hand", "export");

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        if (args.length == 0 || args[0].isEmpty()) return subCmd;
        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            for (String s : subCmd) {
                if (s.startsWith(args[0]) && !s.equals(args[0])) list.add(s);
            }
            if (list.size() == 0) return subCmd;
            else return list;
        }
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length < 1) {
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
                        msg += ":" + itemStack.getItemDamage();
                    }
                    sender.addChatMessage(new ChatComponentText(msg));
                }
                break;

            case "export":
                if (sender instanceof EntityPlayerMP playerMP) {

                    try {
                        Class<?> clazz = Class.forName("net.minecraft.client.Minecraft");
                    } catch (ClassNotFoundException e) {
                        sender.addChatMessage(new ChatComponentText("请勿在服务端使用该指令！"));
                        break;
                    }

                    if (args.length < 2) {
                        sender.addChatMessage(new ChatComponentText("参数错误！/gtm export <方块> [子ID/子ID始] [子ID末]"));
                        break;
                    }

                    String itemName = args[1];
                    Item item = (Item) Item.itemRegistry.getObject(itemName);
                    if (item == null) {
                        sender.addChatMessage(new ChatComponentText("未知物品" + itemName + "！"));
                        break;
                    }

                    List<ItemStack> itemStacks = new ArrayList<>();

                    if (args.length == 3) {
                        int id = Integer.parseInt(args[2]);

                        ItemStack itemStack = new ItemStack(item);
                        itemStack.setItemDamage(id);
                        itemStacks.add(itemStack);
                    } else if (args.length == 4) {
                        int id1 = Integer.parseInt(args[2]);
                        int id2 = Integer.parseInt(args[3]);

                        for (int i = id1; i <= id2; i++) {
                            ItemStack itemStack = new ItemStack(item);
                            itemStack.setItemDamage(i);
                            itemStacks.add(itemStack);
                        }

                    }

                    else itemStacks.add(new ItemStack(item));

                    Minecraft.getMinecraft()
                        .displayGuiScreen(new IconDumper(Minecraft.getMinecraft().currentScreen, itemStacks));
                }

                break;
            default:
                sender.addChatMessage(new ChatComponentText("未知指令！"));
        }
    }
}
