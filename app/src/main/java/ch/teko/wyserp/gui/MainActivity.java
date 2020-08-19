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
import java.util.ResourceBundle;

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

        Stetho.initializeWithDefaults(this); // Google Chrome Debugger for saved Values
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
        String actName = sharedpreferences.getString(name, "Max Muster");
        String actAge = sharedpreferences.getString(age, "25");
        String actWeight = sharedpreferences.getString(weight, "80");
        String actGender = sharedpreferences.getString(gender, "0");

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

    public float calcNewDrink(float AlcWeight) {                    // jasc
        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences(user, Context.MODE_PRIVATE);
        String actAge = sharedpreferences.getString(age, "");   // actAge is not going to be used in the calculation for now
        String actWeight  = sharedpreferences.getString(weight, "");
        String actGender  = sharedpreferences.getString(gender, "");

        float fWeight = Float.parseFloat(actWeight);
        float result = 0.00f;

        if (actGender == "male") {                                // jasc
            float fGender= 0.68f;
            result = AlcWeight / fWeight / fGender;
        }

        if (actGender == ("female")) {                              // jasc
            float fGender= 0.55f;
            result = AlcWeight / fWeight / fGender;
        }
        
        return result;

    }


    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

        switch (groupPosition) {
            case 0: // Bier:
                // Stange:
                if (childPosition == 0) {
                    /*
                    Beverage:                   Stange Eichhof Lager (URL: https://www.eichhof.ch/biere/klassiker/lager)
                    Volume (V):                 300 ml
                    Volume percentage (e):      4.8 % VOL. = 0.048
                    Density of ethanol (ϱ):     0.8 g/ml
                    */

                    // calculation for ethanol in grams (g): A = V * e * ϱ
                    float ethanol = (300f * 0.048f * 0.8f);

                    // calculation for added blood alcohol concentration (BAC):
                    float addToBAC = calcNewDrink(ethanol);
                    System.out.print("Stange = +");
                    System.out.printf("%.4f%s%n", addToBAC,"‰");
                    }
                // Chöbel:
                if (childPosition == 1) {
                    /*
                    Beverage:                   Chöbel Eichhof Lager (URL: https://www.eichhof.ch/biere/klassiker/lager)
                    Volume (V):                 500 ml
                    Volume percentage (e):      4.8 % VOL. = 0.048
                    Density of ethanol (ϱ):     0.8 g/ml
                    */

                    // calculation for ethanol in grams (g): A = V * e * ϱ
                    float ethanol = (500f * 0.048f * 0.8f);

                    // calculation for added blood alcohol concentration (BAC):
                    float addToBAC = calcNewDrink(ethanol);
                    System.out.print("Chöbel = +");
                    System.out.printf("%.4f%s%n", addToBAC,"‰");
                    }
                // Pitcher:
                if (childPosition == 2) {
                    /*
                    Beverage:                   Pitcher Eichhof Lager (URL: https://www.eichhof.ch/biere/klassiker/lager)
                    Volume (V):                 1800 ml
                    Volume percentage (e):      4.8 % VOL. = 0.048
                    Density of ethanol (ϱ):     0.8 g/ml
                    */

                    // calculation for ethanol in grams (g): A = V * e * ϱ
                    float ethanol = (1800f * 0.048f * 0.8f);

                    // calculation for added blood alcohol concentration (BAC):
                    float addToBAC = calcNewDrink(ethanol);
                    System.out.print("Pitcher = +");
                    System.out.printf("%.4f%s%n", addToBAC,"‰");
                }
                break;

            case 1: // Wein
                // Rotwein:
                if (childPosition == 0) {
                    /*
                    Beverage:                   Rotwein 1dl
                    Volume (V):                 100 ml
                    Volume percentage (e):      13 % VOL. = 0.12
                    Density of ethanol (ϱ):     0.8 g/ml
                    */

                    // calculation for ethanol in grams (g): A = V * e * ϱ
                    float ethanol = (100f * 0.13f * 0.8f);

                    // calculation for added blood alcohol concentration (BAC):
                    float addToBAC = calcNewDrink(ethanol);
                    System.out.print("Rotwein 1dl = +");
                    System.out.printf("%.4f%s%n", addToBAC,"‰");
                }
                // Weisswein:
                if (childPosition == 1) {
                    /*
                    Beverage:                   Weisswein 1dl
                    Volume (V):                 100 ml
                    Volume percentage (e):      12 % VOL. = 0.12
                    Density of ethanol (ϱ):     0.8 g/ml
                    */

                    // calculation for ethanol in grams (g): A = V * e * ϱ
                    float ethanol = (100f * 0.12f * 0.8f);

                    // calculation for added blood alcohol concentration (BAC):
                    float addToBAC = calcNewDrink(ethanol);
                    System.out.print("Weisswein 1dl = +");
                    System.out.printf("%.4f%s%n", addToBAC,"‰");
                }
                // Rosé:
                if (childPosition == 2) {
                    /*
                    Beverage:                   Rosé 1dl
                    Volume (V):                 100 ml
                    Volume percentage (e):      10 % VOL. = 0.10
                    Density of ethanol (ϱ):     0.8 g/ml
                    */

                    // calculation for ethanol in grams (g): A = V * e * ϱ
                    float ethanol = (100f * 0.10f * 0.8f);

                    // calculation for added blood alcohol concentration (BAC):
                    float addToBAC = calcNewDrink(ethanol);
                    System.out.print("Rosé 1dl = +");
                    System.out.printf("%.4f%s%n", addToBAC,"‰");
                }
                break;

            case 2: // Longdrinks:
                // Cuba Libre:
                if (childPosition == 0) {
                    /*
                    Beverage:                   Cuba Libre
                    Volume (V):                 40 ml of Rum
                    Volume percentage (e):      24 % VOL. = 0.24
                    Density of ethanol (ϱ):     0.8 g/ml
                    */

                    // calculation for ethanol in grams (g): A = V * e * ϱ
                    float ethanol = (40f * 0.24f * 0.8f);

                    // calculation for added blood alcohol concentration (BAC):
                    float addToBAC = calcNewDrink(ethanol);
                    System.out.print("Cuba Libre = +");
                    System.out.printf("%.4f%s%n", addToBAC,"‰");
                }
                // Long Island:
                if (childPosition == 1) {
                    /*
                    Beverage:                   Long Island
                    Volume (V):                 300 ml
                    Volume percentage (e):      21 % VOL. = 0.21
                    Density of ethanol (ϱ):     0.8 g/ml
                    */

                    // calculation for ethanol in grams (g): A = V * e * ϱ
                    float ethanol = (300f * 0.21f * 0.8f);

                    // calculation for added blood alcohol concentration (BAC):
                    float addToBAC = calcNewDrink(ethanol);
                    System.out.print("Long Island = +");
                    System.out.printf("%.4f%s%n", addToBAC,"‰");
                }
                // Vodka Lemon:
                if (childPosition == 2) {
                    /*
                    Beverage:                   Vodka Lemon
                    Volume (V):                 40 ml of Wodka
                    Volume percentage (e):      40 % VOL. = 0.40
                    Density of ethanol (ϱ):     0.8 g/ml
                    */

                    // calculation for ethanol in grams (g): A = V * e * ϱ
                    float ethanol = (40f * 0.40f * 0.8f);

                    // calculation for added blood alcohol concentration (BAC):
                    float addToBAC = calcNewDrink(ethanol);
                    System.out.print("Vodka Lemon = +");
                    System.out.printf("%.4f%s%n", addToBAC,"‰");
                }
                break;

            case 3: // Shots:
                // Vodka:
                if (childPosition == 0) {
                    /*
                    Beverage:                   Vodka
                    Volume (V):                 20 ml
                    Volume percentage (e):      40 % VOL. = 0.40
                    Density of ethanol (ϱ):     0.8 g/ml
                    */

                    // calculation for ethanol in grams (g): A = V * e * ϱ
                    float ethanol = (20f * 0.40f * 0.8f);

                    // calculation for added blood alcohol concentration (BAC):
                    float addToBAC = calcNewDrink(ethanol);
                    System.out.print("Vodka Shot = +");
                    System.out.printf("%.4f%s%n", addToBAC,"‰");
                }
                // Gin:
                if (childPosition == 1) {
                    /*
                    Beverage:                   Gin
                    Volume (V):                 20 ml
                    Volume percentage (e):      40 % VOL. = 0.40
                    Density of ethanol (ϱ):     0.8 g/ml
                    */

                    // calculation for ethanol in grams (g): A = V * e * ϱ
                    float ethanol = (20f * 0.40f * 0.8f);

                    // calculation for added blood alcohol concentration (BAC):
                    float addToBAC = calcNewDrink(ethanol);
                    System.out.print("Gin Shot = +");
                    System.out.printf("%.4f%s%n", addToBAC,"‰");
                }
                // B52:
                if (childPosition == 2) {
                    /*
                    Beverage:                   B52
                    Volume (V):                 20 ml
                    Volume percentage (e):      55 % VOL. = 0.55
                    Density of ethanol (ϱ):     0.8 g/ml
                    */

                    // calculation for ethanol in grams (g): A = V * e * ϱ
                    float ethanol = (20f * 0.55f * 0.8f);

                    // calculation for added blood alcohol concentration (BAC):
                    float addToBAC = calcNewDrink(ethanol);
                    System.out.print("B52 Shot = +");
                    System.out.printf("%.4f%s%n", addToBAC,"‰");
                }
                break;

            default:
                System.out.println("nothing selected");

        }

        Toast.makeText(this, listItem.get(listGroup.get(groupPosition)).get(childPosition) + " hinzugefügt", Toast.LENGTH_SHORT).show();



        return true;
    }


}
