package com.tubes;

import java.util.Scanner;
import java.util.Vector;

public class SPLSolver {

    public void gaussDriver(Matrix matrix){
//        mOut.makeMatrix(matrix.getNrow(), 1);
        Matrix M;
        Operations.printMatrix(matrix);
        M = gauss(matrix);
        System.out.println("tes");
        int mark = validateGaussMatrix(M);
        Operations.printMatrix(M);
        if(mark==0){
            System.out.println("No possible solution");
        } else if(mark==1){
            System.out.println("Solutions can be determined");
            singleSolution(matrix);
//            Operations.copyMatrix(matrix, mOut);
            System.out.println();
        } else{
            System.out.println("Many solutions");
            Operations.printMatrix(matrix);
            translator(matrix);
//            Operations.copyMatrix(matrix, mOut);
        }
    }

    public void gaussDriverInterpolation(Matrix matrix, Matrix mOut){
        Matrix M;
        Scanner scanner = new Scanner(System.in);
        M = gauss(matrix);
        double x,y;
        y=0;
        int mark = validateGaussMatrix(M);
        if(mark==0){
            System.out.println("No possible solution");
        } else if(mark==1){
            System.out.println("Solutions can be determined");
            Vector<Double> solutions = new Vector<>();
            String equation = "P2(x)= ";
            solutions = singleSolutionReturn(matrix);
            System.out.println(solutions);
            for(int i=solutions.size()-1;i>-1;i--){
                if(i==solutions.size()-1){
                    equation += String.valueOf(solutions.get(i));
                } else if(i==solutions.size()-2){
                    if(solutions.get(i)>0){
                        equation += "+" + solutions.get(i) + "x";
                    } else if(solutions.get(i)<0){
                        equation += solutions.get(i) + "x";
                    }
                } else{
                    if(solutions.get(i)>0){
                        equation += "+" + solutions.get(i) + "x^" + (solutions.size()-1-i);
                    } else if(solutions.get(i)<0){
                        equation += solutions.get(i) + "x^" + (solutions.size()-1-i);
                    }
                }
            }
            System.out.println(equation);
            System.out.print("Input x: ");
            x = scanner.nextDouble();
            for(int i=solutions.size()-1;i>-1;i--){
                if(i==solutions.size()-1){
                    y += solutions.get(i);
                } else{
                    y += solutions.get(i)*Math.pow(x, solutions.size()-1-i);
                }
            }
            mOut.makeMatrix(1, 2);
            mOut.setElmt(0, 0, x);
            mOut.setElmt(0, 1, y);

            System.out.println("P2(" + x + ") = " + y);
            System.out.println();
        } else{
            System.out.println("Many solutions, interpolation cannot be determined");
            translator(matrix);
        }
    }

    public void gaussDriverRegression(Matrix matrix, int n, Matrix mOut){
        double[] x = new double[n];
        double y = 0;
        Matrix M;
        Scanner scanner = new Scanner(System.in);
        M = gauss(matrix);
        int mark = validateGaussMatrix(M);
        if(mark==0){
            System.out.println("No possible solution");
        } else if(mark==1){
            System.out.println("Solutions can be determined");
            Vector<Double> solutions = new Vector<>();
            String equation = "y = ";
            solutions = singleSolutionReturn(matrix);
            System.out.println(solutions);
            for(int i=solutions.size()-1;i>-1;i--){
                if(i==solutions.size()-1){
                    equation += String.valueOf(solutions.get(i));
                } else {
                    if(solutions.get(i) > 0){
                        equation += " + " + solutions.get(i) + "x" + (solutions.size()-1-i);
                    } else if(solutions.get(i) < 0){
                        equation += " - " + (-1*solutions.get(i)) + "x" + (solutions.size()-1-i);
                    }
                }
            }
            System.out.println(equation);

            mOut.makeMatrix(1, n+1);
            for(int i = 0; i < n; i++) {
                System.out.print("Input x" + (i+1) + ": ");
                x[i] = scanner.nextDouble();
                mOut.setElmt(0, i, x[i]);
            }
            for(int i=solutions.size()-1;i>-1;i--){
                int var = 0;
                if(i==solutions.size()-1){
                    y += solutions.get(i);
                } else {
                    y += solutions.get(i)*x[var];
                }
                var++;
            }
            System.out.print("y(");
            for(int i = 0; i < n; i++) {
                if(i != n-1) {
                    System.out.print("x" + i + ", ");
                } else {
                    System.out.print("x" + i + ") = ");
                }
            }
            System.out.println(y);
            mOut.setElmt(0, mOut.getNcol() - 1, y);

            System.out.println();
        } else{
            System.out.println("Many solutions, regression cannot be determined");
        }
    }

