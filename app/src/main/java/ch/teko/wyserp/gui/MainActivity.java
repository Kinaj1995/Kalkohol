package ch.teko.wyserp.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.icu.util.ULocale.getName;
import static ch.teko.wyserp.gui.User.age;
import static ch.teko.wyserp.gui.User.gender;
import static ch.teko.wyserp.gui.User.weight;
import static ch.teko.wyserp.gui.User.name;
import static ch.teko.wyserp.gui.User.user;

import android.content.SharedPreferences;




public class MainActivity extends AppCompatActivity implements View.OnClickListener, ExpandableListView.OnChildClickListener {
    ExpandableListView expandableListView;
    List<String> listGroup;
    HashMap<String,List<String>> listItem;
    MainAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);
        initUserData();

        expandableListView = findViewById(R.id.expandable_list);
        listGroup = new ArrayList<>();
        listItem = new HashMap<>();
        adapter = new MainAdapter( this, listGroup,listItem);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnChildClickListener(this);

        initListData();

        Button details = findViewById(R.id.btn_details);
        Button user = findViewById(R.id.btn_user);


        details.setOnClickListener(this);
        user.setOnClickListener(this);
        
    }

    @Override
    protected void onStart() {
        super.onStart();
        initUserData();
    }

    public void initUserData() {
        super.onResume();
        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences(user, Context.MODE_PRIVATE);
        String actName = sharedpreferences.getString(name, "");
        String actAge = sharedpreferences.getString(age, "");
        String actWeight = sharedpreferences.getString(weight, "");
        String actGender = sharedpreferences.getString(gender, "");

        TextView actProfile = (TextView) findViewById(R.id.profile_username);
        actProfile.setText(actName);

    }

    private void initListData() {
        listGroup.add(getString(R.string.group1));
        listGroup.add(getString(R.string.group2));
        listGroup.add(getString(R.string.group3));
        listGroup.add(getString(R.string.group4));

        String[] array;

        List<String> list1 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group1);
                for (String item : array) {
                    list1.add(item);
                }
        List<String> list2 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group2);
        for (String item : array) {
            list2.add(item);
        }
        List<String> list3 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group3);
        for (String item : array) {
            list3.add(item);
        }
        List<String> list4 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group4);
        for (String item : array) {
            list4.add(item);
        }

        listItem.put(listGroup.get(0),list1);
        listItem.put(listGroup.get(1),list2);
        listItem.put(listGroup.get(2),list3);
        listItem.put(listGroup.get(3),list4);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_user:
                startActivity(new Intent(MainActivity.this, User.class));
                break;
            case R.id.btn_details:
                startActivity(new Intent(MainActivity.this, Details.class));
                break;
        }


    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

        switch (groupPosition) {
            case 0:
                System.out.println("Bier");
                if (childPosition == 0) {
                    System.out.println("Stange"); // insert Code to add a "Stange" to your calculation and comment out the sout
                }
                if (childPosition == 1) {
                    System.out.println("Chöbel"); // insert Code to add a "Chöbel" to your calculation and comment out the sout
                    }
                if (childPosition == 2) {
                    System.out.println("Pitcher"); // insert Code to add a "Pitcher" to your calculation and comment out the sout
                }
                break;
            case 1:
                System.out.println("Wein");
                if (childPosition == 0) {
                    System.out.println("Rotwein 5dl"); // insert Code to add a "Rotwein" to your calculation and comment out the sout
                }
                if (childPosition == 1) {
                    System.out.println("Weisswein 5dl"); // insert Code to add a "Weisswein" to your calculation and comment out the sout
                }
                if (childPosition == 2) {
                    System.out.println("Rosé 5dl"); // insert Code to add a "Roseé" to your calculation and comment out the sout
                }
                break;
            case 2:
                System.out.println("Longdrink");
                if (childPosition == 0) {
                    System.out.println("Cuba Libre"); // insert Code to add a "Cuba Libre" to your calculation and comment out the sout
                }
                if (childPosition == 1) {
                    System.out.println("Long Island"); // insert Code to add a "Long Island" to your calculation and comment out the sout
                }
                if (childPosition == 2) {
                    System.out.println("Vodka Lemon"); // insert Code to add a "Vodka Lemon" to your calculation and comment out the sout
                }
                break;
            case 3:
                System.out.println("Shot");
                if (childPosition == 0) {
                    System.out.println("Vodka"); // insert Code to add a "Stange" to your calculation and comment out the sout
                }
                if (childPosition == 1) {
                    System.out.println("Gin"); // insert Code to add a "Stange" to your calculation and comment out the sout
                }
                if (childPosition == 2) {
                    System.out.println("B52"); // insert Code to add a "Stange" to your calculation and comment out the sout
                }
                break;
            default:
                System.out.println("nothing selected");

        }

        Toast.makeText(this, listItem.get(listGroup.get(groupPosition)).get(childPosition) + " hinzugefügt", Toast.LENGTH_SHORT).show();



        return true;
    }




}
