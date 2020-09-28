package com.tubes;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
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
                    System.out.println("1. Manual Input\n" +
                            "2. Upload file");
                    Matrix matrixSPL = new Matrix();
                    SPLSolver SPL = new SPLSolver();
                    int ops = input.nextInt();
                    if(ops==1) {
                        System.out.print("Baris: ");
                        nrow = input.nextInt();
                        System.out.print("Kolom: ");
                        ncol = input.nextInt();
                        matrixSPL.makeMatrix(nrow, ncol);
                        Operations.fillMatrix(matrixSPL);
                    } else if (ops == 2) {
                        // input nama file
                        System.out.print("Masukkan nama file :\n");
                        Scanner inputFileName = new Scanner(System.in);
                        String namaFile = inputFileName.nextLine();
                        IOFile.fileToMatriks(matrixSPL, namaFile);
                    }
                    Operations.printMatrix(matrixSPL);

                    System.out.println("1. Metode Eliminasi Gauss\n" +
                            "2. Metode eliminasi Gauss-Jordan\n" +
                            "3. Metode matriks balikan\n" +
                            "4. Kaidah crammer ");
                    int op = input.nextInt();
                    if(op==1) {
                        System.out.println("Metode Eliminasi Gauss");
                        SPL.gaussDriver(matrixSPL);
                    } else if(op==2) {
                        System.out.println("Metode eliminasi Gauss-Jordan");
                        SPLSolver.gaussJordanSolver(matrixSPL);
                    } else if(op==3) {
                        System.out.println("Metode matriks balikan");
                        SPLSolver.inversSPL(matrixSPL);
                    } else if(op==4) {
                        System.out.println("Kaidah crammer");
                        SPLSolver.cramerSPL(matrixSPL);
                    } else{
                        System.out.println("Input salah");
                    }
                    break;

                case 2:
                    System.out.println("Determinan");
                    SquareMatrix matrix = new SquareMatrix();
                    System.out.println("1. Manual Input\n" +
                            "2. Upload file");
                    ops = input.nextInt();
                    if(ops==1) {
                        System.out.print("Enter the matrix dimension (nxn) : ");
                        dimension = input.nextInt();
                        matrix.makeMatrix(dimension);
                        Operations.fillMatrix(matrix);
                    } else if (ops == 2) {
                        // input nama file
                        System.out.print("Masukkan nama file :\n");
                        Scanner inputFileName = new Scanner(System.in);
                        String namaFile = inputFileName.nextLine();
                        IOFile.fileToMatriks(matrix, namaFile);
                    }
                    Operations.printMatrix(matrix);

                    double det = Determinant.RowReductionDeterminant(matrix);
                    System.out.println("The determinant value is " + det);
                    det = Determinant.CofactorExpansionDeterminant(matrix);
                    System.out.println("The determinant value is " + det);
                    break;

                case 3:
                    System.out.println("Matriks Balikan");
                    SquareMatrix matrixInverse = new SquareMatrix();
                    System.out.println("1. Manual Input\n" +
                            "2. Upload file");
                    ops = input.nextInt();
                    if (ops == 1) {
                        System.out.print("Enter the matrix dimension (nxn) : ");
                        dimension = input.nextInt();
                        matrixInverse.makeMatrix(dimension);
                        Operations.fillMatrix(matrixInverse);
                    } else if (ops == 2) {
                        // input nama file
                        System.out.print("Masukkan nama file :\n");
                        Scanner inputFileName = new Scanner(System.in);
                        String namaFile = inputFileName.nextLine();
                        IOFile.fileToMatriks(matrixInverse, namaFile);
                    }

                    Operations.printMatrix(matrixInverse);
                    System.out.println("Invers Matriks: ");
                    Inverse.AdjointInverse(matrixInverse);
                    System.out.println("Invers Matriks: ");
                    Inverse.RowOperationInverse(matrixInverse);
                    break;

                case 4:
                    System.out.println("Interpolasi polinom");
                    System.out.println("1. Manual Input\n" +
                            "2. Upload file");
                    ops = input.nextInt();
                    if (ops == 1) {
                        System.out.print("Jumlah titik: ");
                        Scanner p = new Scanner(System.in);
                        int n = p.nextInt();
                        Interpolasi interpolasi = new Interpolasi();
                        interpolasi.driverManual(n);
                    } else if (ops == 2) {
                        // input nama file
                        System.out.print("Masukkan nama file :\n");
                        Scanner inputFileName = new Scanner(System.in);
                        String namaFile = inputFileName.nextLine();
                        Matrix matrixInterpolateIn = new Matrix();
                        IOFile.fileToMatriks(matrixInterpolateIn, namaFile);

                        // membuat matrixInterpolasi
                        Matrix matrixInterpolateOut = new Matrix();
                        matrixInterpolateOut.makeMatrix(matrixInterpolateIn.getNrow(),matrixInterpolateIn.getNrow()+1);
                        Interpolasi.createPowMatrix(matrixInterpolateIn, matrixInterpolateOut);
                        Operations.printMatrix(matrixInterpolateOut);

                        // penghitungan
                        SPLSolver solver = new SPLSolver();
                        solver.gaussDriverInterpolation(matrixInterpolateOut);
                    }

                    break;

                case 5:
                    System.out.println("Regresi Linear Berganda");
                    Scanner q = new Scanner(System.in);
                    Regresi regresi = new Regresi();
                    System.out.print("Jumlah variabel penentu: ");
                    int var = q.nextInt();
                    System.out.print("Jumlah sampel data: ");
                    int data = q.nextInt();
                    regresi.driverManual(var, data);
                    break;

                case 6:
                    System.out.println("Exiting...");
                    exit = true;
                    break;

                default:
                    exit = true;
            }

        }
    }
}