    static Matrix gauss(Matrix matrix){
        int n_row = matrix.getNrow();
        int n_col = matrix.getNcol();
        int row_mark = 0;
        for(int j=0;j<n_col;j++){
            for(int r=row_mark+1;r<n_row;r++){
                boolean colZero = false;
                int a = row_mark;
                int b = a+1;
                while(matrix.getElmt(row_mark,j)==0 && !colZero){
                    if(b>=n_row){
                        colZero=true;
                    } else{
                        Operations.swapRow(a,b,matrix);
                        b++;
                    }
                }
                if(colZero){
                    row_mark -= 1;
                    break;
                }
                double multiplier = matrix.getElmt(r,j)/matrix.getElmt(row_mark,j);
                for(int e=0;e<n_col;e++){
                    matrix.setElmt(r,e, (matrix.getElmt(r,e) - multiplier*matrix.getElmt(row_mark,e)));
                }
            }
            row_mark += 1;
        }

        for(int i=0;i<n_row;i++){
            for(int j=0;j<n_col;j++){
                matrix.setElmt(i,j, Operations.roundAvoid(matrix.getElmt(i,j), 12));
            }
        }

        for(int i=0;i<n_row;i++){
            double multiplier = 0;
            for(int r=0;r<n_col;r++){
                if(matrix.getElmt(i,r)!=0){
                    multiplier = 1/matrix.getElmt(i,r);
                    break;
                }
            }
            for(int e=0;e<n_col;e++){
                if(matrix.getElmt(i,e)!=0){
                    matrix.setElmt(i,e, matrix.getElmt(i,e)*multiplier);
                }
            }
        }

        return matrix;
    }

    // returns 0 for no solution, 1 for single solutions, 2 for many solutions(parametric)
    static int validateGaussMatrix(Matrix matrix){
        int n_row = matrix.getNrow();
        int n_col = matrix.getNcol();
        int zeroRowCounter = n_row;
        for(int i=n_row-1;i>-1;i--){
            boolean isAllZero = true;
            for(int e=0;e<n_col-1;e++){
                if(matrix.getElmt(i,e)!=0){
                    isAllZero=false;
                    zeroRowCounter--;
                    break;
                }
            }
            if(isAllZero && matrix.getElmt(i,n_col-1)!=0){
                return 0;
            }
        }
        if(n_row-zeroRowCounter >= n_col-1){
            return 1;
        }
        return 2;
    }

    static void singleSolution(Matrix matrix){
        int n_row = matrix.getNrow();
        int n_col = matrix.getNcol();
        int eff_row = n_row;
        boolean double_break = false;
        for(int i=n_row-1;i>-1;i--){
            for(int e=0;e<n_col;e++){
                if(matrix.getElmt(i,e)!=0){
                    double_break = true;
                    break;
                }
            }
            if(double_break){
                break;
            } else{
                eff_row--;
            }
        }

        Vector<String> solutions = new Vector<>();

        for(int i=eff_row-1;i>-1;i--){
            for(int j=0;j<n_col-1;j++){
                if(matrix.getElmt(i,j)!=0){
                    for(int row=i-1;row>-1;row--){
                        matrix.setElmt(row,n_col-1, matrix.getElmt(row,n_col-1) - matrix.getElmt(row,j)*matrix.getElmt(i,n_col-1));
                        matrix.setElmt(row,j,0);
                    }
                    solutions.add(Double.toString(matrix.getElmt(i,n_col-1)));
                }
            }
        }

        for(int i=solutions.size()-1;i>-1;i--){
            System.out.println("x"+ (solutions.size() - i) + "=" + solutions.get(i));
        }
    }

