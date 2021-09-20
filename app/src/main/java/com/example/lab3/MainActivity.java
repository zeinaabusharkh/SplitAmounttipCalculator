package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.*;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {
    public float tip =0f;
    public float discount= 0;
    public String billAmount ="";
    public int split_number=1;
    Button submitBtn;
    CheckBox memberBox;
    CheckBox weekdayBox;
    CheckBox specialBox;
    SeekBar seekbar_split;
    EditText input;
    TextView totalAmount;
    TextView totalPerPerson;
    RadioGroup radioGroup;
    RadioButton radioButton_10, radioButton_15,radioButton_20;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find and set the elements
        input=findViewById(R.id.userAmount);
        totalAmount=findViewById(R.id.TotalAmount_text);
        totalPerPerson=findViewById(R.id.TotalPerPerson_text);

        // radio group
        radioGroup=findViewById(R.id.radiogroup);
        radioButton_10 = findViewById(R.id.radioButton_10);
        radioButton_15 = findViewById(R.id.radioButton_15);
        radioButton_20 = findViewById(R.id.radioButton_20);

        //check Boxes
        memberBox = findViewById(R.id.memberBox);
        weekdayBox=findViewById(R.id.weekdayBox);
        specialBox=findViewById(R.id.specialBox);

        //Button
        submitBtn = findViewById(R.id.submit_btn);

        //seekbar
        seekbar_split = findViewById(R.id.seekBar_split);

        // set listeners
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tip =0f;
                discount= 0;
                billAmount ="";
                split_number=1;
                calculateAndPrint();
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences("savedValues",MODE_PRIVATE);
        SharedPreferences.Editor saved = sharedPreferences.edit();
        //get the input
        saved.putString("input" , billAmount);

        //save the number of splits
        saved.putInt("split_number" ,split_number);


        //which radio button is checked
        saved.putInt("radioGroupChecked" ,radioGroup.getCheckedRadioButtonId());

        //is checked
        saved.putBoolean("memberBox" , memberBox.isChecked());
        saved.putBoolean("weekdayBox" , weekdayBox.isChecked());
        saved.putBoolean("specialBox" , specialBox.isChecked());

        saved.apply();
        //calculateAndPrint();


    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences getValues = getSharedPreferences("savedValues", MODE_PRIVATE);

        input.setText(getValues.getString("input",""));
        seekbar_split.setProgress(getValues.getInt("split_number",3));
        radioGroup.check(getValues.getInt("radioGroupChecked", 0) );
        memberBox.setChecked(getValues.getBoolean("memberBox",false));
        weekdayBox.setChecked(getValues.getBoolean("weekdayBox",false));
        specialBox.setChecked(getValues.getBoolean("specialBox",false));
        calculateAndPrint();

    }



    public void calculateAndPrint()
    {
        billAmount= input.getText().toString();

        float bill;


        if (billAmount.isEmpty()) {
            bill = 0;
        }
        else
        { bill= Float.parseFloat(billAmount);}

        // get the tip from the radioGroup
        if( radioGroup.getCheckedRadioButtonId() == radioButton_10.getId())
        {
            tip= 0.10f;
        }else if (radioGroup.getCheckedRadioButtonId() == radioButton_15.getId())
        {
            tip= 0.15f;
        }
        else if(radioGroup.getCheckedRadioButtonId()== radioButton_20.getId())
        {
            tip= 0.20f;
        }

        // get the discounts
        if(memberBox.isChecked())
        {
            discount+=0.05f;
        }
        if(weekdayBox.isChecked())
        {
            discount+=0.02f;
        }
        if(specialBox.isChecked())
        {
            discount+=0.03f;
        }

        // get the split number
        split_number=seekbar_split.getProgress();

        float total = (bill+(bill*tip) - (bill*discount) );



        // calculate  the total amount
        totalAmount.setText( "$"+total);
        totalPerPerson.setText("$" +total/split_number);



    };

}