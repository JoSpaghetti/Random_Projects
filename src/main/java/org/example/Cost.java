package org.example;

public class Cost {
    double value;
    String costName;
    double amount;

    public Cost(double value, String costName, double amount) {
        this.value = value;
        this.costName = costName;
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCostName() {
        return costName;
    }

    public void setCostName(String costName) {
        this.costName = costName;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
