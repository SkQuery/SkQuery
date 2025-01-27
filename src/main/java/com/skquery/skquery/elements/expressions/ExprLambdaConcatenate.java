package com.skquery.skquery.elements.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import com.skquery.skquery.annotations.Patterns;
import com.skquery.skquery.skript.LambdaCondition;
import com.skquery.skquery.skript.LambdaEffect;
import com.skquery.skquery.util.Collect;

import org.bukkit.event.Event;

@Patterns({"%lambda%-\\>%lambda%",
        "%predicate%-\\>%predicate%"})
public class ExprLambdaConcatenate extends SimpleExpression<Object> {

    @SuppressWarnings("rawtypes")
	private Expression base, tail;
    private int match = -1;

    @SuppressWarnings("unchecked")
	@Override
    protected Object[] get(Event e) {
        if (match == 0) {
            LambdaEffect b = ((Expression<LambdaEffect>) base).getSingle(e);
            LambdaEffect t = ((Expression<LambdaEffect>) tail).getSingle(e);
            if (b == null || t == null) return null;
            return Collect.asArray(new LambdaEffect(false).add(b).add(t));
        } else {
            LambdaCondition b = ((Expression<LambdaCondition>) base).getSingle(e);
            LambdaCondition t = ((Expression<LambdaCondition>) tail).getSingle(e);
            if (b == null || t == null) return null;
            return Collect.asArray(new LambdaCondition(null).add(b).add(t));
        }
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<?> getReturnType() {
        return match == -1 ? Object.class : match == 0 ? LambdaEffect.class : LambdaCondition.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "lambda";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        base = exprs[0];
        tail = exprs[1];
        match = matchedPattern;
        return true;
    }
}
