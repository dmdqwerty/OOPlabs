package org.calc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class StringCalculator {
    public int add(String numbers) {
        if (numbers.isEmpty()) {
            return 0;
        }
        int result;
        char[] numbersAsCharArray = numbers.toCharArray();
        String pattern = createRegExPattern(numbersAsCharArray);
        if (hasCustomDelimiters(numbersAsCharArray)) {
            numbers = cutFirstLine(numbersAsCharArray);
        }

        String[] numbersSplitted = splitNumbers(numbers, pattern);

        result = addNumbers(numbersSplitted);
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

    private String[] sortDelimiters(char[] numbersAsCharArray) {
        StringBuilder delimitersUnsorted = new StringBuilder();
        int index = 3;

        while (numbersAsCharArray[index] != '\n') {
            if (numbersAsCharArray[index] == '[') {
                delimitersUnsorted.append("0");
            } else if (numbersAsCharArray[index] != ']') {
                delimitersUnsorted.append("\\").append(numbersAsCharArray[index]);
            }
            index++;
        }
        String[] delimiters = delimitersUnsorted.toString().split("0");
        Arrays.sort(delimiters,  (s1, s2) -> Integer.compare(s2.length(), s1.length()));
        return delimiters;
    }

    private String createRegExPattern(char[] numbersAsCharArray) {
        StringBuilder pattern = new StringBuilder("\n,");
        if (hasCustomDelimiters(numbersAsCharArray)) {
            if (numbersAsCharArray[2] != '[') {
                pattern.append(numbersAsCharArray[2]);
                pattern.append(']').insert(0, '[');
            } else {
                pattern.append(']').insert(0, '[');
                String[] delimiters = sortDelimiters(numbersAsCharArray);
                for (String delimiter : delimiters) {
                    pattern.append("|").append(delimiter);
                }
            }
        } else {
            pattern.append(']').insert(0, '[');
        }
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

    private int addNumbers(String[] numbersSplitted) {
        int result = 0;
        List<Integer> negativeNumbers = new ArrayList<>();
        for (String number: numbersSplitted) {
            int summand = Integer.parseInt(number);
            if (summand < 0) {
                negativeNumbers.add(summand);
            }
            if (summand < 1001) {
                result += summand;
            }
        }
        if (!negativeNumbers.isEmpty()) {
            throw new IllegalArgumentException("Negatives not allowed: " + negativeNumbers);
        }
        return result;
    }

    public static void main(String[] args) {
        StringCalculator calc = new StringCalculator();
        int res = calc.add("//[***][*][;;]\n101*7\n20***5;;10,3");
        System.out.println(res);
    }
}
