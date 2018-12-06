package edu.ucsb.cs.cs184.kheffernan.bowls.BowlsLocalObjects;


import java.text.NumberFormat;
import java.util.ArrayList;

public class Order {
    private String orderID;
    private String ownerID;
    private String address;
    private Double orderCost;

    public Order() {
        // Required no argument constructor for Firebase database
    }

    // Constructor only used to create new spot in interface
    public Order(String ownerID, String address, Double orderCost) {
        this.ownerID = ownerID;
        this.address = address;
        this.orderCost = orderCost;
    }

    public Order(String orderID, String ownerID, String address, Double orderCost) {
        this.orderID = orderID;
        this.ownerID = ownerID;
        this.address = address;
        this.orderCost = orderCost;
    }

    public String formattedOrderCost() {
        return NumberFormat.getCurrencyInstance().format((orderCost));
    }

    public String getorderID() {
        return this.orderID;
    }

    public String getOwnerID() {
        return this.ownerID;
    }

    public String getAddress() {
        return this.address;
    }

    public Double getorderCost() {
        return this.orderCost;
    }

    public void setOrderID(String orderID) { this.orderID = orderID; }

    public void setOwnerID(String ownerID) { this.ownerID = ownerID; }

    public void setAddress(String address) { this.address = address; }

    public void setOrderCost(Double orderCost) {
        this.orderCost = orderCost;
    }

}
