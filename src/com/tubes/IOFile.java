package com.tubes;

import java.util.Scanner;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;

public class IOFile {

    static void fileToMatriks(Matrix matriks, String fileName) throws IOException {
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
        try {
            row = 0;
            col = 0;
            String line;
            // Read file
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            while ((line = br.readLine()) != null) {
                String[] colReader = line.split(" ");
                col = colReader.length;
                row++;
            }
            br.close(); // close the file reader

            matriks.makeMatrix(row, col); // make empty matrix row x col

            // Re-read file from beginning
            br = new BufferedReader(new FileReader(fileName));
            i = 0;
            while ((line = br.readLine()) != null) {
                String[] colReader = line.split(" ");
                for (j = 0; j < colReader.length; j++) {
                    matriks.setElmt(i, j, Double.parseDouble(colReader[j]));
                }
                i++;
            }
            br.close(); // close the file reader
        } catch (FileNotFoundException ex) {
            System.out.println("File " + fileName + " was not found.");
        }
    }

    static void fileToMatriks(SquareMatrix matriks, String fileName) throws IOException {
        /* I.S. matriks sembarang */
        /* F.S. terbentuk matriks dari file yang dibaca */
        // Proses. Membaca file baris perbaris dan mengubahnya persatuan float
        // KAMUS CEUNAH
        int i;
        int j;
        int dim;

        // ALGORITMA
        // baca input file
        try {
            dim = 0;
            String line;
            // Read file
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            while ((line = br.readLine()) != null) {
                String[] colReader = line.split(" ");
                dim = colReader.length;
            }
            br.close(); // close the file reader

            matriks.makeMatrix(dim); // make empty matrix row x col

            // Re-read file from beginning
            br = new BufferedReader(new FileReader(fileName));
            i = 0;
            while ((line = br.readLine()) != null) {
                String[] colReader = line.split(" ");
                for (j = 0; j < colReader.length; j++) {
                    matriks.setElmt(i, j, Double.parseDouble(colReader[j]));
                }
                i++;
            }
            br.close(); // close the file reader
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
                        myWriter.write("\n");
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

    static void matriksToFile(SquareMatrix matriks, String fileName) {
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
                for (i = 0; i < matriks.getDimension(); i++) {
                    line = "";
                    for (j = 0; j < matriks.getDimension(); j++) {
                        line += String.valueOf(matriks.getElmt(i, j));
                        if (j != matriks.getDimension() - 1) {
                            line += " ";
                        }
                    }
                    myWriter.write(line);
                    if (i != matriks.getDimension() - 1) {
                        myWriter.write("\n");
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