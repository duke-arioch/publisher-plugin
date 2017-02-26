package org.sandcast.publisher.plugin.adapter;

import com.mashape.unirest.http.Unirest;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.sandcast.publisher.plugin.PublisherPlugin;

public class HttpAdapter implements PublishingAdapter {

    Map<String, String> playerUrls;

    public HttpAdapter(PublisherPlugin plugin) {
        playerUrls = new HashMap<>();
        Unirest.setTimeouts(1000, 2000);
    }

    @Override
    public void register(String playerName, String... options) {
        final String url = options[0];
        playerUrls.put(playerName, url);
    }

    @Override
    public void unregister(String playerName) {
        playerUrls.remove(playerName);
    }

    public void publish(String player, String contents) {
        if (playerUrls.containsKey(player)) {
            String url = playerUrls.get(player);
            Unirest.post(url)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .field("PLAYER", player)
                    .field("CONTENT", contents)
                    .field("SERVER_NAME", Bukkit.getServerName())
                    .field("SERVER_ID", Bukkit.getServerId())
                    .asStringAsync();
        }
    }
}
