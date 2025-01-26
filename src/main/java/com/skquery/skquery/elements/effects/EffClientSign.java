package com.skquery.skquery.elements.effects;

import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import com.skquery.skquery.annotations.Description;
import com.skquery.skquery.annotations.Examples;
import com.skquery.skquery.annotations.Patterns;
import com.skquery.skquery.util.Collect;
import com.skquery.skquery.util.minecraft.Utils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Client Sign")
@Description("Cause a sign to have custom lines to certain viewers")
@Examples("command /hidesign:;->trigger:;->->make all players see lines of targeted block as \"\", \"\", \"\", \"\"")
@Patterns("make %players% see lines of %blocks% as %string%, %string%, %string%( and|,) %string%")
public class EffClientSign extends Effect {

	private Expression<String> line1, line2, line3, line4;
	private Expression<Player> players;
	private Expression<Block> blocks;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		players = (Expression<Player>) expressions[0];
		blocks = (Expression<Block>) expressions[1];
		line1 = (Expression<String>) expressions[2];
		line2 = (Expression<String>) expressions[3];
		line3 = (Expression<String>) expressions[4];
		line4 = (Expression<String>) expressions[5];
		return true;
	}

	@Override
	protected void execute(Event event) {
		String l1 = line1.getSingle(event);
		String l2 = line2.getSingle(event);
		String l3 = line3.getSingle(event);
		String l4 = line4.getSingle(event);
		for (Block block : blocks.getArray(event)) {
			Material material = block.getType();
			if (!isSign(material))
				continue;
			for (Player player : players.getArray(event))
				player.sendSignChange(block.getLocation(), Collect.asArray(l1, l2, l3, l4));
		}
	}

	public boolean isSign(Material material) {
		if (material == Utils.materialAttempt("SIGN_POST", "SIGN")) //1.8
			return true;
		Material older = Utils.materialAttempt("WALL_SIGN", "SIGN");
		if (older != null && material == older) //1.9-1.12
			return true;
		if (material == Material.DARK_OAK_SIGN || material == Material.DARK_OAK_WALL_SIGN)
			return true;
		if (material == Material.SPRUCE_SIGN || material == Material.SPRUCE_WALL_SIGN)
			return true;
		if (material == Material.JUNGLE_SIGN || material == Material.JUNGLE_WALL_SIGN)
			return true;
		if (material == Material.ACACIA_SIGN || material == Material.ACACIA_WALL_SIGN)
			return true;
		if (material == Material.BIRCH_SIGN || material == Material.BIRCH_WALL_SIGN)
			return true;
		if (material == Material.OAK_SIGN || material == Material.OAK_WALL_SIGN)
			return true;
		return false;
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "client sign for " + players.toString(event, debug);
	}

}
