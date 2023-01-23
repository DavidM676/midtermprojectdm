public class Account {
     public static enum types {
        checking,
        saving;
    }


    private double balance = 0;

    private types acc;

    public Account(types type) {
        acc = type;
    }

    public boolean transfer(double amount, Account acc2) {
        this.withdraw(amount);
        acc2.deposit(amount);
        return true;
    }

    public boolean withdraw(double amount) {
        if (!(amount> balance)) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public boolean withdraw(int amount) {
        if (!(amount> balance)) {
            balance -= amount;
            return true;
        }
        return false;
    }


    public boolean deposit(double amount) {
        balance += amount;
        return true;
    }

    public double getBalance() {
        return balance;
    }

    public types getType() {
        return acc;
    }


}