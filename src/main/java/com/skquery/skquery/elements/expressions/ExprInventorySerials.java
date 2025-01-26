package com.skquery.skquery.elements.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;

import com.skquery.skquery.annotations.PropertyFrom;
import com.skquery.skquery.annotations.PropertyTo;
import com.skquery.skquery.annotations.UsePropertyPatterns;
import com.skquery.skquery.util.Collect;
import com.skquery.skquery.util.serialization.InventorySerialUtils;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;

@UsePropertyPatterns
@PropertyFrom("inventory")
@PropertyTo("serialized contents")
public class ExprInventorySerials extends SimplePropertyExpression<Inventory, String> {
    @Override
    protected String getPropertyName() {
        return "inventory serial";
    }

    @Override
    public String convert(Inventory inventory) {
        return InventorySerialUtils.toBase64(inventory);
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        return mode == Changer.ChangeMode.SET ? Collect.asArray(String.class) : null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        String s = delta[0] == null ? "" : (String) delta[0];
        Inventory i = getExpr().getSingle(e);
        try {
            i.setContents(InventorySerialUtils.fromBase64(s).getContents());
        } catch (Exception ignored) {}
    }
}
