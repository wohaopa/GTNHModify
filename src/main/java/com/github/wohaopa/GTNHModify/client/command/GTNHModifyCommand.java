package com.github.wohaopa.GTNHModify.client.command;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

public class GTNHModifyCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "gtnh-modify";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return sender instanceof EntityClientPlayerMP;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public List<String> getCommandAliases() {
        return Arrays.asList("gtnh-m", "gtm");
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.gtnh-modify.usage";
    }

    private static final List<String> subCmds = Stream.of("hello", "export", "help")
        .sorted()
        .collect(Collectors.toList());

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {

        if (args.length < 1) return subCmds;
        if (args.length == 1) {
            String test = args[0];
            return subCmds.stream()
                .filter(s -> test.isEmpty() || s.startsWith(test))
                .collect(Collectors.toList());
        }
        if (args.length == 2) {
            String subCmd = args[0];
            String test = args[1];
            if (subCmd.equals("help")) {
                return subCmds.stream()
                    .filter(s -> test.isEmpty() || s.startsWith(test))
                    .collect(Collectors.toList());
            }
        }
        return null;
    }

    private void printHelps(ICommandSender sender) {
        for (String str : subCmds) {
            printHelp(sender, str);
        }
    }

    private void printHelp(ICommandSender sender, String subCmd) {
        sender.addChatMessage(new ChatComponentTranslation("commands.gtnh-modify." + subCmd + ".usage"));
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (sender instanceof EntityClientPlayerMP) {
            if (args.length < 1) {
                printHelps(sender);
                return;
            }
            if (args.length == 1) {
                String test = args[0];
                switch (test) {
                    case "help" -> printHelps(sender);
                    case "hello" -> sender.addChatMessage(new ChatComponentText("你好"));
                    default -> {
                        if (subCmds.contains(test)) {
                            printHelp(sender, test);
                        } else {
                            StringBuilder stringBuilder = new StringBuilder("Not Found Subject Command! \n");
                            boolean flag = true;
                            for (String s : subCmds) {
                                if (s.startsWith(test)) {
                                    if (flag) {
                                        stringBuilder.append("Maybe: ");
                                        flag = false;
                                    }
                                    stringBuilder.append(s)
                                        .append(", ");
                                }
                            }
                            sender.addChatMessage(new ChatComponentText(stringBuilder.toString()));
                        }
                    }
                }
                return;
            }

        }
    }
}
