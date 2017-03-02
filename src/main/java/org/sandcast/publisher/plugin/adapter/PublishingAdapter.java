package org.sandcast.publisher.plugin.adapter;

import java.util.Set;

public interface PublishingAdapter {

    void publish(String playerName, String message);

    void register(String playerName, String... options);

    void unregister(String playerName);

    Set<String> publisherTargets();
}
