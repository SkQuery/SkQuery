package com.skquery.skquery.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.skquery.skquery.annotations.Patterns;


@Patterns("send [(resource|texture)] pack from %string% to %players%")
public class EffTexture extends Effect {

    private Expression<Player> target;
    private Expression<String> url;

    @Override
    protected void execute(Event event) {
        String u = url.getSingle(event);
        if(u == null) return;
        for(Player p : target.getAll(event)) {
            p.setResourcePack(u);
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "texture pack";
    }

    @SuppressWarnings("unchecked")
	@Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        url = (Expression<String>) expressions[0];
        target = (Expression<Player>) expressions[1];
        return true;
    }
}
