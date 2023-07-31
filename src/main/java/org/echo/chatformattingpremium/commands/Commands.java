package org.echo.chatformattingpremium.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.echo.chatformattingpremium.ChatFormatting;

public class Commands implements CommandExecutor {

    private ChatFormatting plugin;

    public Commands(ChatFormatting main) { this.plugin = main; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!player.hasPermission("chatformatting.reload")) {
                    player.spigot().sendMessage(plugin.getMessages().getCommandsPermission());
                    return true;
                }
            }
            plugin.reloadPlugin();
            sender.sendMessage("§a[ChatFormatting] successfully reloaded.");
            return true;
        }
        return false;
    }
}
