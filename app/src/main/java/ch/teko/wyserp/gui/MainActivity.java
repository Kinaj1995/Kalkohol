package ch.teko.wyserp.gui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import android.content.SharedPreferences;

import static ch.teko.wyserp.gui.User.gender;
import static ch.teko.wyserp.gui.User.weight;
import static ch.teko.wyserp.gui.User.name;
import static ch.teko.wyserp.gui.User.user;
import static ch.teko.wyserp.gui.User.BAC;
import static ch.teko.wyserp.gui.User.time;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, ExpandableListView.OnChildClickListener, View.OnLongClickListener {
    ExpandableListView expandableListView;
    List<String> listGroup;
    HashMap<String, List<String>> listItem;
    MainAdapter adapter;
    float currentBAC = 0;
    float tts;
    Timestamp timestampOnCreate;
    Timestamp actTimestamp;
    public Dialog popup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // BAC + time vom lokalen Speicher lesen
        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences(user, Context.MODE_PRIVATE);
        String actBAC = sharedpreferences.getString(BAC, "0.0");
        assert actBAC != null;
        this.currentBAC = Float.parseFloat(actBAC);

        Stetho.initializeWithDefaults(this); // Google Chrome Debugger for saved Values
        initUserData();

        expandableListView = findViewById(R.id.expandable_list);
        listGroup = new ArrayList<>();
        listItem = new HashMap<>();
        adapter = new MainAdapter(this, listGroup, listItem);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnChildClickListener(this);

        initListData();

        Button reset = findViewById(R.id.btn_reset);
        Button user = findViewById(R.id.btn_user);


        reset.setOnLongClickListener(this);
        user.setOnClickListener(this);

        this.initTimer();

        this.timestampOnCreate = new Timestamp(System.currentTimeMillis());

        closedAppTime();

        popup = new Dialog(this);
        popup.show();
        popup.setContentView(R.layout.popup);



    }

    @Override
    protected void onStart() {
        super.onStart();
        initUserData();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_user) {
            startActivity(new Intent(MainActivity.this, User.class));
        }

    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == R.id.btn_reset) {
            this.currentBAC = 0.0f;
            setDisplay(0.0f);
            Toast.makeText(this, "Konsumierte Getränke und Alkoholpegel zurückgesetzt", Toast.LENGTH_LONG).show();

            SharedPreferences sharedpreferences;
            sharedpreferences = getSharedPreferences(user, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            String resetBAC = Float.toString(this.currentBAC);
            editor.putString(BAC, resetBAC);
            editor.apply();
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

        float ethanol = 0.0f;

        if (groupPosition == 0) {
            ethanol = this.calcBeerChild(childPosition);
        } else if (groupPosition == 1) {
            ethanol = this.calcWineChild(childPosition);
        } else if (groupPosition == 2) {
            ethanol = this.calcLongdrinksChild(childPosition);
        } else if (groupPosition == 3) {
            ethanol = this.calcShotsChild(childPosition);
        }

        this.setDisplay(ethanol);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Toast.makeText(this, Objects.requireNonNull(listItem.get(listGroup.get(groupPosition))).get(childPosition) + " hinzugefügt", Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    /**
     * childPosition = 0:
     * Beverage:                   Stange Eichhof Lager (URL: https://www.eichhof.ch/biere/klassiker/lager)
     * Volume (V):                 300 ml
     * Volume percentage (e):      4.8 % VOL. = 0.048
     * Density of ethanol (ϱ):     0.8 g/ml
     * <p>
     * childPosition = 1:
     * Beverage:                   Chöbel Eichhof Lager (URL: https://www.eichhof.ch/biere/klassiker/lager)
     * Volume (V):                 500 ml
     * Volume percentage (e):      4.8 % VOL. = 0.048
     * Density of ethanol (ϱ):     0.8 g/ml
     * <p>
     * childPosition = 2:
     * Beverage:                   Pitcher Eichhof Lager (URL: https://www.eichhof.ch/biere/klassiker/lager)
     * Volume (V):                 1800 ml
     * Volume percentage (e):      4.8 % VOL. = 0.048
     * Density of ethanol (ϱ):     0.8 g/ml
     *
     * @param childPos
     * @return
     */
    private float calcBeerChild(int childPos) {

        if (childPos == 0) {
            return this.calcNewDrink(300f * 0.048f * 0.8f);
        } else if (childPos == 1) {
            return this.calcNewDrink(500f * 0.048f * 0.8f);
        } else if (childPos == 2) {
            return this.calcNewDrink(1800f * 0.048f * 0.8f);
        }

        return 0.0f;

    }

    /**
     * childPosition = 0:
     * Beverage:                   Rotwein 1dl
     * Volume (V):                 100 ml
     * Volume percentage (e):      13 % VOL. = 0.12
     * Density of ethanol (ϱ):     0.8 g/ml
     * <p>
     * childPosition = 1:
     * Beverage:                   Weisswein 1dl
     * Volume (V):                 100 ml
     * Volume percentage (e):      12 % VOL. = 0.12
     * Density of ethanol (ϱ):     0.8 g/ml
     * <p>
     * childPosition = 2:
     * Beverage:                   Rosé 1dl
     * Volume (V):                 100 ml
     * Volume percentage (e):      10 % VOL. = 0.10
     * Density of ethanol (ϱ):     0.8 g/ml
     *
     * @param childPos
     * @return
     */
    private float calcWineChild(int childPos) {

        if (childPos == 0) {
            return this.calcNewDrink(100f * 0.13f * 0.8f);
        } else if (childPos == 1) {
            return this.calcNewDrink(100f * 0.12f * 0.8f);
        } else if (childPos == 2) {
            return this.calcNewDrink(100f * 0.10f * 0.8f);
        }

        return 0.0f;

    }

    /**
     * childPosition = 0:
     * Beverage:                   Cuba Libre
     * Volume (V):                 40 ml of Rum
     * Volume percentage (e):      24 % VOL. = 0.24
     * Density of ethanol (ϱ):     0.8 g/ml
     * *
     * *childPosition = 1:
     * Beverage:                   Long Island
     * Volume (V):                 300 ml
     * Volume percentage (e):      21 % VOL. = 0.21
     * Density of ethanol (ϱ):     0.8 g/ml
     * *
     * *childPosition = 2:
     * Beverage:                   Vodka Lemon
     * Volume (V):                 40 ml of Wodka
     * Volume percentage (e):      40 % VOL. = 0.40
     * Density of ethanol (ϱ):     0.8 g/ml
     *
     * @param childPos
     * @return
     */
    private float calcLongdrinksChild(int childPos) {

        if (childPos == 0) {
            return this.calcNewDrink(40f * 0.24f * 0.8f);
        } else if (childPos == 1) {
            return this.calcNewDrink(300f * 0.21f * 0.8f);
        } else if (childPos == 2) {
            return this.calcNewDrink(40f * 0.40f * 0.8f);
        }

        return 0.0f;

    }

    /**
     * childPosition = 0:
     * Beverage:                   Vodka
     * Volume (V):                 20 ml
     * Volume percentage (e):      40 % VOL. = 0.40
     * Density of ethanol (ϱ):     0.8 g/ml
     * *
     * *childPosition = 1:
     * Beverage:                   Gin
     * Volume (V):                 20 ml
     * Volume percentage (e):      40 % VOL. = 0.40
     * Density of ethanol (ϱ):     0.8 g/ml
     * *
     * *childPosition = 2:
     * Beverage:                   B52
     * Volume (V):                 20 ml
     * Volume percentage (e):      55 % VOL. = 0.55
     * Density of ethanol (ϱ):     0.8 g/ml
     *
     * @param childPos
     * @return
     */
    private float calcShotsChild(int childPos) {

        if (childPos == 0) {
            return this.calcNewDrink(20f * 0.40f * 0.8f);
        } else if (childPos == 1) {
            return this.calcNewDrink(20f * 0.40f * 0.8f);
        } else if (childPos == 2) {
            return this.calcNewDrink(20f * 0.55f * 0.8f);
        }

        return 0.0f;
    }

    public void initUserData() {
        super.onResume();
        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences(user, Context.MODE_PRIVATE);
        String actName = sharedpreferences.getString(name, "Max Muster");
        String actGender = sharedpreferences.getString(gender, "0");

        TextView actProfile = findViewById(R.id.profile_username);
        actProfile.setText(actName);

        assert actGender != null;
        if (actGender.equals("0")) {
            Toast.makeText(this, "Erstellen Sie bitte zuerst ein Profil", Toast.LENGTH_SHORT).show();
        }

    }

    private void initListData() {
        listGroup.add(getString(R.string.group1));
        listGroup.add(getString(R.string.group2));
        listGroup.add(getString(R.string.group3));
        listGroup.add(getString(R.string.group4));

        String[] array;

        List<String> list1 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group1);

        Collections.addAll(list1, array);
        List<String> list2 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group2);
        Collections.addAll(list2, array);
        List<String> list3 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group3);
        Collections.addAll(list3, array);
        List<String> list4 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group4);
        Collections.addAll(list4, array);

        listItem.put(listGroup.get(0), list1);
        listItem.put(listGroup.get(1), list2);
        listItem.put(listGroup.get(2), list3);
        listItem.put(listGroup.get(3), list4);
        adapter.notifyDataSetChanged();
    }

    public float calcNewDrink(float AlcWeight) {
        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences(user, Context.MODE_PRIVATE);
        String actWeight = sharedpreferences.getString(weight, "");
        String actGender = sharedpreferences.getString(gender, "");

        assert actWeight != null;
        float fWeight = Float.parseFloat(actWeight);
        float result = 0.00f;

        assert actGender != null;
        if (actGender.equals("male")) {
            float fGender = 0.68f;
            result = AlcWeight / fWeight / fGender;
        }

        if (actGender.equals("female")) {
            float fGender = 0.55f;
            result = AlcWeight / fWeight / fGender;
        }

        return result;

    }

    public void initTimer() {

        Timer timer = new Timer();

        // alcReduction every 60 sec:
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                alcReduction(1);
            }
        }, 0, 60000);

        // Timestamp every 60 sec for calculating alcReduction while app closed:
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                actTimestamp = new Timestamp(System.currentTimeMillis());

                // timestamp im lokalen Speicher speichern
                SharedPreferences sharedpreferences;
                sharedpreferences = getSharedPreferences(user, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                String str_timestamp = actTimestamp.toString();
                editor.putString(time, str_timestamp);
                editor.apply();
            }
        }, 10000, 60000);

    }

    public void closedAppTime(){
        float passedTime;

        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences(user, Context.MODE_PRIVATE);
        String actTime = sharedpreferences.getString(time, "0000-00-00 00:00:00.000");

        assert actTime != null;
        if (!actTime.equals("0000-00-00 00:00:00.000")) {
            this.actTimestamp = Timestamp.valueOf(actTime);
            passedTime = this.timestampOnCreate.getTime() - this.actTimestamp.getTime();
            alcReduction(passedTime / 1000f / 60f);
        }
    }

    public void alcReduction(float elapsedTime) {


        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences(user, Context.MODE_PRIVATE);
        String actGender = sharedpreferences.getString(gender, "");

        float reduction = 0.00f;

        assert actGender != null;
        if (actGender.equals("male")) {
            float fGender = 0.16f / 60;
            reduction = fGender * elapsedTime;
        }

        if (actGender.equals("female")) {
            float fGender = 0.14f / 60;
            reduction = fGender * elapsedTime;
        }

        this.setDisplay((-1) * reduction);
    }

    @SuppressLint("SetTextI18n")
    private void setDisplay(float bac) {

        TextView actBac = findViewById(R.id.state_data);

        this.currentBAC += bac;
        if (this.currentBAC <= 0) {
            this.currentBAC = 0;
        }

        // der currentBAC im lokalen Speicher speichern
        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences(user, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String str_currentBAC = Float.toString(this.currentBAC);
        editor.putString(BAC, str_currentBAC);
        editor.apply();


        this.setNewTimeTillSober();


        float roundedBAC = Math.round(this.currentBAC * 100.0f) / 100.0f;
        actBac.setText(roundedBAC + "‰");

    }

    @SuppressLint("SetTextI18n")
    private void setNewTimeTillSober() {

        if (this.currentBAC <= 0) {
            TextView actTime = findViewById(R.id.time_data);
            actTime.setText(0.0f + "h");
            return;
        }

        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences(user, Context.MODE_PRIVATE);
        String actGender = sharedpreferences.getString(gender, "");

        float fGender = 0.0f;

        assert actGender != null;
        if (actGender.equals("male")) {
            fGender = 0.16f;
        }

        if (actGender.equals("female")) {
            fGender = 0.14f;
        }

        this.tts = this.currentBAC / fGender;
        float roundedTts = Math.round(this.tts * 10.0f) / 10.0f;


        TextView actTime = findViewById(R.id.time_data);
        actTime.setText(roundedTts + "h");

    }


}

