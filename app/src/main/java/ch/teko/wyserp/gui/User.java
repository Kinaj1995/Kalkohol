package ch.teko.wyserp.gui;

        import androidx.appcompat.app.AppCompatActivity;

        import android.annotation.SuppressLint;
        import android.content.Intent;
        import android.graphics.Paint;
        import android.os.Bundle;
        import android.text.Editable;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.NumberPicker;
        import android.widget.RadioButton;
        import android.widget.Toast;


public class User extends AppCompatActivity implements View.OnClickListener {

    private NumberPicker pickerAgeTen;
    private NumberPicker pickerAgeOne;
    private NumberPicker pickerWeightTen;
    private NumberPicker pickerWeightOne;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        pickerAgeTen = findViewById(R.id.pickerAgeTen);
        pickerAgeTen.setMinValue(0);
        pickerAgeTen.setMaxValue(9);

        pickerAgeOne = findViewById(R.id.pickerAgeOne);
        pickerAgeOne.setMinValue(0);
        pickerAgeOne.setMaxValue(9);

        pickerWeightTen = findViewById(R.id.pickerWeightTen);
        pickerWeightTen.setMinValue(0);
        pickerWeightTen.setMaxValue(9);

        pickerWeightOne = findViewById(R.id.pickerWeightOne);
        pickerWeightOne.setMinValue(0);
        pickerWeightOne.setMaxValue(9);


        Button back = findViewById(R.id.btn_back_to_main);
        back.setOnClickListener(this);

        Button male = findViewById(R.id.r_btn_male);
        male.setOnClickListener(this);

        Button female = findViewById(R.id.r_btn_female);
        female.setOnClickListener(this);

        Button userEnter = findViewById(R.id.btn_enter_user);
        userEnter.setOnClickListener(this);



    }

    public void setUserData(){


    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_back_to_main) {
            finish();
        }
        if (v.getId() == R.id.r_btn_male) {
            System.out.println("male");
        }
        if (v.getId() == R.id.r_btn_female) {
            System.out.println("female");
        }
        if (v.getId() == R.id.btn_enter_user) {
            setUserData();
            Toast.makeText(this, "Profil gespeichert", Toast.LENGTH_SHORT).show();
            finish();
        }


    }



}