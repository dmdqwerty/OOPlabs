package org.calc;

public class StringCalculator {
    public int add(String numbers) {
        if (numbers.length() == 0) {
            return 0;
        }
        String[] numbersSplit = numbers.split(",");
        if (numbersSplit.length > 2) {
            throw new IllegalArgumentException("Can't process more than 2 numbers.");
        }

        int result = 0;
        for (String number : numbersSplit) {
            result += Integer.parseInt(number);
        }
        return result;
    }
}
