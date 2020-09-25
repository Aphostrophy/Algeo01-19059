package com.tubes;

import java.util.Scanner;
import java.util.Vector;

public class SPLSolver {

    public void gaussDriver(Matrix matrix){
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
            System.out.println();
        } else{
            System.out.println("Many solutions");
            translator(matrix);
        }
    }

    public void gaussDriverInterpolation(Matrix matrix){
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
            System.out.println("P2(" + x + ") = " + y);
            System.out.println();
        } else{
            System.out.println("Many solutions, interpolation cannot be determined");
            translator(matrix);
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

        // matrix augmented menjadi matriks biasa
        for (i = 0; i < m.getNrow() - 1; i++) {
            for (j = 0; j < m.getNcol() - 1; j++) {
                if (j != m.getNcol()-1) {
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
    static void cramerSPL(Matrix m) {
        // KAMUS
        int i;
        int j;
        int curCol;
        SquareMatrix temp = new SquareMatrix();
        SquareMatrix matriksA = new SquareMatrix();
        Matrix matriksB = new Matrix();
        Matrix hasilMatriks = new Matrix();
        double xi;
        double detA;

        // ALGORITMA
        // matrix augmented menjadi matriks biasa
        extractAug(m, matriksA, matriksB);

        // inisialisasi matriks penyimpan hasil
        hasilMatriks.setNcol(1);
        hasilMatriks.setNrow(matriksA.getDimension());

        // operasi SPL Cramer
        temp.setDimension(m.getNrow());
        xi = 0;
        detA = Determinant.RowReductionDeterminant(matriksA);
        if (detA != 0) {
            for (curCol = 0; curCol < matriksA.getDimension() - 1; curCol++) {
                for (i = 0; i < matriksA.getDimension() - 1; i++) {
                    for (j = 0; j < matriksA.getDimension() - 1; j++) {
                        if (j == curCol) {
                            temp.setElmt(i, j, matriksB.getElmt(i, 0));
                        } else {
                            temp.setElmt(i, j, matriksA.getElmt(i, j));
                        }
                    } 
                }
                xi = Determinant.RowReductionDeterminant(temp) / detA;
                hasilMatriks.setElmt(curCol, 0, xi);
            }

            for (i = 0; i < hasilMatriks.getNrow() - 1; i++) {
                System.out.println("x " + (i + 1) + " : " + hasilMatriks.getElmt(i, 0));
            }

        } else {
            System.out.println("Determinan 0, SPL tidak memiliki solusi unik.");
        }

        
    }

    // SPL Invers Matrix
    static void inversSPL(Matrix m) {
        // KAMUS
        int i;
        int j;
        int k;
        double hasil;
        SquareMatrix matriksA = new SquareMatrix();
        Matrix matriksB = new Matrix();
        Matrix hasilMatriks = new Matrix();

        // ALGORITMA
        // Mengambil matriks SPL dari matriks augmented
        extractAug(m, matriksA, matriksB);

        // invers matriks
        Inverse.RowOperationInverse(matriksA);
        
        // inisialisasi matriks penyimpan hasil
        hasilMatriks.setNcol(1);
        hasilMatriks.setNrow(matriksA.getDimension());

        for (i = 0; i < matriksA.getDimension() - 1; i++) {
            for (j = 0; j < matriksA.getDimension() - 1; j++) {
                hasil = 0;
                for (k = 0; k < matriksA.getDimension() - 1; k++) {
                    hasil += matriksA.getElmt(i, k) * matriksB.getElmt(k, 0);
                }
                hasilMatriks.setElmt(i, 0, hasil);
            }
        }
        
        for (i = 0; i < hasilMatriks.getNrow() - 1; i++) {
            System.out.println("x " + (i + 1) + " : " + hasilMatriks.getElmt(i, 0));
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
                    found = true;
                    break;
                }
            }
            if(found){
                tempIndependent.add(y);
            } else{
                dependent.add(y);
            }
        }

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
}
