package com.tubes;

import java.util.Scanner;

public class Interpolasi {

    public void driverManual(int n){
        Matrix matrix = new Matrix();
        matrix.makeMatrix(n,n+1);
        Scanner scanner = new Scanner(System.in);
        Operations.printMatrix(matrix);
        double Px, Py;
        for(int i=0;i<n;i++){
            Px = scanner.nextDouble();
            Py = scanner.nextDouble();
            for(int y=0;y<n;y++){
                matrix.setElmt(i,y, Math.pow(Px,y));
            }
            matrix.setElmt(i,n, Py);
        }


        Operations.printMatrix(matrix);
        SPLSolver solver = new SPLSolver();
        solver.gaussDriverInterpolation(matrix);
    }
}
