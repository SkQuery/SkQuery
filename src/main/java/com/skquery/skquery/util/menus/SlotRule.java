package com.skquery.skquery.util.menus;

import com.skquery.skquery.skript.LambdaEffect;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public class SlotRule {

	private final Object callback;
	private final boolean close;

	public SlotRule(Object callback, boolean close) {
		this.callback = callback;
		this.close = close;
	}
	
	public boolean willClose() {
		return close;
	}

	public Object getCallback() {
		return callback;
	}

	public void run(Event event) {
		if (callback == null)
			return;
		if (callback instanceof String) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), (String) callback);
		} else if (callback instanceof LambdaEffect) {
			((LambdaEffect) callback).walk(event);
		}
	}

	public SlotRule clone() {
		return new SlotRule(callback, close);
	}

}
