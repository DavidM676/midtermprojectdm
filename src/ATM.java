import java.util.Scanner;


public class ATM {


    private Customer c;
    private Scanner scan = new Scanner(System.in);

    public void start() {
        createUser();
    }



    private void createUser() {

        String pin = "";
        while (pin.equals("")) {
            System.out.print(ConsoleColors.BLUE+"Enter a " + ConsoleColors.CYAN_BOLD_BRIGHT+"PIN:" +ConsoleColors.RESET);
            pin = scan.nextLine();
        }

        System.out.print(ConsoleColors.BLUE+"Enter your " + ConsoleColors.CYAN_BOLD_BRIGHT+ "name: "+ConsoleColors.RESET);
        String name = scan.nextLine();

        c = new Customer(pin, name);


        System.out.println("Login: ");
        while (!(authenticate()));
        mainMenu();
    }

    private boolean authenticate() {
        System.out.print(ConsoleColors.BLUE+"Enter " + ConsoleColors.CYAN_BOLD_BRIGHT+"PIN:" +ConsoleColors.RESET);
        String pin = scan.nextLine();
        if (c.authenticate(pin)) {
            return true;
        }
        System.out.println(ConsoleColors.RED+"Incorrect Pin"+ConsoleColors.RESET);
        return false;
    }

    private int selectAcc(String q) {
        System.out.print(q + ConsoleColors.CYAN_BOLD_BRIGHT+c.getAccountsString()+": "+ConsoleColors.RESET);
        int account = scan.nextInt();
        if (account>=c.getAccounts().length || account<0) {
            System.out.println(ConsoleColors.RED+"Invalid choice"+ConsoleColors.RESET);
            return -1;
        }
        return account;
    }
    private void printLine() {
        System.out.println("------------------------------------------------------");
    }


