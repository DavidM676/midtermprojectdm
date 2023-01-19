import java.util.Scanner;


public class ATM {

    private Customer[] users = new Customer[10];
    private Customer c;
    private Scanner scan = new Scanner(System.in);

    public void start() {
        createUser();
    }

    public void createUser() {

        System.out.print("Enter a PIN: ");
        String pin = scan.nextLine();

        System.out.print("Enter your name: ");
        String name = scan.nextLine();

        users[0] = new Customer(pin, name);

        while (!(authenticate()));

        mainMenu();

//        } else {
//            System.out.println("incorrect pin");
//        }
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
        switch (choice) {
            case 1:
                withdraw();
                break;
            case 2:
                deposit();
                break;
            case 3:
                transfer();
                break;
            case 4:
                getAccountBalances();
                break;
            case 5:
                changePIN();
                break;
            case 6:
                System.out.println("Bye!");
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }



    }

    private boolean authenticate() {
        System.out.println("Login: ");
        System.out.print("Enter PIN: ");
        String pin = scan.nextLine();
        for (int i = 0; i<users.length; i++) {
            if (users[i]==null) {
                continue;
            }
            if (users[i].getPin().equals(pin)) {
                c = users[i];
                return true;
            }
        }
        System.out.println("Incorrect Pin");
        return false;
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

    }

    public void getAccountBalances() {

    }

    public void changePIN() {

    }
    private void deposit() {
        boolean valid = false;
        System.out.print("Which account would you like to deposit to: " + c.getAccountsString()+": ");
        int account = scan.nextInt();
        Account.types acc = c.getAccounts()[account].getType();
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
        mainMenu();
    }
    private void withdraw() {
        boolean valid = false;
        System.out.print("Which account would you like to withdraw from: " + c.getAccountsString()+": ");
        int account = scan.nextInt();
        Account.types acc = c.getAccounts()[account].getType();
        while (!valid) {
            System.out.print("Enter amount to withdraw; -1 to cancel: ");
            int amount = scan.nextInt();

            if (amount==-1) {
                valid = true;
                continue;
            }

            if (!(validAmount(amount))) {
                System.out.println("Amount must be multiple of 5");
            }

            if (c.getAccounts()[account].withdraw(amount)) {
                System.out.println("Withdraw successful");
                valid = true;
            } else {
                System.out.println("Withdraw failed; Max withdraw is: "+c.getAccounts()[account].getBalance());
            }
        }
        mainMenu();
    }


}



