package org.matrix;

import java.util.Arrays;
import java.util.Random;

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

    public ImmutableMatrix(float[] vector) { // Will return square matrix with given vector on the diagonal
        int dimension = vector.length;
        this.rows = dimension;
        this.cols = dimension;
        this.content = new float[rows][cols];
        for (int i = 0; i < dimension; i++) {
            this.content[i][i] = vector[i];
        }
    }

    public ImmutableMatrix(int dimension) { // Will return unit matrix of given dimension
        this.rows = dimension;
        this.cols = dimension;
        this.content = new float[rows][cols];
        for (int i = 0; i < dimension; i++) {
            this.content[i][i] = 1;
        }
    }

    public ImmutableMatrix(int size, boolean rowOrCol) {// Will return either matrix row or matrix-col (bool rowOrCol)
        Random random = new Random();                  // Of a given size, filled with random values.
        if (rowOrCol) {
            this.rows = 1;
            this.cols = size;
            this.content = new float[1][cols];
            for (int i = 0; i < size; i++) {
                this.content[0][i] = random.nextFloat() * 10;
            }
        } else {
            this.rows = size;
            this.cols = 1;
            this.content = new float[rows][1];
            for (int i = 0; i < size; i++) {
                this.content[i][0] = random.nextFloat() * 10;
            }
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

    @Override
    public ImmutableMatrix transpose() {
        MatrixMutable temp = new MatrixMutable(this.getNumberOfCols(), this.getNumberOfRows());
        for (int row = 0; row < this.getNumberOfRows(); row++) {
            for (int col = 0; col < this.getNumberOfCols(); col++) {
                temp.getContent()[col][row] = this.getElement(row, col);
            }
        }
        return new ImmutableMatrix(temp);
    }

    @Override
    public float calculateDeterminant() {
        if (this.getNumberOfRows() != this.getNumberOfCols()) {
            throw new IllegalArgumentException("Determinant exists for square matrices only.");
        }
        int order = this.getNumberOfRows();

        if (order == 1) {
            return this.getElement(0, 0);
        }

        if (order == 2) {
            return this.getElement(0, 0) * this.getElement(1, 1) - this.getElement(0, 1) * this.getElement(1, 0);
        }

        float determinant = 0;
        for (int i = 0; i < order; i++) {
            determinant += Math.pow(-1, i) * this.getElement(0, i) * getSubMatrix(0, i).calculateDeterminant();
        }

        return determinant;
    }

    private ImmutableMatrix getSubMatrix(int rowRemove, int colRemove) { // Removes specified row and column
        if (this.getNumberOfRows() != this.getNumberOfCols()) {
            throw new IllegalArgumentException("Matrix should be square for correct implementation.");
        }
        int order = this.getNumberOfRows();

        float[] content = new float[(order-1)*(order-1)];
        int index = 0;
        for (int i = 0; i < order; i++) {
            for (int j = 0; j < order; j++) {
                if (i != rowRemove && j != colRemove) {
                    content[index] = this.getElement(i, j);
                    index++;
                }
            }
        }
        return new ImmutableMatrix(order-1, order-1, content);
    }

    private ImmutableMatrix calculateAddMatrix() {
        if (this.getNumberOfRows() != this.getNumberOfCols()) {
            throw new IllegalArgumentException("Matrix should be square for correct implementation.");
        }
        int order = this.getNumberOfRows();

        float[] content = new float[order*order];
        int index = 0;
        for (int i = 0; i < order; i++) {
            for (int j = 0; j < order; j++) {
                int sign = (i + j) % 2 == 0 ? 1 : -1;
                float el = sign * this.getSubMatrix(i, j).calculateDeterminant();
                content[index] = el;
                index++;
            }
        }
        return new ImmutableMatrix(order, order, content).transpose();
    }

    @Override
    public ImmutableMatrix calculateInverseMatrix() {
        float det = this.calculateDeterminant();
        if (det == 0) {
            throw new RuntimeException("Determinant = 0. Inverse matrix doesn't exist.");
        }
        int order = this.getNumberOfRows();

        ImmutableMatrix addMatrix = this.calculateAddMatrix();

        float[] content = new float[order*order];
        int index = 0;
        for (int i = 0; i < order; i++) {
            for (int j = 0; j < order; j++) {
                content[index] = addMatrix.getElement(i, j) / det;
                index++;
            }
        }
        return new ImmutableMatrix(order, order, content);
    }
}
