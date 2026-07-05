package com.LazyFlesh.varioushorizons.variants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class VariantCommands extends CommandBase {

    @Override
    public String getCommandName() {
        return "variants";
    }

    @Override
    public List<String> getCommandAliases() {
        return new ArrayList<>(Collections.singleton("Variants"));
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/variants <set|list> [args...]";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 1;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length < 1) {
            printHelpFull(sender);
            return;
        }
        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "list" -> {
                if (args.length > 1 && args[1].equalsIgnoreCase("active")) {
                    StringBuilder formattedNames = new StringBuilder();
                    for (String name : VariantNames.getActiveVariantNames()) {
                        formattedNames.append(name)
                            .append(", ");
                    }
                    sender.addChatMessage(new ChatComponentText(formattedNames.toString()));
                } else {
                    sender.addChatMessage(new ChatComponentText(VariantNames.getVariantNamesFormatted()));
                }
            }
            case "set" -> {
                // make sure person can actually send command
                if (!sender.canCommandSenderUseCommand(getRequiredPermissionLevel(), getCommandName())) return;

                if (args.length > 1) {
                    // attempt at variant name
                    String arg1 = args[1].toLowerCase();
                    if (VariantNames.contains(arg1)) {
                        // if being set true/false
                        if (args.length >= 3) {
                            if (args[3].equalsIgnoreCase("false") || args[3].equalsIgnoreCase("true"))
                                VariantLoader.toggleVariant(
                                    sender,
                                    VariantNames.getVariantFromID(args[2]),
                                    Boolean.getBoolean(args[3]));
                        } else {
                            // this is when its just /variants set <variant name>
                            sender.addChatMessage(
                                new ChatComponentText(args[1] + " is " + VariantNames.activeContains(args[1])));
                        }
                    }
                }
            }
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        List<String> completions = new ArrayList<>();
        String currentArg = args.length == 0 ? "" : args[args.length - 1].trim();

        if (args.length == 1) {
            Stream.of("set", "list")
                .filter(s -> s.startsWith(currentArg))
                .forEach(completions::add);
        } else if (args.length == 2) {
            String subCommand = args[0].toLowerCase();
            if ("set".equals(subCommand)) {
                VariantNames.getVariantNames()
                    .stream()
                    .filter(s -> s.startsWith(currentArg))
                    .forEach(completions::add);
            } else if ("list".equals(subCommand)) {
                Stream.of("all", "active")
                    .filter(s -> s.startsWith(currentArg))
                    .forEach(completions::add);
            }
        } else if (args.length == 3) {
            String subCommand = args[0].toLowerCase();
            if ("set".equals(subCommand)) {
                Stream.of("true", "false")
                    .filter(s -> s.startsWith(currentArg))
                    .forEach(completions::add);
            }
        }

        return completions;
    }

    private void printHelpFull(ICommandSender sender) {
        sender.addChatMessage(new ChatComponentText("Usage: /variants <subcommand> [args...]"));
        sender.addChatMessage(new ChatComponentText(" Subcommands:"));
        sender.addChatMessage(
            new ChatComponentText(
                "  set <variant> - Set Variant state to true/false (on/off) (requires permission level 1)"));
        sender.addChatMessage(new ChatComponentText("  list <all/active> - Lists all/active Variants"));
    }
}
