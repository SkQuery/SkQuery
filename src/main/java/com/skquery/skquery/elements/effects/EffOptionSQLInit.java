package com.skquery.skquery.elements.effects;

import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.SkriptParser;

import java.io.File;

import com.skquery.skquery.annotations.Description;
import com.skquery.skquery.annotations.Examples;
import com.skquery.skquery.annotations.Patterns;
import com.skquery.skquery.elements.effects.base.OptionsPragma;

@Name("Preinit Driver Option")
@Description("Allows you to initialize an SQL driver before accessing it. Not required for an SQL connection.")
@Examples("script options:;->$ init com.mysql.jdbc.Driver;->$ db url jdbc:mysql://localhost:3306/skript;->$ db username admin;->$ db password heil_putin")
@Patterns("$ init <.+>")
public class EffOptionSQLInit extends OptionsPragma {

	@Override
	protected void register(File executingScript, SkriptParser.ParseResult parseResult) {
		try {
			Class.forName(parseResult.regexes.get(0).group());

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
