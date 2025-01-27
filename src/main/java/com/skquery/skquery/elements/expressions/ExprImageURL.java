package com.skquery.skquery.elements.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import com.skquery.skquery.annotations.Patterns;
import com.skquery.skquery.util.Collect;

import org.bukkit.event.Event;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

@Patterns("[the] [buffered[ ]]image from [url] %string%")
public class ExprImageURL extends SimpleExpression<BufferedImage> {

    private Expression<String> url;

    @Override
    protected BufferedImage[] get(Event event) {
        String u = url.getSingle(event);
        if (u == null) return null;
        try {
            return Collect.asArray(ImageIO.read(new URL(u)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends BufferedImage> getReturnType() {
        return BufferedImage.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "image from url";
    }

    @SuppressWarnings("unchecked")
	@Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        url = (Expression<String>) expressions[0];
        return true;
    }
}
