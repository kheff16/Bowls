package edu.ucsb.cs.cs184.kheffernan.bowls.BowlsFirebaseInterface;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ArrayAdapter;

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
import edu.ucsb.cs.cs184.kheffernan.bowls.R;

import static edu.ucsb.cs.cs184.kheffernan.bowls.Utilities.BowlsConstants.ORDER_PATH;
import static edu.ucsb.cs.cs184.kheffernan.bowls.Utilities.BowlsConstants.ORDER_STATUS_COMPLETED;
import static edu.ucsb.cs.cs184.kheffernan.bowls.Utilities.BowlsConstants.USER_PATH;

public class BowlsFirebase {

    private DatabaseReference bowlsDatabase;
    private StorageReference bowlsStorage;

    private ArrayList<Order> ordersWithStatus;

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

    public void getUsersCompletedOrders(final String userID,
                               @NonNull final BowlsFirebaseCallback<ArrayList<Order>> finishedCalback) {

        DatabaseReference myRef = bowlsDatabase.child(ORDER_PATH);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Order> completedOrders = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Order order = postSnapshot.getValue(Order.class);

                    if (order != null) {
                        order.setOrderID(postSnapshot.getKey());
                        if (order.getOrderStatus() == ORDER_STATUS_COMPLETED) {
                            completedOrders.add(order);

                        }
                    }

                }

                finishedCalback.callback(completedOrders);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });
    }





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


//    public synchronized void getAllOrdersWithStatus(final String status,
//            @NonNull final BowlsFirebaseCallback<ArrayList<Order>> finishedCalback) {
//
//        DatabaseReference myRef = bowlsDatabase.child(ORDER_PATH);
//
//        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                ArrayList<Order> Orders = new ArrayList<>();
//
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                    Order order = postSnapshot.getValue(Order.class);
//
//                    if (order != null) {
//                        if (order.getOrderStatus().equals(status)) {
//                            order.setOrderID(postSnapshot.getKey());
//                            //logging shows that the right orders are in fact getting added
//                            Log.d("getAllOrdersWithStatus "+status, order.toString());
//                            Orders.add(order);
//                        }
//                    }
//                }
//
//                finishedCalback.callback(Orders);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {}
//
//        });
//    }

    public synchronized void getAllOrdersWithStatus(final String status,
                                                    @NonNull final BowlsFirebaseCallback<Integer> finishedCalback) {

        DatabaseReference myRef = bowlsDatabase.child(ORDER_PATH);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Order> Orders = new ArrayList<>();
                int size=-1;

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Order order = postSnapshot.getValue(Order.class);

                    if (order != null) {
                        if (order.getOrderStatus().equals(status)) {
                            order.setOrderID(postSnapshot.getKey());
                            //logging shows that the right orders are in fact getting added
                            Log.d("getAllOrdersWithStatus "+status, order.toString());
                            Orders.add(order);
                        }
                    }
                }
                size = Orders.size();
                finishedCalback.callback(size);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });
    }


}
