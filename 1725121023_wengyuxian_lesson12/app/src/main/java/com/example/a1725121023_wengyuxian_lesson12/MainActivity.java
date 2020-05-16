package com.example.a1725121023_wengyuxian_lesson12;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button button1, button2, button3;
    TextView textView;
    Spinner spinner;
    OrderDishDao orderDishDao;
    ArrayAdapter<CustomerOrder> adapter;
    List<String> orderNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        textView = findViewById(R.id.textView);
        spinner = findViewById(R.id.spinner);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "order_info")
                .allowMainThreadQueries()
                .build();
        orderDishDao = db.orderDishDao();
        initDishes();

        List<CustomerOrder> orderList = orderDishDao.loadAllCustomerOrder();
        for (int i = 0; i < orderList.size(); i++) {
            orderNames.add(orderList.get(i).getOrderName());
        }

        adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, orderNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerOrder customerOrder = new CustomerOrder("order_" + (int) (Math.random() * 10000), "Preparing");
                long orderId = orderDishDao.insertOneOrder(customerOrder);

                Random random = new Random();
                OrderDishCrossRef[] orderDishCrossRefs = new OrderDishCrossRef[3];
                orderDishCrossRefs[0] = new OrderDishCrossRef(orderId, random.nextInt(2) + 1);
                orderDishCrossRefs[1] = new OrderDishCrossRef(orderId, random.nextInt(2) + 3);
                orderDishCrossRefs[2] = new OrderDishCrossRef(orderId, random.nextInt(2) + 5);
                orderDishDao.insertMultiDishesForOneOrder(orderDishCrossRefs);
                orderNames.add(customerOrder.getOrderName());
                adapter.notifyDataSetChanged();
                spinner.setSelection(orderNames.size());
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderNames.size() > 0) {
                    String currentOrderName = spinner.getSelectedItem().toString();
                    CustomerOrder currentOrder = orderDishDao.loadOneCustomerOrder(currentOrderName);
                    currentOrder.setOrderStatus("Finished");
                    orderDishDao.updateOneOrder(currentOrder);
                    showOrderWithDishes(currentOrder.getOrderName());
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentOrderName = spinner.getSelectedItem().toString();
                CustomerOrder currentOrder = orderDishDao.loadOneCustomerOrder(currentOrderName);
                for (int i = 0; i < orderNames.size(); i++) {
                    if (orderNames.get(i).equals(currentOrderName)) {
                        orderNames.remove(i);
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
                spinner.setSelection(0);
                if(orderNames.size()>0){
                    showOrderWithDishes(orderNames.get(0).toString());
                }else{
                    textView.setText("Order Info");
                }
                orderDishDao.deleteOneOrder(currentOrder);
            }
        });

    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String content = parent.getItemAtPosition(pos).toString();
        if (parent.getId() == R.id.spinner) {
            showOrderWithDishes(content);
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        textView.setText("Order Info");
    }

    public void showOrderWithDishes(String orderName) {
        OrderWithDishes orderWithDishes = orderDishDao.getOrderWithDishes(orderName);
        String text = orderWithDishes.getCustomerOrder().getOrderName() + ":";
        text = text + orderWithDishes.getCustomerOrder().getOrderStatus() + "\n";
        int totalPrice = 0;
        for (int i = 0; i < orderWithDishes.getDishes().size(); i++) {
            text = text + (i + 1);
            text = text + "  " + orderWithDishes.getDishes().get(i).getDishName();
            text = text + "  " + orderWithDishes.getDishes().get(i).getDishPrice() + "\n";
            totalPrice = totalPrice + orderWithDishes.getDishes().get(i).getDishPrice();
        }
        text = text + "Total Price: " + totalPrice;
        textView.setText(text);
    }

    private void initDishes() {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean("DishesExits", false) == false) {
            Dish[] dishes = new Dish[6];
            dishes[0] = new Dish("豆浆", 35);
            dishes[1] = new Dish("油条", 53);
            dishes[2] = new Dish("炒粉", 24);
            dishes[3] = new Dish("莲花血鸭", 42);
            dishes[4] = new Dish("花蝴蝶", 89);
            dishes[5] = new Dish("小炒肉", 98);
            orderDishDao.insertMultiDishes(dishes);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("DishesExits", true);
            editor.apply();
        }
    }
}
