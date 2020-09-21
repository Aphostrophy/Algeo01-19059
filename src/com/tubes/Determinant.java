package com.tubes;

public class Determinant {

    static double RowReductionDeterminant(SquareMatrix matrix){
        double det,multiplier;
        int sign = 1;
        int n = matrix.getDimension();
        int a,b;

        for(int j=0;j<n;j++){
            for(int r=j+1;r<n;r++){
                a = j;
                b = a+1;
                while(matrix.getElmt(j,j)==0){
                    System.out.println("Oh no");
                    if(b>=n){
                        return 0; //Because of the main diagonal element is zero <=> Determinant equals 0
                    }
                    Operations.swapRow(a, b, matrix);
                    sign *= 1;
                    b++;
                }
                multiplier = matrix.getElmt(r,j)/matrix.getElmt(j,j);
                for(int e=0;e<n;e++){
                    matrix.setElmt(r,e,( matrix.getElmt(r,e) - multiplier*matrix.getElmt(j,e)));
                }
            }
        }

        Operations.printMatrix(matrix);
        det = 1;
        for(int j=0;j<n;j++){
            det *= matrix.getElmt(j,j);
        }
        return sign*det;
    }

    static double CofactorExpansionDeterminant(SquareMatrix matrix){
        return 0;
    }
}
