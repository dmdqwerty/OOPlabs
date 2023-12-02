package org.matrix;

public class Main {
    public static void main(String[] args) {
        float[] content = new float[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        Matrix mutMat = new MatrixMutable(3, 4).fillMatrix(content);
        Matrix immMat = new ImmutableMatrix(3, 4, content);

        immMat.addMatrices(mutMat).printMatrix();

        mutMat.addMatrices(immMat).printMatrix();
    }
}
