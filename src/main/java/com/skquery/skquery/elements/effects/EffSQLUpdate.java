package com.skquery.skquery.elements.effects;

import java.io.File;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.event.Event;

import com.skquery.skquery.SkQuery;
import com.skquery.skquery.annotations.Patterns;
import com.skquery.skquery.sql.ScriptCredentials;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.parser.ParserInstance;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;

@Patterns("update %string%")
public class EffSQLUpdate extends Effect {

	private File executor;
	private Expression<String> query;
	private String pool;

	@Override
	protected void execute(Event event) {
		String q = query.getSingle(event);
		if (q == null) return;
		Statement st = null;
		try {
			st = ScriptCredentials.get(executor, pool).getConnection(pool).createStatement();
			st.executeUpdate(q);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (st != null) try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String toString(Event event, boolean b) {
		return "sql query";
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, ParseResult parseResult) {
		File file = SkQuery.getConfig(ParserInstance.get()).getFile();
		if (file == null)
    		return false;
		if (ScriptCredentials.get(file).getConnection() == null) {
			Skript.error("Database features are disabled until the script has SQL credentials associated with it.", ErrorQuality.SEMANTIC_ERROR);
			return false;
		}
		executor = file;
		query = (Expression<String>) expressions[0];
		pool = ScriptCredentials.currentPool;
		ScriptCredentials.currentPool = "default";
		return true;
	}

}
