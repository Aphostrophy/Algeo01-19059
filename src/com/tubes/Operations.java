package com.tubes;

import java.util.Scanner;

public class Operations {

    static void swapRow(int a,int b,SquareMatrix matrix){
        double temp;
        for(int i=0;i< matrix.getDimension(); i++ ){
            temp = matrix.getElmt(a,i);
            matrix.setElmt(a,i, matrix.getElmt(b,i));
            matrix.setElmt(b,i, temp);
        }
    }

    static void swapRow(int a, int b, Matrix matrix){
        double temp;
        for(int i=0;i< matrix.getNcol(); i++){
            temp = matrix.getElmt(a,i);
            matrix.setElmt(a,i, matrix.getElmt(b,i));
            matrix.setElmt(b,i, temp);
        }
    }

    static void printMatrix(Matrix matrix){
        int i,j;
        int n_row = matrix.getNrow();
        int n_col = matrix.getNcol();
        for(i=0;i<n_row;i++){
            for(j=0;j<n_col;j++){
                if(j==n_col-1){
                    System.out.print(matrix.getElmt(i,j) + "\n");
                } else {
                    System.out.print(matrix.getElmt(i,j) + " ");
                }
            }
        }
    }

    static void printMatrix(SquareMatrix matrix){
        int i,j;
        int n_row = matrix.getDimension();
        int n_col = matrix.getDimension();
        for(i=0;i<n_row;i++){
            for(j=0;j<n_col;j++){
                if(j==n_col-1){
                    System.out.print(matrix.getElmt(i,j)+"\n");
                } else{
                    System.out.print(matrix.getElmt(i,j)+" ");
                }
            }
        }
    }

    static void fillMatrix(Matrix matrix){
        Scanner s = new Scanner(System.in);
        int i,j;
        int n_row = matrix.getNrow();
        int n_col = matrix.getNcol();

        for(i=0;i<n_row;i++){
            for(j=0;j<n_col;j++){
                matrix.setElmt(i,j,s.nextDouble());
            }
        }
    }

    static void fillMatrix(SquareMatrix matrix){
        Scanner s = new Scanner(System.in);
        int i,j;
        int n_row = matrix.getDimension();
        int n_col = matrix.getDimension();

        for(i=0;i<n_row;i++){
            for(j=0;j<n_col;j++){
                matrix.setElmt(i,j, s.nextDouble());
            }
        }
    }

    static SquareMatrix cofactor(SquareMatrix matrix,int row,int column){
        SquareMatrix temp = new SquareMatrix();
        temp.makeMatrix(matrix.getDimension()-1);
        int temp_row = 0;
        int temp_col = 0;
        for(int i=0;i< matrix.getDimension();i++){
            for(int j=0;j< matrix.getDimension();j++){
                if(i!=row && j!=column){
                    temp.setElmt(temp_row,temp_col, matrix.getElmt(i,j));
                    temp_col++;
                }
                else if(i==row){
                    temp_row -=1;
                    break;
                }
            }
            temp_row++;
            temp_col = 0;
        }
        return temp;
    }
    
    static Matrix transpose(Matrix matrix) {
        Matrix temp = new Matrix();
        temp.makeMatrix(matrix.getNcol(), matrix.getNrow());
        for(int i = 0; i < temp.getNrow(); i++) {
            for(int j = 0; j < temp.getNcol(); j++){
                temp.setElmt(i, j, matrix.getElmt(j, i));
            }
        }
        return temp;
    }

    static SquareMatrix transpose(SquareMatrix matrix) {
        SquareMatrix temp = new SquareMatrix();
        temp.makeMatrix(matrix.getDimension());
        for(int i = 0; i < temp.getDimension(); i++) {
            for(int j = 0; j < temp.getDimension(); j++){
                temp.setElmt(i, j, matrix.getElmt(j, i));
            }
        }
        return temp;
    }

    static void scalarMultiplication(Matrix matrix, double x){
        for(int i = 0; i < matrix.getNrow(); i++) {
            for(int j = 0; j < matrix.getNcol(); j++) {
                matrix.setElmt(i, j, matrix.getElmt(i, j) * x);
            }
        }
    }

    static void scalarMultiplication(SquareMatrix matrix, double x){
        for(int i = 0; i < matrix.getDimension(); i++) {
            for(int j = 0; j < matrix.getDimension(); j++) {
                matrix.setElmt(i, j, matrix.getElmt(i, j) * x);
            }
        }
    }

    static SquareMatrix getAdjoint(SquareMatrix matrix) {
        SquareMatrix cofactorMatrix = new SquareMatrix();
        SquareMatrix adjointMatrix = new SquareMatrix();
        cofactorMatrix.makeMatrix(matrix.getDimension());
        adjointMatrix.makeMatrix(matrix.getDimension());
        for(int i = 0; i < matrix.getDimension(); i++) {
            for(int j = 0; j < matrix.getDimension(); j++) {
                cofactorMatrix.setElmt(i, j, Math.pow(-1, i+j)*Determinant.CofactorExpansionDeterminant(cofactor(matrix, i, j)));
            }
        }
        adjointMatrix = transpose(cofactorMatrix);
        return adjointMatrix;
    }

    static double roundAvoid(double value,int digits){
        double scale = Math.pow(10, digits);
        return Math.round(value * scale)/scale;
    }

    static void  copyMatrix(Matrix matrixIn, Matrix matrixOut) {
        for (int i = 0; i < matrixIn.getNrow(); i++) {
            for (int j = 0; j < matrixIn.getNcol(); j++) {
                matrixOut.setElmt(i, j, matrixIn.getElmt(i, j));
            }
        }
    }

    static void  copyMatrix(SquareMatrix matrixIn, SquareMatrix matrixOut) {
        for (int i = 0; i < matrixIn.getDimension(); i++) {
            for (int j = 0; j < matrixIn.getDimension(); j++) {
                matrixOut.setElmt(i, j, matrixIn.getElmt(i, j));
            }
        }
    }

    static void  copyMatrix(SquareMatrix matrixIn, Matrix matrixOut) {
        for (int i = 0; i < matrixIn.getDimension(); i++) {
            for (int j = 0; j < matrixIn.getDimension(); j++) {
                matrixOut.setElmt(i, j, matrixIn.getElmt(i, j));
            }
        }
    }
}