    static void extractAug(Matrix m, SquareMatrix matriksA, Matrix matriksB) {
        // KAMUS
        int i;
        int j;

        // inisiasi matriks
        matriksA.makeMatrix(m.getNrow());
        matriksB.makeMatrix(m.getNrow(), 1);

        // matrix augmented menjadi matriks biasa
        for (i = 0; i < m.getNrow(); i++) {
            for (j = 0; j < m.getNcol(); j++) {
                if (j != m.getNcol() - 1) {
                    matriksA.setElmt(i, j, m.getElmt(i, j));
                } else {
                    matriksB.setElmt(i, 0, m.getElmt(i, j));
                }
            }
        }
        matriksA.setDimension(m.getNrow());

        matriksB.setNrow(m.getNrow());
        matriksB.setNcol(1);
    }

    // SPL Cramer
    static void cramerSPL(Matrix m, Matrix mOut) {
        /* I.S. menerima sebuah matriks augmented AB */
        /* F.S. menampilkan hasil penghitungan SPL menggunakan cramer */
        /* Proses : membuat matriks temp dengan elemen yang disisipkan matriks B
                    melakukan penghitungan dan menyimpan dalam matriks hasil */
        /* Hasil hanya dapat : unik atau tidak dapat ditentukan */
        // KAMUS
        int i;
        int j;
        int curCol;
        SquareMatrix temp = new SquareMatrix();
        SquareMatrix matriksA = new SquareMatrix();
        Matrix matriksB = new Matrix();
        double xi;
        double detA;

        // ALGORITMA
        // matrix augmented menjadi matriks biasa; matriks A dan matriks B
        extractAug(m, matriksA, matriksB);

        // inisialisasi matriks penyimpan hasil
        mOut.makeMatrix(matriksA.getDimension(), 1);

        // operasi SPL Cramer
        temp.makeMatrix(m.getNrow());
        detA = Determinant.CofactorExpansionDeterminant(matriksA);
        System.out.println(detA);

        // jika determinan matriks A tidak nol, dapat dilanjutkan
        if (detA != 0) {
            // membuat matriks A dengan sisipan elemen matriks B pada current Column
            for (curCol = 0; curCol < matriksA.getDimension(); curCol++) {
                for (i = 0; i < matriksA.getDimension(); i++) {
                    for (j = 0; j < matriksA.getDimension(); j++) {
                        // jika j indeks yang sama dengan current column, temp[j] di isi elemen matriks B
                        if (j == curCol) {
                            temp.setElmt(i, j, matriksB.getElmt(i, 0));
                        } else { // jika tidak, temp[j] diisi elemen matriks A
                            temp.setElmt(i, j, matriksA.getElmt(i, j));
                        }
                    } 
                }
                // menyimpan hasil perhitungan Determinan Ai / Determinan A
                xi = Determinant.CofactorExpansionDeterminant(temp) / detA;
                mOut.setElmt(curCol, 0, xi);
            }
            // menampilkan hasil penghitungan
            for (i = 0; i < mOut.getNrow(); i++) {
                System.out.println("x" + (i + 1) + " = " + mOut.getElmt(i, 0));
            }
        } else { // jika determinan matriks 0, tidak dapat dilakukan penghitungan
            System.out.println("Determinan 0, SPL tidak memiliki solusi unik.");
        }
    }

    // SPL Invers Matrix
    static void inversSPL(Matrix m, Matrix mOut) {
        /* I.S. menerima matriks augmented AB */
        /* F.S. menampilkan solusi SPL dengan menggunakan matriks invers */
        /* Proses : melakukan perkalian matriks balikan A dengan matriks B.
                    Jika matriks A tidak memiliki balikan (determinan = 0),
                    tidak dapat ditentukan hasilnya */
        /* Hasil hanya dapat : unik atau tidak dapat ditentukan  */
        // KAMUS
        int i;
        int k;
        double hasil;
        SquareMatrix matriksA = new SquareMatrix();
        Matrix matriksB = new Matrix();
        Matrix mInversed = new Matrix();

        // ALGORITMA
        // Mengambil matriks SPL dari matriks augmented
        extractAug(m, matriksA, matriksB);

        // invers matriks
        Inverse.AdjointInverse(matriksA, mInversed);
        
        // inisialisasi matriks penyimpan hasil
        mOut.makeMatrix(mInversed.getNrow(), 1);

        for (i = 0; i < mInversed.getNrow(); i++) {
            hasil = 0;
            for (k = 0; k < mInversed.getNcol(); k++) {
                hasil += mInversed.getElmt(i, k) * matriksB.getElmt(k, 0);
            }
            mOut.setElmt(i, 0, hasil);
        }
        
        for (i = 0; i < mOut.getNrow(); i++) {
            System.out.println("x" + (i + 1) + " = " + mOut.getElmt(i, 0));
        }
    }

