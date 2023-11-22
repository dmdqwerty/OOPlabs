package org.calc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringCalculatorTest {
    private StringCalculator calc;

    @BeforeEach
    public void setUp() {
        calc = new StringCalculator();
    }

    @Test
    void emptyStrArgCase() {
        Assertions.assertEquals(0, calc.add(""));
    }

    @Test
    void singleNumberCase() {
        Assertions.assertEquals(3, calc.add("3"));
    }

    @Test
    void twoNumbersCase() {
        Assertions.assertEquals(7, calc.add("2,5"));
    }

    @Test
    void anyNumberOfNumbersCase() {
        Assertions.assertEquals(320, calc.add("100,10,10,150,50"));
    }

    @Test
    void newLineAsDelimiterCase() {
        Assertions.assertEquals(100, calc.add("30,50\n20"));
    }

    @Test
    void twoDelimitersInARowCase() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> calc.add("3,5,\n6"), "Two delimiters in a row are not allowed");
    }

    @Test
    void customDelimiterCase() {
        Assertions.assertEquals(250, calc.add("//[;]\n5,100;100;45"));
        Assertions.assertEquals(250, calc.add("//;\n5,100;100;45"));
    }

    @Test
    void negativeNumbersCase() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> calc.add("-2,4,-3"), "Negatives not allowed: [-2, -3]");
    }
}
