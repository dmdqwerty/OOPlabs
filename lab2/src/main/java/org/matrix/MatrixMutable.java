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
        System.out.println("------------");
        for (int row = 0; row < this.getNumberOfRows(); row++) {
            System.out.println(Arrays.toString(this.getContent()[row]));
        }
        System.out.println("------------");
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

    public MatrixMutable(Matrix matrix) {
        this.rows = matrix.getSize()[0];
        this.cols = matrix.getSize()[1];
        this.content = new float[rows][];
        for (int i = 0; i < this.getNumberOfRows(); i++) {
            this.content[i] = matrix.getRow(i);
        }
    }

    public MatrixMutable(float[] vector) { // Will return square matrix with given vector on the diagonal
        int dimension = vector.length;
        this.rows = dimension;
        this.cols = dimension;
        this.content = new float[rows][cols];
        for (int i = 0; i < dimension; i++) {
            this.content[i][i] = vector[i];
        }
    }

    public MatrixMutable(int dimension) { // Will return unit matrix of given dimension
        this.rows = dimension;
        this.cols = dimension;
        this.content = new float[rows][cols];
        for (int i = 0; i < dimension; i++) {
            this.content[i][i] = 1;
        }
    }

    @Override
    public MatrixMutable fillMatrix(float[] content) {
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
        return this;
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
    public MatrixMutable addMatrices(Matrix matrix) {
        if (!Arrays.equals(this.getSize(), matrix.getSize())) {
            throw new IllegalArgumentException("Matrices of different size can't be added.");
        }
        for (int row = 0; row < this.getNumberOfRows(); row++) {
            for (int col = 0; col < this.getNumberOfCols(); col++) {
                this.content[row][col] += matrix.getElement(row, col);
            }
        }
        return this;
    }

    @Override
    public MatrixMutable multiplyBy(int multiplier) {
        for (int row = 0; row < this.getNumberOfRows(); row++) {
            for (int col = 0; col < this.getNumberOfCols(); col++) {
                this.content[row][col] *= multiplier;
            }
        }
        return this;
    }

    @Override
    public MatrixMutable multiplyBy(Matrix matrix) {
        if (this.getNumberOfCols() != matrix.getSize()[0]) {
            throw new IllegalArgumentException("Matrices size doesn't match.");
        }
        MatrixMutable temp = new MatrixMutable(this.getNumberOfRows(), matrix.getSize()[1]);

        for (int row = 0; row < this.getNumberOfRows(); row++) {
            for (int col = 0; col < matrix.getSize()[1]; col++) {
                for (int k = 0; k < this.getNumberOfCols(); k++) {
                    temp.content[row][col] += this.getElement(row, k) * matrix.getElement(k, col);
                }
            }
        }
        this.cols = temp.cols;
        this.content = temp.content;
        return this;
    }

    @Override
    public MatrixMutable transpose() {
        MatrixMutable temp = new MatrixMutable(this.getNumberOfCols(), this.getNumberOfRows());
        for (int row = 0; row < this.getNumberOfRows(); row++) {
            for (int col = 0; col < this.getNumberOfCols(); col++) {
                temp.getContent()[col][row] = this.getElement(row, col);
            }
        }
        this.rows = temp.rows;
        this.cols = temp.cols;
        this.content = temp.content;
        return this;
    }
}
