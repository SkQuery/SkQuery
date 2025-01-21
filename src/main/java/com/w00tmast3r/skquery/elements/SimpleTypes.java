package com.w00tmast3r.skquery.elements;

import com.w00tmast3r.skquery.annotations.AbstractTask;
import com.w00tmast3r.skquery.skript.TypeClassInfo;
import ch.njol.skript.classes.EnumClassInfo;
import ch.njol.skript.classes.registry.RegistryClassInfo;
import ch.njol.skript.registrations.Classes;

import java.sql.ResultSet;

import org.bukkit.Art;
import org.bukkit.Particle;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Villager;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.scoreboard.DisplaySlot;


public class SimpleTypes extends AbstractTask {

	@Override
	public void run() {
		Classes.registerClass(new EnumClassInfo<>(InventoryType.class, "inventorytype", "inventory type"));
		Classes.registerClass(new EnumClassInfo<>(DisplaySlot.class, "displayslot", "display slot"));
		Classes.registerClass(new EnumClassInfo<>(Particle.class, "particle", "particle"));

		Classes.registerClass(new RegistryClassInfo<>(Villager.Profession.class, Registry.VILLAGER_PROFESSION, "profession", "profession"));
		Classes.registerClass(new RegistryClassInfo<>(Sound.class, Registry.SOUNDS, "sound", "sound"));
		Classes.registerClass(new RegistryClassInfo<>(Art.class, Registry.ART, "art", "art"));

		TypeClassInfo.create(WorldBorder.class, "worldborder").register();
		TypeClassInfo.create(ResultSet.class, "queryresult").register();
	}

}
