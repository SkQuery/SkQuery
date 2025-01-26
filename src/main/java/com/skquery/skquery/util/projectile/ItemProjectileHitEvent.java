package com.skquery.skquery.util.projectile;

import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ItemProjectileHitEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	private final LivingEntity shooter;
	private final Item projectile;

	public ItemProjectileHitEvent(Item projectile, LivingEntity shooter) {
		this.projectile = projectile;
		this.shooter = shooter;
	}

	public Item getProjectile() {
		return projectile;
	}

	public LivingEntity getShooter() {
		return shooter;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
	
}
