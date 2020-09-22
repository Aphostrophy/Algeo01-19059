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
