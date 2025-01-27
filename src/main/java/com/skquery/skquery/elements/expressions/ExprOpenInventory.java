package com.skquery.skquery.elements.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.skquery.skquery.annotations.PropertyFrom;
import com.skquery.skquery.annotations.PropertyTo;
import com.skquery.skquery.annotations.UsePropertyPatterns;

@UsePropertyPatterns
@PropertyFrom("player")
@PropertyTo("(current|open) inventory")
public class ExprOpenInventory extends SimplePropertyExpression<Player, Inventory> {

    @Override
    protected String getPropertyName() {
        return "open inventory";
    }

    @Override
    public Inventory convert(Player player) {
        return player.getOpenInventory().getTopInventory();
    }

    @Override
    public Class<? extends Inventory> getReturnType() {
        return Inventory.class;
    }
}
