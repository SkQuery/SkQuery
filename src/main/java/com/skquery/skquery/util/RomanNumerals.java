package com.skquery.skquery.util;

public class RomanNumerals {

    public static  String toRoman(int number) {
        String roman[] = {"M","XM","CM","D","XD","CD","C","XC","L","XL","X","IX","V","IV","I"};
        int arab[] = {1000, 990, 900, 500, 490, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        StringBuilder result = new StringBuilder();
        int i = 0;
        while (number > 0 || arab.length == (i - 1)) {
            while ((number - arab[i]) >= 0) {
                number -= arab[i];
                result.append(roman[i]);
            }
            i++;
        }
        return result.toString();
    }

    public static int fromRoman(String number) {
        if (number.startsWith("M")) return 1000 + fromRoman(number.substring(1));
        if (number.startsWith("CM")) return 900 + fromRoman(number.substring(2));
        if (number.startsWith("D")) return 500 + fromRoman(number.substring(1));
        if (number.startsWith("CD")) return 400 + fromRoman(number.substring(2));
        if (number.startsWith("C")) return 100 + fromRoman(number.substring(1));
        if (number.startsWith("XC")) return 90 + fromRoman(number.substring(2));
        if (number.startsWith("L")) return 50 + fromRoman(number.substring(1));
        if (number.startsWith("XL")) return 40 + fromRoman(number.substring(2));
        if (number.startsWith("X")) return 10 + fromRoman(number.substring(1));
        if (number.startsWith("IX")) return 9 + fromRoman(number.substring(2));
        if (number.startsWith("V")) return 5 + fromRoman(number.substring(1));
        if (number.startsWith("IV")) return 4 + fromRoman(number.substring(2));
        if (number.startsWith("I")) return 1 + fromRoman(number.substring(1));
        return 0;
    }
}
