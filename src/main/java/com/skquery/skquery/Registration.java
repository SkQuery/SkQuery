package com.skquery.skquery;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;

import com.skquery.skquery.annotations.AbstractTask;
import com.skquery.skquery.annotations.AntiDependency;
import com.skquery.skquery.annotations.Dependency;
import com.skquery.skquery.annotations.Disabled;
import com.skquery.skquery.annotations.Patterns;
import com.skquery.skquery.annotations.PropertyFrom;
import com.skquery.skquery.annotations.PropertyTo;
import com.skquery.skquery.annotations.UsePropertyPatterns;
import ch.njol.skript.Skript;
import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.util.Utils;

public class Registration {

	public static void enable() {
		register(SkQuery.getInstance().getDescription(), getClasses());
	}

	public static Class<?>[] getClasses() {
		List<Class<?>> classes = new ArrayList<>();
		com.skquery.skquery.util.ClassLoader loader = com.skquery.skquery.util.ClassLoader.builder()
			.basePackage("com.skquery.skquery")
			.addSubPackages("elements")
			.deep(true)
			.initialize(false)
			.forEachClass(classes::add)
			.build();

		SkQuery plugin = SkQuery.getInstance();
		File jarFile = Utils.getFile(plugin);
		if (jarFile != null) {
			loader.loadClasses(plugin.getClass(), jarFile);
		} else {
			loader.loadClasses(plugin.getClass());
		}
		return classes.toArray(new Class[0]);
	}

	private static void register(PluginDescriptionFile info, Class<?>[] classes) {
		int success = 0;
		Bukkit.getLogger().info("[skQuery] Beginning to process a total of " + classes.length + " from " + info.getName());
		main: for (Class<?> clazz : classes) {
			Annotation[] annotations = clazz.getAnnotations();
			for (Annotation a : annotations) {
				if (a instanceof Dependency) {
					for (String s : ((Dependency) a).value()) {
						if (Bukkit.getPluginManager().getPlugin(s) == null) {
							continue main;
						}
					}
				}
				if (a instanceof AntiDependency) {
					for (String s : ((AntiDependency) a).value()) {
						if (Bukkit.getPluginManager().getPlugin(s) != null) {
							continue main;
						}
					}
				}
				if (a instanceof Disabled) continue main;
			}
			if (Effect.class.isAssignableFrom(clazz)) {
				if (clazz.isAnnotationPresent(Patterns.class)) {
					Skript.registerEffect((Class<? extends Effect>) clazz, ((Patterns) clazz.getAnnotation(Patterns.class)).value());
					success++;
				} else {
					Bukkit.getLogger().info("[skQuery] " + clazz.getCanonicalName() + " is patternless and failed to register. This is most likely a code error.");
				}
			} else if (Condition.class.isAssignableFrom(clazz)) {
				if (clazz.isAnnotationPresent(Patterns.class)) {
					Skript.registerCondition((Class<? extends Condition>) clazz, ((Patterns) clazz.getAnnotation(Patterns.class)).value());
					success++;
				} else if (!PropertyCondition.class.isAssignableFrom(clazz)) {
					//Bukkit.getLogger().info("[skQuery] " + clazz.getCanonicalName() + " is patternless and failed to register. This is most likely a code error.");
				}
			} else if (Expression.class.isAssignableFrom(clazz)) {
				if (clazz.isAnnotationPresent(Patterns.class)) {
					try {
						Expression ex = (Expression) clazz.newInstance();
						Skript.registerExpression((Class<? extends Expression>) clazz, ex.getReturnType(), ExpressionType.PROPERTY, ((Patterns) clazz.getAnnotation(Patterns.class)).value());
						success++;
					} catch (InstantiationException e) {
						Bukkit.getLogger().info("[skQuery] " + clazz.getCanonicalName() + " could not be instantiated by skQuery!");
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				} else if (clazz.isAnnotationPresent(UsePropertyPatterns.class) && clazz.isAnnotationPresent(PropertyFrom.class) && clazz.isAnnotationPresent(PropertyTo.class) ) {
					try {
						Expression ex = (Expression) clazz.newInstance();
						Skript.registerExpression((Class<? extends Expression>) clazz, ex.getReturnType(), ExpressionType.PROPERTY,
								"[the] " + ((PropertyTo) clazz.getAnnotation(PropertyTo.class)).value() + " of %" + ((PropertyFrom) clazz.getAnnotation(PropertyFrom.class)).value() + "%",
								"%" + ((PropertyFrom) clazz.getAnnotation(PropertyFrom.class)).value() + "%'[s] " + ((PropertyTo) clazz.getAnnotation(PropertyTo.class)).value());
						success++;
					} catch (InstantiationException e) {
						Bukkit.getLogger().info("[skQuery] " + clazz.getCanonicalName() + " could not be instantiated by skQuery!");
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				} else {
					Bukkit.getLogger().info("[skQuery] " + clazz.getCanonicalName() + " is patternless and failed to register. This is most likely a code error.");
				}
			} else if (AbstractTask.class.isAssignableFrom(clazz)) {
				try {
					AbstractTask task = (AbstractTask) clazz.newInstance();
					task.run();
					success++;
				} catch (InstantiationException e) {
					Bukkit.getLogger().info("[skQuery] " + clazz.getCanonicalName() + " could not be instantiated by skQuery!");
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		Bukkit.getLogger().info("[skQuery] Out of " + classes.length + " classes, " + success + " classes were loaded from " + info.getName());
	}

}
