package com.skquery.skquery.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

import com.skquery.skquery.annotations.Patterns;
import com.skquery.skquery.util.Reflection;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


@Patterns("display credits to %player%")
public class EffShowCredits extends Effect {

    private Expression<Player> player;

    @SuppressWarnings({ "rawtypes" })
	@Override
    protected void execute(Event event) {
        Player p = player.getSingle(event);
        if(p == null) return;
        try {
            Constructor packetConstructor = Reflection.getNMSClass("PacketPlayOutGameStateChange").getConstructor(int.class, int.class);
            Object packet = packetConstructor.newInstance(4, 0);
            Reflection.sendPacket(packet, p);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "credits";
    }

    @SuppressWarnings("unchecked")
	@Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        player = (Expression<Player>) expressions[0];
        return true;
    }
}
