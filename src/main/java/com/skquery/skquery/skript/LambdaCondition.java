package com.skquery.skquery.skript;

import ch.njol.skript.lang.Condition;
import org.bukkit.event.Event;

import java.util.ArrayList;

public class LambdaCondition {

    private final ArrayList<Condition> chain = new ArrayList<>();

    public LambdaCondition(Condition e) {
        if (e != null) chain.add(e);
    }

    public LambdaCondition add(LambdaCondition e) {
        if (e != null) chain.addAll(e.getChain());
        return this;
    }

    public ArrayList<Condition> getChain() {
        return chain;
    }

    public boolean check(Event e) {
        for (Condition c : chain) {
            if (!c.check(e)) return false;
        }
        return true;
    }
}
