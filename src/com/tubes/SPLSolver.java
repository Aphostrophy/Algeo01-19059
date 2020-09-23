package com.tubes;

import java.util.Vector;

public class SPLSolver {

    public void gaussDriver(Matrix matrix){
        Matrix M = new Matrix();
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
        }
    }

    static Matrix gauss(Matrix matrix){
        int n_row = matrix.getNrow();
        int n_col = matrix.getNcol();
        int row_mark = 0;
        for(int j=0;j<n_col;j++){
            for(int r=j+1;r<n_row;r++){
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
}
