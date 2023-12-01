package org.matrix;

import java.lang.module.FindException;
import java.util.Arrays;

public final class ImmutableMatrix implements Matrix {
    private final int rows, cols;
    private final float[][] content;


    public float[][] getContent() {
        float[][] copyContent = new float[this.rows][];
        for (int i = 0; i < this.getNumberOfRows(); i++) {
            copyContent[i] = Arrays.copyOf(content[i], getNumberOfCols());
        }
        return copyContent;
    }

    public int getNumberOfRows() {
        return rows;
    }

    public int getNumberOfCols() {
        return cols;
    }

    @Override
    public void printMatrix() {
        System.out.println("------------");
        for (int row = 0; row < this.getNumberOfRows(); row++) {
            System.out.println(Arrays.toString(this.getContent()[row]));
        }
        System.out.println("------------");
    }

    public ImmutableMatrix() {
        this.rows = 0;
        this.cols = 0;
        this.content = new float[0][0];
    }

    public ImmutableMatrix(int rows, int cols, float[][] content) {
        this.rows = rows;
        this.cols = cols;
        this.content = new float[rows][];
        for (int i = 0; i < rows; i++) {
            this.content[i] = Arrays.copyOf(content[i], cols);
        }
    }

    public ImmutableMatrix(int rows, int cols, float[] content) {
        this.rows = rows;
        this.cols = cols;
        this.content = new float[rows][cols];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.content[i][j] = content[index];
                index++;
            }
        }
    }

    public ImmutableMatrix(Matrix matrix) {
        this.rows = matrix.getSize()[0];
        this.cols = matrix.getSize()[1];
        this.content = new float[rows][];
        for (int i = 0; i < rows; i++) {
            this.content[i] = Arrays.copyOf(matrix.getRow(i), cols);
        }
    }

    @Override
    public ImmutableMatrix fillMatrix(float[] content) {
        if (this.cols * this.rows == content.length) {
            return new ImmutableMatrix(this.rows, this.cols, content);
        } else {
            throw new IllegalArgumentException("Matrix size doesn't match content array length.");
        }
    }

    @Override
    public float getElement(int row, int col) {
        return this.getContent()[row][col];
    }

    @Override
    public float[] getRow(int row) {
        return this.getContent()[row];
    }

    @Override
    public float[] getCol(int col) {
        int numRows = this.getNumberOfRows();
        float[] column = new float[numRows];

        for (int row = 0; row < numRows; row ++) {
            column[row] = this.getContent()[row][col];
        }
        return column;
    }

    @Override
    public int[] getSize() {
        return new int[] {this.getNumberOfRows(), this.getNumberOfCols()};
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Matrix matrix)) {
            return false;
        }
        if (!Arrays.equals(this.getSize(), matrix.getSize())) {
            return false;
        }
        for (int row = 0; row < this.getNumberOfRows(); row++) {
            if (!Arrays.equals(this.getContent()[row], matrix.getRow(row))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = this.getNumberOfRows();
        result = 31 * result + this.getNumberOfCols();
        result = 31 * result + Arrays.deepHashCode(this.getContent());
        return result;
    }
}
