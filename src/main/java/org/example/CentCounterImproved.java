package org.example;

import java.util.*;

public class CentCounterImproved {
    static String[] coinNames = {"Pennie", "Nickle", "Dime", "Quarter"};
    static double [] coinValues = {0.01, 0.05, 0.10, 0.25};
    static String[] dollarNames = {"1 Dollar Bill", "5 Dollar Bill", "10 Dollar Bill", "20 Dollar Bill", "50 Dollar Bill", "100 Dollar Bill"};
    static double [] dollarValues = {1,5,10,20,50,100};
    static double totalCost;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        List<Cost> costList = new ArrayList<>();
        for (int i = dollarNames.length-1; i >= 0; --i) {
            costList.add(new Cost(dollarValues[i], dollarNames[i], 0));
        }
        for (int i = coinNames.length-1; i >= 0; --i) {
            costList.add(new Cost(coinValues[i], coinNames[i], 0 ));
        }
        while (true) {
            System.out.print("How much change do you have? (Format $dd.cc)\n");
            totalCost = costValidate();
            scanner.nextLine();
            setCostListAmount(costList);
            formatCostListPrint(costList);
        }
    }
    private static double costValidate () {
        while (true) {
            try {
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.print("The input is not valid. Try again.\n");
                scanner.nextLine();
            }
        }
    }

    private static void formatCostListPrint(List<Cost> costList) {
        System.out.printf("Given %1.2f, you will need: ", totalCost);
        for (Cost cost: costList) {
            if (cost.getAmount() > 0) {
                System.out.printf("\n%1.0f %ss", cost.getAmount(), cost.getCostName());
            }
        }
        System.out.print ("\n\n");
    }

    private static void setCostListAmount(List<Cost> costList ) {
        int iterator = 0;
        double tempTotal = totalCost;
        for (Cost cost: costList) {
            while (true) {
                if (tempTotal >= cost.getValue()) {
                    tempTotal = tempTotal - cost.getValue();
                    ++iterator;
                } else {
                    break;
                }
            }
            cost.setAmount(iterator);
            iterator = 0;
        }
    }
}
