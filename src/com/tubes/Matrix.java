package com.tubes;

public class Matrix {
    private int nrow;
    private int ncol;
    private double[][] Matrix;

    public void makeMatrix(int nrow, int ncol){
        this.nrow = nrow;
        this.ncol = ncol;
        this.Matrix = new double[nrow][ncol];
        System.out.println("Matrix created");
    }
}
