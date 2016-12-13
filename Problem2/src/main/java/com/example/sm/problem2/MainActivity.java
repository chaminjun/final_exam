package com.example.sm.problem2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MyBaseAdapter adapter;
    ListView listview;
    static TextView text_employeeName, text_employeeAge, text_employeeSalary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Employee> emp_list = new ArrayList<>();

//        emp_list.add(new Employee("차", 50, 100));
        text_employeeAge = (TextView)findViewById(R.id.text_employeeAge);
        text_employeeName = (TextView)findViewById(R.id.text_employeeName);
        text_employeeSalary = (TextView)findViewById(R.id.text_employeeSalary);



        adapter = new MyBaseAdapter(this, emp_list);
        listview = (ListView) findViewById(R.id.listView1) ;
        listview.setAdapter(adapter);
        listview.setOnItemClickListener((AdapterView.OnItemClickListener)adapter);
    }
    @Override
    public void onClick(View v){
        EditText edit_name = (EditText) findViewById(R.id.edit_name);
        EditText edit_age = (EditText) findViewById(R.id.edit_age);
        EditText edit_salary = (EditText) findViewById(R.id.edit_salary);

        String employee_name = edit_name.getText().toString();
        int employee_age = Integer.parseInt(edit_age.getText().toString());
        int employee_salary = Integer.parseInt(edit_salary.getText().toString());

        Employee employee = new Employee(employee_name, employee_age, employee_salary);


        switch (v.getId()){
            case R.id.btn_inc:
                // need something here
                break;

            case R.id.btn_dec:
                // need something here
                break;

            case R.id.btn_store:
                adapter.add(employee);
                break;

            case R.id.btn_modify:
                // need something here
                break;

            case R.id.btn_delete:


                break;
        }
    }
}

interface Payment {
    void increase();
    void decrease();
}
