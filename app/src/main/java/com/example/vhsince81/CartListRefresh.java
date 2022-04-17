package com.example.vhsince81;

import com.example.vhsince81.DBPOJOpackage.CARTITEMDTO;

public interface CartListRefresh {

    void cart_delete(CARTITEMDTO item);
    void cart_update(CARTITEMDTO item);
    void cart_plus_quant(CARTITEMDTO item);
    void cart_minus_quant(CARTITEMDTO item);

}

