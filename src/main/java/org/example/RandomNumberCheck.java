package org.example;

import java.util.*;

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
        System.out.print("What range should the numbers have? Give the upper limit.");
        int arrayListRange = validateNumber();
        System.out.print("Do you want the list sorted? \nType true or false:");
        boolean isSorted = scanner.nextBoolean();
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
        boolean isUnique = scanner.nextBoolean();
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

    private static int validateNumber () {
        boolean numberValid = false; //boolean determines the validity of the number
        int choose = 0; //determines which choice the program goes down
        while (!numberValid) {
            try {
                choose = scanner.nextInt();
                numberValid = true;
            } catch (java.util.InputMismatchException e) {
                scanner.nextLine();
                System.out.print("\nThe input is not correct. Please try again");
            }
        }

        return choose;
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
    private static void WriteToFile (ArrayList<Integer> arrayList) {
        while (isRunning) {
            System.out.print("""
                    \n
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
                case 2 -> printArray(arrayList);
                case 1 -> writeToExcel(arrayList);
                case 3 -> writeToFile(arrayList);
                default -> {
                }
            }
        }

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
    private static void writeToFile(ArrayList<Integer> arrayList) {
        //name the Excel file
        //create a method that formats the info based on the Excel spreadsheet
        //send a message to the user if the file is successfully created
    }
    private static void writeToExcel(ArrayList<Integer> arrayList) {
        //XSSFWorkbook workbook = new XSSFWorkbook();
        //name the Excel file
        //create a method that formats the info based on the Excel spreadsheet
        //send a message to the user if the file is successfully created
    }
}