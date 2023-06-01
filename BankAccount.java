//Ashab Naveed
//Henry Wise Wood High School
//2022-2023 Semester 2
//Bank Account GUI

import java.io.*;
import java.nio.file.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    // Define a decimal format to format numbers with two decimal places
    private static final DecimalFormat df = new DecimalFormat("0.00");
    // Specify the directory where data files will be stored
    private static final String DATA_DIRECTORY = "data/";
    // Define a date-time formatter to format LocalDateTime objects
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Instance variables
    private String accountNumber;
    private double balance = 0;
    private double withdrawalFee;
    private double annualInterestRate;

    // ArrayList to store transaction history
    private ArrayList<Transaction> transactionList = new ArrayList<>();

    // Constructor to initialize a bank account with just the account number
    public BankAccount(String accountNum) {
        accountNumber = accountNum;
        // Load the balance and transaction history from files
        loadBalance();
        loadTransactions();
    }

    // Constructor to initialize a bank account with the account number and initial balance
    public BankAccount(String accountNum, double initialBalance) {
        accountNumber = accountNum;
        balance = initialBalance;
        // Save the initial balance to a file
        saveBalance();
    }

    // Constructor to initialize a bank account with the account number, initial balance, withdrawal fee, and annual interest rate
    public BankAccount(String accountNum, double initialBalance, double fee, double annualInterest) {
        accountNumber = accountNum;
        balance = initialBalance;
        withdrawalFee = fee;
        annualInterestRate = annualInterest;
        // Save the initial balance to a file
        saveBalance();
    }

    // Getter methods
    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public double getWithdrawalFee() {
        return withdrawalFee;
    }

    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    // Setter methods
    public void setAnnualInterestRate(double rate) {
        annualInterestRate = rate;
    }

    public void setWithdrawalFee(double fee) {
        withdrawalFee = fee;
    }

    // Method to deposit money into the account with the current date and time
    public void deposit(double amount) {
        balance += amount;
        // Create a new Transaction object for the deposit and add it to the transactionList
        Transaction deposit = new Transaction(LocalDateTime.now(), amount, null);
        transactionList.add(deposit);
        // Save the transaction and balance to files
        saveTransaction(deposit);
        saveBalance();
    }

    // Method to deposit money into the account with a specified date and time
    public void deposit(LocalDateTime transactionTime, double amount, String description) {
        balance += amount;
        // Create a new Transaction object for the deposit with the specified transactionTime and description, and add it to the transactionList
        Transaction deposit = new Transaction(transactionTime, amount, description);
        transactionList.add(deposit);
        // Save the transaction and balance to files
        saveTransaction(deposit);
        saveBalance();
    }

    // Method to deposit money into the account with the current date and time and a description
    public void deposit(double amount, String description) {
        balance += amount;
        // Create a new Transaction object for the deposit with the current date and time and the specified description, and add it to the transactionList
        Transaction deposit = new Transaction(LocalDateTime.now(), amount, description);
        transactionList.add(deposit);
        // Save the transaction and balance to files
        saveTransaction(deposit);
        saveBalance();
    }

    // Method to withdraw money from the account with the current date and time
    public void withdraw(double amount) {
        balance -= (amount + withdrawalFee);
        // Create a new Transaction object for the withdrawal and add it to the transactionList
        Transaction withdrawal = new Transaction(LocalDateTime.now(), amount, null);
        transactionList.add(withdrawal);
        // Save the transaction and balance to files
        saveTransaction(withdrawal);
        saveBalance();
    }

    // Method to withdraw money from the account with a specified date and time
    public void withdraw(LocalDateTime transactionTime, double amount, String description) {
        balance -= (amount + withdrawalFee);
        // Create a new Transaction object for the withdrawal with the specified transactionTime and description, and add it to the transactionList
        Transaction withdrawal = new Transaction(transactionTime, amount, description);
        transactionList.add(withdrawal);
        // Save the transaction and balance to files
        saveTransaction(withdrawal);
        saveBalance();
    }

    // Method to withdraw money from the account with the current date and time and a description
    public void withdraw(double amount, String description) {
        balance -= (amount + withdrawalFee);
        // Create a new Transaction object for the withdrawal with the current date and time and the specified description, and add it to the transactionList
        Transaction withdrawal = new Transaction(LocalDateTime.now(), amount, description);
        transactionList.add(withdrawal);
        // Save the transaction and balance to files
        saveTransaction(withdrawal);
        saveBalance();
    }

    // Method to check if the account is overdrawn (balance is negative)
    public boolean isOverDrawn() {
        return balance < 0;
    }

    // Method to get the transaction history
    public ArrayList<Transaction> getTransactionHistory() {
        return transactionList;
    }

    // Method to save a transaction to a file
    public void saveTransaction(Transaction transaction) {
        try {
            // Create a string representation of the transaction
            String transactionString = transaction.getAmount() + ","
                    + transaction.getTimestamp().format(formatter) + ","
                    + transaction.getDescription();

            // Write the transaction string to the account's data file, with a new line separator
            Files.write(Paths.get(DATA_DIRECTORY + accountNumber + ".txt"),
                    (transactionString + System.lineSeparator()).getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load the transaction history from a file
    public void loadTransactions() {
        String filePath = DATA_DIRECTORY + accountNumber + ".txt";
        try {
            // Read all lines from the transaction data file
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                // Split each line into parts using a comma as the delimiter
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    // Parse the amount, transaction time, and description from the parts array
                    double amount = Integer.parseInt(parts[0]);
                    LocalDateTime transactionTime = LocalDateTime.parse(parts[1], formatter);
                    String description = parts[2];
                    // Create a new Transaction object with the parsed values and add it to the transactionList
                    Transaction transaction = new Transaction(transactionTime, amount, description);
                    transactionList.add(transaction);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load the account balance from a file
    public void loadBalance() {
        String filePath = DATA_DIRECTORY + accountNumber + ".balance";
        try {
            // Read the balance string from the balance file
            String balanceString = Files.readString(Paths.get(filePath));
            // Parse the balance string to an integer and assign it to the balance variable
            balance = Integer.parseInt(balanceString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to save the account balance to a file
    public void saveBalance() {
        String filePath = DATA_DIRECTORY + accountNumber + ".balance";
        try {
            // Create a FileWriter to write the balance to the balance file
            FileWriter writer = new FileWriter(filePath);
            // Write the balance as a string to the file
            writer.write(Double.toString(balance));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to get a string representation of the bank account
    public String toString() {
        String res;
        if (balance < 0) {
            res = "BankAccount " + accountNumber + ": ($" + df.format(-1 * balance) + ")";
        } else if (balance > 0) {
            res = "BankAccount " + accountNumber + ": $" + df.format(balance);
        } else {
            res = "BankAccount " + accountNumber + ": $0.00";
        }
        System.out.println(res);
        return res;
    }
}
