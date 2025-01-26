package com.skquery.skquery.annotations;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.util.SimpleEvent;
import org.bukkit.event.Event;

/**
 * A runnable with a set of tools that help for registering events.
 */
public abstract class AbstractTask implements Runnable {

	protected void registerEvent(String name, Class<? extends Event> event, String... patterns) {
		registerEvent(name, SimpleEvent.class, event, patterns);
	}

	protected void registerEvent(String name, Class<? extends SkriptEvent> handler, Class<? extends Event> event, final String... patterns) {
		Skript.registerEvent(name, handler, event, patterns);
	}
}
