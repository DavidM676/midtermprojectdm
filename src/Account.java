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

    public void withdraw(int amount) {
        balence -= amount;
    }

    public int getBalance() {
        return balence;
    }

    public types getType() {
        return acc;
    }


}