package org.example;

import java.io.*;
import java.util.*;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
what to do

4. Separate code into different files
5. (Long term goal) Create HTML/Web version of the program
 */

public class RandomNumberCheck {
    static Scanner scanner = new Scanner (System.in);
    static ArrayList<Integer> randNumbers = new ArrayList<>();
    static ArrayList<Integer> tempArray = new ArrayList<>();
    static Random random = new Random();
    static final int MAX_PRINT_SIZE = 30;
    static boolean isRunning = true;
    public static void main(String[] args) {
        int choose; //determines which choice the program goes down
        isRunning = true;
        while (isRunning) {

            System.out.print ("""
                --Random Number Checker--\s
                Welcome. This program generates random numbers. You can change the parameters of the program if you want.\s
                Please enter the number that corresponds with the input you want\s
                1: Generates a list of random numbers\s
                2: Generates a list of random numbers that are equal with an iterator
                3. Leave the Program\s
                """);

            choose = validateNumber();
            switch (choose) {
                case 1 -> {
                    ArrayList<Integer> randNumberArr = generateRandomNumberArray();
                    WriteToFile(randNumberArr);
                }
                case 2 -> {
                    ArrayList<Integer> randIteratorArr = generateRandomNumberIteratorArray();
                    WriteToFile(randIteratorArr);
                }
                case 3 -> isRunning = false;
                default -> System.out.print("This isn't a valid input, please try again.\n");
            }
        }

    }
    private static ArrayList<Integer> generateRandomNumberArray() {
        System.out.print("How many random numbers do you want?");
        int arrayListSize = validateNumber();
        int arrayListRange;
        while (true) {
            System.out.print("What range should the numbers have? Give the upper limit.");
            arrayListRange = validateNumber();
            if (arrayListRange > 0) {
                break;
            }
            System.out.print("The range cannot be less then 1\n");
        }
        System.out.print("Do you want the list sorted? \nType true or false:");
        boolean isSorted = validateBoolean();
        return generateRandomArrayList(arrayListSize, arrayListRange, isSorted);
    }

    private static ArrayList<Integer> generateRandomNumberIteratorArray() {
        int tempRange;
        System.out.print("What range do you want for the random input check? ");
        while (true) {
            tempRange = validateNumber();
            if (tempRange > 2) {
                break;
            }
            System.out.print("Please give a larger number");
        }
        System.out.print("Do you want unique random integers (an oxymoron, I know)? \nType true or false:");
        boolean isUnique = validateBoolean();
        return randomNumberIterator(isUnique, tempRange);
    }

    private static ArrayList<Integer> generateRandomArrayList(int arrayListSize, int arrayListRange, boolean isSorted) {
        ArrayList<Integer> tempArray = new ArrayList<>();
        int randInt;

        for (int i = 0; i < arrayListSize; ++i) {
            randInt = Math.abs(random.nextInt(arrayListRange));
            tempArray.add(randInt);
        }
        if (isSorted) {
            Collections.sort(tempArray);
        }
        return tempArray;
    }
    private static boolean validateBoolean() {
        boolean tempBool;
        while (true) {
            try {
                tempBool = scanner.nextBoolean();
                scanner.nextLine();
                return tempBool;
            }
            catch (java.util.InputMismatchException e) {
                scanner.nextLine();
                System.out.print("\nThe input is not correct. Please try again");
            }
        }
    }

    private static int validateNumber () {
        int choose;
        while (true) {
            try {
                choose = scanner.nextInt();
                scanner.nextLine();
                return choose;
            } catch (java.util.InputMismatchException e) {
                scanner.nextLine();
                System.out.print("\nThe input is not correct. Please try again");
            }

        }
    }
    private static int randomNumberGenerator(boolean isUnique, int bound) {
        if (isUnique) {
            return uniqueRandom();
        }
        return Math.abs(random.nextInt(bound + 1) );
    }

    private static ArrayList<Integer> randomNumberIterator (boolean isUnique, int maxNumber) {
        if (isUnique) {
            return uniqueRandomNumberIterator(maxNumber);
        } else {
            return randomNumberIterator(maxNumber);
        }
    }

    private static ArrayList<Integer> randomNumberIterator(int maxNumber) {
        int randInt;

        for (int i=0; i < maxNumber-1; ++i) {
            randInt = randomNumberGenerator(false, maxNumber);

            if (i > 1) {
                System.out.printf ("\ni: %d, randInt: %d", i, randInt);
            }

            if (randInt == i) {
                try {
                    tempArray.add(randInt); //used to catch out of memory error
                } catch (OutOfMemoryError e) {
                    System.out.printf("\nWe've run out of memory (int number %d)", i);
                    break;
                }
            }
        }
        return tempArray;
    }

    private static ArrayList<Integer> uniqueRandomNumberIterator(int maxNumber) {
        int randInt;
        randNumbers.add(-1);

        for (int i=0; i < maxNumber-1; ++i) {
            randInt = randomNumberGenerator(true, 0);

            try {
                randNumbers.add(randInt); //used to catch out of memory error
            } catch (OutOfMemoryError e ) {
                System.out.printf ("\nWe've run out of memory (int number %d). Try again?", i);
                break;
            }

            Collections.sort(randNumbers);//uses collections to sort the array

            if (i > 1) {
                System.out.printf ("\ni: %d, randInt: %d", i, randInt);
            }

            if (randInt == i) {
                try {
                    tempArray.add(randInt); //used to catch out of memory error
                } catch (OutOfMemoryError e ) {
                    System.out.printf ("\nWe've run out of memory (int number %d).", i);
                    break;
                }
                System.out.printf(" -%d, ", i);
            }
        }
        return tempArray;
    }
    private static int uniqueRandom() {
        boolean intIsUnique = false;
        int randInt = 0;
        while (!intIsUnique) {
            randInt = Math.abs(random.nextInt(randNumbers.size()+ 1) ); //creates a random int
            int intIsFound = Collections.binarySearch(randNumbers, randInt); //uses collections binary search algo to find number
            if (intIsFound < 0) { //if int is unique, collections returns 0
                intIsUnique = true;
            }
        }

        return randInt;
    }

