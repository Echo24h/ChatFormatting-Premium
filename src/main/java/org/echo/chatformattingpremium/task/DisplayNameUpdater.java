package org.echo.chatformattingpremium.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.echo.chatformattingpremium.ChatFormatting;
import org.echo.chatformattingpremium.format.formatters.DisplayNamesFormat;

public class DisplayNameUpdater extends BukkitRunnable {

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            DisplayNamesFormat.setPlayerDisplayName(ChatFormatting.getInstance(), player);
        }
    }

    // Méthode pour démarrer la mise à jour du display name à intervalle régulier
    public void startUpdating() {
        // Démarre la tâche asynchrone avec une répétition de 20 ticks (1 seconde)
        this.runTaskTimerAsynchronously(ChatFormatting.getInstance(), 0, ChatFormatting.getInstance().getDisplayNames().getTickUpdate());
    }
}
