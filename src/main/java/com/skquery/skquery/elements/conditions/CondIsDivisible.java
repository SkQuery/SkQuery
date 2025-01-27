package com.skquery.skquery.elements.conditions;

import org.bukkit.event.Event;

import com.skquery.skquery.annotations.Description;
import com.skquery.skquery.annotations.Patterns;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.conditions.base.PropertyCondition.PropertyType;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Name("Is Divisible")
@Description("Checks whether or not a number can be divided into another number.")
@Patterns({"%numbers% (is|are) divisible by %number%", "%number% (isn't|is not|aren't|are not) divisible by %number%"})
public class CondIsDivisible extends Condition {

	private Expression<Number> numbers, divisible;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		numbers = (Expression<Number>) expressions[0];
		divisible = (Expression<Number>) expressions[1];
		setNegated(matchedPattern == 1);
		return true;
	}

	@Override
	public boolean check(Event event) {
		float div = divisible.getSingle(event).floatValue();
		return numbers.check(event, number -> ((number.floatValue() % div) == 0), isNegated());
	}

	@Override
	public String toString(Event event, boolean debug) {
		return PropertyCondition.toString(this, PropertyType.BE, event, debug, numbers, "divisible by " + divisible.toString(event, debug));
	}

}
