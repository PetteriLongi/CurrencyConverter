package controller;

import model.Currency;
import model.CurrencyModel;

import java.util.ArrayList;

public class CurrencyController {
    private CurrencyModel model;

    public CurrencyController() {
        model = new CurrencyModel();

        model.addCurrency(new Currency("USD", "US Dollar",  1.00));
        model.addCurrency(new Currency("EUR", "Euro",  0.92));
        model.addCurrency(new Currency("GBP", "British Pound",  0.77));
        model.addCurrency(new Currency("JPY", "Japanese Yen",  150.00));
    }
    public ArrayList<Currency> getCurrencies() {
        return model.getCurrencies();
    }
    public double convert(double amount, Currency from, Currency to) {
        return amount * (to.getExchangeRate() / from.getExchangeRate());
    }

}
