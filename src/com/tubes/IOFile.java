package com.tubes;

import java.util.Scanner;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;

public class IOFile {

    static void fileToMatriks(Matrix matriks, String fileName) {
        /* I.S. matriks sembarang */
        /* F.S. terbentuk matriks dari file yang dibaca */
        // Proses. Membaca file baris perbaris dan mengubahnya persatuan float
        // KAMUS CEUNAH
        int i;
        int j;
        int col;
        int row;

        // ALGORITMA
        // baca input file
        try (Scanner fileReader= new Scanner(new BufferedReader(new FileReader(fileName)))) {
            i = 0;
            row = 0;
            col = 0;
            while (fileReader.hasNextLine()) {
                row++;
//                Scanner colReader = new Scanner(fileReader.nextLine());
//                while(colReader.hasNextDouble()) {
//                    col++;
//                }
                String[] line = fileReader.nextLine().trim().split(" ");
                for (j = 0; j < line.length; j++) {
                    col++;
                }
            }

            matriks.makeMatrix(row, col);

            for (i = 0; i < row; i++) {
                Scanner colReader = new Scanner(fileReader.nextLine());
                for (j = 0; j < col; j++) {
                    if (colReader.hasNextDouble()) {
                        matriks.setElmt(i, j, colReader.nextDouble());
                    }
                }
            }
//            while (fileReader.hasNextLine()) {
//                String[] line = fileReader.nextLine().trim().split(" ");
//                for (j = 0; j < line.length; j++) {
//                    matriks.setElmt(i, j, Double.parseDouble(line[j]));
//                    col = j;
//                }
//                i++;
//            }
        } catch (FileNotFoundException ex) {
            System.out.println("File " + fileName + " was not found.");
        }
    }

    static void matriksToFile(Matrix matriks, String fileName) {
        // KAMUS
        int i;
        int j;
        String line;

        // ALGORITMA
        try {
            File myFile = new File(fileName);
            if (myFile.createNewFile()) {
                System.out.println("File created: " + myFile.getName());
            } 

            try (FileWriter myWriter = new FileWriter(myFile.getName())) {
                for (i = 0; i < matriks.getNrow(); i++) {
                    line = "";
                    for (j = 0; j < matriks.getNcol(); j++) {
                        line += String.valueOf(matriks.getElmt(i, j));
                        if (j != matriks.getNcol() - 1) {
                            line += " ";
                        }
                    }
                    myWriter.write(line);
                    if (i != matriks.getNrow() - 1) {
                        myWriter.write("%n");
                    }
                }
            } catch (FileNotFoundException ex) {
                System.out.println("File " + fileName + " was not found.");
            }
            
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}