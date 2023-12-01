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
    // Those 3 getters are 0-indexed
    @Test
    void getElementTest() {
        Assertions.assertEquals(7, testMatrix.getElement(1, 2));
        Assertions.assertEquals(2, testMatrix.getElement(0, 1));
    }

    @Test
    void getRowTest() {
        float[] rowExpected = new float[] {5, 6, 7, 8};
        for (int i = 0; i < testMatrix.getNumberOfCols(); i++) {
            Assertions.assertEquals(rowExpected[i], testMatrix.getRow(1)[i]);
        }
    }

    @Test
    void getColTest() {
        float[] colExpected = new float[] {3, 7, 11};
        for (int i = 0; i < testMatrix.getNumberOfRows(); i++) {
            Assertions.assertEquals(colExpected[i], testMatrix.getCol(2)[i]);
        }
    }

    @Test
    void getSizeTest() {
        Assertions.assertArrayEquals(new int[]{3, 4}, testMatrix.getSize());
    }

    @Test
    void equalsTest() {
        Matrix testMatrix2 = new MatrixMutable(testMatrix);
        Matrix testMatrix3 = new MatrixMutable(3, 4);
        testMatrix3.fillMatrix(new float[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});
        Assertions.assertEquals(testMatrix, testMatrix); // reflexivity
        Assertions.assertEquals(testMatrix.equals(testMatrix2), testMatrix2.equals(testMatrix)); // symmetry
        // transitivity
        Assertions.assertEquals(testMatrix, testMatrix3);
        Assertions.assertEquals(testMatrix2, testMatrix3);
    }

    @Test
    void hashCodeTest() {
        Matrix testMatrix2 = new MatrixMutable(testMatrix);
        Matrix testMatrix3 = new MatrixMutable(3, 4);
        testMatrix3.fillMatrix(new float[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});
        Assertions.assertEquals(testMatrix.hashCode(), testMatrix2.hashCode());
        Assertions.assertEquals(testMatrix2.hashCode(), testMatrix3.hashCode());
    }

    @Test
    void addMatricesTest() {
        float[] content = new float[] {1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23};
        Matrix matrixToBeAdded = new ImmutableMatrix(3, 4, content);
        float[] expectedContent = new float[] {2, 5, 8, 11, 14, 17, 20, 23, 26, 29, 32, 35};
        Matrix expectedMatrix = new MatrixMutable(3, 4).fillMatrix(expectedContent);
        testMatrix.addMatrices(matrixToBeAdded);
        Assertions.assertEquals(expectedMatrix, testMatrix);
    }

    @Test
    void addImproperMatrixTest() {
        float[] content = new float[] {1, 3, 5, 7, 9, 11, 13, 15, 17};
        Matrix matrixToBeAdded = new MatrixMutable(3, 3).fillMatrix(content);
        Assertions.assertThrows(IllegalArgumentException.class, () -> testMatrix.addMatrices(matrixToBeAdded));
    }

    @Test
    void scaleMultiplyTest() {
        float[] expectedContent = new float[] {2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24};
        Matrix expectedMatrix = new MatrixMutable(3, 4).fillMatrix(expectedContent);
        Assertions.assertEquals(expectedMatrix, testMatrix.multiplyBy(2));
    }

    @Test
    void matricesMultiplicationTest() {
        float[] expectedContent = new float[] {90, 110, 202, 254, 314, 398};
        Matrix expectedMatrix = new MatrixMutable(3, 2).fillMatrix(expectedContent);
        float[] someContent = new float[] {1, 3, 5, 7, 9, 11, 13, 15};
        Matrix multiplierMatrix = new MatrixMutable(4 ,2).fillMatrix(someContent);
        Assertions.assertEquals(expectedMatrix, testMatrix.multiplyBy(multiplierMatrix));
    }
}
