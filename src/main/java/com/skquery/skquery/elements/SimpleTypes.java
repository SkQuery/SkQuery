package com.skquery.skquery.elements;

import com.skquery.skquery.annotations.AbstractTask;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.EnumClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.classes.registry.RegistryClassInfo;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;

import java.sql.ResultSet;

import org.bukkit.Art;
import org.bukkit.Particle;
import org.bukkit.Registry;
import org.bukkit.WorldBorder;
import org.bukkit.scoreboard.DisplaySlot;

public class SimpleTypes extends AbstractTask {

	@Override
	public void run() {
		if (Classes.getExactClassInfo(DisplaySlot.class) == null) 
			Classes.registerClass(new EnumClassInfo<>(DisplaySlot.class, "displayslot", "display slot"));

		if (Classes.getExactClassInfo(Particle.class) == null)
			Classes.registerClass(new EnumClassInfo<>(Particle.class, "particle", "particle"));

		if (Classes.getExactClassInfo(Art.class) == null)
			Classes.registerClass(new RegistryClassInfo<>(Art.class, Registry.ART, "art", "art"));

		if (Classes.getExactClassInfo(WorldBorder.class) == null) {
			Classes.registerClass(new ClassInfo<>(WorldBorder.class, "worldborder")
				.user("world ?borders?")
				.name("World Border")
				.parser(new Parser<WorldBorder>() {
					@Override
					public boolean canParse(ParseContext context) {
						return false;
					}

					@Override
					public String toString(WorldBorder border, int flags) {
						return toVariableNameString(border);
					}

					@Override
					public String toVariableNameString(WorldBorder border) {
						return "worldborder:" + border.getWorld().getName();
					}
				})
			);
		}

		if (Classes.getExactClassInfo(ResultSet.class) == null) {
			Classes.registerClass(new ClassInfo<>(ResultSet.class, "queryresult")
				.user("query ?results?")
				.name("Query Result")
				.parser(new Parser<ResultSet>() {
					@Override
					public boolean canParse(ParseContext context) {
						return false;
					}

					@Override
					public String toString(ResultSet result, int flags) {
						return toVariableNameString(result);
					}

					@Override
					public String toVariableNameString(ResultSet result) {
						return "queryresult:" + result.toString();
					}
				})
			);
		}
	}

}
