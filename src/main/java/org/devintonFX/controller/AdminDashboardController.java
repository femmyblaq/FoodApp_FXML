package com.mycompany.javafxapp;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminDashboardController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label totalOrdersLabel;

    @FXML
    private Label totalSpendLabel;

    @FXML
    private Label loyaltyPointsLabel;

    @FXML
    private VBox recentOrdersList;

    @FXML
    private FlowPane productsGrid;

    @FXML
    private LineChart<String, Number> spendChart;

    @FXML
    private void initialize() {
        System.out.println("Admin Dashboard controller loaded");
        welcomeLabel.setText("Welcome Back, Admin!");
        loadStats();
        loadRecentOrders();
        loadProducts();
        loadChartData();
    }

    private void loadStats() {
        if (totalOrdersLabel != null) {
            totalOrdersLabel.setText("124 Orders");
        }
        if (totalSpendLabel != null) {
            totalSpendLabel.setText("$12,480.90");
        }
        if (loyaltyPointsLabel != null) {
            loyaltyPointsLabel.setText("312 Users");
        }
    }

    private void loadRecentOrders() {
        if (recentOrdersList == null) return;
        recentOrdersList.getChildren().clear();

        SampleOrder[] orders = new SampleOrder[] {
                new SampleOrder(2024, "Premium Burger", 18.40, "Completed", "June 13, 6:32 PM", "alice"),
                new SampleOrder(2023, "Veggie Pizza", 24.90, "Completed", "June 12, 4:50 PM", "bob"),
                new SampleOrder(2022, "Club Sandwich", 16.70, "Delivered", "June 11, 3:10 PM", "carol"),
                new SampleOrder(2021, "Golden Salad", 13.20, "Pending", "June 10, 1:45 PM", "dave"),
                new SampleOrder(2020, "Mighty Wrap", 12.90, "Completed", "June 09, 12:05 PM", "ella")
        };

        for (SampleOrder order : orders) {
            HBox row = new HBox();
            row.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
            row.getStyleClass().add("recent-order-row");
            row.setPadding(new javafx.geometry.Insets(8));

            VBox infoVBox = new VBox();
            infoVBox.setSpacing(2);
            HBox.setHgrow(infoVBox, javafx.scene.layout.Priority.ALWAYS);

            Label nameLabel = new Label(order.emoji + order.itemName);
            nameLabel.getStyleClass().add("order-row-name");

            Label dateLabel = new Label("Order #" + order.orderId + " • " + order.userName + " • " + order.orderDate);
            dateLabel.getStyleClass().add("order-row-date");

            infoVBox.getChildren().addAll(nameLabel, dateLabel);

            Label priceLabel = new Label(String.format("$%.2f", order.price));
            priceLabel.getStyleClass().add("order-row-price");

            Label statusLabel = new Label(order.status);
            statusLabel.getStyleClass().add("order-badge-delivered");
            statusLabel.setMinWidth(javafx.scene.layout.Region.USE_PREF_SIZE);

            row.getChildren().addAll(infoVBox, priceLabel, statusLabel);
            recentOrdersList.getChildren().add(row);
        }
    }

    private void loadProducts() {
        if (productsGrid == null) return;
        productsGrid.getChildren().clear();

        SampleProduct[] products = new SampleProduct[] {
                new SampleProduct("Premium Burger", 18.40, 4.9),
                new SampleProduct("Veggie Pizza", 24.90, 4.8),
                new SampleProduct("Club Sandwich", 16.70, 4.4),
                new SampleProduct("Golden Salad", 13.20, 4.5)
        };

        for (SampleProduct product : products) {
            VBox card = new VBox();
            card.getStyleClass().add("product-card");
            card.setSpacing(10);
            card.setAlignment(javafx.geometry.Pos.TOP_CENTER);
            card.setMinWidth(200);
            card.setMaxWidth(200);
            card.setPrefWidth(200);
            card.setPadding(new javafx.geometry.Insets(12));

            javafx.scene.layout.Region img = new javafx.scene.layout.Region();
            img.getStyleClass().add("product-image");
            img.setMinHeight(110);
            img.setMaxHeight(110);
            img.setPrefHeight(110);

            VBox info = new VBox();
            info.setAlignment(javafx.geometry.Pos.TOP_LEFT);
            info.setSpacing(4);
            VBox.setVgrow(info, javafx.scene.layout.Priority.ALWAYS);

            javafx.scene.text.Text nameText = new javafx.scene.text.Text(product.name);
            nameText.getStyleClass().add("product-name");
            nameText.setWrappingWidth(176);

            StringBuilder stars = new StringBuilder();
            int fullStars = (int) Math.round(product.rating);
            for (int i = 0; i < 5; i++) {
                stars.append(i < fullStars ? "⭐" : "★");
            }
            stars.append(String.format(" (%.1f)", product.rating));

            javafx.scene.text.Text ratingText = new javafx.scene.text.Text(stars.toString());
            ratingText.getStyleClass().add("product-stars");

            info.getChildren().addAll(nameText, ratingText);

            HBox actionBox = new HBox();
            actionBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
            actionBox.getStyleClass().add("product-action-box");

            javafx.scene.text.Text priceText = new javafx.scene.text.Text(String.format("$%.2f", product.price));
            priceText.getStyleClass().add("product-price");

            javafx.scene.layout.Region spacer = new javafx.scene.layout.Region();
            HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

            Label statusLabel = new Label("Active");
            statusLabel.getStyleClass().add("order-badge-delivered");

            actionBox.getChildren().addAll(priceText, spacer, statusLabel);
            card.getChildren().addAll(img, info, actionBox);
            productsGrid.getChildren().add(card);
        }
    }

    private void loadChartData() {
        if (spendChart == null) return;
        spendChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Weekly Store Sales ($)");

        double[] weeklySpend = {150.00, 230.50, 180.00, 420.90, 310.20, 680.00, 720.50};
        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

        for (int i = 0; i < days.length; i++) {
            series.getData().add(new XYChart.Data<>(days[i], weeklySpend[i]));
        }

        spendChart.getData().add(series);
    }

    @FXML
    private void handleLogoutAction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/mycompany/javafxapp/MainView.fxml"));
        Stage stage = (Stage) welcomeLabel.getScene().getWindow();
        stage.setMinWidth(960);
        stage.setMinHeight(600);
        stage.setScene(new Scene(root, 960, 600));
    }

    private static final class SampleProduct {
        final String name;
        final double price;
        final double rating;

        SampleProduct(String name, double price, double rating) {
            this.name = name;
            this.price = price;
            this.rating = rating;
        }
    }

    private static final class SampleOrder {
        final int orderId;
        final String itemName;
        final double price;
        final String status;
        final String orderDate;
        final String userName;
        final String emoji;

        SampleOrder(int orderId, String itemName, double price, String status, String orderDate, String userName) {
            this.orderId = orderId;
            this.itemName = itemName;
            this.price = price;
            this.status = status;
            this.orderDate = orderDate;
            this.userName = userName;
            if (itemName.toLowerCase().contains("burger")) {
                this.emoji = "🍔 ";
            } else if (itemName.toLowerCase().contains("pizza")) {
                this.emoji = "🍕 ";
            } else if (itemName.toLowerCase().contains("sandwich")) {
                this.emoji = "🥪 ";
            } else {
                this.emoji = "📦 ";
            }
        }
    }
}