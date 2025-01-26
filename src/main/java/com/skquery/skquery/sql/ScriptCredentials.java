package com.skquery.skquery.sql;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ScriptCredentials {

	private static Map<File, ScriptCredentials> credentials = new HashMap<>();
	private Map<String, Connection> connection = new HashMap<>();
	public static String currentPool = "default";
	private String url, username, password;

	public static void setURL(File script, String url) {
		ScriptCredentials sc = get(script);
		sc.url = url;
		sc.validate();
	}

	public static void setUsername(File script, String username) {
		ScriptCredentials sc = get(script);
		sc.username = username;
		sc.validate();
	}

	public static void setPassword(File script, String password) {
		ScriptCredentials sc = get(script);
		sc.password = password;
		sc.validate();
	}

	public Connection getConnection() {
		return getConnection("default");
	}

	public Connection getConnection(String pool) {
		return connection.get(pool);
	}

	public static ScriptCredentials get(File script) {
		return get(script, "default");
	}

	public static ScriptCredentials get(File script, String pool) {
		if (!credentials.containsKey(script))
			credentials.put(script, new ScriptCredentials());
		ScriptCredentials c = credentials.get(script);
		try {
			if (c.connection != null && (!c.connection.containsKey(pool) || !c.connection.get(pool).isValid(1))) {
				 c.validate(pool);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

	public static void clear() {
		for (ScriptCredentials sc : credentials.values()) {
			if (sc.connection != null) try {
				for (Connection connection : sc.connection.values()) {
					connection.close();
				}
			} catch (SQLException ignored) {}
		}
	}

	private void validate() {
		if (url != null && username != null && password != null) {
			connection = new HashMap<>();
			validate("default");
		}
	}

	private void validate(String pool) {
		if (url != null && username != null && password != null) {
			try {
				connection.put(pool, DriverManager.getConnection(url, username, password));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