    static Vector<Double> singleSolutionReturn(Matrix matrix){
        int n_row = matrix.getNrow();
        int n_col = matrix.getNcol();
        int eff_row = n_row;
        boolean double_break = false;
        for(int i=n_row-1;i>-1;i--){
            for(int e=0;e<n_col;e++){
                if(matrix.getElmt(i,e)!=0){
                    double_break = true;
                    break;
                }
            }
            if(double_break){
                break;
            } else{
                eff_row--;
            }
        }

        Vector<Double> solutions = new Vector<>();

        for(int i=eff_row-1;i>-1;i--){
            for(int j=0;j<n_col-1;j++){
                if(matrix.getElmt(i,j)!=0){
                    for(int row=i-1;row>-1;row--){
                        matrix.setElmt(row,n_col-1, matrix.getElmt(row,n_col-1) - matrix.getElmt(row,j)*matrix.getElmt(i,n_col-1));
                        matrix.setElmt(row,j,0);
                    }
                    solutions.add(Operations.roundAvoid(matrix.getElmt(i,n_col-1),4));
                }
            }
        }
        return solutions;
    }

    static void translator(Matrix matrix){
        int row = matrix.getNrow();
        int col = matrix.getNcol();

        Vector<Integer> tempIndependent = new Vector<>();
        Vector<Integer> dependent = new Vector<>();
        for(int y=0;y<col-1;y++){
            boolean found = false;
            for(int x=row-1;x>-1;x--){
                if(Operations.roundAvoid(matrix.getElmt(x,y), 15)==1){
                    if(y == 0) {
                        found = true;
                        break;
                    } else {
                        boolean isLeading = true;
                        for(int e = y-1; e >= 0; e--) {
                            if(matrix.getElmt(x,e) != 0) {
                                isLeading = false;
                            }
                        }
                        if(isLeading) {
                            found = true;
                            break;
                        }
                    }
                }
            }
            if(found){
                tempIndependent.add(y);
            } else{
                dependent.add(y);
            }
        }

        System.out.println(tempIndependent);
        System.out.println(dependent);

        //Do backwards subtitution for dependents
        Matrix padding = new Matrix();
        padding.makeMatrix(row, dependent.size());
        for(int y=0;y<dependent.size();y++){
            for(int x=0;x<row;x++){
                if(matrix.getElmt(x, dependent.get(y))!=0){
                    padding.setElmt(x,y, padding.getElmt(x,y)+matrix.getElmt(x,dependent.get(y))*-1);
                }
                matrix.setElmt(x, dependent.get(y), 0);
            }
        }

        Vector<Integer> independent = new Vector<>();
        for(int i=tempIndependent.size()-1;i>-1;i--){
            independent.add(tempIndependent.get(i));
        }

        for(int y=0;y<independent.size();y++){
            int mark = row -1;
            boolean found = false;
            double value = 1;
            for(int x=row-1;x>-1;x--){
                if(Operations.roundAvoid(matrix.getElmt(x, independent.get(y)),14)==1){
                    value = matrix.getElmt(x,col-1);
                    found = true;
                    mark = x;
                    break;
                }
            }
            if(found){
                for(int x=mark-1;x>-1;x--){
                    double v = matrix.getElmt(x,independent.get(y))*value;
                    matrix.setElmt(x,col-1, matrix.getElmt(x,col-1)-v);
                    double mul = matrix.getElmt(x, independent.get(y))/matrix.getElmt(mark, independent.get(y));
                    for(int e=0;e<padding.getNcol();e++){
                        padding.setElmt(x,e, padding.getElmt(x,e)-mul*padding.getElmt(mark,e));
                    }
                    matrix.setElmt(x, independent.get(y),0);
                }
            }
        }

        Operations.printMatrix(matrix);
        Operations.printMatrix(padding);

        for(int r=0;r<dependent.size();r++){
            String parametric = new String();
            parametric = "x" + Integer.toString(dependent.get(r)+1) + "="+ (char)(119-r);
            System.out.println(parametric);
        }


        for(int r=0;r<row;r++){
            String res = paddingParser(matrix,r,padding);
            Character c = res.charAt(0);
            Character d = "=".charAt(0);
            if(c.equals(d)){
                break;
            }else{
                System.out.println(res);
            }
        }
    }

