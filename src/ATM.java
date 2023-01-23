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
            System.out.print("Enter a PIN: ");
            pin = scan.nextLine();
        }

        System.out.print("Enter your name: ");
        String name = scan.nextLine();

        c = new Customer(pin, name);


        System.out.println("Login: ");
        while (!(authenticate()));
        mainMenu();
    }

    private boolean authenticate() {
        System.out.print("Enter PIN: ");
        String pin = scan.nextLine();
        if (c.authenticate(pin)) {
            return true;
        }
        System.out.println("Incorrect Pin");
        return false;
    }


    private void mainMenu() {
        System.out.println("1: Withdraw money");
        System.out.println("2: Deposit money");
        System.out.println("3: Transfer money between accounts");
        System.out.println("4: Get account balences");
        System.out.println("5: Change PIN");
        System.out.println("6: Exit");
        System.out.print("Enter your choice: ");
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
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }



    }



    private boolean validAmount(int amount)
    {
        if (amount < 0) {
            return false;
        }
        if ((amount % 5) != 0) {
            return false;
        }
        return true;
    }




    public void transfer() {

        boolean valid = false;

        System.out.print("Transfer FROM: " + c.getAccountsString()+": ");
        int account = scan.nextInt();

        System.out.print("Transfer TO: " + c.getAccountsString()+": ");
        int accountTo = scan.nextInt();
        Account acc2 = c.getAccounts()[accountTo];

        while (!valid) {
            System.out.print("Enter amount to transfer; -1 to cancel: ");
            double amount = scan.nextDouble();

            if (amount==-1) {
                valid = true;
                continue;
            }

            c.getAccounts()[account].transfer(amount, acc2);
            valid = true;
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

    }


    private void deposit() {
        boolean valid = false;
        System.out.print("Which account would you like to deposit to: " + c.getAccountsString()+": ");
        int account = scan.nextInt();
        while (!valid) {
            System.out.print("Enter amount to deposit; -1 to cancel: ");
            double amount = scan.nextDouble();

            if (amount==-1) {
                valid = true;
                continue;
            }

            if (c.getAccounts()[account].deposit(amount)) {
                System.out.println("Deposit successful");
                valid = true;
            } else {
                System.out.println("Deposit failed");
            }
        }
    }
    private void withdraw() {
        boolean valid = false;
        System.out.print("Which account would you like to withdraw from: " + c.getAccountsString()+": ");
        int account = scan.nextInt();
        while (!valid) {
            System.out.print("Enter amount to withdraw; -1 to cancel: ");
            int amount = scan.nextInt();

            if (amount==-1) {
                valid = true;
                continue;
            }

            if (!(validAmount(amount))) {
                System.out.println("Amount must be multiple of 5");
                continue;
            }

            if (c.getAccounts()[account].withdraw(amount)) {
                System.out.println("Withdraw successful");
                valid = true;

            } else {
                System.out.println("Withdraw failed; Max withdraw is: "+c.getAccounts()[account].getBalance());
            }
        }
    }


}



