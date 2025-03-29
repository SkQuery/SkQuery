package com.w00tmast3r.skquery.elements.expressions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event;

import com.skquery.skquery.annotations.Patterns;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Patterns("yaml (1¦value|2¦nodes|3¦nodes with keys|4¦list) %string% from [file] %string%")
public class ExprYaml extends SimpleExpression<Object>{
	
	private Expression<String> node, file;
	private static enum States {
		VALUE, NODES, NODES_KEYS, LIST
	}
	
	private States state;
	@Override
	public Class<? extends Object> getReturnType() {
		return Object.class;
	}
	
	@Override
	public boolean isSingle() {
		return state == States.VALUE ? true : false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parse) {
		if (parse.mark == 1) {
			state = States.VALUE;
		} else if (parse.mark == 2) {
			state = States.NODES;
		} else if (parse.mark == 3) {
			state = States.NODES_KEYS;
		} else if (parse.mark == 4) {
			state = States.LIST;
		}
		node = (Expression<String>) e[0];
		file = (Expression<String>) e[1];
		return true;
	}
	
	@Override
	public String toString(Event e, boolean arg1) {
		return "[skellett] (file|y[a]ml) [file] (1¦value|2¦node[s]|3¦node[s with] keys|4¦list) %string% (in|at|from) [file] %string%";
	}
	
	@Override
	protected Object[] get(Event e) {
		File fileExistance = new File(file.getSingle(e));
		if (file.getSingle(e).contains("/")) {
			fileExistance = new File(file.getSingle(e).replaceAll("/'", File.separator));
		}
		if (!fileExistance.exists()) {
			try {
				createFileAndPath(fileExistance);
			} catch (IOException error) {
				error.printStackTrace();
			}
		}
		FileConfiguration config = YamlConfiguration.loadConfiguration(fileExistance);
		if (!config.contains(node.getSingle(e))) {
			return null;
		}
		if (state == States.VALUE) {
			return CollectionUtils.array(config.get(node.getSingle(e)));
		} else if (state == States.NODES) {
			if (!config.isConfigurationSection(node.getSingle(e)))
				return null;
			Set<String> nodes = config.getConfigurationSection(node.getSingle(e)).getKeys(false);
			return nodes.toArray(new String[nodes.size()]);
		} else if (state == States.NODES_KEYS) {
			if (!config.isConfigurationSection(node.getSingle(e)))
				return null;
			Set<String> nodesKeys = config.getConfigurationSection(node.getSingle(e)).getKeys(true);
			return nodesKeys.toArray(new String[nodesKeys.size()]);
		} else if (state == States.LIST) {
			List<?> items = config.getList(node.getSingle(e));
			if (items == null || items.isEmpty())
				return null;
			return items.toArray();
		}
		return null;
	}
	
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		File fileExistance = new File(file.getSingle(e));
		if (file.getSingle(e).contains("/")) {
			fileExistance = new File(file.getSingle(e).replaceAll("/'", File.separator));
		}
		if (!fileExistance.exists()) {
			try {
				createFileAndPath(fileExistance);
			} catch (IOException error) {
				error.printStackTrace();
			}
		}
		FileConfiguration config = YamlConfiguration.loadConfiguration(fileExistance);
		if (mode == ChangeMode.DELETE || mode == ChangeMode.RESET) {
			config.set(node.getSingle(e), null);
			return;
		}
		Object target = delta[0] == null ? "" : delta[0];
		if (state == States.VALUE) {
			if (mode == ChangeMode.SET) {
				config.set(node.getSingle(e), delta[0]);
			}
		} else if (state == States.NODES_KEYS) {
			if (mode == ChangeMode.ADD) {
				config.createSection(node.getSingle(e));
			} else if (mode == ChangeMode.REMOVE) {
				config.set(node.getSingle(e) + "." + target, null);
			}
		} else if (state == States.LIST) {
			if (mode == ChangeMode.ADD) {
				@SuppressWarnings("unchecked")
				ArrayList<Object> objects = (ArrayList<Object>) config.getList(node.getSingle(e));
				if (config.getList(node.getSingle(e)) == null) {
					ArrayList<Object> obj = new ArrayList<>();
					obj.add(delta[0]);
					config.set(node.getSingle(e), obj);
				} else {
					objects.add(delta[0]);
				}
			} else if (mode == ChangeMode.REMOVE) {
				config.getList(node.getSingle(e)).remove(delta[0]);
			}
		}
		try {
			File f = new File(file.getSingle(e));
			if (!f.exists()) {
				f.mkdirs();
				f.createNewFile();
			}
			config.save(f);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.DELETE || mode == Changer.ChangeMode.RESET) {
			return CollectionUtils.array(Object.class);
		}
		if (state == States.VALUE) {
			if (mode == Changer.ChangeMode.SET) {
				return CollectionUtils.array(Object.class);
			}
		} else if (state == States.LIST) {
			if (mode == Changer.ChangeMode.ADD || mode == Changer.ChangeMode.REMOVE) {
				return CollectionUtils.array(Object.class);
			}
		}
		return null;
	}
	
	@SuppressWarnings("unused")
	public static void createFileAndPath(File file) throws IOException {
		if (!file.exists()) {
			String folderPath;
			File folder;
			String filePath = file.getPath();
			int index = filePath.lastIndexOf(File.separator);
			if (index >= 0 && !(folder = new File(folderPath = filePath.substring(0, index))).exists()) {
				folder.mkdirs();
			}
			file.createNewFile();
		}
	}
}