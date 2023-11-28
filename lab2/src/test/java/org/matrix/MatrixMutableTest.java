package org.matrix;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MatrixMutableTest {
    MatrixMutable testMatrix;

    @BeforeEach
    void setUp() {
        testMatrix = new MatrixMutable(3, 4);
        float[] content = new float[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        testMatrix.fillMatrix(content);
    }

    @Test
    void fillMatrixTest() {
        float[][] expectedMatrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        for (int row = 0; row < testMatrix.getNumberOfRows(); row++) {
            for (int col = 0; col < testMatrix.getNumberOfCols(); col++) {
                Assertions.assertEquals(expectedMatrix[row][col], testMatrix.getContent()[row][col]);
            }
        }
    }

    @Test
    void overrideValuesTest() {
        float[] someAnotherContent = new float[] {1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23};
        testMatrix.fillMatrix(someAnotherContent);
        float[][] expectedMatrix = {{1, 3, 5, 7}, {9, 11, 13, 15}, {17, 19, 21, 23}};
        for (int row = 0; row < testMatrix.getNumberOfRows(); row++) {
            for (int col = 0; col < testMatrix.getNumberOfCols(); col++) {
                Assertions.assertEquals(expectedMatrix[row][col], testMatrix.getContent()[row][col]);
            }
        }
    }

    @Test
    void fillMatrixWithWrongContentTest() {
        float[] notEnoughContent = new float[] {1, 2, 3, 4, 5, 6};
        Assertions.assertThrows(IllegalArgumentException.class, () -> testMatrix.fillMatrix(notEnoughContent));
    }

    @Test
    void getElementTest() {
        Assertions.assertEquals(7, testMatrix.getElement(2, 3));
        Assertions.assertEquals(2, testMatrix.getElement(1, 2));
    }

    @Test
    void getRowTest() {
        float[] rowExpected = new float[] {5, 6, 7, 8};
        for (int i = 0; i < testMatrix.getNumberOfCols(); i++) {
            Assertions.assertEquals(rowExpected[i], testMatrix.getRow(2)[i]);
        }
    }

    @Test
    void getColTest() {
        float[] colExpected = new float[] {3, 7, 11};
        for (int i = 0; i < testMatrix.getNumberOfRows(); i++) {
            Assertions.assertEquals(colExpected[i], testMatrix.getCol(3)[i]);
        }
    }
}
