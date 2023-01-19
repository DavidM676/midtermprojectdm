public class Account {
     public static enum types {
        checking,
        saving;
    }


    private int balence = 0;

    private types acc;

    public Account(types type) {
        acc = type;
    }

    public boolean withdraw(int amount) {
        if (!(amount>balence)) {
            balence -= amount;
            return true;
        }
        return false;
    }

    public int getBalance() {
        return balence;
    }

    public types getType() {
        return acc;
    }


}