package com.skquery.skquery.elements.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;

import com.skquery.skquery.annotations.PropertyFrom;
import com.skquery.skquery.annotations.PropertyTo;
import com.skquery.skquery.annotations.UsePropertyPatterns;
import com.skquery.skquery.util.RomanNumerals;

@UsePropertyPatterns
@PropertyFrom("strings")
@PropertyTo("arabic num(ber|eral)")
public class ExprRomanNumeralFrom extends SimplePropertyExpression<String, Number> {

    @Override
    protected String getPropertyName() {
        return "roman numeral";
    }

    @Override
    public Number convert(String number) {
        return RomanNumerals.fromRoman(number);
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

}
