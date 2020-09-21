package com.tubes;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int nrow;
        int ncol;

        boolean exit = false;
	    while(!exit){
            System.out.println("MENU\n" +
                    "1. Sistem Persamaan Linear" +
                    "2. Determinan" +
                    "3. Matriks Balikan" +
                    "4. Interpolasi Polinom" +
                    "5. Regresi Linear Berganda" +
                    "6. Exit");
            Scanner input = new Scanner(System.in);
            int opt = input.nextInt();

            switch (opt){
                case 1:
                    System.out.println("Sistem persamaan linear");
                    nrow = input.nextInt();
                    ncol = input.nextInt();
                    break;

                case 2:
                    System.out.println("Determinan");
                    break;

                case 3:
                    System.out.println("Matriks Balikan");
                    break;

                case 4:
                    System.out.println("Interpolasi polinom");
                    break;

                case 5:
                    System.out.println("Regresi Linear Berganda");

                case 6:
                    System.out.println("Exit");
                    break;

                default:
                    exit = true;
            }

        }
    }
}
