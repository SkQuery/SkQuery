package com.skquery.skquery.elements.effects;

import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.skquery.skquery.annotations.Description;
import com.skquery.skquery.annotations.Examples;
import com.skquery.skquery.annotations.Patterns;

@Name("Make Say")
@Description("Force players to say text in chat. If you add a leading slash in the text, it will execute a command.")
@Examples("on chat:;->cancel event;->make a random player out of all players say message")
@Patterns("(make|force) %players% say %strings%")
public class EffMakeSay extends Effect {

	private Expression<Player> players;
	private Expression<String> strings;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		players = (Expression<Player>) expressions[0];
		strings = (Expression<String>) expressions[1];
		return true;
	}
	
	@Override
	protected void execute(Event event) {
		for (String text : strings.getArray(event)) {
			for (Player player : players.getArray(event)) {
				player.chat(text);
			}
		}
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "say wat " + players.toString(event, debug) + " " + strings.toString(event, debug);
	}

}
