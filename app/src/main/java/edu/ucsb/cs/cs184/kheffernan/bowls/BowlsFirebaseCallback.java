package edu.ucsb.cs.cs184.kheffernan.bowls;

public interface BowlsFirebaseCallback<T> {
    void callback(T data);
}