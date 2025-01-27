package com.skquery.skquery.elements.effects;

import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.SkriptParser;

import com.skquery.skquery.annotations.Description;
import com.skquery.skquery.annotations.Examples;
import com.skquery.skquery.annotations.Patterns;
import com.skquery.skquery.elements.effects.base.OptionsPragma;
import com.skquery.skquery.sql.ScriptCredentials;

import java.io.File;

@Name("SQL Username Option")
@Description("Sets the script-wide database connection username. Required for an SQL connection.")
@Examples("script options:;->$ init com.mysql.jdbc.Driver;->$ db url jdbc:mysql://localhost:3306/skript;->$ db username admin;->$ db password heil_putin")
@Patterns("$ db username <.+>")
public class EffOptionSQLUsername extends OptionsPragma {

	@Override
	protected void register(File executingScript, SkriptParser.ParseResult parseResult) {
		ScriptCredentials.setUsername(executingScript, parseResult.regexes.get(0).group());
	}

}
