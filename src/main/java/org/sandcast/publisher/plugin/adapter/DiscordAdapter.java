package org.sandcast.publisher.plugin.adapter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

public class DiscordAdapter implements PublishingAdapter {

    private JDA jda;
    private final Map<String, TextChannel> playerChannels;

    public DiscordAdapter(String discordToken) {
        playerChannels = new HashMap<>();
        try {
            jda = new JDABuilder(AccountType.BOT).setToken(discordToken).buildAsync();
        } catch (LoginException | IllegalArgumentException | RateLimitedException ex) {
            Logger.getLogger(DiscordAdapter.class.getName()).log(Level.SEVERE, "Discord Login Failure", ex);
        }
    }

    @Override
    public void register(String playerName, String... options) {
        final String guildName = options[0];
        final String channelName = options[1];
        final Optional<TextChannel> channel
                = jda.getGuildsByName(guildName, false)
                .stream()
                .flatMap(t -> t.getTextChannelsByName(channelName, false).stream())
                .findFirst();
        if (channel.isPresent()) {
            playerChannels.put(playerName, channel.get());
        }
    }

    @Override
    public void unregister(String playerName) {
        playerChannels.remove(playerName);
    }

    @Override
    public void publish(String playerName, String message) {
        if (playerChannels.containsKey(playerName)) {
            playerChannels.get(playerName).sendMessage(message).queue();
        }
    }

    @Override
    public Set<String> publisherTargets() {
        return Collections.unmodifiableSet(playerChannels.keySet());
    }
}
