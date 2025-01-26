package com.skquery.skquery.elements.effects;

import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;

import com.skquery.skquery.annotations.Description;
import com.skquery.skquery.annotations.Examples;
import com.skquery.skquery.annotations.Patterns;

@Name("Leash Entities")
@Description("Cause multiple entities to leash other entities.")
@Examples("on click:;->make player leash clicked entity")
@Patterns({"make %entities% (leash|lead) %livingentities%", "(leash|lead) %livingentities% to %entities%"})
public class EffLeash extends Effect {

	private Expression<Entity> holders;
	private Expression<LivingEntity> targets;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		holders = (Expression<Entity>) expressions[matchedPattern];
		targets = (Expression<LivingEntity>) expressions[1 - matchedPattern];
		return true;
	}
	
	@Override
	protected void execute(Event event) {
		for (Entity entity : holders.getArray(event)) {
			for (LivingEntity living : targets.getArray(event)) {
				living.setLeashHolder(entity);
			}
		}
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "leash " + targets.toString(event, debug) + " to " + holders.toString(event, debug);
	}

}
