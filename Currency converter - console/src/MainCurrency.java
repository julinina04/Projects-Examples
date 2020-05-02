import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainCurrency {
    static Scanner in = new Scanner(System.in);
    static ArrayList<String> convertResultsList;
    final static int DOLLAR_TO_SHEKEL = 1;
    final static int SHEKEL_TO_DOLLAR = 2;
    final static char EXIT = 'N';
    final static char CONTINUE = 'Y';
    final static String CONVERTER_FILES_FOLDER = "C:\\Users\\julia\\CurrencyConverter";

    // Main program method
    public static void main(String[] args) {
        currencyConverterOpen();
        do
            firstScreen();
        while (getAnswer() == CONTINUE);
        fourScreen();
    }

    // actions on program opening
    static void currencyConverterOpen(){
        System.out.println("Welcome to currency converter");
        convertResultsList = new ArrayList<>();
    }

    // takes user input and perform convert currency until entering "stop" value
    static void firstScreen(){
        int choice;
        double amount;
        choice = getChoice();
        amount = secondScreen();
        thirdScreen(choice,amount);
    }

    // return user choice convert mode (NIS -> $ or $ -> NIS)
    static int getChoice(){
        int userChoice = 0;

        System.out.println("Please choose an option(1/2):");
        System.out.println("1. Dollars to Shekels");
        System.out.println("2. Shekels to Dollars");

        while (userChoice == 0){
            try {
                userChoice = in.nextInt();
            }
            catch (InputMismatchException e){
                in.nextLine();
            }
            finally {
                if (!((userChoice == DOLLAR_TO_SHEKEL) || (userChoice == SHEKEL_TO_DOLLAR))) {
                    userChoice = 0;
                    System.out.println("Invalid Choice, please try again");
                }
            }
        }
        return userChoice;
    }

    // return entered amount to convert
    static double secondScreen(){
        System.out.println("Please enter an amount to convert");
        double userAmount = 0.0;
        try {
            userAmount = in.nextDouble();
        }
        catch (InputMismatchException e){
            in.nextLine();
            System.out.println("Illegal input, calculate amount = 0.0");
        }
        return userAmount;
    }

    // calculate convert on entered amount + choice type and add results to ResultsListArray
    static void thirdScreen(int calcChoice, double calcAmount){
        String tempString;

        if (calcChoice == DOLLAR_TO_SHEKEL){
            Coin k = new USD();
            tempString = calcAmount + " $ converted to " + calcAmount*k.getValue() + " shekel";
            System.out.println(tempString);
            convertResultsList.add(tempString);
        }
        else if (calcChoice == SHEKEL_TO_DOLLAR){
            Coin k = new ILS();
            tempString = calcAmount + " shekel converted to " + calcAmount*k.getValue() + " $";
            System.out.println(tempString);
            convertResultsList.add(tempString);
        }
    }

    // return user answer/choice to continue/stop program running
    static char getAnswer() {
        String Answer = "";

        while (Answer.equals("")) {
            try {
                System.out.println("To continue enter 'Y', to exit enter 'N' : ");
                Answer = in.next();
            } finally {
                if (Answer.equalsIgnoreCase(String.valueOf(CONTINUE))) {
                    return CONTINUE;
                } else if (Answer.equalsIgnoreCase(String.valueOf(EXIT))) {
                    return EXIT;
                } else {
                    Answer = "";
                    System.out.println("Illegal input, try again...");
                }
            }
        }
        return ' ';
    }

    // perform actions on program closing
    static void fourScreen(){
        System.out.println("Thanks for using our currency converter");
        for (String str:convertResultsList
             ) {
            System.out.println(str);
        }
        in.close();
        saveToFileConverterActions();
    }

    // return file name to save log for program running
    static String getFileName(){
        String retPath = "";

        try {
            Date currTS = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy_hh-mm-ss");

            if (!Files.isDirectory(Paths.get(CONVERTER_FILES_FOLDER))) {
                File createDir = new File(CONVERTER_FILES_FOLDER);
                createDir.mkdir();
            }
            return CONVERTER_FILES_FOLDER + "\\Converter" + formatter.format(currTS) + ".txt";
        }
        catch (SecurityException e ){
            System.out.println("Folder not created: log not saved");
        }
        catch (NullPointerException e){
            System.out.println("Folder not created: log not saved");
        }
        catch (InvalidPathException e){
            System.out.println("InvalidPath: log not saved");
        }
        return retPath;
    }

    // save all performed convert results to txt file
    static void saveToFileConverterActions(){
        try {
            FileWriter lastActionsFile = new FileWriter(getFileName());
            for (String str:convertResultsList
                 ) {
                lastActionsFile.write(str + System.lineSeparator());
            }
            lastActionsFile.close();
        }
        catch (IOException e) {
            System.out.println("Error : log not saved");
        }
    }

 }
