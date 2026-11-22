package view;

import controller.CurrencyController;
import model.Currency;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CurrencyView extends Application {

    private CurrencyController controller;

    @Override
    public void start(Stage stage) {
        controller = new CurrencyController();

        // Root container: stacks the top bar and the grid vertically
        VBox root = new VBox();
        root.setSpacing(10);
        root.setPadding(new Insets(15));

        // Grid that holds the input components
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(10);

        // Help button with a swanky icon (AlertType)
        Button helpButton = new Button("?");
        helpButton.setTooltip(new Tooltip("Instructions"));

        helpButton.setOnAction(event -> {
            Alert helpAlert = new Alert(Alert.AlertType.INFORMATION);
            // Apply CSS to the popup
            helpAlert.getDialogPane().getStylesheets().add(
                    getClass().getResource("/style.css").toExternalForm()
            );
            helpAlert.setTitle("Currency Converter Instructions");
            helpAlert.setHeaderText("How to use the converter");
            helpAlert.setContentText(
                    "1. Enter the amount you want to convert.\n\n" +
                            "2. Select the currency you are converting from.\n\n" +
                            "3. Select the currency you want to convert to.\n\n" +
                            "4. Press Convert to see the converted amount.\n\n" +
                            "Exchange rates are based on USD as the reference currency."
            );
            helpAlert.showAndWait();
        });

        // Amount input
        // Each label sits on top of its component
        Label amountLabel = new Label("Amount:");
        TextField amountField = new TextField();

        VBox amountBox = new VBox(5);
        amountBox.getChildren().addAll(amountLabel, amountField);

        // Source currency
        Label fromLabel = new Label("From currency:");
        ComboBox<Currency> fromCurrency = new ComboBox<>();

        VBox fromBox = new VBox(5);
        fromBox.getChildren().addAll(fromLabel, fromCurrency);

        // Target currency
        Label toLabel = new Label("To currency:");
        ComboBox<Currency> toCurrency = new ComboBox<>();

        VBox toBox = new VBox(5);
        toBox.getChildren().addAll(toLabel, toCurrency);

        // Fill the combo boxes with the currencies
        fromCurrency.getItems().addAll(controller.getCurrencies());
        toCurrency.getItems().addAll(controller.getCurrencies());

        // Preselect different currencies
        fromCurrency.getSelectionModel().selectFirst();
        toCurrency.getSelectionModel().select(1);

        // Result output
        Label resultLabel = new Label("Converted amount:");
        TextField resultField = new TextField();
        resultField.setEditable(false); // lock the box so user cant edit it

        VBox resultBox = new VBox(5);
        resultBox.getChildren().addAll(resultLabel, resultField);

        // Place components in the grid
        grid.add(amountBox, 0, 0);
        grid.add(resultBox, 1, 0);
        grid.add(fromBox, 0, 1);
        grid.add(toBox, 1, 1);

        // Convert button
        Button convertButton = new Button("Convert");

        convertButton.setOnAction(event -> {

            if (amountField.getText().isEmpty()) {
                showError("Please enter an amount.");
                return;
            }

            double amount;

            try {
                amount = Double.parseDouble(amountField.getText());
            } catch (NumberFormatException e) {
                showError("Please enter a valid number.");
                return;
            }

            if (amount < 0) {
                showError("Please enter a positive amount.");
                return;
            }

            Currency from = fromCurrency.getValue();
            Currency to = toCurrency.getValue();

            double result = controller.convert(amount, from, to);
            resultField.setText(String.format("%.2f", result));
        });

        // Center the button below the grid
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(convertButton);
        grid.add(buttonBox, 0, 2, 2, 1);

        // Top bar
        Label titleLabel = new Label("Currency Converter");
        titleLabel.getStyleClass().add("title-label"); // styled in style.css

        BorderPane topBar = new BorderPane();
        topBar.setLeft(titleLabel);
        topBar.setRight(helpButton);

        // Scene and window
        root.getChildren().addAll(topBar, grid);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(
                getClass().getResource("/style.css").toExternalForm()
        );

        stage.setTitle("Currency Converter");
        stage.setScene(scene);
        stage.show(); // the window is sized to the scene automatically
    }

    // Shows an error dialog with the given message and a swanky icon
    private void showError(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        // Apply CSS to the popup
        errorAlert.getDialogPane().getStylesheets().add(
                getClass().getResource("/style.css").toExternalForm()
        );
        errorAlert.setTitle("Input Error");
        errorAlert.setHeaderText("Unable to convert currency");
        errorAlert.setContentText(message);
        errorAlert.showAndWait();

    }
}