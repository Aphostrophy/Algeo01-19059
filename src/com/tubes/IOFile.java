package com.tubes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class IOFile {

    public void fileToMatriks(String fileName) {
        try {
            // KAMUS CEUNAH
            Matrix matriksFile = new Matrix();
            int row, col;

            // ALGORITMA
            // baca input file
            File myFile = new File(fileName);
            Scanner fileReader = new Scanner(myFile);
            try {
                row = 0;
                while (fileReader.hasNextLine()) {
                    String stringFile = fileReader.nextLine();
                    Scanner scannerString = new Scanner(stringFile);
                    try {
                        col = 0;
                        while (scannerString.hasNextDouble()) {
                            matriksFile.setElmt(row, col, scannerString.nextDouble());
                            col++;
                        }
                    } finally {
                        scannerString.close();
                    }
                    row++;
                }
            } finally {
                fileReader.close();
            } 
        } catch (FileNotFoundException e) {
            System.out.println("File tidak ditemukan.");
            e.printStackTrace();
        }
    }
}