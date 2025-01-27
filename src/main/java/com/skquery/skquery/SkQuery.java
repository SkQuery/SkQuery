package com.skquery.skquery;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import com.skquery.skquery.elements.events.EvtLambdaWhen;
import com.skquery.skquery.skript.LambdaCondition;
import com.skquery.skquery.sql.ScriptCredentials;
import com.skquery.skquery.util.menus.FormattedSlotManager;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.config.Config;
import ch.njol.skript.lang.parser.ParserInstance;

public final class SkQuery extends JavaPlugin {

	public final static boolean LIME_EDIT = true;

	private static SkriptAddon addonInstance;
	private static SkQuery instance;
	private static Metrics metrics;

	@Override
	public void onEnable() {
/* 		if (Skript.getVersion().isSmallerThan(new Version(2, 10, 0))) {
			getLogger().severe("SkQuery 4.3.0+ requires Skript 2.10.0 or later to run.");
			getServer().getPluginManager().disablePlugin(this);
			return;
		} */
		instance = this;
		addonInstance = Skript.registerAddon(this).setLanguageFileDirectory("lang");
		Registration.enable();
		Bukkit.getPluginManager().registerEvents(new FormattedSlotManager(), this);
		metrics = new Metrics(this, 437);
		metrics.addCustomChart(new SimplePie("skriptVersion", () ->
			Skript.getInstance().getDescription().getVersion()
		));
		//new Documentation(this);
	}

	@Override
	public void onDisable() {
		ScriptCredentials.clear();
		Set<LambdaCondition> limiter = EvtLambdaWhen.limiter;
		if (limiter == null || limiter.isEmpty())
			return;
		EvtLambdaWhen.limiter.clear();
	}

	public static String cc(String colour) {
		return ChatColor.translateAlternateColorCodes('&', colour);
	}

	public static SkriptAddon getAddonInstance() {
		return addonInstance;
	}

	public static SkQuery getInstance() {
		return instance;
	}

	public static Metrics getMetrics() {
		return metrics;
	}

	private static boolean OLDER_2_7 = Skript.methodExists(ParserInstance.class, "getCurrentScript", null, Config.class);
	private static Method method;
	static {
		try {
			method = ParserInstance.class.getMethod("getCurrentScript");
		} catch (NoSuchMethodException | SecurityException e) {}
	}

	/**
	 * To support Skript 2.6.4 with 2.7 support
	 */
	@Deprecated
	public static Config getConfig(ParserInstance parser) {
		if (OLDER_2_7) {
			try {
				return (Config) method.invoke(parser);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {}
		} else {
			return parser.getCurrentScript().getConfig();
		}
		return null;
	}

}
