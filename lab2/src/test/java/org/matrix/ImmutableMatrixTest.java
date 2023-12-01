package org.matrix;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ImmutableMatrixTest {
    ImmutableMatrix immMat;
    MatrixMutable mutMat;

    @BeforeEach
    void setUp() {
        float[] content = new float[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        mutMat = new MatrixMutable(3, 4).fillMatrix(content);
        immMat = new ImmutableMatrix(3, 4, content);
    }

    @Test
    void fillMatrixTest() {
        float[][] expectedMatrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        for (int row = 0; row < immMat.getNumberOfRows(); row++) {
            for (int col = 0; col < immMat.getNumberOfCols(); col++) {
                Assertions.assertEquals(expectedMatrix[row][col], immMat.getContent()[row][col]);
            }
        }
    }

    @Test
    void overrideValuesTest() {
        float[] someAnotherContent = new float[] {1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23};
        ImmutableMatrix newImmMat = immMat.fillMatrix(someAnotherContent);
        float[][] expectedMatrix = {{1, 3, 5, 7}, {9, 11, 13, 15}, {17, 19, 21, 23}};
        float[][] expectedMatrixOrigin = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        Assertions.assertEquals(immMat.getNumberOfRows(), newImmMat.getNumberOfRows());
        Assertions.assertEquals(immMat.getNumberOfCols(), newImmMat.getNumberOfCols());

        for (int row = 0; row < newImmMat.getNumberOfRows(); row++) {
            for (int col = 0; col < newImmMat.getNumberOfCols(); col++) {
                Assertions.assertEquals(expectedMatrix[row][col], newImmMat.getContent()[row][col]);
            }
        }
        for (int row = 0; row < immMat.getNumberOfRows(); row++) {
            for (int col = 0; col < immMat.getNumberOfCols(); col++) {
                Assertions.assertEquals(expectedMatrixOrigin[row][col], immMat.getContent()[row][col]);
            }
        }

        immMat.fillMatrix(new float[] {1, 2, 3, 4, 5, 6, 77777, 8, 9, 10, 11, 12});
        for (int row = 0; row < immMat.getNumberOfRows(); row++) {
            for (int col = 0; col < immMat.getNumberOfCols(); col++) {
                Assertions.assertEquals(expectedMatrixOrigin[row][col], immMat.getContent()[row][col]);
            }
        }
    }

    @Test
    void fillMatrixWithWrongContentTest() {
        float[] notEnoughContent = new float[] {1, 2, 3, 4, 5, 6};
        Assertions.assertThrows(IllegalArgumentException.class, () -> immMat.fillMatrix(notEnoughContent));
    }

    @Test
    void getElementTest() {
        Assertions.assertEquals(7, immMat.getElement(1, 2));
        Assertions.assertEquals(2, immMat.getElement(0, 1));
    }

    @Test
    void getRowTest() {
        float[] rowExpected = new float[] {5, 6, 7, 8};
        for (int i = 0; i < immMat.getNumberOfCols(); i++) {
            Assertions.assertEquals(rowExpected[i], immMat.getRow(1)[i]);
        }
    }

    @Test
    void getColTest() {
        float[] colExpected = new float[] {3, 7, 11};
        for (int i = 0; i < immMat.getNumberOfRows(); i++) {
            Assertions.assertEquals(colExpected[i], immMat.getCol(2)[i]);
        }
    }

    @Test
    void getSizeTest() {
        Assertions.assertArrayEquals(new int[]{3, 4}, immMat.getSize());
    }

    @Test
    void hashCodeTest() {
        Assertions.assertEquals(immMat.hashCode(), mutMat.hashCode());
    }

    @Test
    void equalsTest() {
        Assertions.assertEquals(immMat, mutMat); // Those 2 have same size, content and Interface
        Assertions.assertEquals(mutMat, immMat); // Symmetry check
        immMat.fillMatrix(new float[] {1, 2, 3, 4, 5, 6, 77777, 8, 9, 10, 11, 12});
        Assertions.assertEquals(immMat, mutMat); // Immutable matrix is immutable
        mutMat.fillMatrix(new float[] {1, 2, 3, 4, 5, 6, 77777, 8, 9, 10, 11, 12});
        Assertions.assertNotEquals(mutMat, immMat); // mutMat's content was overridden
    }
}
