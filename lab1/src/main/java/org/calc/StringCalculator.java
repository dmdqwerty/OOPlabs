package org.calc;

import java.util.regex.Pattern;

public class StringCalculator {
    public int add(String numbers) {
        if (numbers.isEmpty()) {
            return 0;
        }
        int result = 0;
        String[] numbersSplitted = splitNumbers(numbers);
        for (String num : numbersSplitted) {
            result += Integer.parseInt(num);
        }
        return result;
    }

    private String[] splitNumbers(String numbers) {
        String[] numbersSplitted = Pattern.compile("[\n,]").split(numbers);
        for (String number : numbersSplitted) {
            if (number.isEmpty()) {
                throw new IllegalArgumentException("Two delimiters in a row are not allowed.");
            }
        }
        return numbersSplitted;
    }

    public static void main(String[] args) {
        StringCalculator calc = new StringCalculator();
        int res = calc.add("3,7\n20");
        System.out.println(res);
    }
}
