package com.w00tmast3r.skquery.elements.conditions;

import java.io.File;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import com.w00tmast3r.skquery.annotations.Patterns;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Name("File existance")
@Description("Checks whether or not a file at the defined path(s) exist.")
@Patterns({"file %string% (does|does(n't| not)) exist", "existance of [file] %string% is %boolean%"}) // Last syntax is to support Umbaska.
public class CondFileExistance extends Condition {

	private Expression<Boolean> check;
	private Expression<String> files;

	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		files = (Expression<String>) expressions[0];
		setNegated(parseResult.mark == 1);
		if (expressions.length > 1)
			check = (Expression<Boolean>) expressions[0];
		return true;
	}

	@Override
	public boolean check(Event event) {
		if (files == null)
			return !isNegated();
		Boolean negated = (check != null) ? check.getSingle(event) : isNegated(); 
		File file = new File(files.getSingle(event));
		return file.exists() ? negated : !negated;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return "file existance" + files != null ? files.toString(event, debug) : "";
	}

}
