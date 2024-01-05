import java.util.ArrayList;
import java.util.Scanner;

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

class BankAccount {
    private String accountNumber;
    private String ownerName;
    private double balance;
    private ArrayList<Transaction> transactions;

    public BankAccount(String accountNumber, String ownerName) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactions.add(new Transaction("Deposit", amount));
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactions.add(new Transaction("Withdrawal", amount));
        } else {
            System.out.println("Insufficient funds!");
        }
    }

    public void transfer(BankAccount recipient, double amount) {
        if (balance >= amount) {
            withdraw(amount);
            recipient.deposit(amount);
            transactions.add(new Transaction("Transfer to " + recipient.getOwnerName(), amount));
        } else {
            System.out.println("Insufficient funds for transfer!");
        }
    }

    public void printTransactionHistory() {
        System.out.println("\nTransaction History for Account " + accountNumber + ":");
        for (Transaction transaction : transactions) {
            System.out.println(transaction.getType() + ": $" + transaction.getAmount());
        }
    }
}

class Bank {
    private ArrayList<BankAccount> accounts;

    public Bank() {
        this.accounts = new ArrayList<>();
    }

    public void createAccount(String accountNumber, String ownerName) {
        BankAccount newAccount = new BankAccount(accountNumber, ownerName);
        accounts.add(newAccount);
        System.out.println("Account created successfully for " + ownerName + " with account number " + accountNumber);
    }

    public BankAccount findAccount(String accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null; // Account not found
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();

        while (true) {
            System.out.println("\nWelcome to the Online Bank!");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. View Transaction History");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter account number: ");
                    String accNumber = scanner.nextLine();
                    System.out.print("Enter owner name: ");
                    String ownerName = scanner.nextLine();
                    bank.createAccount(accNumber, ownerName);
                    break;

                case 2:
                    System.out.print("Enter account number: ");
                    String depositAccNumber = scanner.nextLine();
                    BankAccount depositAccount = bank.findAccount(depositAccNumber);
                    if (depositAccount != null) {
                        System.out.print("Enter deposit amount: $");
                        double depositAmount = scanner.nextDouble();
                        depositAccount.deposit(depositAmount);
                        System.out.println("Deposit successful!");
                    } else {
                        System.out.println("Account not found!");
                    }
                    break;

                case 3:
                    System.out.print("Enter account number: ");
                    String withdrawAccNumber = scanner.nextLine();
                    BankAccount withdrawAccount = bank.findAccount(withdrawAccNumber);
                    if (withdrawAccount != null) {
                        System.out.print("Enter withdrawal amount: $");
                        double withdrawAmount = scanner.nextDouble();
                        withdrawAccount.withdraw(withdrawAmount);
                    } else {
                        System.out.println("Account not found!");
                    }
                    break;

                case 4:
                    System.out.print("Enter sender account number: ");
                    String senderAccNumber = scanner.nextLine();
                    BankAccount senderAccount = bank.findAccount(senderAccNumber);

                    System.out.print("Enter recipient account number: ");
                    String recipientAccNumber = scanner.nextLine();
                    BankAccount recipientAccount = bank.findAccount(recipientAccNumber);

                    if (senderAccount != null && recipientAccount != null) {
                        System.out.print("Enter transfer amount: $");
                        double transferAmount = scanner.nextDouble();
                        senderAccount.transfer(recipientAccount, transferAmount);
                    } else {
                        System.out.println("One or both accounts not found!");
                    }
                    break;

                case 5:
                    System.out.print("Enter account number: ");
                    String historyAccNumber = scanner.nextLine();
                    BankAccount historyAccount = bank.findAccount(historyAccNumber);
                    if (historyAccount != null) {
                        historyAccount.printTransactionHistory();
                    } else {
                        System.out.println("Account not found!");
                    }
                    break;

                case 6:
                    System.out.println("Exiting the Online Bank. Goodbye!");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
