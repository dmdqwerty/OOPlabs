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

}
