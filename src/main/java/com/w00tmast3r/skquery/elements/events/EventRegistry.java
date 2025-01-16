package com.w00tmast3r.skquery.elements.events;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.registrations.EventValues;

import com.w00tmast3r.skquery.SkQuery;
import com.w00tmast3r.skquery.annotations.AbstractTask;
import com.w00tmast3r.skquery.elements.events.bukkit.AttachedTabCompleteEvent;
import com.w00tmast3r.skquery.elements.events.lang.*;
import com.w00tmast3r.skquery.util.projectile.ItemProjectileHitEvent;

import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.event.entity.SheepDyeWoolEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EventRegistry extends AbstractTask {

    @Override
    public void run() {
        registerEvent("Enchant", EnchantItemEvent.class, "enchant");
        EventValues.registerEventValue(EnchantItemEvent.class, ItemStack.class, EnchantItemEvent::getItem, 0);
        EventValues.registerEventValue(EnchantItemEvent.class, Player.class, EnchantItemEvent::getEnchanter, 0);

        registerEvent("Sheep Dye", SheepDyeWoolEvent.class, "sheep dye");
        
        registerEvent("Horse Jump", HorseJumpEvent.class, "horse jump");
        
        registerEvent("Book Editing", PlayerEditBookEvent.class, "[book] edit");
       
        registerEvent("Flight Toggle", PlayerToggleFlightEvent.class, "[player] toggl(e|ing) (flight|fly)", "[player] (flight|fly) toggl(e|ing)");
       
        registerEvent("Inventory Click",  InventoryClickEvent.class, "inventory click");
        EventValues.registerEventValue(InventoryClickEvent.class, ItemStack.class, InventoryClickEvent::getCurrentItem, 0);

        registerEvent("Generic Move", PlayerMoveEvent.class, "any move[ment]");

        registerEvent("Server Ping", ServerListPingEvent.class, "[server] [list] ping");

        registerEvent("Item Projectile Hit", ItemProjectileHitEvent.class, "item [projectile] hit");
        EventValues.registerEventValue(ItemProjectileHitEvent.class, ItemType.class,
                (itemProjectileHitEvent) -> new ItemType(itemProjectileHitEvent.getProjectile().getItemStack()), 0);
        EventValues.registerEventValue(ItemProjectileHitEvent.class, LivingEntity.class,
                ItemProjectileHitEvent::getShooter, 0);
        EventValues.registerEventValue(ItemProjectileHitEvent.class, Location.class,
                (itemProjectileHitEvent)-> itemProjectileHitEvent.getProjectile().getLocation(), 0);

        registerEvent("Falling Block Land", EvtBlockLand.class, EntityChangeBlockEvent.class, "block land");
        EventValues.registerEventValue(EntityChangeBlockEvent.class, ItemStack.class,
                (entityChangeBlockEvent) -> entityChangeBlockEvent.getEntity() instanceof FallingBlock ? new ItemStack(((FallingBlock) entityChangeBlockEvent.getEntity()).getBlockData().getMaterial()) : null, 0);
        EventValues.registerEventValue(EntityChangeBlockEvent.class, Entity.class,
                (entityChangeBlockEvent) -> entityChangeBlockEvent.getEntity() instanceof FallingBlock ? entityChangeBlockEvent.getEntity() : null, 0);

        registerEvent("Close Inventory", InventoryCloseEvent.class, "inventory [window] close");
        EventValues.registerEventValue(InventoryCloseEvent.class, Inventory.class, InventoryEvent::getInventory, 0);
        EventValues.registerEventValue(InventoryCloseEvent.class, Player.class,
                (inventoryCloseEvent) -> inventoryCloseEvent.getPlayer() instanceof Player ? (Player) inventoryCloseEvent.getPlayer() : null, 0);

        registerEvent("Vehicle Collide With Block", VehicleBlockCollisionEvent.class, "vehicle (block collide|collide with block)");
        EventValues.registerEventValue(VehicleBlockCollisionEvent.class, Entity.class, VehicleBlockCollisionEvent::getVehicle, 0);

        EventValues.registerEventValue(VehicleBlockCollisionEvent.class, Block.class, VehicleBlockCollisionEvent::getBlock, 0);

        registerEvent("Vehicle Collide With Entity", VehicleBlockCollisionEvent.class, "vehicle (entity collide|collide with entity)");
        EventValues.registerEventValue(VehicleEntityCollisionEvent.class, Entity.class, VehicleEntityCollisionEvent::getVehicle, 0);

        EventValues.registerEventValue(VehicleEntityCollisionEvent.class, Entity.class, VehicleEntityCollisionEvent::getEntity, 0);

        registerEvent("*Script Options Header", ScriptOptionsEvent.class, "script options");

        registerEvent("*Tab Complete", EvtAttachCompleter.class, AttachedTabCompleteEvent.class, "tab complet(er|ion) [for [command]] %string%");;
        EventValues.registerEventValue(AttachedTabCompleteEvent.class, Player.class,
                (e) -> e.getSender() instanceof Player ? ((Player) e.getSender()) : null, 0);

        try {
			SkQuery.getAddonInstance().loadClasses("com.w00tmast3r.skquery.elements", "events");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
