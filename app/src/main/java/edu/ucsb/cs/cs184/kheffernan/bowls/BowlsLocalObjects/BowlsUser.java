package edu.ucsb.cs.cs184.kheffernan.bowls.BowlsLocalObjects;


import java.util.HashMap;
import java.util.Map;

public class BowlsUser {


        private String userID;
        private String email;
        private String fullname;
        private String location;
        private String imageUrl;
        private Map<String, String> rentedSpots = new HashMap<>();


        public BowlsUser() {
            // Default no argument constructor needed for Firebase database
        }

        public BowlsUser(String userID, String email, String fullname, String location) {
            this.userID = userID;
            this.email = email;
            this.fullname = fullname;
            this.location = location;
        }

        public void addRentedSpot(String blockedDates, String spotID) {
            rentedSpots.put(blockedDates, spotID);
        }

        public void removeRentedSpot(String blockedDates) {
            rentedSpots.remove(blockedDates);
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

        public String getLocation() { return this.location; }

        public String getImageUrl() { return this.imageUrl; }

        public Map<String, String> getRentedSpots() { return this.rentedSpots; }

        public void setUserID(String userID) { this.userID = userID; }

        public void setEmail(String email) { this.email = email; }

        public void setFullname(String fullname) { this.fullname = fullname; }

        public void setLocation(String location) { this.location = location; }

        public void setImageUrl(String url) { this.imageUrl = url; }

        public void setRentedSpots(Map<String, String> rentedSpots) { this.rentedSpots = rentedSpots; }
}


