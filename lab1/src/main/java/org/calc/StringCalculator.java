package org.calc;

public class StringCalculator {
    public int add(String numbers) {
        if (numbers.length() == 0) {
            return 0;
        }

        String[] numbersSplit = numbers.split(",");
        int result = 0;
        for (String number : numbersSplit) {
            result += Integer.parseInt(number);
        }
        return result;
    }
}
