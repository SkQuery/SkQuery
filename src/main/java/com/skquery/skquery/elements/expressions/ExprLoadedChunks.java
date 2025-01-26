package com.skquery.skquery.elements.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.event.Event;

import com.skquery.skquery.annotations.PropertyFrom;
import com.skquery.skquery.annotations.PropertyTo;
import com.skquery.skquery.annotations.UsePropertyPatterns;

import java.util.ArrayList;
import java.util.Arrays;

@UsePropertyPatterns
@PropertyFrom("worlds")
@PropertyTo("loaded chunks")
public class ExprLoadedChunks extends SimpleExpression<Chunk> {

    private Expression<World> worlds;

    @Override
    protected Chunk[] get(Event event) {
        ArrayList<Chunk> chunks = new ArrayList<>();
        for (World w : worlds.getAll(event)) {
            chunks.addAll(Arrays.asList(w.getLoadedChunks()));
        }
        return chunks.toArray(new Chunk[chunks.size()]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Chunk> getReturnType() {
        return Chunk.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "loaded chunks";
    }

    @SuppressWarnings("unchecked")
	@Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        worlds = (Expression<World>) expressions[0];
        return true;
    }
}
