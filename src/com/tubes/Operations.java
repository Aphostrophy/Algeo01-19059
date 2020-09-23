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

    static void swapRow(int a, int b,int n_col, Matrix matrix){
        double temp;
        for(int i=0;i< matrix.getNrow(); i++){
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
                System.out.print(matrix.getElmt(i,j));
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

    static Matrix cofactor(Matrix matrix,int row,int column){
        Matrix malesmikir = new Matrix();
        return malesmikir;
    }

    static Matrix gauss(Matrix matrix){
        int n_row = matrix.getNrow();
        int n_col = matrix.getNcol();
        int row_mark = 0;
        for(int j=0;j<n_col;j++){
            for(int r=0;r<n_row;r++){
                boolean colZero = false;
                int a = row_mark;
                int b = a+1;
                while(matrix.getElmt(row_mark,j)==0 && !colZero){
                    if(b>=n_row){
                        colZero=true;
                    } else{
                        swapRow(a,b,n_col,matrix);
                    }
                }
                if(colZero){
                    break;
                }
                double multiplier = matrix.getElmt(r,j)/matrix.getElmt(row_mark,j);
                for(int e=0;e<n_col;e++){
                    matrix.setElmt(r,e, (matrix.getElmt(r,e) - multiplier*matrix.getElmt(row_mark,e)));
                }
                row_mark += 1;
            }
        }
        return matrix;
    }


    
}
