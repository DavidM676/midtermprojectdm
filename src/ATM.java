import java.util.Scanner;


public class ATM {


    private Customer c;
    private Scanner scan = new Scanner(System.in);

    public void start() {
        createUser();
    }



    public void createUser() {

        String pin = "";
        while (pin.equals("")) {
            System.out.print(ConsoleColors.BLUE+"Enter a PIN: "+ConsoleColors.RESET);
            pin = scan.nextLine();
        }

        System.out.print(ConsoleColors.BLUE+"Enter your name: "+ConsoleColors.RESET);
        String name = scan.nextLine();

        c = new Customer(pin, name);


        System.out.println("Login: ");
        while (!(authenticate()));
        mainMenu();
    }

    private boolean authenticate() {
        System.out.print(ConsoleColors.BLUE+"Enter PIN: "+ConsoleColors.RESET);
        String pin = scan.nextLine();
        if (c.authenticate(pin)) {
            return true;
        }
        System.out.println(ConsoleColors.RED+"Incorrect Pin"+ConsoleColors.RESET);
        return false;
    }



    private void mainMenu() {
        ConsoleThings.clearScreen();
        System.out.println("1: Withdraw money");
        System.out.println("2: Deposit money");
        System.out.println("3: Transfer money between accounts");
        System.out.println("4: Get account balences");
        System.out.println("5: Change PIN");
        System.out.println("6: Exit");
        System.out.print(ConsoleColors.BLUE+"Enter your choice: "+ConsoleColors.RESET);
        int choice = scan.nextInt();
        scan.nextLine();
        switch (choice) {
            case 1:
                while (!(authenticate()));
                withdraw();
                c.logout();
                mainMenu();
                break;
            case 2:
                while (!(authenticate()));
                deposit();
                c.logout();
                mainMenu();
                break;
            case 3:
                while (!(authenticate()));
                transfer();
                c.logout();
                mainMenu();
                break;
            case 4:
                while (!(authenticate()));
                getAccountBalances();
                c.logout();
                mainMenu();
                break;
            case 5:
                while (!(authenticate()));
                changePIN();
                c.logout();
                mainMenu();
                break;
            case 6:
                System.out.println("bye");
                break;
            default:
                System.out.println(ConsoleColors.RED+"Invalid choice"+ConsoleColors.RESET);
                mainMenu();
                break;
        }



    }



    public void transfer() {
        boolean cancel = false;
        boolean valid = false;

        System.out.print("Transfer FROM: " + c.getAccountsString()+": ");
        int account = scan.nextInt();
        Account acc1 = c.getAccounts()[account];

        System.out.print("Transfer TO: " + c.getAccountsString()+": ");
        int accountTo = scan.nextInt();
        Account acc2 = c.getAccounts()[accountTo];

        double amount=0;


        while (!valid && !cancel) {
            System.out.print("Enter amount to transfer; -1 to cancel: ");
            amount = scan.nextDouble();

            if (amount==-1) {
                cancel = true;
                continue;
            }

            acc1.transfer(amount, acc2);
            valid = true;
        }
        if (!cancel) {
            System.out.println("Transaction " + c.getTransactionID() + ": Transferred $" + amount + " from " + acc1.getType().toString() + " to " + acc2.getType().toString());
            System.out.println("Current balances: ");
            System.out.println(acc1.getType().toString()+": $"+acc1.getBalance());
            System.out.println(acc2.getType().toString()+": $"+acc2.getBalance());
        }
        }

    public void getAccountBalances() {
        Account[] accs = c.getAccounts();
        for (Account acc: accs) {
            System.out.println(acc.getType().toString()+": $"+acc.getBalance());
        }
    }

    public void changePIN() {
        String pin = "";
        while (pin.equals("")) {
            System.out.print("Enter a new PIN: ");
            pin = scan.nextLine();

            System.out.print("Repeat new PIN: ");
            if (!(pin.equals(scan.nextLine()))) {
                System.out.println("Pins do not match");
                pin = "";
            }
        }

        c.setPin(pin);
        System.out.println("Changed Pin");

    }


    private void deposit() {
        boolean valid = false;
        boolean cancel = false;

        System.out.print("Which account would you like to deposit to: " + c.getAccountsString()+": ");
        int account = scan.nextInt();
        Account acc = c.getAccounts()[account];

        double amount = 0;
        while (!valid && !cancel) {
            System.out.print("Enter amount to deposit; -1 to cancel: ");
            amount = scan.nextDouble();

            if (amount==-1) {
                cancel = true;
                continue;
            }

            if (acc.deposit(amount)) {
                System.out.println("Deposit successful");
                valid = true;
            } else {
                System.out.println("Deposit failed");
            }
        }
        if (!cancel) {
            System.out.println("Transaction " + c.getTransactionID() + ": Deposited $"+amount+" to "+acc.getType().toString()+"\n"+"Current balance: $"+acc.getBalance());
        }
    }
    private void withdraw() {
        boolean valid = false;
        boolean cancel = false;

        System.out.print("Which account would you like to withdraw from: " + c.getAccountsString()+": ");
        int account = scan.nextInt();
        Account acc = c.getAccounts()[account];

        int amount = 0;

        System.out.println("Select 5's and 20's");
        while (!valid && !cancel) {
            System.out.print("Enter amount of 5 dollar bills to withdraw; -1 to cancel: ");

            if (amount < 0) {
                cancel = true;
                continue;
            }

            amount += 5*scan.nextInt();

            System.out.print("Enter amount of 20 dollar bills to withdraw; -1 to cancel: ");

            if (amount < 0) {
                cancel = true;
                continue;
            }

            amount += 20*scan.nextInt();



            if (acc.withdraw(amount)) {
                valid = true;

            } else {
                System.out.println("Withdraw failed; Max withdraw is: "+c.getAccounts()[account].getBalance());
            }
        }
        if (!cancel) {
            System.out.println("Transaction " + c.getTransactionID() + ": Withdrew $"+amount+" to "+acc.getType().toString()+"\n"+"Current balance: $"+acc.getBalance());

        }
    }


}



