package com.skquery.skquery.elements.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;

import com.skquery.skquery.annotations.PropertyFrom;
import com.skquery.skquery.annotations.PropertyTo;
import com.skquery.skquery.annotations.UsePropertyPatterns;
import com.skquery.skquery.util.Collect;

import org.bukkit.Art;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Painting;
import org.bukkit.event.Event;

@UsePropertyPatterns
@PropertyFrom("entities")
@PropertyTo("[displayed] art")
public class ExprArt extends SimplePropertyExpression<Entity, Art> {

    @Override
    protected String getPropertyName() {
        return "art art art";
    }

    @Override
    public Art convert(Entity entity) {
        return entity instanceof Painting ? ((Painting) entity).getArt() : null;
    }

    @Override
    public Class<? extends Art> getReturnType() {
        return Art.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.SET) return Collect.asArray(Art.class);
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        Art n = delta[0] == null ? Art.ALBAN : (Art) delta[0] ;
        switch (mode) {
            case SET:
                for (Entity p : getExpr().getAll(e))  {
                    if (p instanceof Painting) ((Painting) p).setArt(n, true);
                }
                break;
            case RESET:
                for (Entity p : getExpr().getAll(e))  {
                    if (p instanceof Painting) ((Painting) p).setArt(Art.ALBAN, true);
                }
            case ADD:
            case REMOVE:
            case REMOVE_ALL:
            case DELETE:
                assert false;
        }
    }
}
