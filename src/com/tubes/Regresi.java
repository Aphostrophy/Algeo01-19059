package com.tubes;

import java.util.Scanner;

public class Regresi {

    public void driverManual(Matrix tabel, Matrix dataMatrix, int var, int nData) {
        // Mengisi baris pertama dataMatrix
        dataMatrix.setElmt(0, 0, nData);
        int colDat = 0;
        double sum;
        for (int j = 1; j < dataMatrix.getNcol(); j++) {
            sum = 0;
            for (int rowDat = 0; rowDat < tabel.getNrow(); rowDat++) {
                sum += tabel.getElmt(rowDat, colDat);
            }
            dataMatrix.setElmt(0, j, sum);
            colDat++;
        }

        // Mengisi sisa var baris berikutnya
        colDat = 0;
        for (int i = 1; i < dataMatrix.getNrow(); i++) {
            int k = 0;
            for (int j = 0; j < dataMatrix.getNcol(); j++) {
                sum = 0;
                if (j == 0) {
                    for (int rowDat = 0; rowDat < tabel.getNrow(); rowDat++) {
                        sum += tabel.getElmt(rowDat, colDat);
                    }
                    dataMatrix.setElmt(i, j, sum);
                    continue;
                } else {
                    for (int rowDat = 0; rowDat < tabel.getNrow(); rowDat++) {
                        sum += tabel.getElmt(rowDat, colDat) * tabel.getElmt(rowDat, k);
                    }
                    dataMatrix.setElmt(i, j, sum);
                }
                k++;
            }
            colDat++;
        }
    }

    public void as(int var, int nData) {
        Matrix tabel = new Matrix();
        tabel.makeMatrix(nData, var + 1);

        Matrix dataMatrix = new Matrix();
        dataMatrix.makeMatrix(var + 1, var + 2);

        Scanner scanner = new Scanner(System.in);

        Operations.fillMatrix(tabel);

        // Mengisi baris pertama dataMatrix
        dataMatrix.setElmt(0, 0, nData);
        int colDat = 0;
        double sum;
        for (int j = 1; j < dataMatrix.getNcol(); j++) {
            sum = 0;
            for (int rowDat = 0; rowDat < tabel.getNrow(); rowDat++) {
                sum += tabel.getElmt(rowDat, colDat);
            }
            dataMatrix.setElmt(0, j, sum);
            colDat++;
        }

        // Mengisi sisa var baris berikutnya
        colDat = 0;
        for (int i = 1; i < dataMatrix.getNrow(); i++) {
            int k = 0;
            for (int j = 0; j < dataMatrix.getNcol(); j++) {
                sum = 0;
                if (j == 0) {
                    for (int rowDat = 0; rowDat < tabel.getNrow(); rowDat++) {
                        sum += tabel.getElmt(rowDat, colDat);
                    }
                    dataMatrix.setElmt(i, j, sum);
                    continue;
                } else {
                    for (int rowDat = 0; rowDat < tabel.getNrow(); rowDat++) {
                        sum += tabel.getElmt(rowDat, colDat) * tabel.getElmt(rowDat, k);
                    }
                    dataMatrix.setElmt(i, j, sum);
                }
                k++;
            }
            colDat++;
        }

        Operations.printMatrix(dataMatrix);
        SPLSolver solver = new SPLSolver();
        solver.gaussDriverRegression(dataMatrix, var);
    }
}