package model;

public class Currency {
    private String abbreviation;
    private String name;
    private double exchangeRate;

    public Currency(String abbreviation, String name, double exchangeRate) {
        this.abbreviation = abbreviation;
        this.name = name;
        this.exchangeRate = exchangeRate;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
    public String getName() {
        return name;
    }
    public double getExchangeRate() {
        return exchangeRate;
    }

    @Override
    public String toString() {
        return abbreviation + " - " + name +  " - " + exchangeRate;
    }
}
