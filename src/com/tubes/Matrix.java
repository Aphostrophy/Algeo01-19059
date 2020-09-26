package com.tubes;

public class Matrix {
    private int n_row;
    private int n_col;
    private double[][] Matrix;

    public void makeMatrix(int n_row, int n_col){
        this.n_row = n_row;
        this.n_col = n_col;
        this.Matrix = new double[n_row][n_col];
        System.out.println("Matrix created");
    }

    public double getElmt(int row, int col){ return Matrix[row][col]; }

    public void setElmt(int row, int col, double Elmt){
        this.Matrix[row][col] = Elmt;
    }

    public int getNrow() {
        return n_row;
    }

    public void setNrow(int nrow) {
        this.n_row = nrow;
    }

    public int getNcol() {
        return n_col;
    }

    public void setNcol(int ncol) {
        this.n_col = ncol;
    }
}
