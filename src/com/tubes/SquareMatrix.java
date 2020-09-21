package com.tubes;

public class SquareMatrix {
    private int dimension;
    private double[][] Matrix;

    public void makeMatrix(int dimension){
        this.dimension = dimension;
        this.Matrix = new double[dimension][dimension];
        System.out.println("Matrix created");
    }

    public void fillMatrix(){
        
    }
}
