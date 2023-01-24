public class Customer {

    private Account saving = new Account(Account.types.saving);
    private Account checking = new Account(Account.types.checking);

    private Account[] accounts = {saving, checking};
    private String name;
    private String pin;
    private int transactionID = 0;

    private boolean authenticated = false;

    public Customer(String pin, String name) {
        this.pin = pin;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean authenticate(String pin) {
        if (this.pin.equals(pin)) {
            authenticated = true;
            return true;
        }
        return false;
    }

    public void logout() {
        authenticated = false;
    }

    public void setPin(String newPin) {
        if (authenticated) {
            authenticated = false;
            pin = newPin;
        }
    }


    public Account[] getAccounts() {
        if (authenticated) {
            return accounts;
        }
        return null;
    }

    public String getAccountsString() {
        if (!(authenticated)) {
            return null;
        }

        String f = "";
        for (int i = 0; i<accounts.length; i++) {
            f += "("+i+")"+accounts[i].getType().name()+" ";
        }
        return f;
    }

    public int applyChanges(Account a) {
        if (authenticated) {
            authenticated = false;
            a.applyChanges();
            newTransaction();
            return transactionID;
        }
        return -1;
    }

    public int applyChanges(Account a, Account b) {
        if (authenticated) {
            authenticated = false;
            a.applyChanges();
            b.applyChanges();
            newTransaction();
            return transactionID;
        }
        return -1;
    }


    private void newTransaction() {
        transactionID += 1;
    }


}