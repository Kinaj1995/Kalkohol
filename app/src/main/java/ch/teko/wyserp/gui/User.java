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
        import android.widget.RadioGroup;
        import android.widget.Toast;

        import static ch.teko.wyserp.gui.R.id.checked;
        import static ch.teko.wyserp.gui.R.id.r_btn_female;
        import static ch.teko.wyserp.gui.R.id.radio;


public class User extends AppCompatActivity implements View.OnClickListener {

    private NumberPicker pickerAge;
    private NumberPicker pickerWeight;
    EditText ed1;
    public static String getGender = null;
    public static final String user = "user" ;
    public static final String name = "name";
    public static final String age = "age";
    public static final String weight = "weight";
    public static final String gender = "gender";
    public static final String BAC = "BAC";


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
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    public void initData() {
        RadioButton male = findViewById(R.id.r_btn_male);
        male.setOnClickListener(this);
        RadioButton female = findViewById(R.id.r_btn_female);
        female.setOnClickListener(this);
        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences(user, Context.MODE_PRIVATE);
        String actAge = sharedpreferences.getString(age, "25");
        String actWeight = sharedpreferences.getString(weight, "80");
        String actGender = sharedpreferences.getString(gender, "0");

        assert actAge != null;
        int iAge= Integer.parseInt(actAge);
        assert actWeight != null;
        int iWeight= Integer.parseInt(actWeight);

        assert actGender != null;
        if (actGender.equals("male")) {
            System.out.println("male");
            male.setChecked(true);
            female.setChecked(false);
        } else if (actGender.equals("female")){
            System.out.println("female");
            female.setChecked(true);
            male.setChecked(false);
        }


        pickerAge = findViewById(R.id.pickerAge);
        pickerAge.setMinValue(0);
        pickerAge.setMaxValue(100);
        pickerAge.setValue(iAge);

        pickerWeight = findViewById(R.id.pickerWeight);
        pickerWeight.setMinValue(0);
        pickerWeight.setMaxValue(100);
        pickerWeight.setValue(iWeight);

        Button back = findViewById(R.id.btn_back_to_main);
        back.setOnClickListener(this);

        Button userEnter = findViewById(R.id.btn_enter_user);
        userEnter.setOnClickListener(this);
    }

    public void setUserData(){
        RadioButton vMale = findViewById(R.id.r_btn_male);
        RadioButton vFemale = findViewById(R.id.r_btn_female);
        ed1 = (EditText)findViewById(R.id.editProfileName);
        String str_userName = ed1.getText().toString();
        sharedpreferences = getSharedPreferences(user, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        if (vMale.isChecked()){
            getGender = "male";
        }
        else if (vFemale.isChecked()){
            getGender ="female";
        }


        String str_Age = "" + pickerAge.getValue();
        String str_Weight = "" + pickerWeight.getValue();
        String str_Gender = getGender;

        editor.putString(name, str_userName);
        editor.putString(age, str_Age);
        editor.putString(weight, str_Weight);
        editor.putString(gender, str_Gender);

        editor.apply();

        System.out.println(str_userName);

    }

    public String getName(String sName){
        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences(user, Context.MODE_PRIVATE);
        return sharedpreferences.getString(sName, "Max Muster");

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_back_to_main) {
            finish();
        }
         if (v.getId() == R.id.r_btn_male) {
            getGender = "male";
            setUserData();
        }
        if (v.getId() == r_btn_female) {
            getGender = "female";
            setUserData();
        }
        if (v.getId() == R.id.btn_enter_user) {
            Toast.makeText(this, "Profil gespeichert", Toast.LENGTH_SHORT).show();
            setUserData();
            finish();
        }
    }
}