package com.skquery.skquery.elements.effects;

import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.skquery.skquery.SkQuery;
import com.skquery.skquery.annotations.Description;
import com.skquery.skquery.annotations.Patterns;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.parser.ParserInstance;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;

@Name("Trail Projectile")
@Description("Trails the projectile in a ProjectileLaunchEvent.")
@Patterns("trail projectile with %particles%")
public class EffTrail extends Effect {

	private Expression<Particle> particles;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		if (!ParserInstance.get().isCurrentEvent(ProjectileLaunchEvent.class)) {
			Skript.error("Trailing can only be used in a shoot event", ErrorQuality.SEMANTIC_ERROR);
			return false;
        }
		particles = (Expression<Particle>) expressions[0];
		return true;
	}

	@Override
	protected void execute(Event event) {
		Particle[] particles = this.particles.getArray(event);
		if (particles == null || particles.length <= 0)
			return;
		Projectile projectile = ((ProjectileLaunchEvent) event).getEntity();
		new BukkitRunnable() {
			@Override
			public void run() {
				World world = projectile.getWorld();
				for (Particle particle : particles) {
					if (particle.getDataType() != null) {
						cancel();
						return;
					}
					world.spawnParticle(particle, projectile.getLocation(), 0, 0, 0, 0);
				}
				if (!projectile.isValid() || projectile.isOnGround())
					cancel();
			}
		}.runTaskTimer(SkQuery.getInstance(), 0, 1);
	}

	@Override
	public String toString(Event event, boolean debug) {
		ProjectileLaunchEvent launch = (ProjectileLaunchEvent) event;
		return "trail " + launch.getEntity().getType() + " with visual effect " + particles.toString(event, debug);
	}

}
