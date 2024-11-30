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
    public void setPassword(String password){
        this.password = password;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }
    public String getPassword(){
        return password;
    }
    public double getBalance() {
        return balance;
    }
}

public class Main {
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

        System.out.print("Set a password: ");
        user.setPassword(scan.nextLine());

        System.out.print("Enter Initial Balance: ");
        double enteredInitialBalance = scan.nextDouble();
        if (enteredInitialBalance < 1000){
            System.out.println("\n| Invalid! Our Minimum Initial Balance is 1000");
        } else {
            user.setBalance(enteredInitialBalance);

            String userPin = generateUserPin();
            hashMap.put(userPin, user);

            System.out.println("\n| Hello " + user.getName() + "!, your UserPin is: " + userPin);
            System.out.println("| Your Current Balance is: " + user.getBalance());
        }
    }

    public static void LogIn() {
        System.out.print("Enter your UserPin: ");
        scan.nextLine();
        String inputUserPin = scan.nextLine();

        if (!hashMap.containsKey(inputUserPin)) {
            System.out.println("\n| Invalid UserPin or Did not Exist!");
        } else {
            UserAccount user = hashMap.get(inputUserPin);

            System.out.print("Enter password: ");
            String inputPassword = scan.nextLine();

            if (!user.getPassword().equals(inputPassword)) {
                System.out.println("\nInvalid password");
            } else {
                System.out.println("\n| Greetings, " + user.getName() + "!");
                System.out.println("| Your Current Balance is: " + user.getBalance());
                System.out.println("");

                while (true) {
                    System.out.print("RUBEN BANK INC"
                            + "\n* Logged in as: " + user.getName()
                            + "\n1. Withdraw"
                            + "\n2. Deposit"
                            + "\n3. Check Balance"
                            + "\n4. LogOut"
                            + "\n\nChoose: ");
                    int command = scan.nextInt();

                    switch (command) {
                        case 1 -> withdraw(user);
                        case 2 -> deposit(user);
                        case 3 -> {
                            System.out.println("Your Current Balance is: " + user.getBalance());
                        }
                        case 4 -> {
                            System.out.println("\nLogged Out!");
                            return;
                        }
                        default -> System.out.println("INVALID COMMAND. Please try again.");
                    }
                }
            }
        }
    }

    public static void withdraw(UserAccount user) {
        System.out.print("Enter amount to withdraw: ");
        double amount = scan.nextDouble();

        if (amount <= 0) {
            System.out.println("Invalid amount. Please enter a positive number.");
        } else if (amount > user.getBalance()) {
            System.out.println("Insufficient balance.");
        } else {
            user.setBalance(user.getBalance() - amount);
            System.out.println("Successfully withdrew " + amount + ". New balance: " + user.getBalance());
        }
    }

    public static void deposit(UserAccount user) {
        System.out.print("Enter amount to deposit: ");
        double amount = scan.nextDouble();

        if (amount <= 0) {
            System.out.println("Invalid amount. Please enter a positive number.");
        } else {
            user.setBalance(user.getBalance() + amount);
            System.out.println("Successfully deposited " + amount + ". New balance: " + user.getBalance());
        }
    }

    public static void main(String[] args) {
        int maxAttempts = 4;
        while (maxAttempts > 0) {
            System.out.print("\nWELCOME TO RUBEN BANK"
                    + "\n1. SignUp"
                    + "\n2. LogIn"
                    + "\n3. Exit"
                    + "\n\nChoose: ");

            int command = scan.nextInt();

            switch (command) {
                case 1 -> {
                    SignUp();
                    maxAttempts++;
                }
                case 2 -> {
                    LogIn();
                    maxAttempts++;
                }
                case 3 -> {
                    System.out.println("Thank you for using Ruben Bank. Goodbye!");
                    return;
                }
                default -> {
                    maxAttempts--;
                    System.out.println("\n| INVALID COMMAND. Please try again. ATTEMPT" + maxAttempts);
                }
            }

            if (maxAttempts == 0) {
                System.out.println("\n| No valid input after 3 attempts. Exiting the program.");
                break;
            }
        }
    }
}

