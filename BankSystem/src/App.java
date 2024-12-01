

import java.util.*;

abstract class UserInfo {
    public String name;
    public String password;
    public double balance;
}

class UserAccount extends UserInfo {
    public void setName(String name) {
        this.name = name;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
    public double getBalance() {
        return balance;
    }
}

public class App {

    static HashMap<String, UserAccount> hashMap = new HashMap<>();
    static Scanner scan = new Scanner(System.in);

    // Generate a random 5-digit UserPin
    public static String generateUserPin() {
        int randomNumber = (int) (Math.random() * (99999 - 10000 + 1)) + 10000;
        return String.valueOf(randomNumber);
    }

    public static void SignUp() {
        UserAccount user = new UserAccount();

        System.out.print("\nSet a Name: ");
        scan.nextLine();
        user.setName(scan.nextLine());

        System.out.print("Set a Password: ");
        user.setPassword(scan.nextLine());

        System.out.print("Enter Initial Balance: ");
        try {
            double enteredInitialBalance = scan.nextDouble();
            if (enteredInitialBalance < 1000) {
                System.out.println("\n| Invalid! Our Minimum Initial Balance is 1000");
            } else {
                user.setBalance(enteredInitialBalance);

                String userPin = generateUserPin();
                hashMap.put(userPin, user);

                System.out.println("\n| Hello " + user.getName() + "!, your UserPin is: " + userPin);
                System.out.printf("| Your Current Balance is: %.2f \n",user.getBalance());
            }
        } catch (InputMismatchException e) {
            scan.nextLine();
            System.out.println("\n| Invalid Input! Please enter a numeric value.");
        }
    }

    public static void LogIn() {
        System.out.print("Enter your UserPin: ");
        scan.nextLine();
        String inputUserPin = scan.nextLine();

        if (!hashMap.containsKey(inputUserPin)) {
            System.out.println("\n| Invalid UserPin or Does Not Exist!");
        } else {
            UserAccount user = hashMap.get(inputUserPin);

            System.out.print("Enter Password: ");
            String inputPassword = scan.nextLine();

            if (!user.getPassword().equals(inputPassword)) {
                System.out.println("\nInvalid Password");
            } else {
                System.out.println("\n| Greetings, " + user.getName() + "!");
                System.out.printf("| Current Balance: %.2f",user.getBalance());
                System.out.println("");

                while (true) {
                    System.out.print("\nRUBEN BANK INC"
                            + "\n* Logged in as: " + user.getName()
                            + "\n1. Withdraw"
                            + "\n2. Deposit"
                            + "\n3. Check Balance"
                            + "\n4. LogOut"
                            + "\nChoose: ");
                    try {
                        int command = scan.nextInt();

                        switch (command) {
                            case 1 -> withdraw(user);
                            case 2 -> deposit(user);
                            case 3 -> System.out.printf("\nYour Current Balance is: %.2f\n", user.getBalance());
                            case 4 -> {
                                System.out.println("\nLogged Out!");
                                return;
                            }
                            default -> System.out.println("\nINVALID COMMAND. Please try again.");
                        }
                    } catch (InputMismatchException e) {
                        scan.nextLine();
                        System.out.println("\n| Invalid Input! Please enter a valid command.");
                    }
                }
            }
        }
    }

    public static void withdraw(UserAccount user) {
        System.out.print("Enter amount to withdraw: ");
        try {
            double amount = scan.nextDouble();

            if (amount > 0) {
                if (amount < 100) {
                    System.out.println("\n| Our Minimum Withdrawal is 100");
                } else if (amount > user.getBalance()) {
                    System.out.println("\n| Insufficient Balance!!");
                } else {
                    user.setBalance(user.getBalance() - amount);
                    System.out.printf("\nSuccessfully withdrew %.2f. New balance: %.2f\n", amount, user.getBalance());
                }
            } else {
                System.out.println("\n| Amount should be greater than 0!");
            }
        } catch (InputMismatchException e) {
            scan.nextLine();
            System.out.println("\n| Invalid Input! Please enter a numeric value.");
        }
    }

    public static void deposit(UserAccount user) {
        System.out.print("Enter amount to deposit: ");
        try {
            double amount = scan.nextDouble();

            if (amount > 0) {
                if (amount < 100) {
                    System.out.println("\n| Our Minimum Deposit is 100");
                } else {
                    user.setBalance(user.getBalance() + amount);
                    System.out.printf("\nSuccessfully deposited %.2f. New balance: %.2f\n", amount, user.getBalance());
                }
            } else {
                System.out.println("\n| Amount should be greater than 0!");
            }
        } catch (InputMismatchException e) {
            scan.nextLine();
            System.out.println("\n| Invalid Input! Please enter a numeric value.");
        }
    }

    public static void main(String[] args) {
        int maxAttempts = 4;

        while (maxAttempts > 0) {
            System.out.print(
                  "\nWELCOME TO RUBEN BANK"
                + "\n1. SignUp"
                + "\n2. LogIn"
                + "\n3. Exit"
                + "\nChoose: ");

            try {
                int command = scan.nextInt();

                if (command < 1 || command > 3) {
                    maxAttempts--;
                    System.out.println("\n| Input 1-3 only \n| Attempts left: " + maxAttempts);
                } else {
                    switch (command) {
                        case 1 -> {
                            SignUp();
                            maxAttempts = 4;
                        }
                        case 2 -> {
                            LogIn();
                            maxAttempts = 4;
                        }
                        case 3 -> {
                            System.out.println("\nThank you for using Ruben Bank. Goodbye!");
                            return;
                        }
                    }
                }
            } catch (InputMismatchException e) {
                scan.nextLine();
                maxAttempts--;
                System.out.println("\n| Invalid Input! Attempts left: " + maxAttempts);
            }
        }

        System.out.println("| No valid input after multiple attempts. Exiting the program...");
    }
}
