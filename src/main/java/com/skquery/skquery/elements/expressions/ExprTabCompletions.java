package com.skquery.skquery.elements.expressions;

import java.util.List;

import org.bukkit.event.Event;

import com.skquery.skquery.annotations.Patterns;
import com.skquery.skquery.elements.events.bukkit.AttachedTabCompleteEvent;
import com.skquery.skquery.util.Collect;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.parser.ParserInstance;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;


@Patterns("[tab] (completions|suggestions)")
public class ExprTabCompletions extends SimpleExpression<String> {

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		if (!ParserInstance.get().isCurrentEvent(AttachedTabCompleteEvent.class)) {
			Skript.error("Tab completers can only be accessed from tab complete events.", ErrorQuality.SEMANTIC_ERROR);
			return false;
		}
		return true;
	}

	protected String[] get(Event event) {
		List<String> completions = ((AttachedTabCompleteEvent) event).getResult();
		return completions.toArray(new String[completions.size()]);
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "tab results";
	}

	@Override
	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.ADD)
			return Collect.asArray(String.class);
		return null;
	}

	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		String node = delta[0] == null ? "" : (String) delta[0];
		AttachedTabCompleteEvent event = ((AttachedTabCompleteEvent) e);
		if (node.toLowerCase().startsWith(event.getArgs()[event.getArgs().length - 1].toLowerCase())) {
			event.getResult().add(node);
		}
	}

}
