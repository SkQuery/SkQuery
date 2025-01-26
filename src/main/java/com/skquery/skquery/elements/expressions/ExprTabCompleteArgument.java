package com.skquery.skquery.elements.expressions;

import org.bukkit.event.Event;

import com.skquery.skquery.annotations.Patterns;
import com.skquery.skquery.elements.events.bukkit.AttachedTabCompleteEvent;
import com.skquery.skquery.util.Collect;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.parser.ParserInstance;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;


@Patterns("arg[ument] at %number%")
public class ExprTabCompleteArgument extends SimpleExpression<String> {

    private Expression<Number> num;

    protected String[] get(Event e) {
        Number n = num.getSingle(e);
        if (n == null) return null;
        try {
            return Collect.asArray(((AttachedTabCompleteEvent) e).getArgs()[n.intValue() - 1]);
        } catch (IndexOutOfBoundsException ex) {
            return null;
        }
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "tab results";
    }

    @SuppressWarnings("unchecked")
	@Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (!ParserInstance.get().isCurrentEvent(AttachedTabCompleteEvent.class)) {
            Skript.error("Tab completers can only be accessed from tab complete events.", ErrorQuality.SEMANTIC_ERROR);
            return false;
        }
        num = (Expression<Number>) exprs[0];
        return true;
    }
}
