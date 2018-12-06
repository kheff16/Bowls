package edu.ucsb.cs.cs184.kheffernan.bowls;

public class UserObject {

    private String userID;
    private String email;
    private String fullName;
    private String phoneNumber;


    public UserObject(){
        //DEFAULT NO ARG CONSTRUCTOR
    }
    public UserObject(String userID, String email, String fullName, String phoneNumber){
        this.userID = userID;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }


    public String getUserID() {
        return this.userID;
    }

    public String getEmail() {
        return this.email;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setUserID(String userID) { this.userID = userID; }

    public void setEmail(String email) { this.email = email; }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber; }


}
