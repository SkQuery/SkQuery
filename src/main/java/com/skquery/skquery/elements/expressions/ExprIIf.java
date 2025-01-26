package com.skquery.skquery.elements.expressions;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.util.Utils;
import ch.njol.util.Kleenean;

import com.skquery.skquery.annotations.Patterns;
import com.skquery.skquery.util.Collect;

import org.bukkit.event.Event;

@Patterns({"%boolean%[ ]?[ ]%object%[ ]:[ ]%object%"})
public class ExprIIf extends SimpleExpression<Object> {

    private Expression<Boolean> op;
    private Expression<?> truePart, falsePart;
    private Class<?> returnType = Object.class;

    protected Object[] get(Event e) {
        Object[] array = Collect.newArray(returnType, 1);
        if (Boolean.TRUE.equals(op.getSingle(e)))  array[0] = truePart.getSingle(e);
        else array[0] = falsePart.getSingle(e);
        return array;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<?> getReturnType() {
        return returnType;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "iif";
    }

    @SuppressWarnings("unchecked")
	@Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        op = (Expression<Boolean>) exprs[0];
        truePart = exprs[1];
        falsePart = exprs[2];
        final ClassInfo<?> ci;
        if (truePart instanceof Variable && falsePart instanceof Variable) {
            ci = Classes.getExactClassInfo(Object.class);
            truePart = truePart.getConvertedExpression(Object.class);
            falsePart = falsePart.getConvertedExpression(Object.class);
        } else if (truePart instanceof Literal<?> && falsePart instanceof Literal<?>) {
            truePart = truePart.getConvertedExpression(Object.class);
            falsePart = falsePart.getConvertedExpression(Object.class);
            if (truePart == null || falsePart == null) return false;
            ci = Classes.getSuperClassInfo(Utils.getSuperType(truePart.getReturnType(), falsePart.getReturnType()));
        } else {
            if (truePart instanceof Literal<?>) {
                truePart = truePart.getConvertedExpression(falsePart.getReturnType());
                if (truePart == null) return false;
            } else if (falsePart instanceof Literal<?>) {
                falsePart = falsePart.getConvertedExpression(truePart.getReturnType());
                if (falsePart == null) return false;
            }
            if (truePart instanceof Variable) {
                truePart = truePart.getConvertedExpression(falsePart.getReturnType());
            } else if (falsePart instanceof Variable) {
                falsePart = falsePart.getConvertedExpression(truePart.getReturnType());
            }
            assert truePart != null && falsePart != null;
            ci = Classes.getSuperClassInfo(Utils.getSuperType(truePart.getReturnType(), falsePart.getReturnType()));
        }
        returnType = ci.getC();
        return true;
    }

}

