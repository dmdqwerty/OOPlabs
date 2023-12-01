package org.matrix;

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

    @Override
    public ImmutableMatrix addMatrices(Matrix matrix) {
        if (!Arrays.equals(this.getSize(), matrix.getSize())) {
            throw new IllegalArgumentException("Matrices of different size can't be added.");
        }
        int index = 0;
        float[] newContent = new float[matrix.getSize()[0] * matrix.getSize()[1]];
        for (int row = 0; row < this.getNumberOfRows(); row++) {
            for (int col = 0; col < this.getNumberOfCols(); col++) {
                newContent[index] = this.getElement(row, col) + matrix.getElement(row, col);
                index++;
            }
        }
        return new ImmutableMatrix(matrix.getSize()[0], matrix.getSize()[1], newContent);
    }

    @Override
    public ImmutableMatrix multiplyBy(int multiplier) {
        float[] content = new float[this.getNumberOfRows() * this.getNumberOfCols()];
        int index = 0;
        for (int row = 0; row < this.getNumberOfRows(); row++) {
            for (int col = 0; col < this.getNumberOfCols(); col++) {
                content[index] = this.getElement(row, col) * multiplier;
                index++;
            }
        }
        return new ImmutableMatrix(this.getSize()[0], this.getSize()[1], content);
    }

    @Override
    public ImmutableMatrix multiplyBy(Matrix matrix) {
        if (this.getNumberOfCols() != matrix.getSize()[0]) {
            throw new IllegalArgumentException("Matrices size doesn't match.");
        }
        MatrixMutable temp = new MatrixMutable(this.getNumberOfRows(), matrix.getSize()[1]);

        for (int row = 0; row < this.getNumberOfRows(); row++) {
            for (int col = 0; col < matrix.getSize()[1]; col++) {
                for (int k = 0; k < this.getNumberOfCols(); k++) {
                    temp.getContent()[row][col] += this.getElement(row, k) * matrix.getElement(k, col);
                }
            }
        }
        return new ImmutableMatrix(temp);
    }
}
