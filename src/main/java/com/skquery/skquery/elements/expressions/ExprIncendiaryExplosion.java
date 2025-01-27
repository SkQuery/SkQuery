package com.skquery.skquery.elements.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;

import com.skquery.skquery.annotations.PropertyFrom;
import com.skquery.skquery.annotations.PropertyTo;
import com.skquery.skquery.annotations.UsePropertyPatterns;
import com.skquery.skquery.util.Collect;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Explosive;
import org.bukkit.entity.LargeFireball;
import org.bukkit.event.Event;

@UsePropertyPatterns
@PropertyFrom("entities")
@PropertyTo("(blast|explosion) fire (state|ability|mode)")
public class ExprIncendiaryExplosion extends SimplePropertyExpression<Entity, Boolean> {
    @Override
    protected String getPropertyName() {
        return "blast size";
    }

    @Override
    public Boolean convert(Entity entity) {
        return entity instanceof Explosive ? ((Explosive) entity).isIncendiary() : null;
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.SET) return Collect.asArray(Boolean.class);
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        boolean b = delta != null && (Boolean) delta[0];
        switch (mode) {
            case SET:
                for (Entity en : getExpr().getAll(e))  {
                    if (en instanceof Explosive) ((Explosive) en).setIsIncendiary(b);
                }
                break;
            case RESET:
                for (Entity en : getExpr().getAll(e))  {
                    if (!(en instanceof Explosive)) continue;
                    if (en instanceof LargeFireball) ((LargeFireball) en).setIsIncendiary(true);
                    else ((Explosive) en).setIsIncendiary(false);
                }
            case ADD:
            case REMOVE:
            case REMOVE_ALL:
            case DELETE:
                assert false;
        }
    }
}