    static ArrayList<String> filenamesFromMemory = new ArrayList<>();
    static String file = "Output Files/fileLedger.txt";

    private static void WriteToFile (ArrayList<Integer> arrayList) {
        readFilenamesFromMemory();
        while (isRunning) {
            System.out.print("""
                    
                    --Write to File--
                    Now that you have a list of numbers, what do you want to do with those numbers?\s
                    1. Print the numbers\s
                    2. Write the numbers to Excel\s
                    3. Write the numbers to a file\s
                    4. Nothing. I've had my fun\s
                    """);
            int choose = validateNumber();

            switch (choose) {
                case 4 -> {
                    System.out.print("Cool. See you next time!\n\n");
                    isRunning = false;
                }
                case 1 -> printArray(arrayList);
                case 2 -> {
                    String filename = createFileName(".xlsx");
                    writeToExcel(arrayList, filename);
                }
                case 3 -> {
                    String filename = createFileName(".txt");
                    writeToFile(arrayList, filename);
                }
                default -> {
                }
            }
        }
        scanner.close();
    }
    private static String createFileName (String fileExtension) {
        String filename;
        while (true) {
            filename = formatFileName(fileExtension);
            if (isFileUnique(filename)) {
                addFilenameToMemory(filename);
                return filename;
            } else {
                System.out.print("Another file shares this file name. Please write a new file name\n");
            }
        }
    }

    private static boolean isFileUnique (String filename) {
        Collections.sort(filenamesFromMemory);
        int isFileUnique = Collections.binarySearch(filenamesFromMemory, filename);
        return isFileUnique < 0;
    }

    private static void readFilenamesFromMemory () {
        String oldFileNames;
        try (BufferedReader bfReader = new BufferedReader(new FileReader(file))) {
            while ( (oldFileNames = bfReader.readLine()) != null ) {
                filenamesFromMemory.add(oldFileNames);
                }
        } catch (IOException e) {
            System.out.print("Error: read from line failed " + e.getMessage());
        }
        filenamesFromMemory.add("List of Files:");
    }

    private static void addFilenameToMemory (String filename) {
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            filename += "\n";//adds a new line to the filename
            fileWriter.write(filename);
            fileWriter.close();
        } catch (IOException | NullPointerException e) {
            System.err.print("Error: Write to file failed - " + e.getMessage());
        }
        filenamesFromMemory.add(filename);
    }

    private static String formatFileName(String fileExtension) {
        System.out.print("What should the file be named?");
        String userFileName;
        while (true) {
            userFileName = scanner.nextLine();
            if (userFileName != null) {
                break;
            }
            System.out.print("The filename cannot be empty. Please try again\n");
        }
        return ("Output Files/" + userFileName + fileExtension);

    }

    private static void printArray(ArrayList<Integer> arrayList) {
        System.out.print("Alright, here is your array. ");
        if (arrayList.isEmpty()) {
            System.out.print("\nNULL (the array is empty)");
        } else if (arrayList.size() > MAX_PRINT_SIZE) {
            int i = 0;
            StringBuilder arrayString = new StringBuilder();
            System.out.printf("Due to size limitations, the first %d array integers will be printed.\n", MAX_PRINT_SIZE);
            while (i <= MAX_PRINT_SIZE) {
                arrayString.append("[").append(arrayList.get(i)).append("], ");
                i++;
            }
            System.out.print(arrayString);
        }
    }
    private static void writeToFile(ArrayList<Integer> arrayList, String filename) {
        try {
            FileWriter newFile = new FileWriter(filename); //opens file
            newFile.write(arrayList.toString()); //writes array to file
            newFile.close(); //closes file
            System.out.print("File has been written too");
        } catch (IOException e) {
            System.out.print("Error: write to file failed - " + e.getMessage());
        }
    }
    private static void writeToExcel(ArrayList<Integer> arrayList, String filename) {
        //creating XSSF workbook object
        XSSFWorkbook workbook = new XSSFWorkbook();
        //creating spreadsheet object
        XSSFSheet sheet = workbook.createSheet();

        //creating row, cell, cellID and rowID for spreadsheet
        int rowID = 0;
        XSSFRow row;

        int cellID = 0;
        XSSFCell cell;

        row = sheet.createRow(0);
        cell = row.createCell(cellID);
        cell.setCellValue("Random Numbers");

        for (int integer: arrayList) {
            row = sheet.createRow(++rowID);
            cell = row.createCell(cellID);
            cell.setCellValue(integer);
        }

        try {
            FileOutputStream output = new FileOutputStream(filename);
            workbook.write(output);
            output.close();

            //sends message to user
            System.out.printf("%s has been written successfully.", filename);
        } catch (Exception e) {
            System.out.print("The file is not found.");
            e.printStackTrace();
        }
    }
}