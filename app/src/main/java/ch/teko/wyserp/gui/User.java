package ch.teko.wyserp.gui;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Context;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.NumberPicker;
        import android.widget.RadioButton;
        import android.widget.Toast;


public class User extends AppCompatActivity implements View.OnClickListener {

    private NumberPicker pickerAge;
    private NumberPicker pickerWeigt;
    EditText ed1;
    String getGender;
    public static final String user = "user" ;
    public static final String name = "name";
    public static final String age = "age";
    public static final String weight = "weight";
    public static final String gender = "gender";
    public static SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ed1 = (EditText)findViewById(R.id.editProfileName);
        ed1.setText(getName("name"));
        initData();


    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    public void initData() {

        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences(user, Context.MODE_PRIVATE);
        String actAge = sharedpreferences.getString(age, "");
        String actWeight = sharedpreferences.getString(weight, "");
        String actGender = sharedpreferences.getString(gender, "");

        int iAge= Integer.parseInt(actAge);
        int iWeight= Integer.parseInt(actWeight);


        pickerAge = findViewById(R.id.pickerAge);
        pickerAge.setMinValue(0);
        pickerAge.setMaxValue(100);
        pickerAge.setValue(iAge);

        pickerWeigt = findViewById(R.id.pickerWeight);
        pickerWeigt.setMinValue(0);
        pickerWeigt.setMaxValue(100);
        pickerWeigt.setValue(iWeight);


        Button back = findViewById(R.id.btn_back_to_main);
        back.setOnClickListener(this);

        RadioButton male = findViewById(R.id.r_btn_male);
        male.setOnClickListener(this);

        RadioButton female = findViewById(R.id.r_btn_female);
        female.setOnClickListener(this);

        Button userEnter = findViewById(R.id.btn_enter_user);
        userEnter.setOnClickListener(this);

        System.out.println(actGender);

        if (actGender == ("male")) {
            male.setChecked(true);
            female.setChecked(false);
            float fGender= 0.68f;                                   // jasc

        }
        if (actGender == ("female")) {
            female.setChecked(true);
            male.setChecked(false);
            float fGender= 0.55f;                                   // jasc
        }

    }

    public void setUserData(){
        ed1 = (EditText)findViewById(R.id.editProfileName);

        String str_userName = ed1.getText().toString();
        sharedpreferences = getSharedPreferences(user, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        String str_Age = "" + pickerAge.getValue();
        String str_Weight = "" + pickerWeigt.getValue();
        String str_Gender = getGender;

        editor.putString(name, str_userName);
        editor.putString(age, str_Age);
        editor.putString(weight, str_Weight);
        editor.putString(gender, str_Gender);

        editor.commit();

        System.out.println(str_userName);

    }

    public String getName(String sName){
        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences(user, Context.MODE_PRIVATE);
        String User1 = sharedpreferences.getString(sName, "");
        return User1;

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_back_to_main) {
            finish();
        }
        if (v.getId() == R.id.r_btn_male) {
            System.out.println("male");
            getGender = "male";
        }
        if (v.getId() == R.id.r_btn_female) {
            System.out.println("female");
            getGender = "female";
        }
        if (v.getId() == R.id.btn_enter_user) {
            Toast.makeText(this, "Profil gespeichert", Toast.LENGTH_SHORT).show();
            setUserData();
            finish();
        }


    }

}