package com.skquery.skquery.elements.conditions;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.conditions.base.PropertyCondition.PropertyType;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffectType;

import com.skquery.skquery.annotations.Description;
import com.skquery.skquery.annotations.Patterns;

@Name("Entity has Potion")
@Description("Checks whether or not an entity has a certain potion effect.")
@Patterns({"%livingentities% (has|have) [potion [effect]] %potioneffecttypes%","%livingentities% (doesn't|does not|do not|don't) have [potion [effect]] %potioneffecttypes%"})
public class CondHasPotion extends Condition {

	private Expression<PotionEffectType> effects;
	private Expression<LivingEntity> entities;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		entities = (Expression<LivingEntity>) expressions[0];
		effects = (Expression<PotionEffectType>) expressions[1];
		setNegated(matchedPattern == 1);
		return true;
	}

	@Override
	public boolean check(Event event) {
		return entities.check(event, entity -> effects.check(event, effect -> entity.hasPotionEffect(effect)), isNegated());
	}

	@Override
	public String toString(Event event, boolean debug) {
		return PropertyCondition.toString(this, PropertyType.HAVE, event, debug, entities, "effects " + effects.toString(event, debug));
	}

}
