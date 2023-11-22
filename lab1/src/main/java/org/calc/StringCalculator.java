package org.calc;

import java.util.regex.Pattern;

public class StringCalculator {
    public int add(String numbers) {
        if (numbers.isEmpty()) {
            return 0;
        }
        int result = 0;
        char[] numbersAsCharArray = numbers.toCharArray();
        String pattern = createRegExPattern(numbersAsCharArray);
        if (hasCustomDelimiters(numbersAsCharArray)) {
            numbers = cutFirstLine(numbersAsCharArray);
        }

        String[] numbersSplitted = splitNumbers(numbers, pattern);

        for (String num : numbersSplitted) {
            result += Integer.parseInt(num);
        }
        return result;
    }

    private boolean hasCustomDelimiters(char[] numbersAsCharArray) {
        return numbersAsCharArray[0] == '/' && numbersAsCharArray[1] == '/';
    }

    private String cutFirstLine(char[] numbersAsCharArray) {
        int index = 0;
        while (numbersAsCharArray[index] != '\n') {
            index++;
        }
        index++;
        char[] numbersCut = new char[numbersAsCharArray.length - index];
        System.arraycopy(numbersAsCharArray, index, numbersCut, 0, numbersCut.length);

        return new String(numbersCut);
    }

    private String createRegExPattern(char[] numbersAsCharArray) {
        StringBuilder pattern = new StringBuilder("\n,");
        if (hasCustomDelimiters(numbersAsCharArray)) {
            if (numbersAsCharArray[2] != '[') {
                pattern.append(numbersAsCharArray[2]);
            } else {
                pattern.append(numbersAsCharArray[3]);
            }
        }
        pattern.append("]").insert(0, '[');
        return pattern.toString();

    }

    private String[] splitNumbers(String numbers, String pattern) {
        String[] numbersSplitted = Pattern.compile(pattern).split(numbers);
        for (String number : numbersSplitted) {
            if (number.isEmpty()) {
                throw new IllegalArgumentException("Two delimiters in a row are not allowed.");
            }
        }
        return numbersSplitted;
    }

    public static void main(String[] args) {
        StringCalculator calc = new StringCalculator();
        int res = calc.add("//[*]\n3*7\n20,5*10");
        System.out.println(res);
    }
}
