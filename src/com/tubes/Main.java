package com.tubes;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int nrow,ncol,dimension;

        boolean exit = false;
	    while(!exit){
            System.out.println("MENU\n" +
                    "1. Sistem Persamaan Linear\n" +
                    "2. Determinan\n" +
                    "3. Matriks Balikan\n" +
                    "4. Interpolasi Polinom\n" +
                    "5. Regresi Linear Berganda\n" +
                    "6. Exit\n");
            Scanner input = new Scanner(System.in);
            int opt = input.nextInt();

            switch (opt){
                case 1:
                    System.out.println("Sistem persamaan linear");
                    nrow = input.nextInt();
                    ncol = input.nextInt();
                    System.out.println("1. Manual Input" +
                            "2. Upload file");
                    Matrix matrixSPL = new Matrix();
                    int ops = input.nextInt();
                    switch(ops){
                        case 1:
                            matrixSPL.makeMatrix(nrow,ncol);
                            Operations.fillMatrix(matrixSPL);
                        default:
                            System.out.println("Fitur belum tersedia");
                    }
                    System.out.println("1. Metode Eliminasi Gauss\n" +
                            "2. Metode eliminasi Gauss-Jordan\n" +
                            "3. Metode matriks balikan\n" +
                            "4. Kaidah crammer ");
                    int op = input.nextInt();
                    switch(op){
                        case 1:
                            System.out.println("Metode Eliminasi Gauss");
                        case 2:
                            System.out.println("Metode eliminasi Gauss-Jordan");
                        case 3:
                            System.out.println("Metode matriks balikan");
                        case 4:
                            System.out.println("Kaidah crammer");
                        default:
                            System.out.println("Input salah");
                    }
                    break;

                case 2:
                    System.out.println("Determinan");
                    System.out.print("Enter the matrix dimension (nxn) : ");
                    dimension = input.nextInt();
                    SquareMatrix matrix = new SquareMatrix();
                    matrix.makeMatrix(dimension);
                    Operations.fillMatrix(matrix);
                    Operations.printMatrix(matrix);
                    double det = Determinant.RowReductionDeterminant(matrix);
                    System.out.println("The determinant value is " + det);
                    det = Determinant.CofactorExpansionDeterminant(matrix);
                    System.out.println("The determinant value is " + det);
                    break;

                case 3:
                    System.out.println("Matriks Balikan");
                    break;

                case 4:
                    System.out.println("Interpolasi polinom");
                    break;

                case 5:
                    System.out.println("Regresi Linear Berganda");

                case 6:
                    System.out.println("Exit");
                    break;

                default:
                    exit = true;
            }

        }
    }
}
