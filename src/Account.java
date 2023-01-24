public class Account {
     public static enum types {
        checking,
        saving;
    }


    private double balance = 0;
    private double changes = 0;
    private types acc;

    public Account(types type) {
        acc = type;
    }

    public void applyChanges() {
        balance = changes;
    }
    public void resetChanges() {
        changes = balance;
    }

    public boolean transfer(double amount, Account acc2) {
        boolean x = this.withdraw(amount);
        if (x) {
            acc2.deposit(amount);
        }
        return x;

    }

    public boolean withdraw(double amount) {
        if (!(amount> changes)) {
            changes -= amount;
            return true;
        }
        return false;
    }

    public boolean withdraw(int amount) {
        if (!(amount> changes)) {
            changes -= amount;
            return true;
        }
        return false;
    }


    public boolean deposit(double amount) {
        if (amount < 0) {
            return false;
        }
        changes += amount;
        return true;
    }

    public double getBalance() {
        return balance;
    }
    public double getChanges() {
        return changes;
    }

    public types getType() {
        return acc;
    }


}