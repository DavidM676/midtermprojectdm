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


        if (authenticate()) {
            while (true) {
                mainMenu();
            }

        } else {
            System.out.println("incorrect pin");
        }
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


    public void deposit() {

    }

    public void transfer() {

    }

    public void getAccountBalances() {

    }

    public void changePIN() {

    }

    private void withdraw() {
        System.out.println("Which account would you like to withdraw from: "+c.getAccountsString());
        int account = scan.nextInt();
        Account.types t = c.getAccounts()[account].getType();

        System.out.print("Enter amount to withdraw: ");
        int amount = scan.nextInt();


        if (!(validAmount(amount))) {
            break;
        }


//        if (validAmount(amount)) {
//            if (c.withdraw(amount, acc)) {
//                System.out.println("Withdraw successful");
//            } else {
//                System.out.println("Withdraw failed");
//            }
//        }
    }


}



