package com.skquery.skquery.elements.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;

import com.skquery.skquery.annotations.PropertyFrom;
import com.skquery.skquery.annotations.PropertyTo;
import com.skquery.skquery.annotations.UsePropertyPatterns;
import com.skquery.skquery.util.serialization.InventorySerialUtils;
import org.bukkit.entity.Player;

@UsePropertyPatterns
@PropertyFrom("player")
@PropertyTo("serialized inventory")
public class ExprSerial extends SimplePropertyExpression<Player, String> {
    @Override
    protected String getPropertyName() {
        return "serial";
    }

    @Override
    public String convert(Player player) {
        return InventorySerialUtils.toBase64(player.getInventory());
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
