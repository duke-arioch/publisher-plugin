package org.sandcast.publisher.plugin;

//import com.mashape.unirest.http.Unirest;
import java.util.Set;
import org.sandcast.publisher.plugin.listener.PublishableEventListener;
import java.util.logging.Level;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jasypt.util.text.BasicTextEncryptor;
import org.sandcast.publisher.plugin.command.PublisherCommand;
import org.sandcast.publisher.plugin.adapter.DiscordAdapter;
import org.sandcast.publisher.plugin.adapter.PublishingAdapter;

public class PublisherPlugin extends JavaPlugin {

    private PublishingAdapter adapter;
    private PublishableEventListener publishableListener;
    private final BasicTextEncryptor textEncryptor = new BasicTextEncryptor();

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(publishableListener);
    }

    @Override
    public void onEnable() {
        getConfig().addDefault("discord-token", "DISCORD-TOKEN");
        final String pluginToken = getConfig().getString("discord-token");
        getConfig().addDefault("encryption-key", "iguessthisworks?");
        getConfig().options().copyDefaults(true);
        saveConfig();
        final String encryptionKey = getConfig().getString("discord-token");
        textEncryptor.setPassword(encryptionKey);
        adapter = new DiscordAdapter(pluginToken); //could instead register multiple including httpaddapter...
        publishableListener = new PublishableEventListener(adapter);
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(publishableListener, this);
        getCommand("discord").setExecutor(new PublisherCommand(adapter, textEncryptor));
        PluginDescriptionFile pdfFile = this.getDescription();
        getLogger().log(Level.INFO, "{0} version {1} is enabled!", new Object[]{pdfFile.getName(), pdfFile.getVersion()});
    }

    public PublishingAdapter getWireController() {
        return adapter;
    }

    public Set<String> publisherTargets() {
        return adapter.publisherTargets();
    }
}
