package com.tubes;

public class Inverse {

    static void AdjointInverse(SquareMatrix matrix, Matrix mOut) {
        mOut.makeMatrix(matrix.getDimension(), matrix.getDimension());

        SquareMatrix adjoint = Operations.getAdjoint(matrix);
        double multiplier = 1/Determinant.CofactorExpansionDeterminant(matrix); //Menghitung 1/det
        Operations.scalarMultiplication(adjoint, multiplier); //Mengalikan adj dengan 1/det
        Operations.printMatrix(adjoint);

        Operations.copyMatrix(adjoint, mOut);
    }

    static void RowOperationInverse(SquareMatrix matrix, Matrix mOut) {
        mOut.makeMatrix(matrix.getDimension(), matrix.getDimension());

        // Menginisialisasi matriks identitas
        SquareMatrix detMatrix = new SquareMatrix();
        detMatrix.makeMatrix(matrix.getDimension());
        // Menyalin isi matrix ke detMatrix
        for(int i = 0; i < matrix.getDimension(); i++) {
            for(int j = 0; j < matrix.getDimension(); j++) {
                detMatrix.setElmt(i, j, matrix.getElmt(i, j));
            }
        }

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

        if (Determinant.RowReductionDeterminant(detMatrix) == 0) {
            System.out.println("Matrix tidak memiliki invers");
            return;
        }

        for(int idx = 0; idx < matrix.getDimension(); idx++) {
            double temp = matrix.getElmt(idx, idx); //Menginisialisasi nilai temp dengan elemen diagonal

            //Melakukan pertukaran baris jika elemen diagonal ada yang bernilai 0
            if (temp == 0) {
                int i = idx + 1;
                while (i < matrix.getDimension()) {
                    if (matrix.getElmt(i, idx) != 0) {
                        //Melakukan pertukaran baris
                        //Menukar baris pada matrix
                        Operations.swapRow(i, idx, matrix);
                        //Menukar baris pada matrix identitas
                        Operations.swapRow(i, idx, identity);
                        break;
                    }
                    i++;
                }
            }

            //Menginisialisasi temp kembali jika terjadi pertukaran baris
            temp = matrix.getElmt(idx, idx);

            //Membagi seluruh baris dengan temp, agar elemen diagonal menjadi 1
            for(int i = 0; i < matrix.getDimension(); i++) {
                matrix.setElmt(idx, i, matrix.getElmt(idx, i)/temp);
                identity.setElmt(idx, i, identity.getElmt(idx, i)/temp);
            }

            //Melakukan operasi Pengurangan baris OBE untuk mendapatkan nilai 0 di elemen bukan diagonal
            for(int i = 0; i < matrix.getDimension(); i++) {
                double multiplier = matrix.getElmt(i, idx);
                for(int j = 0; j < matrix.getDimension(); j++) {
                    if(i == idx) {
                        break;
                    } //Menghindari pengurangan terhadap elemen diagonal
                    matrix.setElmt(i, j, matrix.getElmt(i, j) - matrix.getElmt(idx, j)*multiplier);
                    identity.setElmt(i, j, identity.getElmt(i, j) - identity.getElmt(idx, j)*multiplier);
                }
            }
        }

        // Operasi OBE selesai
        Operations.printMatrix(identity);
        Operations.copyMatrix(identity, mOut);
    }
}
