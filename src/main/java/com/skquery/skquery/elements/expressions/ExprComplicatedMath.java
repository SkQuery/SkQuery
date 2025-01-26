package com.skquery.skquery.elements.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import com.skquery.skquery.annotations.Patterns;
import com.skquery.skquery.util.Collect;

import org.bukkit.event.Event;

import java.math.BigInteger;

@Patterns({
        "sin[e] %number%",
        "cos[ine] %number%",
        "tan[gent] %number%",
        "arc sin[e] %number%",
        "arc cos[ine] %number%",
        "arc tan[gent] %number%",
        "hyperbolic sin[e] %number%",
        "hyperbolic cos[ine] %number%",
        "hyperbolic tan[gent] %number%",
        "[natural ]log[arithm] %number%",
        "base(-| )10 log[arithm] %number%",
        "signum %number%",
        "(sqrt|square root)[ of] %number%",
        "(%number% factorial|%number%!)"
})
public class ExprComplicatedMath extends SimpleExpression<Number> {

    private Expression<Number> arg;
    private int math;

    @Override
    protected Number[] get(Event event) {
        Number n = arg.getSingle(event);
        if (n == null) return null;
        double endResult = 0;
        switch (math) {
            case 0:
                endResult = StrictMath.sin(n.doubleValue());
                break;
            case 1:
                endResult = StrictMath.cos(n.doubleValue());
                break;
            case 2:
                endResult = StrictMath.tan(n.doubleValue());
                break;
            case 3:
                endResult = StrictMath.asin(n.doubleValue());
                break;
            case 4:
                endResult = StrictMath.acos(n.doubleValue());
                break;
            case 5:
                endResult = StrictMath.atan(n.doubleValue());
                break;
            case 6:
                endResult = StrictMath.sinh(n.doubleValue());
                break;
            case 7:
                endResult = StrictMath.cosh(n.doubleValue());
                break;
            case 8:
                endResult = StrictMath.tanh(n.doubleValue());
                break;
            case 9:
                endResult = StrictMath.log(n.doubleValue());
                break;
            case 10:
                endResult = StrictMath.log10(n.doubleValue());
                break;
            case 11:
                endResult = StrictMath.signum(n.doubleValue());
                break;
            case 12:
                endResult = StrictMath.sqrt(n.doubleValue());
                break;
            case 13:
                endResult = factorial(n.intValue()).doubleValue();
                break;
        }
        return Collect.asArray(endResult);
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "mathing";
    }

    @SuppressWarnings("unchecked")
	@Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        arg = (Expression<Number>) expressions[0];
        math = i;
        return true;
    }

    private static BigInteger factorial(int num) {
        BigInteger fact = BigInteger.ONE;
        for (int i = 1; i <= num; i++)
            fact = fact.multiply(BigInteger.valueOf(i));
        return fact;
    }
}
