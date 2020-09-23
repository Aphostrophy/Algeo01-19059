package com.tubes;

public class Inverse {

    static SquareMatrix AdjointInverse(SquareMatrix matrix) {
        SquareMatrix adjoint = Operations.getAdjoint(matrix);
        double multiplier = 1/Determinant.CofactorExpansionDeterminant(matrix); //Menghitung 1/det
        Operations.scalarMultiplication(adjoint, multiplier); //Mengalikan adj dengan 1/det
        return adjoint;
    }

    static SquareMatrix RowOperationInverse(SquareMatrix matrix) {

        // Menginisialisasi matriks identitas
        SquareMatrix identity = new SquareMatrix();
        identity.makeMatrix(matrix.getDimension());
        for(int i = 0; i < identity.getDimension(); i++) {
            for(int j = 0; j < identity.getDimension(); j++) {
                if(i == j) {
                    identity.setElmt(i, j, 1);
                } else {
                    identity.setElmt(i, j, 0);
                }
            }
        }

        if (Determinant.RowReductionDeterminant(matrix) == 0) {
            System.out.println("Matrix tidak memiliki invers");
            return matrix;
        }

        for(int idx = 0; idx < matrix.getDimension(); idx++) {
            double temp = matrix.getElmt(idx, idx); //Menginisialisasi nilai temp dengan elemen diagonal

            //Membagi seluruh baris dengan temp, agar elemen diagonal menjadi 1
            for(int i = 0; i < matrix.getDimension(); i++) {
                matrix.setElmt(idx, i, matrix.getElmt(idx, i)/temp);
                identity.setElmt(idx, i, identity.getElmt(idx, i)/temp);
            }

            //Melakukan operasi Pengurangan baris OBE untuk mendapatkan nilai 0 di elemen bukan diagonal
            for(int i = 0; i < matrix.getDimension(); i++) {
                temp = matrix.getElmt(i, idx);
                for(int j = 0; j < matrix.getDimension(); j++) {
                    if(i == idx) break; //Menghindari pengurangan terhadap elemen diagonal
                    matrix.setElmt(i, j, matrix.getElmt(i, j) - matrix.getElmt(idx, j)*temp);
                    identity.setElmt(i, j, identity.getElmt(i, j) - identity.getElmt(idx, j)*temp);
                }
            }
        }

        // Operasi OBE selesai
        return identity;
    }
}
