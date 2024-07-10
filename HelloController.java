package com.example.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private TableView<Pizza> pizzaTable;
    @FXML
    private TableColumn<Pizza, Integer> orderIdCol;
    @FXML
    private TableColumn<Pizza, String> cnameCol;
    @FXML
    private TableColumn<Pizza, String> mobCol;
    @FXML
    private TableColumn<Pizza, String> sizeCol;
    @FXML
    private TableColumn<Pizza, String> toppingsCol;
    @FXML
    private TableColumn<Pizza, Double> totalCol;
    @FXML
    private TextField orderId;
    @FXML
    private TextField cname;
    @FXML
    private TextField mob;
    @FXML
    private TextField size;
    @FXML
    private TextField toppings;
    @FXML
    private TextField total;
    @FXML
    private Label welcomeText;

    private ObservableList<Pizza> list = FXCollections.observableArrayList();

    @FXML
    protected void onHelloButtonClick() {
        fetchData();
    }

    private void fetchData() {
        list.clear();

        String jdbcUrl = "jdbc:mysql://localhost:3306/sii";
        String dbUser = "root";
        String dbPassword = "";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "SELECT * FROM pizza";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int orderId = resultSet.getInt("OrderId");
                String cname = resultSet.getString("Cname");
                String mob = resultSet.getString("Mob");
                String size = resultSet.getString("Size");
                String toppings = resultSet.getString("Toppings");
                double total = resultSet.getDouble("Total");
                pizzaTable.getItems().add(new Pizza(orderId, cname, mob, size, toppings, total));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        cnameCol.setCellValueFactory(new PropertyValueFactory<>("cname"));
        mobCol.setCellValueFactory(new PropertyValueFactory<>("mob"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
        toppingsCol.setCellValueFactory(new PropertyValueFactory<>("toppings"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
        pizzaTable.setItems(list);
    }

    @FXML
    public void InsertData(ActionEvent actionEvent) {
        String cname = this.cname.getText();
        String mob = this.mob.getText();
        String size = this.size.getText();
        String toppings = this.toppings.getText();
        double total = Double.parseDouble(this.total.getText());

        String jdbcUrl = "jdbc:mysql://localhost:3306/sii";
        String dbUser = "root";
        String dbPassword = "";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "INSERT INTO pizza (Cname, Mob, Size, Toppings, Total) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, cname);
            statement.setString(2, mob);
            statement.setString(3, size);
            statement.setString(4, toppings);
            statement.setDouble(5, total);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void UpdateData(ActionEvent actionEvent) {
        int orderId = Integer.parseInt(this.orderId.getText());
        String cname = this.cname.getText();
        String mob = this.mob.getText();
        String size = this.size.getText();
        String toppings = this.toppings.getText();
        double total = Double.parseDouble(this.total.getText());

        String jdbcUrl = "jdbc:mysql://localhost:3306/sii";
        String dbUser = "root";
        String dbPassword = "";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "UPDATE pizza SET Cname = ?, Mob = ?, Size = ?, Toppings = ?, Total = ? WHERE OrderId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, cname);
            statement.setString(2, mob);
            statement.setString(3, size);
            statement.setString(4, toppings);
            statement.setDouble(5, total);
            statement.setInt(6, orderId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void DeleteData(ActionEvent actionEvent) {
        int orderId = Integer.parseInt(this.orderId.getText());

        String jdbcUrl = "jdbc:mysql://localhost:3306/sii";
        String dbUser = "root";
        String dbPassword = "";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "DELETE FROM pizza WHERE OrderId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, orderId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void LoadData(ActionEvent actionEvent) {
        int orderId = Integer.parseInt(this.orderId.getText());

        String jdbcUrl = "jdbc:mysql://localhost:3306/sii";
        String dbUser = "root";
        String dbPassword = "";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "SELECT * FROM pizza WHERE OrderId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String cname = resultSet.getString("Cname");
                String mob = resultSet.getString("Mob");
                String size = resultSet.getString("Size");
                String toppings = resultSet.getString("Toppings");
                double total = resultSet.getDouble("Total");

                this.cname.setText(cname);
                this.mob.setText(mob);
                this.size.setText(size);
                this.toppings.setText(toppings);
                this.total.setText(String.valueOf(total));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}