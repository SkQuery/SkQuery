package com.skquery.skquery.elements.effects;

import ch.njol.skript.lang.SkriptParser;

import com.skquery.skquery.annotations.Patterns;
import com.skquery.skquery.elements.effects.base.OptionsPragma;
import com.skquery.skquery.sql.ScriptCredentials;

import java.io.File;

@Patterns("$ pool <.+>")
public class EffOptionSQLPool extends OptionsPragma {
    @Override
    protected void register(File executingScript, SkriptParser.ParseResult parseResult) {
        ScriptCredentials.currentPool = parseResult.regexes.get(0).group();
    }
}
