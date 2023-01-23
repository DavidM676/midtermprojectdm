public class Account {
     public static enum types {
        checking,
        saving;
    }


    private double balence = 0;

    private types acc;

    public Account(types type) {
        acc = type;
    }

    public boolean transfer(double amount, Account acc2) {
        this.withdraw(amount);
        acc2.deposit(amount);
        return true;
    }
    public boolean withdraw(int amount) {
        if (!(amount>balence)) {
            balence -= amount;
            return true;
        }
        return false;
    }

    public boolean withdraw(double amount) {
        if (!(amount>balence)) {
            balence -= amount;
            return true;
        }
        return false;
    }

    public boolean deposit(double amount) {
        balence += amount;
        return true;
    }

    public double getBalance() {
        return balence;
    }

    public types getType() {
        return acc;
    }


}