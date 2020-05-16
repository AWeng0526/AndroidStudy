package com.example.a1725121023_wengyuxian_lesson12;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"order_id", "dish_id"})
public class OrderDishCrossRef {
    @ColumnInfo(name = "order_id")
    private long orderId;
    @ColumnInfo(name = "dish_id")
    private long dishId;

    public long getDishId() {
        return dishId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setDishId(long dishId) {
        this.dishId = dishId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public OrderDishCrossRef(long orderId, long dishId) {
        this.orderId = orderId;
        this.dishId = dishId;
    }
}
