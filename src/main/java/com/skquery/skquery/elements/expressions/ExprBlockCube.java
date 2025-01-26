package com.skquery.skquery.elements.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.iterator.EmptyIterator;
import ch.njol.util.coll.iterator.IteratorIterable;

import com.skquery.skquery.annotations.Patterns;
import com.skquery.skquery.util.region.CuboidIterator;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Patterns("blocks within %location% (to|and) %location%")
public class ExprBlockCube extends SimpleExpression<Block> {

	private Expression<Location> pos1, pos2;

	@Override
	protected Block[] get(Event event) {
		Location p1 = pos1.getSingle(event);
		Location p2 = pos2.getSingle(event);
		if(p1 == null || p2 == null) return null;
		List<Block> list = new ArrayList<>();
		for(Block b : new IteratorIterable<>(iterator(event))){
			list.add(b);
		}
		return list.toArray(new Block[list.size()]);
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public Class<? extends Block> getReturnType() {
		return Block.class;
	}

	@Override
	public String toString(Event event, boolean b) {
		return "cuboid";
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		pos1 = (Expression<Location>) expressions[0];
		pos2 = (Expression<Location>) expressions[1];
		return true;
	}

	@Override
	public boolean isLoopOf(String s) {
		return s.equalsIgnoreCase("block");
	}

	@Override
	public Iterator<Block> iterator(Event e) {
		Location p1 = pos1.getSingle(e);
		Location p2 = pos2.getSingle(e);
		if (p1 == null || p2 == null)
			return new EmptyIterator<>();
		return new CuboidIterator(p1, p2);
	}

}
