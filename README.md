Publisher Plugin

currently set up to publish to Discord channels. Includes Http adapter though to send to any HTTP endpoint.

Responds to publishing events generated code such as

```java
Bukkit.getServer().getPluginManager().callEvent(new PublishableEvent(player, message));
```

No other direct integration is necessary.

