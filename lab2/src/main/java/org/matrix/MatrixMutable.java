package org.matrix;


import java.util.Arrays;

public class MatrixMutable implements Matrix {
    private int rows, cols;
    private float[][] content;

    public float[][] getContent() {
        return content;
    }

    public int getNumberOfRows() {
        return rows;
    }

    public int getNumberOfCols() {
        return cols;
    }

    @Override
    public void printMatrix() {
        for (int row = 0; row < this.getNumberOfRows(); row++) {
            System.out.println(Arrays.toString(this.getContent()[row]));
        }
    }

    public MatrixMutable() {
        this.rows = 0;
        this.cols = 0;
        this.content = new float[0][0];
    }

    public MatrixMutable(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.content = new float[rows][cols];
    }

    public MatrixMutable(MatrixMutable matrix) {
        this.rows = matrix.rows;
        this.cols = matrix.rows;
        this.content = matrix.content;
    }

    @Override
    public void fillMatrix(float[] content) {
        if (this.cols * this.rows == content.length) {
            int index = 0;
            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < this.cols; j++) {
                    this.content[i][j] = content[index];
                    index++;
                }
            }
        } else {
            throw new IllegalArgumentException("Matrix size doesn't match content array length.");
        }
    }

    // Those 3 getters are "1 indexed" for better readability when testing
    @Override
    public float getElement(int row, int col) {
        return this.getContent()[row-1][col-1];
    }

    @Override
    public float[] getRow(int row) {
        return this.getContent()[row-1];
    }

    @Override
    public float[] getCol(int col) {
        int numRows = this.getNumberOfRows();
        float[] column = new float[numRows];

        for (int row = 0; row < numRows; row ++) {
            column[row] = this.getContent()[row][col-1];
        }

        return column;
    }
}
