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

@Name("Is Prime")
@Description("Checks whether or not a number is prime.")
@Patterns({"%numbers% (is|are) [a] prime [number]", "%numbers% (isn't|is not|aren't|are not) [a] prime [number]"})
public class CondIsPrime extends Condition {

	private Expression<Number> numbers;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		numbers = (Expression<Number>) expressions[0];
		setNegated(matchedPattern == 1);
		return true;
	}

	@Override
	public boolean check(Event event) {
		return numbers.check(event, number -> isPrime(number.intValue()), isNegated());
	}

	@Override
	public String toString(Event event, boolean debug) {
		return PropertyCondition.toString(this, PropertyType.BE, event, debug, numbers, "prime");
	}

	public static boolean isPrime(int number) {
		if (number == 1)
			return false;
		if (number % 2 == 0)
			return false;
		for (int i = 3; i * i <= number; i += 2) {
			if (number % i == 0)
				return false;
		}
		return true;
	}

}
