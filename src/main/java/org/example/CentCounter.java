package org.example;

import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CentCounter {
    static Scanner scanner = new Scanner(System.in);
    public static void main (String[] args) {
        boolean hasRunOnce = false;
        String centAmount;
        String dollarAmount;
        while (true) {
            System.out.print("How much change do you have? (Format $dd.cc)\n");
            if (hasRunOnce) {
                System.out.print("...or do you want to exit (input 9)\n");
                if (scanner.hasNext("9")) {
                    break;
                }
            }
            String userInput = dollarFormat(scanner.nextLine());

            if (!Objects.equals(userInput, "-1")) {
                String[]totalAmountArr = userInput.split("\\.");
                dollarAmount = dollarCounter(totalAmountArr[0]);
                System.out.print (dollarAmount);
                if (totalAmountArr.length == 1) {
                    centAmount = centCounter("00");
                } else {
                    centAmount = centCounter(totalAmountArr[1]);
                }
                System.out.print (centAmount);
            } else {
                System.out.print("The number input is false. Please try again\n");
            }
            hasRunOnce = true;
        }
    }
    private static String dollarFormat(String userInput) {
        Pattern pattern = Pattern.compile("(\\$[0-9]+(.[0-9]+)?)");
        //bug i guess "/" passes the test
        Matcher matcher = pattern.matcher(userInput);
        if (matcher.find()) {
            return userInput.substring(1);
        } else {
            return "-1";
        }
    }

    private static String centCounter(String cents) {
        //Types of cents: 25, 10, 5, 1
        int[] centTypes = {25,10,5,1 }; //Types of cents
        String[] centTypesName = {"quarter(s)", "dime(s)", "nickle(s)", "pennies"};
        return costStringFormatter(centTypes, centTypesName, cents);
    }

    private static String dollarCounter(String dollar) {
        //Types of dollars: 100, 50, 10, 5, 2, 1
        int[] dollarTypes = {100,50,10,5,2,1}; //Types of cents
        String[] dollarTypesName = {"$100 Dollar Bill(s)", "$50 Dollar Bill(s)", "$10 Dollar Bill(s)", "$5 Dollar Bill(s)", "$2 Dollar Bill(s)", "$1 Dollar Bill(s)"};
        return costStringFormatter(dollarTypes, dollarTypesName, dollar);
    }
    private static String costStringFormatter(int[] countTypes, String[] countTypesNames, String cost) {
        String costApprox = String.valueOf(costApproximate(cost, countTypes));
        if (Double.parseDouble(costApprox) >= 1999999) {
            return "Null\n";
        }
        //*test print*/System.out.print("Cost Approx Test: " + costApprox + "\n");
        StringBuilder costTxt = new StringBuilder("You need: ");
        for (int i = 0; i < countTypes.length; i++) {
            char costCharValue = costApprox.charAt(i+1);
            if (costCharValue != '0') {
                if (i == countTypes.length - 1) {
                    costTxt.append("and ");
                }
                costTxt.append(costCharValue).append(" ").append(countTypesNames[i]);

                if (i != countTypes.length - 1) {
                    costTxt.append(", ");
                }
            }

            if (i == countTypes.length-1) {
                if (costTxt.charAt(costTxt.length()-1) == ',') {
                    costTxt.deleteCharAt(costTxt.length()-1);
                }
                costTxt.append(".\n");
            }

        }

        return String.valueOf(costTxt);
    }
    private static double costApproximate(String costTotal, int[] costTypes) {
        int cost = Integer.parseInt(costTotal);
        double[] costValue;
        double encodeTotal = Math.pow(10,costTypes.length );
        int digitValue = costTypes.length;
        for (int costType : costTypes) {
            costValue = costEncoder(cost, costType, digitValue);
            --digitValue;
            cost = (int) costValue[1];
            encodeTotal += costValue[0];
        }

        return encodeTotal;

    }

    private static double[] costEncoder (int cost, int modifier, int digitPlace) {
        boolean isTrue = false;
        double encodeValue = 0;
        while (!isTrue) {
            if (cost >= modifier && encodeValue <= 8) {
                cost -= modifier;
                encodeValue++;
            } else {
                isTrue = true;
            }
        }
        encodeValue *= Math.pow(10, (digitPlace-1) );
        return new double[]{encodeValue, cost};

    }
}
