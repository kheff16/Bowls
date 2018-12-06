package edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsLocalObjects.BowlsUser;
import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsLocalObjects.Order;

import static edu.ucsb.cs.cs184.kheffernan.bowls.Utilities.BowlsConstants.ORDER_PATH;
import static edu.ucsb.cs.cs184.kheffernan.bowls.Utilities.BowlsConstants.USER_PATH;

public class BowlsFirebase {

    private DatabaseReference bowlsDatabase;
    private StorageReference bowlsStorage;

    public BowlsFirebase() {
        bowlsDatabase = FirebaseDatabase.getInstance().getReference();
        bowlsStorage = FirebaseStorage.getInstance().getReference();
    }


    // Create a new parking order in the database
    public String createNewOrder(Order order) {
        String newOrderID = "order-" + UUID.randomUUID().toString();

        bowlsDatabase.child(ORDER_PATH).child(newOrderID).setValue(order);

        return newOrderID;
    }

    // Update a current parking order from the data base
    public void updateorder(final String orderID, Order updatedorder) {
        bowlsDatabase.child(ORDER_PATH).child(orderID).setValue(updatedorder);
    }


    // Get a parking order from the data base
    public void getOrder(final String orderID,
                               @NonNull final BowlsFirebaseCallback<Order> finishedCallback) {

        DatabaseReference myRef = bowlsDatabase.child(ORDER_PATH);

        myRef.child(orderID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Order order = dataSnapshot.getValue(Order.class);

                if(order != null) {
                    order.setOrderID(orderID);
                }

                finishedCallback.callback(order);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });
    }

    public void getAllOrders(
            @NonNull final BowlsFirebaseCallback<ArrayList<Order>> finishedCalback) {

        DatabaseReference myRef = bowlsDatabase.child(ORDER_PATH);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Order> Orders = new ArrayList<>();

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Order order = postSnapshot.getValue(Order.class);

                    if (order != null) {
                        order.setOrderID(postSnapshot.getKey());
                        Orders.add(order);
                    }
                }

                finishedCalback.callback(Orders);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });
    }


//getAvailableOrders
//    public void getAvailableOrders(final long start, final long end,
//                                         @NonNull final BowlsFirebaseCallback<ArrayList<Order>> finishedCallback) {
//
//        DatabaseReference myRef = bowlsDatabase.child(ORDER_PATH);
//
//        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                ArrayList<Order> availableOrders = new ArrayList<>();
//
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                    Order order = postSnapshot.getValue(Order.class);
//
//                    if(order != null) {
//                        order.setOrderID(postSnapshot.getKey());
//
//                        if (order.getBlockedDatesCount() == 0) {
//                            availableOrders.add(order);
//
//                        } else {
//                            Boolean available = true;
//                            ArrayList<BlockedDates> blockedDates = order.getBlockedDatesList();
//
//                            for ( BlockedDates block : blockedDates) {
//                                if(block.conflict(start, end)) {
//                                    available = false;
//                                    break;
//                                }
//                            }
//
//                            if(available) {
//                                availableOrders.add(order);
//                            }
//                        }
//                    }
//                }
//
//                finishedCallback.callback(availableOrders);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }

    public void deleteOrder(String orderID) {
        DatabaseReference myRef = bowlsDatabase.child(ORDER_PATH).child(orderID);
        myRef.removeValue();
    }




    public void getUsersOrders(final String userID,
                                     @NonNull final BowlsFirebaseCallback<ArrayList<Order>> finishedCalback) {

        DatabaseReference myRef = bowlsDatabase.child(ORDER_PATH);

        Query query = myRef.orderByChild("ownerID").equalTo(userID);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Order> Orders = new ArrayList<>();

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Order order = postSnapshot.getValue(Order.class);

                    if ((order != null)) {
                        order.setOrderID(postSnapshot.getKey());
                        Orders.add(order);
                    }
                }

                finishedCalback.callback(Orders);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });
    }

//    public void getPastOrders(
//            final BowlsUser user,
//            @NonNull final BowlsFirebaseCallback<Map<Order, BlockedDates>> finishedCallback) {
//
//        final Map<Order, BlockedDates> ordersAndBlocks = new HashMap<>();
//
//        final Map<String, String> rentedorders = user.getRentedorders();
//
//        for (final String key : rentedorders.keySet()) {
//            getOrder(rentedorders.get(key), new BowlsFirebaseCallback<Order>() {
//                @Override
//                public void callback(Order data) {
//                    if(data != null) {
//                        ordersAndBlocks.put(data, new BlockedDates(key));
//                    }
//
//                    if(ordersAndBlocks.size() == rentedorders.size()) {
//                        finishedCallback.callback(ordersAndBlocks);
//                    }
//                }
//            });
//        }
//    }

    /**
     *
     * MARK - BowlsUser Interface
     */

    // Create or modify user object on database
    public void uploadUser(BowlsUser user) {
        bowlsDatabase.child(USER_PATH).child(user.getUserID()).setValue(user);
    }

    // Get a user from the database
    public void getBowlsUser(final String userID,
                          @NonNull final BowlsFirebaseCallback<BowlsUser> finishedCallback) {

        DatabaseReference myRef = bowlsDatabase.child(USER_PATH);

        myRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                BowlsUser user = dataSnapshot.getValue(BowlsUser.class);

                if(user != null) {
                    user.setUserID(userID);
                }

                finishedCallback.callback(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });
    }



    public void deleteUser(String userID) {
        DatabaseReference myRef = bowlsDatabase.child(USER_PATH).child(userID);
        myRef.removeValue();
    }

}