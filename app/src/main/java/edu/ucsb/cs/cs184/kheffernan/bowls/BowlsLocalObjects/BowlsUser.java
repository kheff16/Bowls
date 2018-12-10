package edu.ucsb.cs.cs184.kheffernan.bowls.BowlsLocalObjects;


import java.util.HashMap;
import java.util.Map;

public class BowlsUser {


        private String userID;
        private String email;
        private String fullname;
        private String phoneNumber;
        private String accountType;
        private Map<String, String> currentOrdersCart = new HashMap<>();


        public BowlsUser() {
            // Default no argument constructor needed for Firebase database
        }

        public BowlsUser(String userID, String email, String fullname, String accountType, String phoneNumber) {
            this.userID = userID;
            this.email = email;
            this.fullname = fullname;
            this.accountType = accountType;
            this.phoneNumber = phoneNumber;
        }

        public void addOrderItemToUser( String item, String cost) {
            currentOrdersCart.put(item, cost);
        }

        public void removeOrderItemFromUser(String item) {
            currentOrdersCart.remove(item);
        }

        public String getUserID() {
            return this.userID;
        }

        public String getEmail() {
            return this.email;
        }

        public String getFullname() {
            return this.fullname;
        }

        public String getAccountType() { return this.accountType; }

        public String getPhoneNumber() {return this.phoneNumber;}


        public Map<String, String> getCurrentOrdersCart() { return this.currentOrdersCart; }

        public void setUserID(String userID) { this.userID = userID; }

        public void setEmail(String email) { this.email = email; }

        public void setFullname(String fullname) { this.fullname = fullname; }

        public void setAccountType(String accountType) { this.accountType = accountType; }

        public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}

        public void setCurrentOrdersCart(Map<String, String> currentOrders) { this.currentOrdersCart = currentOrders; }
}


