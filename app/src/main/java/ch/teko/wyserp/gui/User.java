package ch.teko.wyserp.gui;

        import androidx.appcompat.app.AppCompatActivity;

        import android.annotation.SuppressLint;
        import android.content.Intent;
        import android.graphics.Paint;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.NumberPicker;



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
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_back_to_main) {
            finish();
        }

    }

}