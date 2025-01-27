package com.skquery.skquery.elements.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;

import com.skquery.skquery.annotations.PropertyFrom;
import com.skquery.skquery.annotations.PropertyTo;
import com.skquery.skquery.annotations.UsePropertyPatterns;
import com.skquery.skquery.util.RomanNumerals;

@UsePropertyPatterns
@PropertyFrom("numbers")
@PropertyTo("roman num(ber|eral)")
public class ExprRomanNumeralTo extends SimplePropertyExpression<Number, String> {

    @Override
    protected String getPropertyName() {
        return "roman numeral";
    }

    @Override
    public String convert(Number number) {
        return RomanNumerals.toRoman(number.intValue());
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

}
