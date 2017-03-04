package org.sandcast.publisher.plugin.command;

import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Encoder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jasypt.util.text.BasicTextEncryptor;
import org.sandcast.publisher.plugin.adapter.PublishingAdapter;

public class PublisherCommand implements CommandExecutor {

    private final PublishingAdapter adapter;
    BasicTextEncryptor textEncryptor;
    ConsoleCommandSender console;
    Encoder encoder = Base64.getEncoder().withoutPadding();

    public PublisherCommand(PublishingAdapter adapter, BasicTextEncryptor textEncryptor) {
        this.adapter = adapter;
        this.textEncryptor = textEncryptor;
        console = Bukkit.getServer().getConsoleSender();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] split) {
        final String subcommand = split[0];
        if (!(sender instanceof Player) && split.length > 1) {
            final String name = textEncryptor.decrypt(new String(Base64.getDecoder().decode(split[1])));
            console.sendMessage("decrypted " + split[1] + " to " + name);
            switch (subcommand.toLowerCase()) {
                case "clear":
                    adapter.unregister(name);
                    break;
                case "set":
                    adapter.register(name, Arrays.copyOfRange(split, 2, split.length));
                    break;
            }
        } else {
            switch (subcommand.toLowerCase()) {
                case "user":
                    Player player = (Player) sender;
                    String encrypted = encoder.encodeToString(textEncryptor.encrypt(player.getName()).getBytes());
                    player.sendMessage("Your encrypted key is " + ChatColor.YELLOW + "" + ChatColor.BOLD + encrypted);
                    console.sendMessage("encrypted " + player.getName() + " to " + encrypted);
                    break;
            }
        }
        return true;
    }
}
