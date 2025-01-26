package com.skquery.skquery.elements.expressions;

import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.skquery.skquery.annotations.Patterns;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.parser.ParserInstance;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;

@Patterns("inventory (title|name)")
public class ExprInventoryName extends SimpleExpression<String> {

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		if (!ParserInstance.get().isCurrentEvent(InventoryClickEvent.class)) {
			Skript.error("In 1.13+ you cannot get the title name of an inventory outside of an InventoryClickEvent, please only use 'Inventory Name' within the InventoryClickEvent `on inventory click`", ErrorQuality.SEMANTIC_ERROR);
			return false;
		}
		return true;
	}

	protected String[] get(Event event) {
		return new String[] {((InventoryClickEvent)event).getView().getTitle()};
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "inventory name";
	}

}
