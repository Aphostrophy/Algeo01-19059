package com.tubes;

import java.util.Scanner;

public class SquareMatrix {
    private int dimension;
    public double[][] Matrix;

    public void makeMatrix(int dimension){
        this.dimension = dimension;
        this.Matrix = new double[this.dimension][this.dimension];
        System.out.println("Matrix created");
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public double getElmt(int row, int col){
        return Matrix[row][col];
    }

    public void setElmt(int row, int col, double Elmt){
        this.Matrix[row][col] = Elmt;
    }
}
