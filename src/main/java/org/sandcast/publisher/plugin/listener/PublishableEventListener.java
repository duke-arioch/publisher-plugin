package org.sandcast.publisher.plugin.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.sandcast.publisher.plugin.adapter.PublishingAdapter;
import org.sandcast.publisher.plugin.event.PublishableEvent;

public class PublishableEventListener implements Listener {

    PublishingAdapter adapter;

    public PublishableEventListener(PublishingAdapter adapter) {
        this.adapter = adapter;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPublishable(PublishableEvent pe) {
        adapter.publish(pe.getPlayer().getName(), pe.getMessage());
    }
}
