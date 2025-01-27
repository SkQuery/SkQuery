package com.skquery.skquery;

/* import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;

import com.skquery.skquery.annotations.Description;
import com.skquery.skquery.annotations.DocumentationHidden;
import com.skquery.skquery.annotations.Examples;
import com.skquery.skquery.annotations.Patterns;
import com.skquery.skquery.annotations.PropertyFrom;
import com.skquery.skquery.annotations.PropertyTo;
import com.skquery.skquery.annotations.UsePropertyPatterns;
import com.skquery.skquery.util.Reflection;
import com.skquery.skquery.util.SkQueryInternalException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.event.Event; */

public class Documentation {

	/* private static HashMap<Class<?>, String[]> events = new HashMap<>();
	private static Set<Class<?>> expressions = new HashSet<>();
	private static Set<Class<?>> conditions = new HashSet<>();
	private static Set<Class<? extends Effect>> effects = new HashSet<>();
	private final SkQuery instance;

	public Documentation(SkQuery instance) {
		this.instance = instance;
		if (!Reflection.getCaller().getProtectionDomain().getCodeSource().getLocation().sameFile(Documentation.class.getProtectionDomain().getCodeSource().getLocation()))
			throw new SkQueryInternalException("Documentation could not be generated externally.");
		try {
			generateEffects();
			generateDocsFor(conditions, "conditions");
			generateDocsFor(expressions, "expressions");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void generateEffects() throws IOException {
		File html = new File(instance.getDataFolder().getAbsolutePath() + File.separator + "effects.html");
		html.mkdir();
		html.createNewFile();
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(html))) {
			try (InputStream inputStream = instance.getResource("documentation/effects.html")) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
				while (reader.ready()) {
					String line = reader.readLine();
					if (!line.trim().equals("INSERT-SYNTAX")) {
						if (line.trim().equals("INSERT-SCROLL")) {
							for (Class<?> effect : effects) {
								if (effect.isAnnotationPresent(DocumentationHidden.class))
									continue;
								writer.write("<a class=\"nav-link scrollto\" href=\"#" + effect.getSimpleName() + "\">" + effect.getSimpleName() + "</a>");
								writer.newLine();
							}
							continue;
						}
						writer.write(line);
						writer.newLine();
						continue;
					}
					for (Class<?> effect : effects) {
						if (effect.isAnnotationPresent(DocumentationHidden.class))
							continue;
						writer.write("<section id=\"" + effect.getSimpleName() + "\" class=\"doc-section\">");
						writer.newLine();
						if (effect.isAnnotationPresent(Name.class))
							writer.write("	<h2 class=\"section-title\">" + effect.getAnnotation(Name.class).value() + "</h2>");
						else
							writer.write("	<h2 class=\"section-title\">" + effect.getSimpleName() + "</h2>");
						writer.newLine();
						writer.write("	<div class=\"section-block\">");
						writer.newLine();
						if (effect.isAnnotationPresent(Description.class)) {
							writer.write("		<p>" + effect.getAnnotation(Description.class).value().replaceAll("\\(([^\\(\\)]+)?\\(([^\\(\\)]+)\\)([^\\(\\)]+)\\)", "<a href=\"$1#$2\">$3</a>") + "</p>");
							writer.newLine();
						}
						writer.write("	</div>");
						writer.newLine();
						writer.write("	<div class=\"syntax-effects\">");
						writer.newLine();
						String syntax = "";
						if (effect.isAnnotationPresent(UsePropertyPatterns.class)) {
							syntax = "%" + effect.getAnnotation(PropertyFrom.class).value() + "%'s " + effect.getAnnotation(PropertyTo.class).value() + "<br/>";
							syntax = syntax + "[the] " + effect.getAnnotation(PropertyTo.class).value() + " of %" + effect.getAnnotation(PropertyFrom.class).value() + "%";
						} else {
							for (String s : effect.getAnnotation(Patterns.class).value()) {
								syntax = syntax + s + "<br/><br/>";
							}
						}
						writer.write("		<code class=\"syntax-effects-button\">" + syntax.substring(0, syntax.lastIndexOf("<br/>")) + "</code>");
						writer.newLine();
						writer.write("		<span aria-hidden=\"true\" class=\"syntax-effects-copy-button icon_documents_alt\"></span>");
						writer.newLine();
						writer.write("	</div>");
						writer.newLine();
						if (effect.isAnnotationPresent(Examples.class)) {
							writer.write("	<div id=\"html\" class=\"section-block\">");
							writer.newLine();
							writer.write("		<h6>Example</h6>");
							writer.newLine();
							String example = "";
							for (String s : effect.getAnnotation(Examples.class).value()) {
								example = example + s.replace("->", "\t").replace(";", "\n") + "\n";
							}
							writer.write("		<pre><code class=\"language-markup\">" + example + "</code></pre>");
							writer.newLine();
							writer.write("	</div>");
							writer.newLine();
							writer.write("</section>");
							writer.newLine();
						}
			    	 }
				}
			}
		}
	}

	private void generateDocsFor(Set<Class<?>> classes, String filename) throws IOException {
		File html = new File(instance.getDataFolder().getAbsolutePath() + File.separator + filename + ".html");
		html.createNewFile();
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(html))) {
			for (Class<?> e : classes) {
				if (e.isAnnotationPresent(DocumentationHidden.class))
					continue;
				writer.write("<div class=\"column\">");
				writer.newLine();
				writer.write("<div id=\"" + e.getSimpleName() + "\" class=\"ui segment\">");
				writer.newLine();
				if (e.isAnnotationPresent(Deprecated.class)) {
					writer.write("<span class=\"ui red ribbon label deprecated\">Deprecated</span><br/>");
					writer.newLine();
				}
				writer.write("<h3 class=\"ui header\">" + (e.isAnnotationPresent(Name.class) ? e.getAnnotation(Name.class).value() : (Effect.class.isAssignableFrom(e) ? e.getSimpleName().substring(3) : e.getSimpleName().substring(4))) + "</h3>");
				writer.newLine();
				writer.write("<div class=\"ui two column grid\">");
				writer.newLine();
				writer.write("<div class=\"column\">");
				writer.newLine();
				writer.write("<p>");
				writer.newLine();
				if (e.isAnnotationPresent(Description.class)) {
					writer.write(e.getAnnotation(Description.class).value().replaceAll("\\(([^\\(\\)]+)?\\(([^\\(\\)]+)\\)([^\\(\\)]+)\\)", "<a href=\"$1#$2\">$3</a>"));
					writer.newLine();
				}
				writer.write("</p>");
				writer.newLine();
				writer.write("</div>");
				writer.newLine();
				writer.write("<div class=\"column\">");
				writer.newLine();
				writer.write("<div class=\"ui basic accordion\">");
				writer.newLine();
				writer.write("<div class=\"active title\">");
				writer.newLine();
				writer.write("<i class=\"dropdown icon\"></i>Patterns");
				writer.newLine();
				writer.write("</div>");
				writer.newLine();
				writer.write("<div class=\"active content\">");
				writer.newLine();
				writer.write("<code class=\"ui content inverted segment\">");
				writer.newLine();
				if (e.isAnnotationPresent(UsePropertyPatterns.class)) {
					writer.write("%" + e.getAnnotation(PropertyFrom.class).value() + "%'s " + e.getAnnotation(PropertyTo.class).value() + "<br/>");
					writer.write("[the] " + e.getAnnotation(PropertyTo.class).value() + " of %" + e.getAnnotation(PropertyFrom.class).value() + "%");
				} else {
					for (String s : e.getAnnotation(Patterns.class).value()) {
						writer.write("" + s + "<br/>");
					}
				}
				writer.newLine();
				writer.write("</code>");
				writer.newLine();
				writer.write("</div>");
				writer.write("<div class=\"title\">");
				writer.newLine();
				writer.write("<i class=\"dropdown icon\"></i>Examples");
				writer.newLine();
				writer.write("</div>");
				writer.newLine();
				writer.write("<div class=\"content\">");
				writer.newLine();
				writer.write("<code class=\"ui content inverted segment\">");
				writer.newLine();
				if (e.isAnnotationPresent(Examples.class)) {
					writer.write("<code>");
					writer.newLine();
					for (String s : e.getAnnotation(Examples.class).value()) {
						writer.write(s.replace(";", "</br>\n") + "<br/>");
					}
					writer.newLine();
					writer.write("</code>");
				} else {
					writer.write("<span>&nbsp;</span>");
				}
				writer.newLine();
				writer.write("</code>");
				writer.newLine();
				writer.write("</div>");
				writer.newLine();
				writer.write("</div>");
				writer.newLine();
				writer.write("</div>");
				writer.newLine();
				writer.write("</div>");
				writer.newLine();
				writer.write("</div>");
				writer.newLine();
				writer.write("</div>");
				writer.newLine();
			}
			writer.newLine();
			for (Class<?> e : classes) {
				if (!e.isAnnotationPresent(DocumentationHidden.class)) {
					writer.newLine();
					writer.write("<a href=\"#" + e.getSimpleName() + "\" class=\"item\">" + (e.isAnnotationPresent(Name.class) ? e.getAnnotation(Name.class).value() : (e.getSimpleName().startsWith("Expr") ? e.getSimpleName().substring(4) : e.getSimpleName())) + "</a>");
				}
			}
		}
	}

	public static void addEvent(Class<? extends Event> event, String... patterns) {
		events.put(event, patterns);
	}

	public static void addCondition(Class<? extends Condition> condition) {
		conditions.add(condition);
	}

	public static void addEffect(Class<? extends Effect> effect) {
		effects.add(effect);
	}

	public static void addExpression(Class<? extends Expression<?>> expression) {
		expressions.add(expression);
	} */

}
