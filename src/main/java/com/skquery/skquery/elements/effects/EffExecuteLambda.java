package com.skquery.skquery.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import com.skquery.skquery.annotations.Patterns;
import com.skquery.skquery.elements.expressions.ExprInput;
import com.skquery.skquery.skript.LambdaEffect;

import org.bukkit.event.Event;

@Patterns("(do|execute) [%-number% time[s]] %lambda%")
public class EffExecuteLambda extends Effect {

	private Expression<LambdaEffect> effect;
	private Expression<Number> times;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		times = (Expression<Number>) expressions[0];
		effect = (Expression<LambdaEffect>) expressions[1];
		return true;
	}
	
	@Override
	protected void execute(Event event) {
		LambdaEffect lambda = effect.getSingle(event);
		Number number = times.getSingle(event);
		if (lambda == null)
			return;
		if (number != null) {
			for (int i = 0; i < number.intValue() ; i++) {
				ExprInput.setInput(event, i);
				lambda.walk(event);
				ExprInput.removeInput(event);
			}
		} else {
			lambda.walk(event);
		}
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "execute lambda " + effect.toString(event, debug);
	}

}
