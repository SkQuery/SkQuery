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

@Patterns({"%object%[ ]?[ ]%object%"})
public class ExprDefaultValue extends SimpleExpression<Object> {

    private Expression<?> object, defaultPart;
    private Class<?> returnType = Object.class;

    protected Object[] get(Event e) {
        Object[] array = Collect.newArray(returnType, 1);
        array[0] = object.getSingle(e) == null ? defaultPart.getSingle(e) : object.getSingle(e);
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
        return "default";
    }

    @SuppressWarnings("unchecked")
	@Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        object = exprs[0];
        defaultPart = exprs[1];
        final ClassInfo<?> ci;
        if (object instanceof Variable && defaultPart instanceof Variable) {
            ci = Classes.getExactClassInfo(Object.class);
            object = object.getConvertedExpression(Object.class);
            defaultPart = defaultPart.getConvertedExpression(Object.class);
        } else if (object instanceof Literal<?> && defaultPart instanceof Literal<?>) {
            object = object.getConvertedExpression(Object.class);
            defaultPart = defaultPart.getConvertedExpression(Object.class);
            if (object == null || defaultPart == null) return false;
            ci = Classes.getSuperClassInfo(Utils.getSuperType(object.getReturnType(), defaultPart.getReturnType()));
        } else {
            if (object instanceof Literal<?>) {
                object = object.getConvertedExpression(defaultPart.getReturnType());
                if (object == null) return false;
            } else if (defaultPart instanceof Literal<?>) {
                defaultPart = defaultPart.getConvertedExpression(object.getReturnType());
                if (defaultPart == null) return false;
            }
            if (object instanceof Variable) {
                object = object.getConvertedExpression(defaultPart.getReturnType());
            } else if (defaultPart instanceof Variable) {
                defaultPart = defaultPart.getConvertedExpression(object.getReturnType());
            }
            assert object != null && defaultPart != null;
            ci = Classes.getSuperClassInfo(Utils.getSuperType(object.getReturnType(), defaultPart.getReturnType()));
        }
        returnType = ci.getC();
        return true;
    }
}
