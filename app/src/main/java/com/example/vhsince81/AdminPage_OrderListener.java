package com.example.vhsince81;

public interface AdminPage_OrderListener {

    void onOrderPackedClick(int position);
    void onOrderPlacedClick(int position);
    void onOrderDeletedClick(int position);
    void orderDetails(int position, String type);
}
