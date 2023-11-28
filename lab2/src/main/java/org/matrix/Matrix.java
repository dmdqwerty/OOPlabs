package org.matrix;

public interface Matrix {
    void printMatrix();
    void fillMatrix(float[] content);
    float getElement(int row, int col);
    float[] getRow(int row);
    float[] getCol(int col);
}
