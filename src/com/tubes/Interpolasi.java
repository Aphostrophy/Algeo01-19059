package com.tubes;

import java.util.Scanner;

public class Interpolasi {

    public void driverManual(int n, Matrix mOut){
        Matrix matrix = new Matrix();
        matrix.makeMatrix(n,n+1);
        Scanner scanner = new Scanner(System.in);
        double Px, Py;
        for(int i=0;i<n;i++){
            System.out.println("Masukkan titik ke-" + (i+1));
            Px = scanner.nextDouble();
            Py = scanner.nextDouble();

            // create matrix
            for(int y=0;y<n;y++){
                matrix.setElmt(i,y, Math.pow(Px,y));
            }
            matrix.setElmt(i,n, Py);
        }

        Operations.printMatrix(matrix);
        SPLSolver solver = new SPLSolver();
        solver.gaussDriverInterpolation(matrix, mOut);
    }

    static void createPowMatrix(Matrix matrixIn, Matrix matrixOut) {
        double Px, Py;
        for(int i=0;i<matrixIn.getNrow();i++){
            System.out.println("Masukkan titik ke-" + (i+1));
            Px = matrixIn.getElmt(i, 0);
            Py = matrixIn.getElmt(i, 1);

            // create matrix
            for(int y=0;y<matrixIn.getNrow();y++){
                matrixOut.setElmt(i,y, Math.pow(Px,y));
            }
            matrixOut.setElmt(i, matrixIn.getNrow(), Py);
        }
    }
}
