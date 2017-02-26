package org.sandcast.publisher.plugin.command;

import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.sandcast.publisher.plugin.adapter.PublishingAdapter;

public class PublisherCommand implements CommandExecutor {

    private final PublishingAdapter adapter;
    ConsoleCommandSender console;

    public PublisherCommand(PublishingAdapter adapter) {
        this.adapter = adapter;
        console = Bukkit.getServer().getConsoleSender();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] split) {
        if (!(sender instanceof Player) && split.length > 1) {
            final String subcommand = split[0];
            final String name = split[1];
            switch (subcommand.toLowerCase()) {
                case "clear":
                    adapter.unregister(name);
                    break;
                case "set":
                    adapter.register(name, Arrays.copyOfRange(split, 2, split.length));
                    break;
            }
        }
        return true;
    }
}
