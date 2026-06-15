package com.mycompany.javafxapp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomerDashboardController {

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
    private VBox cartList;

    @FXML
    private Label cartSubtotalLabel;

    @FXML
    private Button checkoutBtn;

    @FXML
    private Button headerCartBtn;

    @FXML
    private Button filterAllBtn;

    @FXML
    private Button filterBurgersBtn;

    @FXML
    private Button filterSandwichesBtn;

    @FXML
    private Button filterPizzaBtn;

    private static class CartItem {
        String name;
        double price;
        int quantity;

        CartItem(String name, double price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }
    }

    private final Map<String, CartItem> cart = new HashMap<>();
    private String activeCategory = "all";

    @FXML
    private void initialize() {
        System.out.println("Customer Dashboard controller loaded");
        welcomeLabel.setText("Welcome Back, Foodie!");

        loadStats();
        loadRecentOrders();
        loadProducts();
        updateCartUi();
    }

    private void loadStats() {
        if (totalOrdersLabel != null) {
            totalOrdersLabel.setText("8 Orders");
        }
        if (totalSpendLabel != null) {
            totalSpendLabel.setText("$259.70");
        }
        if (loyaltyPointsLabel != null) {
            loyaltyPointsLabel.setText("420 Points");
        }
    }

    private void loadRecentOrders() {
        if (recentOrdersList == null) return;
        recentOrdersList.getChildren().clear();

        SampleOrder[] orders = new SampleOrder[] {
                new SampleOrder(5024, "Veg. Mania Burger", 16.36, "Delivered", "Today, 2:15 PM"),
                new SampleOrder(5023, "Turkish Pizza", 22.54, "Delivered", "June 10, 8:40 PM"),
                new SampleOrder(5022, "Italian Sandwich", 16.54, "Delivered", "June 08, 1:12 PM")
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

            Label dateLabel = new Label("Order #" + order.orderId + " • " + order.orderDate);
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
                new SampleProduct("Veg. Mania Burger", 16.36, 4.5),
                new SampleProduct("Mexican Patty", 8.28, 4.2),
                new SampleProduct("Turkish Pizza", 22.54, 4.8),
                new SampleProduct("Italian Sandwich", 16.54, 4.4)
        };

        for (SampleProduct product : products) {
            if (!activeCategory.equals("all") && !product.matchesCategory(activeCategory)) {
                continue;
            }

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

            Button addBtn = new Button("+ Add");
            addBtn.getStyleClass().add("add-button");
            addBtn.setOnAction(e -> addToCart(product.name, product.price));

            actionBox.getChildren().addAll(priceText, spacer, addBtn);
            card.getChildren().addAll(img, info, actionBox);
            productsGrid.getChildren().add(card);
        }
    }

    private void addToCart(String name, double price) {
        if (cart.containsKey(name)) {
            cart.get(name).quantity++;
        } else {
            cart.put(name, new CartItem(name, price, 1));
        }
        updateCartUi();
    }

    private void updateCartUi() {
        if (cartList == null) return;
        cartList.getChildren().clear();

        if (cart.isEmpty()) {
            Label emptyLbl = new Label("Your cart is empty. Add some tasty items!");
            emptyLbl.getStyleClass().add("order-row-date");
            emptyLbl.setPadding(new javafx.geometry.Insets(10, 0, 10, 0));
            cartList.getChildren().add(emptyLbl);

            cartSubtotalLabel.setText("$0.00");
            checkoutBtn.setDisable(true);
            headerCartBtn.setText("🛒 Cart (0)");
            return;
        }

        double total = 0.0;
        int totalQty = 0;

        for (CartItem item : cart.values()) {
            double itemTotal = item.price * item.quantity;
            total += itemTotal;
            totalQty += item.quantity;

            HBox row = new HBox();
            row.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
            row.setSpacing(6);
            row.setPadding(new javafx.geometry.Insets(6, 0, 6, 0));

            VBox itemInfo = new VBox();
            itemInfo.setSpacing(2);
            HBox.setHgrow(itemInfo, javafx.scene.layout.Priority.ALWAYS);

            Label nameLabel = new Label(item.name);
            nameLabel.getStyleClass().add("order-row-name");
            nameLabel.setStyle("-fx-font-size: 13px;");

            Label priceLabel = new Label(String.format("$%.2f each", item.price));
            priceLabel.getStyleClass().add("order-row-date");

            itemInfo.getChildren().addAll(nameLabel, priceLabel);

            HBox qtyControls = new HBox();
            qtyControls.setAlignment(javafx.geometry.Pos.CENTER);
            qtyControls.getStyleClass().add("qty-pill");
            qtyControls.setSpacing(4);

            Button minusBtn = new Button("-");
            minusBtn.getStyleClass().add("qty-btn");
            minusBtn.setOnAction(e -> {
                if (item.quantity > 1) {
                    item.quantity--;
                } else {
                    cart.remove(item.name);
                }
                updateCartUi();
            });

            Label qtyLabel = new Label(String.valueOf(item.quantity));
            qtyLabel.getStyleClass().add("qty-lbl");

            Button plusBtn = new Button("+");
            plusBtn.getStyleClass().add("qty-btn");
            plusBtn.setOnAction(e -> {
                item.quantity++;
                updateCartUi();
            });

            qtyControls.getChildren().addAll(minusBtn, qtyLabel, plusBtn);

            Label totalLabel = new Label(String.format("$%.2f", itemTotal));
            totalLabel.getStyleClass().add("order-row-price");
            totalLabel.setStyle("-fx-font-size: 13px;");

            row.getChildren().addAll(itemInfo, qtyControls, totalLabel);
            cartList.getChildren().add(row);
        }

        cartSubtotalLabel.setText(String.format("$%.2f", total));
        checkoutBtn.setDisable(false);
        headerCartBtn.setText("🛒 Cart (" + totalQty + ")");
    }

    @FXML
    private void handleCheckout() {
        cart.clear();
        updateCartUi();
        showNotification("Checkout Successful", "Your order was placed successfully.");
        loadStats();
        loadRecentOrders();
    }

    private void setFilter(String category, Button activeBtn) {
        activeCategory = category;

        filterAllBtn.getStyleClass().remove("filter-active");
        filterBurgersBtn.getStyleClass().remove("filter-active");
        filterSandwichesBtn.getStyleClass().remove("filter-active");
        filterPizzaBtn.getStyleClass().remove("filter-active");

        activeBtn.getStyleClass().add("filter-active");

        loadProducts();
    }

    @FXML
    private void handleFilterAll() {
        setFilter("all", filterAllBtn);
    }

    @FXML
    private void handleFilterBurgers() {
        setFilter("burger", filterBurgersBtn);
    }

    @FXML
    private void handleFilterSandwiches() {
        setFilter("sandwich", filterSandwichesBtn);
    }

    @FXML
    private void handleFilterPizza() {
        setFilter("pizza", filterPizzaBtn);
    }

    @FXML
    private void handleLogoutAction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/mycompany/javafxapp/MainView.fxml"));
        Stage stage = (Stage) welcomeLabel.getScene().getWindow();
        stage.setMinWidth(960);
        stage.setMinHeight(600);
        stage.setScene(new Scene(root, 960, 600));
    }

    private void showNotification(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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

        boolean matchesCategory(String category) {
            String lower = name.toLowerCase();
            return "all".equalsIgnoreCase(category)
                    || ("burger".equalsIgnoreCase(category) && lower.contains("burger"))
                    || ("pizza".equalsIgnoreCase(category) && lower.contains("pizza"))
                    || ("sandwich".equalsIgnoreCase(category) && lower.contains("sandwich"));
        }
    }

    private static final class SampleOrder {
        final int orderId;
        final String itemName;
        final double price;
        final String status;
        final String orderDate;
        final String emoji;

        SampleOrder(int orderId, String itemName, double price, String status, String orderDate) {
            this.orderId = orderId;
            this.itemName = itemName;
            this.price = price;
            this.status = status;
            this.orderDate = orderDate;
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