package com.skquery.skquery.elements.expressions;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.event.Event;

import com.skquery.skquery.SkQuery;
import com.skquery.skquery.annotations.Patterns;
import com.skquery.skquery.sql.ScriptCredentials;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.parser.ParserInstance;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;

@Patterns("result of query %string%")
public class ExprSQLQuery extends SimpleExpression<ResultSet> {

    private File executor;
    private Expression<String> query;
    private String pool;

    @Override
    protected ResultSet[] get(Event event) {
        String q = query.getSingle(event);
        if (q == null) return null;
        Statement st = null;
        try {
            st = ScriptCredentials.get(executor, pool).getConnection(pool).createStatement();
            return new ResultSet[]{st.executeQuery(q)};
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (st != null) try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends ResultSet> getReturnType() {
        return ResultSet.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "sql query";
    }

    @SuppressWarnings("unchecked")
	@Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
    	File file = SkQuery.getConfig(ParserInstance.get()).getFile();
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
