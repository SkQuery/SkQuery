package com.skquery.skquery.elements.effects;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

import org.bukkit.block.Block;
import org.bukkit.event.Event;

import com.skquery.skquery.annotations.Description;
import com.skquery.skquery.annotations.Patterns;

@Name("Set Block Without Updates")
@Description("Set blocks to another block without causing surrounding blocks to update.")
@Patterns("set %blocks% to %itemtype% with no [block] updates")
public class EffSetBlock extends Effect {

    private Expression<Block> blocks;
    private Expression<ItemType> toSet;

    @Override
    protected void execute(Event event) {
        ItemType t = toSet.getSingle(event);
        if (t == null) return;
        for (Block b : blocks.getAll(event)) {
            t.getBlock().setBlock(b, false);
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "true";
    }

    @SuppressWarnings("unchecked")
	@Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        blocks = (Expression<Block>) expressions[0];
        toSet = (Expression<ItemType>) expressions[1];
        return true;
    }
}
