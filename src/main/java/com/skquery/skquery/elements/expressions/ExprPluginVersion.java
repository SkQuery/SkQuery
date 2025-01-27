package com.skquery.skquery.elements.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.skquery.skquery.annotations.PropertyFrom;
import com.skquery.skquery.annotations.PropertyTo;
import com.skquery.skquery.annotations.UsePropertyPatterns;

@UsePropertyPatterns
@PropertyFrom("string")
@PropertyTo("version")
public class ExprPluginVersion extends SimplePropertyExpression<String, String> {
    @Override
    protected String getPropertyName() {
        return "plugin version";
    }

    @Override
    public String convert(String s) {
        Plugin p = Bukkit.getPluginManager().getPlugin(s);
        return p == null ? null : p.getDescription().getVersion();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
