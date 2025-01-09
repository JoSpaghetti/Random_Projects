package org.example;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;


public class ExcelFileWriteTest {
    static final int MAX_INPUT = 60;//maximum numbers for inputs
    static ArrayList<Integer> intArr = new ArrayList<>();//array list of random integers
    static Random random = new Random();

    public static void main (String[] args) {
        int randInt = 0;

        for (int i = 0; i < MAX_INPUT; ++i) {
            randInt = Math.abs(random.nextInt(MAX_INPUT)); //creates a new positive random int
            intArr.add(randInt); //adds the int to the array list
        }

        //creating XSSF workbook object
        XSSFWorkbook workbook = new XSSFWorkbook();
        //creating spreadsheet object
        XSSFSheet sheet = workbook.createSheet();

        String filename = "testExcelFile.xlsx";

        //creating row, cell, cellID and rowID for spreadsheet
        int rowID = 0;
        XSSFRow row;

        int cellID = 0;
        XSSFCell cell;

        row = sheet.createRow(0);
        cell = row.createCell(cellID);
        cell.setCellValue("Random Numbers");

        for (int integer: intArr) {
            row = sheet.createRow(++rowID);
            cell = row.createCell(cellID);
            cell.setCellValue(integer);
        }

        try {
            FileOutputStream output = new FileOutputStream(filename);
            workbook.write(output);
            output.close();
            System.out.printf("%s has been written successfully.", filename);
        } catch (Exception e) {
            System.out.print("The file is not found.");
            //e.printStackTrace();
        }
    }
}
