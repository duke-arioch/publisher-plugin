package org.sandcast.publisher.plugin.adapter;

public interface PublishingAdapter {

    void publish(String playerName, String message);
    void register (String playerName, String... options);
    void unregister (String playerName);
}
