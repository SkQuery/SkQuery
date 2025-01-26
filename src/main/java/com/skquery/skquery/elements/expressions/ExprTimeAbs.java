package com.skquery.skquery.elements.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.util.Time;

import com.skquery.skquery.annotations.PropertyFrom;
import com.skquery.skquery.annotations.PropertyTo;
import com.skquery.skquery.annotations.UsePropertyPatterns;
import com.skquery.skquery.util.Collect;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@UsePropertyPatterns
@PropertyFrom("players")
@PropertyTo("time")
public class ExprTimeAbs extends SimplePropertyExpression<Player, Time> {

    @Override
    protected String getPropertyName() {
        return "time";
    }

    @Override
    public Time convert(Player player) {
        return new Time(((Number)player.getPlayerTime()).intValue());
    }

    @Override
    public Class<? extends Time> getReturnType() {
        return Time.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.SET) return Collect.asArray(Time.class);
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        Time n = delta[0] == null ? new Time() : (Time) delta[0] ;
        switch (mode) {
            case SET:
                for (Player p : getExpr().getAll(e))  {
                    p.setPlayerTime(n.getTicks(), false);
                }
                break;
            case RESET:
                for (Player p : getExpr().getAll(e))  {
                    p.resetPlayerTime();
                }
            case ADD:
            case REMOVE:
            case REMOVE_ALL:
            case DELETE:
                assert false;
        }
    }
}
