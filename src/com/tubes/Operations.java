package com.tubes;

import java.util.Scanner;

public class Operations {

    static void swapRow(int a,int b,SquareMatrix matrix){
        double temp;
        for(int i=0;i< matrix.getDimension(); i++ ){
            temp = matrix.getElmt(i,a);
            matrix.setElmt(i,a, matrix.getElmt(i,b));
            matrix.setElmt(i,b, temp);
        }
    }

    static void swapRow(int a, int b, Matrix matrix){
        double temp;
        for(int i=0;i< matrix.getNrow(); i++){
            temp = matrix.getElmt(i,a);
            matrix.setElmt(i,a, matrix.getElmt(i,b));
        }
    }

    static void printMatrix(Matrix matrix){
        int i,j;
        int n_row = matrix.getNrow();
        int n_col = matrix.getNcol();
        for(i=0;i<n_row;i++){
            for(j=0;j<n_col;j++){
                if(j==n_col-1){
                    System.out.println(matrix.getElmt(i,j) + "\n");
                } else {
                    System.out.println(matrix.getElmt(i,j) + " ");
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
                    System.out.print(matrix.getElmt(i,j) + "\n");
                } else {
                    System.out.print(matrix.getElmt(i,j) + " ");
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
}
