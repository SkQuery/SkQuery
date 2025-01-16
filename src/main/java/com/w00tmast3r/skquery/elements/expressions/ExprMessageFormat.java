package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;

import com.w00tmast3r.skquery.annotations.Patterns;
import com.w00tmast3r.skquery.util.Collect;

import org.bukkit.event.Event;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;

@SuppressWarnings("deprecation")
@Patterns("message format")
public class ExprMessageFormat extends SimpleExpression<String> {
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
    public String toString(Event event, boolean b) {
        return "chat message format";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if (getParser().isCurrentEvent(PlayerChatEvent.class)) {
            Skript.error("Message format can only be used inside a chat event", ErrorQuality.SEMANTIC_ERROR);
            return false;
        }
        return true;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        String format = delta[0] == null ? "" : (String) delta[0];
        ((AsyncPlayerChatEvent) e).setFormat(format);
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) return Collect.asArray(String.class);
        return null;
    }
}
