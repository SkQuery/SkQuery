package com.skquery.skquery.elements.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import com.skquery.skquery.SkQuery;
import com.skquery.skquery.annotations.Patterns;

@Patterns("text from [url] %string%")
public class ExprURLText extends SimplePropertyExpression<String, String> {

    @Override
    protected String getPropertyName() {
        return "URL";
    }

    @Override
    public String convert(String s) {
        try {
            URL url = new URI(s).toURL();
            try (Scanner scanner = new Scanner(url.openStream())) {
                scanner.useDelimiter("\\A");
                return scanner.hasNext() ? scanner.next() : "";
            }
        } catch (IOException | URISyntaxException ex) {
            if (SkQuery.getInstance().getConfig().getBoolean("debug", false)) {
                ex.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
