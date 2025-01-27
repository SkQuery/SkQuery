package com.skquery.skquery.skript;

import ch.njol.skript.lang.Effect;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Collection;

public class LambdaEffect {

    private final ArrayList<Effect> chain = new ArrayList<>();
    private final boolean isVoid;

    public LambdaEffect(boolean isVoid) {
        this.isVoid = isVoid;
    }

    public LambdaEffect(Effect e) {
        if (e != null) chain.add(e);
        isVoid = false;
    }

    public LambdaEffect add(LambdaEffect e) {
        if (e != null) chain.addAll(e.getChain());
        return this;
    }

    public Collection<Effect> getChain() {
        if (isVoid) return new ArrayList<>();
        return chain;
    }

    public void walk(Event e) {
        for (Effect effect : chain) {
            effect.run(e);
        }
    }

    public boolean isVoid() {
        return isVoid;
    }
}
