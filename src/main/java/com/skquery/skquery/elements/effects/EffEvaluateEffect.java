package com.skquery.skquery.elements.effects;

import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.parser.ParserInstance;
import ch.njol.util.Kleenean;

import com.skquery.skquery.annotations.Description;
import com.skquery.skquery.annotations.Examples;
import com.skquery.skquery.annotations.Patterns;
import com.skquery.skquery.skript.Markup;

import org.bukkit.event.Event;

@Name("Evaluate Input Effect")
@Description("Runs the input string relative to the calling trigger which invoked it.")
@Examples("command /effectcommand <text>:;->trigger:;->->evaluate argument")
@Patterns({"evaluate %string/markup%", "^%markup%"})
public class EffEvaluateEffect extends Effect {

	private Expression<?> effect;

	@Override
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		effect = expressions[0];
		return true;
	}

	@Override
	protected void execute(Event event) {
		String pre = null;
		Object object = effect.getSingle(event);
		if (object instanceof String) pre = (String) object;
		else if (object instanceof Markup) pre = object.toString();
		if (pre == null) return;
		ParserInstance instance = ParserInstance.get();
		instance.setCurrentEvent("this", event.getClass());
		Effect e = Effect.parse(pre, null);
		instance.deleteCurrentEvent();
		if (e == null) return;
		e.run(event);
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "evaluate " + effect.toString(event, debug);
	}

}