    private void mainMenu() {
        printLine();
        System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT+"1:" +ConsoleColors.RESET+"Withdraw money");
        System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT+"2:" +ConsoleColors.RESET+"Deposit money");
        System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT+"3:" +ConsoleColors.RESET+"Transfer money between accounts");
        System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT+"4:" +ConsoleColors.RESET+"Get account balences");
        System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT+"5:" +ConsoleColors.RESET+"Change PIN");
        System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT+"6:" +ConsoleColors.RESET+"Exit");
        System.out.print(ConsoleColors.BLUE+"Enter your " +ConsoleColors.CYAN_BOLD_BRIGHT+ "choice: "+ConsoleColors.RESET);
        int choice = scan.nextInt();
        scan.nextLine();
        switch (choice) {
            case 1:
                while (!(authenticate()));
                withdraw();
                mainMenu();
                break;
            case 2:
                while (!(authenticate()));
                deposit();
                mainMenu();
                break;
            case 3:
                while (!(authenticate()));
                transfer();
                mainMenu();
                break;
            case 4:
                while (!(authenticate()));
                getAccountBalances();
                mainMenu();
                break;
            case 5:
                while (!(authenticate()));
                changePIN();
                mainMenu();
                break;
            case 6:
                System.out.println("bye "+c.getName());
                break;
            default:
                System.out.println(ConsoleColors.RED+"Invalid choice"+ConsoleColors.RESET);
                mainMenu();
                break;
        }



    }

    private void getAccountBalances() {
        printLine();
        System.out.print(ConsoleColors.GREEN_BOLD_BRIGHT);
        Account[] accs = c.getAccounts();
        for (Account acc: accs) {
            System.out.println(acc.getType().toString()+": $"+acc.getBalance());
        }
        System.out.print(ConsoleColors.RESET);
    }

    private void changePIN() {
        printLine();
        String pin = "";
        while (pin.equals("")) {
            System.out.print(ConsoleColors.BLUE+"Enter a new " + ConsoleColors.CYAN_BOLD_BRIGHT+"PIN:" +ConsoleColors.RESET);
            pin = scan.nextLine();

            System.out.print(ConsoleColors.BLUE+"Repeat new " + ConsoleColors.CYAN_BOLD_BRIGHT+"PIN:" +ConsoleColors.RESET);
            if (!(pin.equals(scan.nextLine()))) {
                System.out.println(ConsoleColors.RED+"Pins do not match"+ConsoleColors.RESET);
                pin = "";
            }
        }

        c.setPin(pin);
        System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT+"Changed Pin"+ConsoleColors.RESET);

    }


    private void transfer() {
        printLine();
        boolean valid = false;
        int account = -1;
        int accountTo = -1;

        while (account == -1) {
            account = selectAcc(ConsoleColors.BLUE+"Transfer FROM: "+ConsoleColors.RESET);
        };
        Account acc1 = c.getAccounts()[account];

        while (accountTo == -1) {
            accountTo = selectAcc(ConsoleColors.BLUE+"Transfer TO: "+ConsoleColors.RESET);
        }
        Account acc2 = c.getAccounts()[accountTo];

        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT+acc1.getType().toString()+" Current balance: $"+acc1.getBalance()+ConsoleColors.RESET);

        double amount=0;
        int tID;

        while (!valid) {
            System.out.print(ConsoleColors.BLUE+"Enter amount to transfer; -1 to cancel: "+ConsoleColors.RESET);
            amount = scan.nextDouble();
            scan.nextLine();

            if (amount<0) {
                System.out.println(ConsoleColors.RED+"Transaction canceled"+ConsoleColors.RESET);
                valid = true;
                continue;
            }

            System.out.print(ConsoleColors.BLUE+"Transfer "+ConsoleColors.GREEN_BOLD_BRIGHT+"$"+amount+ConsoleColors.BLUE+" from " + ConsoleColors.CYAN_BOLD_BRIGHT+ acc1.getType().toString() + " to " + acc2.getType().toString()+"(y/n): "+ConsoleColors.RESET);
            if (scan.nextLine().equals("y")) {
                if (acc1.transfer(amount, acc2)) {
                    tID = c.applyChanges(acc1, acc2);
                    valid = true;

                    System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT+"Transaction " +ConsoleColors.CYAN_BOLD_BRIGHT+ tID + ConsoleColors.BLUE+ ": Transferred "+ConsoleColors.GREEN_BOLD_BRIGHT+ "$" + amount +ConsoleColors.BLUE+ " from " +ConsoleColors.CYAN_BOLD_BRIGHT+ acc1.getType().toString() + " to " + acc2.getType().toString()+ConsoleColors.RESET);
                    System.out.print(ConsoleColors.GREEN_BOLD_BRIGHT);
                    System.out.println("Current balances: ");
                    System.out.println(acc1.getType().toString()+": $"+acc1.getBalance());
                    System.out.println(acc2.getType().toString()+": $"+acc2.getBalance());
                    System.out.print(ConsoleColors.RESET);

                } else {
                    System.out.println(ConsoleColors.RED+"Transaction failed; Max withdraw is: "+c.getAccounts()[account].getBalance()+ConsoleColors.RESET);
                }
            } else {
                valid = true;
                System.out.println(ConsoleColors.RED+"Transaction canceled"+ConsoleColors.RESET);
            }
        }

        }




    private void deposit() {
        printLine();
        boolean valid = false;
        int account = -1;

        while (account == -1) {
            account = selectAcc(ConsoleColors.BLUE+"Which account would you like to deposit to: "+ConsoleColors.RESET);
        };

        Account acc = c.getAccounts()[account];

        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT+acc.getType().toString()+" Current balance: $"+acc.getBalance()+ConsoleColors.RESET);

        double amount = 0;
        int tID;

        while (!valid) {
            System.out.print(ConsoleColors.BLUE +"Enter amount to deposit; -1 to cancel: "+ConsoleColors.RESET);
            amount = scan.nextDouble();
            scan.nextLine();

            if (amount<0) {
                System.out.println(ConsoleColors.RED+"Transaction canceled"+ConsoleColors.RESET);
                valid  = true;
                continue;
            }

            System.out.print(ConsoleColors.BLUE+"Deposit "+ConsoleColors.GREEN_BOLD_BRIGHT+"$"+amount+ConsoleColors.BLUE+" to " + ConsoleColors.CYAN_BOLD_BRIGHT+acc.getType().toString() + "(y/n): "+ConsoleColors.RESET);
            if (scan.nextLine().equals("y")) {
                if (acc.deposit(amount)) {
                    tID = c.applyChanges(acc);
                    System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT+"Transaction " +ConsoleColors.CYAN_BOLD_BRIGHT+ tID + ConsoleColors.BLUE+": Deposited "+ConsoleColors.GREEN_BOLD_BRIGHT+"$"+amount+ConsoleColors.BLUE+" to "+ConsoleColors.CYAN_BOLD_BRIGHT+acc.getType().toString()+ConsoleColors.GREEN_BOLD_BRIGHT+"; Current balance: $"+acc.getBalance()+ConsoleColors.RESET);
                    valid = true;
                } else {
                    System.out.println(ConsoleColors.RED+"Transaction failed"+ConsoleColors.RESET);
                }
            } else {
                valid = true;
                System.out.println(ConsoleColors.RED+"Transaction canceled"+ConsoleColors.RESET);
            }
        }

    }
    private void withdraw() {
        printLine();
        boolean valid = false;
        int account = -1;

        while (account == -1) {
            account = selectAcc(ConsoleColors.BLUE + "Which account would you like to withdraw from: "+ConsoleColors.RESET);
        };

        Account acc = c.getAccounts()[account];

        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + acc.getType().toString()+" Current balance: $"+acc.getBalance() + ConsoleColors.RESET);


        int inp = 0;
        int tID;

        System.out.println("Select 5's and 20's");
        while (!valid) {
            int amount = 0;

            System.out.print(ConsoleColors.BLUE + "Enter amount of 5 dollar bills to withdraw; -1 to cancel: "+ConsoleColors.RESET);

            inp = scan.nextInt();

            scan.nextLine();

            if (inp < 0) {
                valid = true;
                System.out.println(ConsoleColors.RED+"Transaction canceled"+ConsoleColors.RESET);
                continue;
            }
            amount += 5*inp;

            System.out.print(ConsoleColors.BLUE + "Enter amount of 20 dollar bills to withdraw; -1 to cancel: "+ConsoleColors.RESET);

            inp = scan.nextInt();

            scan.nextLine();

            if (inp < 0) {
                valid = true;
                System.out.println(ConsoleColors.RED+"Transaction canceled"+ConsoleColors.RESET);
                continue;
            }
            amount += 20*inp;

            System.out.print(ConsoleColors.BLUE + "Withdraw " +ConsoleColors.GREEN_BOLD_BRIGHT+"$"+amount+ConsoleColors.BLUE + " from " + ConsoleColors.CYAN_BOLD_BRIGHT+acc.getType().toString() + "(y/n): "+ConsoleColors.RESET);
            if (scan.nextLine().equals("y")) {
                if (acc.withdraw(amount)) {
                    tID = c.applyChanges(acc);
                    System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT+"Transaction " +ConsoleColors.CYAN_BOLD_BRIGHT+ tID + ConsoleColors.BLUE+": Withdrew "+ConsoleColors.GREEN_BOLD_BRIGHT+"$"+amount+ConsoleColors.BLUE+" from "+ConsoleColors.CYAN_BOLD_BRIGHT+acc.getType().toString()+ConsoleColors.GREEN_BOLD_BRIGHT+"; Current balance: $"+acc.getBalance()+ConsoleColors.RESET);
                    valid = true;

                } else {
                    System.out.println(ConsoleColors.RED+"Withdraw failed; Max withdraw is: "+c.getAccounts()[account].getBalance()+ConsoleColors.RESET);
                }
            } else {
                valid = true;
                System.out.println(ConsoleColors.RED+"Transaction canceled"+ConsoleColors.RESET);
            }

    }
    }




}



