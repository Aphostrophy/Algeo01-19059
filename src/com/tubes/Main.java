package com.tubes;

import javax.swing.*;
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
                    // inisiasi
                    System.out.println("Sistem persamaan linear");
                    Matrix matrixSPL = new Matrix();
                    SPLSolver SPL = new SPLSolver();

                    // jenis input
                    int ops = OpsiInput();
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

                    Matrix hasilMat = new Matrix();
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
                        SPLSolver.cramerSPL(matrixSPL, hasilMat);
                    } else{
                        System.out.println("Input salah");
                    }
                    OpsiPrintHasil(hasilMat);
                    break;

                case 2:
                    // inisiasi
                    System.out.println("Determinan");
                    SquareMatrix matrix = new SquareMatrix();

                    // jenis input
                    ops = OpsiInput();
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

                    // hasil matriks untuk diprint
                    hasilMat = new Matrix();
                    hasilMat.makeMatrix(1, 1);

                    hasilMat.setElmt(0, 0, det);
                    OpsiPrintHasil(hasilMat);
                    break;

                case 3:
                    // inisiasi
                    System.out.println("Matriks Balikan");
                    SquareMatrix matrixInverse = new SquareMatrix();

                    // jenis input
                    ops = OpsiInput();
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

                    // jenis input
                    ops = OpsiInput();
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
                    // inisiasi
                    System.out.println("Regresi Linear Berganda");
                    Matrix tabel = new Matrix();
                    Matrix dataMatrix = new Matrix();
                    Regresi regresi = new Regresi();
                    int var = 0;
                    int nData = 0;

                    // jenis input
                    ops = OpsiInput();
                    if (ops == 1) {
                        Scanner q = new Scanner(System.in);
                        System.out.print("Jumlah variabel penentu: ");
                        var = q.nextInt();
                        System.out.print("Jumlah sampel data: ");
                        nData = q.nextInt();

                        tabel.makeMatrix(nData, var + 1);
                        dataMatrix.makeMatrix(var + 1, var + 2);

                        Operations.fillMatrix(tabel);
                        Operations.printMatrix(tabel);

                    } else if (ops == 2) {
                        Matrix temp = new Matrix();

                        // membaca matriks dari file
                        System.out.print("Masukkan nama file :\n");
                        Scanner inputFileName = new Scanner(System.in);
                        String namaFile = inputFileName.nextLine();
                        IOFile.fileToMatriks(temp, namaFile);

                        // inisiasi penyimpan
                        nData = temp.getNrow();
                        var = temp.getNcol() - 1; // kolom y bukan banyaknya variabel
                        tabel.makeMatrix(nData, var + 1);
                        dataMatrix.makeMatrix(var + 1, var + 2);

                        // copy matriks temp ke tabel
                        Operations.copyMatrix(temp, tabel);
                        Operations.printMatrix(tabel);
                    }
                    regresi.driverManual(tabel, dataMatrix, var, nData);

                    System.out.println("Matriks dari persamaan yang terbentuk : ");
                    Operations.printMatrix(dataMatrix);
                    SPLSolver solver = new SPLSolver();
                    solver.gaussDriverRegression(dataMatrix, var);
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

    static int OpsiInput() {
        Scanner input = new Scanner(System.in);
        System.out.println("1. Manual Input\n" +
                "2. Upload file");
        return input.nextInt();
    }

    static void OpsiPrintHasil(Matrix matrix) {
        // Menawarkan menuliskan hasil operasi ke dalam file .txt
        Scanner input = new Scanner(System.in);
        System.out.println("Print hasil?\n" +
                "1. Ya\n" +
                "2. Tidak");
        int ops = input.nextInt();
        if (ops == 1) {
            System.out.print("Masukkan nama file :\n");
            Scanner inputFileName = new Scanner(System.in);
            String fileName = inputFileName.nextLine();
            IOFile.matriksToFile(matrix, fileName);
        }
    }
}
