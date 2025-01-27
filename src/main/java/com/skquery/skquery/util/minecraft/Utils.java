package com.skquery.skquery.util.minecraft;

import org.bukkit.Material;
import org.bukkit.Sound;

public class Utils {

	public static Material materialAttempt(String attempt, String fallback) {
		Material material = null;
		try {
			material = Material.valueOf(attempt);
		} catch (Exception e) {
			try {
				material = Material.valueOf(fallback);
			} catch (Exception e1) {}
		}
		if (material == null)
			material = Material.CHEST;
		return material;
	}
	
	public static Sound soundAttempt(String attempt, String fallback) {
		Sound sound = null;
		try {
			sound = Sound.valueOf(attempt);
		} catch (Exception e) {
			try {
				sound = Sound.valueOf(fallback);
			} catch (Exception e1) {}
		}
		if (sound == null)
			sound = Sound.ENTITY_PLAYER_LEVELUP;
		return sound;
	}
	
}