    static String paddingParser(Matrix matrix, int row, Matrix padding){
        String stringify = "";
        for(int e=0;e<matrix.getNcol()-1;e++){
            if(Operations.roundAvoid(matrix.getElmt(row,e),15)==1){
                stringify += "x" + (e + 1);
                break;
            }
        }
        if(matrix.getElmt(row, matrix.getNcol()-1) != 0){
            stringify += "=" + matrix.getElmt(row, matrix.getNcol() - 1);
        } else{
            stringify += "=";
        }
        //Handling padding
        for(int e=0;e<padding.getNcol();e++){
            if(padding.getElmt(row,e)>0){
                stringify += "+" + Double.toString(padding.getElmt(row, e)) + (char)(119-e);
            } else if(padding.getElmt(row,e)<0){
                stringify += Double.toString(padding.getElmt(row,e)) + (char)(119-e);
            }
        }
        Character c = stringify.charAt(stringify.length()-1);
        Character d = "=".charAt(0);
        if(c.equals(d)){
            stringify += "0";
        }
        return stringify;
    }

    static void gaussJordanSolver (Matrix matrix) {
        int[] countVarBrs = new int[matrix.getNrow()]; // Array untuk menampung jumlah variabel yg terdefinisi tiap barisnya setelah OBE
        char[] definedVar = new char[matrix.getNcol()-1]; // Array untuk menyimpan apakah setiap variabel terdefinisi setelaj OBE
        boolean solvable = false; // Variabel yang menyatakan SPL solvable atau tidak

//        mOut.makeMatrix(matrix.getNrow(), 1);

        // Inisialisasi array countVarBrs
        for(int i = 0; i < matrix.getNrow(); i++) {
            countVarBrs[i] = 0;
        }
        for(int i = 0; i < matrix.getNcol()-1; i++) {
            definedVar[i] = ' ';
        }

        // Awal operasi OBE
        int idxRow = 0;
        int idxCol = 0;

        while(idxRow < matrix.getNrow() && idxCol < matrix.getNcol()) {
            double temp = matrix.getElmt(idxRow, idxCol);

            // Jika elemen paling calon ledaing one matriks merupakan 0, lakukan swap baris
            if(temp == 0) {
                int i = idxRow + 1;
                while(i < matrix.getNrow()) {
                    if(matrix.getElmt(i, idxRow) != 0) {
                        Operations.swapRow(i, idxRow, matrix);
                        break;
                    }
                    i++;
                }
                if(i == matrix.getNrow()) { // Jika semua baris dibawahnya sudah bernilai 0, lanjutkan ke kolom berikutnya
                    idxCol++;
                    continue;
                }
            }

            // Menginisialisasi kembali nilai temp dengan elemen matriks yang akan dijadikan leading one
            temp = matrix.getElmt(idxRow, idxCol);

            // Membagi seluruh baris dengan temp
            for(int j = 0; j < matrix.getNcol(); j++) {
                matrix.setElmt(idxRow, j, matrix.getElmt(idxRow, j) / temp);
            }

            // Melakukan pengurangan baris untuk mendapatkan matriks eselon baris tereduksi
            for(int i = 0; i < matrix.getNrow(); i++) {
                temp = matrix.getElmt(i, idxCol);
                for(int j = 0; j < matrix.getNcol(); j++) {
                    if(i == idxRow) { // Tidak boleh melakukan pengurangan terhadap diri sendiri
                        break;
                    } else {
                        matrix.setElmt(i, j, matrix.getElmt(i, j) - (matrix.getElmt(idxRow, j)*temp));
                    }
                }
            }
            Operations.printMatrix(matrix);

            // Melanjutkan ke baris berikutnya
            idxRow++;
            idxCol++;
        }

        // Mencari tahu apakah SPL ada solusinya atau tidak
        if(matrix.getElmt(matrix.getNrow()-1, matrix.getNcol()-1) != 0) {
            for(int j = 0; j < matrix.getNcol()-1; j++) {
                if(matrix.getElmt(matrix.getNrow()-1, j) != 0) {
                    solvable = true;
                }
            }
        } else {
            solvable = true;
        }

        // Menghitung jumlah variabel yang tidak 0 dalam setiap baris
        for(int i = 0; i < matrix.getNrow(); i++) {
            for(int j = 0; j < matrix.getNcol(); j++) {
                if(matrix.getElmt(i, j) != 0) {
                    countVarBrs[i]++;
                }
            }
        }

        // Proses mencetak solusi
        if(!solvable) {
            System.out.println("SPL tidak memiliki solusi");
        } else {
            // Mengganti variabel dependen dengan variabel lain jika SPL memilki banyak solusi
            int abjad = 0;
            for(int i = 0; i < matrix.getNrow(); i++) {
                int j = 0;
                while (j < matrix.getNcol()-1) {
                    if(matrix.getElmt(i, j) != 0) {
                        for(int k = j+1; k < matrix.getNcol()-1; k++) {
                            if(matrix.getElmt(i, k) != 0 && definedVar[k] == ' ') {
                                System.out.println("x" + (k+1) + " = " + (char)(119-abjad));
                                definedVar[k] = (char)(119-abjad);
                                abjad++;
                            }
                        }
                    }
                    j++;
                }
            }

            // Mencetak solusi independen
            for(int i = 0; i < matrix.getNrow(); i++) {
                int j = 0;
                int temp = countVarBrs[i];
                boolean allZero = true; // Variabel yang menyatakan apakah suatu baris setelah OBE bernilai 0 semua atau tidak

                // Jika b[j] != 0
                if(matrix.getElmt(i, matrix.getNcol()-1) != 0) {
                    allZero = false;
                    while(j < matrix.getNcol()-1) {

                        // Mencetak variabel pertama yg terdefinsi dalam baris besert b[j]
                        if (matrix.getElmt(i, j) != 0 && temp == countVarBrs[i]) {
                            System.out.print("x" + (j + 1) + " = " + matrix.getElmt(i, matrix.getNcol() - 1));
                            temp--;
                        }

                        // Mencetak sisa variabel yang dipindahruaskan
                        else if (matrix.getElmt(i, j) != 0) {
                            if (matrix.getElmt(i, j) >= 0) {
                                System.out.print(" - " + matrix.getElmt(i, j) + definedVar[j]);
                                abjad++;
                            } else {
                                System.out.print(" + " + (-1 * matrix.getElmt(i, j)) + definedVar[j]);
                                abjad++;
                            }
                            temp--;
                        }
                        j++;
                    }
                } else { // b[j] == 0
                    while(j < matrix.getNcol()-1 && temp >= 0) {
                        // Mencetak variabel pertama yang terdefinisi, b[j], dan sisa variabel terdefinsi dalam baris yang sudah dipindahruaskan
                        if(matrix.getElmt(i, j) != 0 && temp == countVarBrs[i]) {
                            allZero = false;
                            System.out.print("x" + (j+1) + " = ");
                            j++;
                            // Jika hanya ada 1 variabel terdefinisi dalam baris, cetak 0
                            if(countVarBrs[i] == 1) {
                                System.out.print(0);
                            } else if(matrix.getElmt(i, j) >= 0) {
                                System.out.print("-" + matrix.getElmt(i, j) + definedVar[j]);
                                abjad++;
                            } else {
                                System.out.print((-1 * matrix.getElmt(i, j)) + "" + definedVar[j]);
                                abjad++;
                            }
                            temp -= 2;
                            j++;
                        } else if(matrix.getElmt(i, j) != 0) {
                            allZero = false;
                            if(matrix.getElmt(i, j) >= 0) {
                                System.out.print(" - " + matrix.getElmt(i, j) + definedVar[j]);
                                abjad++;
                            } else {
                                System.out.print(" + " + (-1*matrix.getElmt(i, j)) + definedVar[j]);
                                abjad++;
                            }
                            temp--;
                        }
                        j++;
                    }
                }
                if(allZero) {break;}
                else {System.out.println("");}
            }
        }
    }
}
