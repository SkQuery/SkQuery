package com.skquery.skquery.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import com.skquery.skquery.annotations.Patterns;
import com.skquery.skquery.util.Collect;

import org.bukkit.event.Event;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@Patterns("message format")
public class ExprMessageFormat extends SimpleExpression<String> {

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        if (getParser().isCurrentEvent(AsyncPlayerChatEvent.class)) {
            Skript.error("Message format can only be used inside a chat event");
            return false;
        }
        return true;
    }

    @Override
    protected String[] get(Event event) {
        return Collect.asArray(((AsyncPlayerChatEvent) event).getFormat());
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
    public void change(Event event, Object[] delta, ChangeMode mode) {
        String format = delta[0] == null ? "" : (String) delta[0];
        ((AsyncPlayerChatEvent) event).setFormat(format);
    }

    @Override
    public Class<?>[] acceptChange(ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) return Collect.asArray(String.class);
        return null;
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "chat message format";
    }

}
