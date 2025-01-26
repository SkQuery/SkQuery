package com.skquery.skquery.util.menus;

import com.skquery.skquery.SkQuery;
import com.skquery.skquery.util.BiValue;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class FormattedSlotManager implements Listener {

	private static final Map<UUID, BiValue<HashMap<Integer, SlotRule>, Event>> playerRules = new HashMap<>();
	private static final Set<UUID> exempt = new HashSet<>();

	public static Map<Integer, SlotRule> getRules(Player player) {
		UUID uuid = player.getUniqueId();
		return playerRules.containsKey(uuid) ? playerRules.get(uuid).getFirst() : new HashMap<Integer, SlotRule>();
	}

	public static void setRules(Event event, Player player, HashMap<Integer, SlotRule> slotRules) {
		playerRules.put(player.getUniqueId(), new BiValue<>(slotRules, event));
	}

	public static void exemptNextClose(Player player) {
		exempt.add(player.getUniqueId());
	}

	public static void addRule(Event e, Player player, int slot, SlotRule rule) {
		UUID uuid = player.getUniqueId();
		if (!playerRules.containsKey(uuid)) {
			playerRules.put(uuid, new BiValue<HashMap<Integer, SlotRule>, Event>(new HashMap<Integer, SlotRule>(), null));
		}
		playerRules.get(uuid).getFirst().put(slot, rule);
		playerRules.get(uuid).setSecond(e);
	}

	public static void removeRule(Player player, int slot) {
		UUID uuid = player.getUniqueId();
		if (!playerRules.containsKey(uuid)) {
			playerRules.put(uuid, new BiValue<HashMap<Integer, SlotRule>, Event>(new HashMap<Integer, SlotRule>(), null));
		}
		playerRules.get(uuid).getFirst().remove(slot);
	}

	@EventHandler
	public void onDisconnect(PlayerQuitEvent event) {
		playerRules.remove(event.getPlayer().getUniqueId());
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		Map<Integer, SlotRule> map = getRules(player);
		if (event.isShiftClick() && map != null && map.size() > 0)
			event.setCancelled(true);
		assert map != null;
		UUID uuid = player.getUniqueId();
		if (playerRules.containsKey(uuid) && event.getSlotType() == SlotType.CONTAINER && map.get(event.getSlot()) != null) {
			event.setCancelled(true);
			SlotRule rule = playerRules.get(uuid).getFirst().get(event.getSlot());
			rule.run(playerRules.get(uuid).getSecond());
			if (rule.willClose()) {
				Bukkit.getScheduler().runTaskLater(SkQuery.getInstance(), new Runnable() {
					@Override
					public void run() {
						player.getOpenInventory().close();
					}
				}, 1);
			}
		}
	}

	@EventHandler
	public void onClose(InventoryCloseEvent event) {
		UUID uuid = event.getPlayer().getUniqueId();
		if (exempt.contains(uuid)) {
			exempt.remove(uuid);
			return;
		}
		Bukkit.getScheduler().runTaskLater(SkQuery.getInstance(), new Runnable() {
			@Override
			public void run() {
				if (playerRules.containsKey(uuid)) {
					if (playerRules.get(uuid) != null)
						playerRules.get(uuid).getFirst().clear();
					playerRules.get(uuid).setSecond(null);
				}
			}
		}, 1);
	}

}
