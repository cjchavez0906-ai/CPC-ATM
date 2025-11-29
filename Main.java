import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    static double balance = 0;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String[] usernames = new String[100];
        String[] passwords = new String[100];
        int count = 0;

        while (true) {
            System.out.println("\nWelcome to CPC ATM");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Type number: ");
            int choice = scan.nextInt();
            scan.nextLine();

            if (choice == 1) {
                count = register(scan, usernames, passwords, count);
            } else if (choice == 2) {
                login(scan, usernames, passwords, count);
            } else if (choice == 3) {
                System.out.println("Goodbye.");
                break;
            } else {
                System.out.println("Error.");
            }
        }
    }

    public static int register(Scanner scan, String[] usernames, String[] passwords, int count) {
        System.out.println("Registration");
        System.out.print("Enter username: ");
        String user = scan.nextLine();
        System.out.print("Enter password: ");
        String pass = scan.nextLine();

        for (int i = 0; i < count; i++) {
            if (usernames[i].equals(user)) {
                System.out.println("Username is already in use.");
                return count;
            }
        }

        usernames[count] = user;
        passwords[count] = pass;
        count++;

        System.out.println("Account created successfully.");
        return count;
    }

    public static void login(Scanner scan, String[] usernames, String[] passwords, int count) {
        System.out.println("Account Login");
        String user, pass;
        String cancel = "b";
        int attempts = 0;


        while (true) {
            System.out.println("Enter username: ");
            user = scan.nextLine();
            System.out.println("Enter password: ");
            pass = scan.nextLine();

            for (int i = 0; i < count; i++) {
                if (usernames[i].equals(user) && passwords[i].equals(pass)) {
                    System.out.println("Login successful.");
                    accountType(scan);
                    return;

                }

            }

            System.out.println("Wrong username or password.");
            attempts++;

            if (attempts >= 3) {
                System.out.println("Press B to stop or any key to try again");
                String stop = scan.nextLine();
                if (stop.equalsIgnoreCase(cancel)) {
                    System.out.println("Login Cancelled.");
                    break;
                }
                attempts = 0;
            }
        }
    }

    public static void accountType(Scanner scan) {
        while (true) {
            try {
                System.out.println("\nWhat do you want to access?");
                System.out.println("1 - Deposit");
                System.out.println("2 - Withdraw");
                System.out.println("3 - Balance");
                System.out.println("4 - Logout");
                System.out.println("5 - Transaction History");
                System.out.print("Choice: ");

                int choice = scan.nextInt();
                scan.nextLine();

                if (choice == 1) {
                    System.out.print("Enter deposit amount: ");
                    double deposit = scan.nextDouble();
                    scan.nextLine();
                    balance = balance + deposit;
                    System.out.println("Deposited: " + deposit);
                    writeHistory("Deposited: " + deposit);

                } else if (choice == 2) {
                    System.out.print("Enter withdraw amount: ");
                    double withdraw = scan.nextDouble();
                    scan.nextLine();

                    if (withdraw > balance) {
                        System.out.println("Insufficient balance.");
                    } else {
                        balance = balance - withdraw;
                        System.out.println("Withdrew: " + withdraw);
                        writeHistory("Withdrew: " + withdraw);
                    }

                } else if (choice == 3) {
                    System.out.println("Current Balance: " + balance);
                    writeHistory("Balance: " + balance);

                } else if (choice == 4) {
                    System.out.println("Logged out.");
                    break;
                } else if (choice == 5) {
                    readFile(scan);
                }
            } catch (Exception e) {
                System.out.println("Invalid input.");
                scan.nextLine();
            }

        }
    }

    public static void writeHistory(String text) {
        try {
            FileWriter writer = new FileWriter("TransactionHistory.txt", true);
            writer.write(text + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing history.");
        }
    }
    public static void readFile(Scanner scan) {
        try {
            File file = new File("TransactionHistory.txt");

            if (!file.exists()) {
                System.out.println("No transaction history yet.");
                return;
            }

            Scanner reader = new Scanner(file);
            System.out.println("\n Transaction History ");

            while (reader.hasNextLine()) {
                System.out.println(reader.nextLine());
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Error reading file.");
        }
    }
}