package edu.ucsb.cs.cs184.kheffernan.bowls.BowlsLocalObjects;


import java.text.NumberFormat;
import java.util.ArrayList;

import static edu.ucsb.cs.cs184.kheffernan.bowls.Utilities.BowlsConstants.ORDER_STATUS_CREATED;

public class Order {
    private String orderID;
    private String ownerID;
    private Double orderCost;
    private String orderStatus;
    private String items;

    public Order() {
        // Required no argument constructor for Firebase database
    }

    // Constructor only used to create new spot in interface
    public Order(String ownerID, Double orderCost) {
        this.ownerID = ownerID;
        this.orderCost = orderCost;
        this.orderStatus = ORDER_STATUS_CREATED;
    }

    public Order(String orderID, String ownerID, String items, Double orderCost) {
        this.orderID = orderID;
        this.ownerID = ownerID;
        this.orderCost = orderCost;
        this.orderStatus = ORDER_STATUS_CREATED;
        this.items = items;
    }

    public String formattedOrderCost() {
        return NumberFormat.getCurrencyInstance().format((orderCost));
    }

    public String getOrderID() {
        return this.orderID;
    }

    public String getOwnerID() {
        return this.ownerID;
    }

    public Double getOrderCost() {
        return this.orderCost;
    }

    public String getItems(){return this.items;}

    public String getOrderStatus(){return this.orderStatus;}

    public void setOrderID(String orderID) { this.orderID = orderID; }

    public void setOwnerID(String ownerID) { this.ownerID = ownerID; }

    public void setItems(String items) { this.items = items; }

    public void setOrderCost(Double orderCost) {
        this.orderCost = orderCost;
    }

    public void setOrderStatus(String status){ this.orderStatus = status; }

}
