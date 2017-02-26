package org.sandcast.publisher.plugin.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PublishableEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final String message;
    private final Player player;

    public PublishableEvent(Player player, String message) {
        this.message = message;
        this.player = player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public String getMessage() {
        return message;
    }

    public Player getPlayer() {
        return player;
    }
}
