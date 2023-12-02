package org.matrix;

public interface Matrix {
    void printMatrix();
    Matrix fillMatrix(float[] content);
    float getElement(int row, int col);
    float[] getRow(int row);
    float[] getCol(int col);
    int[] getSize();
    Matrix addMatrices(Matrix matrix);
    Matrix multiplyBy(int multiplier);
    Matrix multiplyBy(Matrix matrix);
    Matrix transpose();
    float calculateDeterminant();
    Matrix calculateInverseMatrix();
}
