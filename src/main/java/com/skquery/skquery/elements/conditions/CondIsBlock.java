package com.skquery.skquery.elements.conditions;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.conditions.base.PropertyCondition.PropertyType;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import org.bukkit.event.Event;

import com.skquery.skquery.annotations.Description;
import com.skquery.skquery.annotations.Patterns;

// Exists to fix a bug in Skript that LimeGlass fixed around 2.8-2.9
@Name("Is Block")
@Description("Checks whether or not a certain itemtype is a placeable block.")
@Patterns({"%itemtype% is [a] block", "%itemtype% (isn't|is not) [a] block"})
public class CondIsBlock extends Condition {

	private Expression<ItemType> itemtype;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		itemtype = (Expression<ItemType>) expressions[0];
		setNegated(matchedPattern == 1);
		return true;
	}
	
	@Override
	public boolean check(Event event) {
		return isNegated() ? !itemtype.getSingle(event).hasBlock() : itemtype.getSingle(event).hasBlock();
	}

	@Override
	public String toString(Event event, boolean debug) {
		return PropertyCondition.toString(this, PropertyType.BE, event, debug, itemtype, "block");
	}

}
