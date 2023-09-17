package org.AccountPojo;

public class Account {

    public int AccountNum;

    public int getAccountNum() {
        return AccountNum;
    }

    public void setAccountNum(int accountNum) {
        AccountNum = accountNum;
    }

    @Override
    public String toString() {
        return "Account{" +
                "AccountNum=" + AccountNum +
                ", Name='" + Name + '\'' +
                ", amountDebited=" + amountDebited +
                ", remBalance=" + remBalance +
                '}';
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getAmountDebited() {
        return amountDebited;
    }

    public void setAmountDebited(int amountDebited) {
        this.amountDebited = amountDebited;
    }

    public int getRemBalance() {
        return remBalance;
    }

    public void setRemBalance(int remBalance) {
        this.remBalance = remBalance;
    }

    public String Name;
    public int amountDebited;
    public int remBalance;

}
