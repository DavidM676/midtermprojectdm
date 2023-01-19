public class Customer {

    private Account saving = new Account(Account.types.saving);
    private Account checking = new Account(Account.types.checking);

    private Account[] accounts = {saving, checking};
    private String name;
    private String pin;

    public Customer(String pin, String name) {
        this.pin = pin;
        this.name = name;
    }

    public String getPin() {
        return pin;
    }

    public String getName() {
        return name;
    }

    public Account[] getAccounts() {
        return accounts;
    }

    public String getAccountsString() {
        String f = "";
        for (int i = 0; i<accounts.length; i++) {
            f += "("+i+")"+accounts[i].getType().name()+" ";
        }
        return f;
    }

//    public boolean withdraw(int amount, String type) {
//        if (type.equals("Saving")) {
//            if (amount > saving.getBalance()) {
//                return false;
//            }
//            saving.withdraw(amount);
//        } else if (type.equals("Checking")) {
//            if (amount > checking.getBalance()) {
//                return false;
//            }
//            checking.withdraw(amount);
//
//        }
//        return true;
//
//    }


}