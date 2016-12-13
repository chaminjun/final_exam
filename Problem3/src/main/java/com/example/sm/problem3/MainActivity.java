package com.example.sm.problem3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

   static TextView text_name, text_money;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_name = (TextView)findViewById(R.id.text_name);
        text_money = (TextView)findViewById(R.id.text_money);

        ArrayList<CustomerThread> list = new ArrayList<CustomerThread>();
        Manager manager = new Manager();

        for(int i = 0 ; i < 10 ; i++){
            Customer customer = new Customer("Customer" + i);
            CustomerThread ct = new CustomerThread(customer);
            list.add(ct);
            manager.add_customer(customer);
            ct.start();
        }


        for(CustomerThread ct : list){

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) { }
        }

        manager.sort();

        MyBaseAdapter adapter = new MyBaseAdapter(this, manager.list);
        ListView listview = (ListView) findViewById(R.id.listView1) ;
        listview.setAdapter(adapter);


    }
}

class CustomerThread extends Thread{

    Customer customer;

    CustomerThread(Customer customer){
        this.customer = customer;
    }

    public void run(){
        customer.work();
    }
}

abstract class Person{

    static int money = 100000;
    int spent_money = 0;
    abstract void work();
}


class Customer extends Person{

    String name;
    Customer(String name){
        this.name = name;
    }

    @Override
    void work() {
        if(Person.money >= 0) {
            Random mRand = new Random();
            int ran_num = mRand.nextInt(1000);
            spent_money += ran_num;
            money -= ran_num;
        }else{
            return;
        }

    }
}


class Manager extends Person{
    ArrayList <Customer> list = new ArrayList<Customer>();

    void add_customer(Customer customer) {
        list.add(customer);
    }

    void sort(){
        for(int i = 0; i < list.size(); i++){
            for(int j = i+1; j < list.size()-1; j++){
                if(list.get(j).spent_money <= list.get(i).spent_money)
                {
                    int tempmoney = list.get(j).spent_money;
                    list.get(j).spent_money = list.get(i).spent_money;
                    list.get(i).spent_money = tempmoney;
                }
            }
        }
    }

    @Override
    void work() {
        sort();
    }
}

// need something here











