package com.skquery.skquery.elements.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import com.skquery.skquery.annotations.Patterns;
import com.skquery.skquery.util.Collect;

import org.bukkit.event.Event;
import java.util.HashMap;

@Patterns("%*classinfo% input")
public class ExprInput extends SimpleExpression<Object> {

    static HashMap<Event, Object> in = new HashMap<>();
    private Class<?> returnType = Object.class;

    @Override
    protected Object[] get(Event e) {
        if (!in.containsKey(e)) return null;
        Object[] array = Collect.newArray(returnType, 1);
        array[0] = in.get(e);
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
        return "input";
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        returnType = ((Literal<ClassInfo>) exprs[0]).getSingle().getC();
        return true;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        return Collect.asArray(Object.class);
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        switch (mode) {
            case SET:
                ExprInput.setInput(e, delta[0]);
                break;
            case RESET:
            	ExprInput.removeInput(e);
            case ADD:
            	ExprInput.setInput(e, delta[0]);
            case REMOVE:
            	ExprInput.removeInput(e);
            case REMOVE_ALL:
            	ExprInput.removeInput(e);
            case DELETE:
            	ExprInput.removeInput(e);
        }
    }

    public static void setInput(Event e, Object o) {
        in.put(e, o);
    }

    public static void removeInput(Event e) {
        in.remove(e);
    }
}
