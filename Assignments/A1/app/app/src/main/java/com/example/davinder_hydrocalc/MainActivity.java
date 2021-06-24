package com.example.davinder_hydrocalc;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = this.getClass().getCanonicalName();

    //1 - create an object to represent the view
    private Button btnCalculate;

    private TextView consumptionChargesOutput;
    private TextView onPeakOutput;
    private TextView offPeakOutput;
    private TextView midPeakOutput;
    private TextView regulatoryChargeOutput;
    private TextView hstOutput;
    private TextView rebateOutput;
    private TextView totalOutput;

    private EditText onPeak;
    private EditText offPeak;
    private EditText midPeak;

    //Values to display
    double hydroConsumptionCharge;
    double onPeakCharge;
    double offPeakCharge;
    double midPeakCharge;
    double regulatoryCharge;
    double hstCharge;
    double rebateCharge;
    double totalCharge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //2 - find the reference of the view and associate it with object
        this.btnCalculate = findViewById(R.id.btn_calculate);
        this.btnCalculate.setOnClickListener(this);

        this.onPeak = findViewById(R.id.edit_onPeak_input);
        this.offPeak = findViewById(R.id.edit_offPeak_input);
        this.midPeak = findViewById(R.id.edit_midPeak_input);

        this.consumptionChargesOutput = findViewById(R.id.consumption_charges_output);
        this.onPeakOutput = findViewById(R.id.onPeak_output);
        this.offPeakOutput = findViewById(R.id.offPeak_output);
        this.midPeakOutput = findViewById(R.id.midPeak_output);
        this.regulatoryChargeOutput = findViewById(R.id.regulatory_charges_output);
        this.hstOutput = findViewById(R.id.hst_output);
        this.rebateOutput = findViewById(R.id.rebate_output);
        this.totalOutput = findViewById(R.id.total_amount);
        }

    @Override
    public void onClick(View v) {
        if (v != null){
            switch (v.getId()){
                case R.id.btn_calculate:{

                    Log.d(TAG, "Calculate Button Clicked");

                    calValue();

                    this.consumptionChargesOutput.setText(String.format("$%.2f", hydroConsumptionCharge));
                    this.onPeakOutput.setText(String.format("$%.2f", onPeakCharge));
                    this.offPeakOutput.setText(String.format("$%.2f", offPeakCharge));
                    this.midPeakOutput.setText(String.format("$%.2f", midPeakCharge));
                    this.regulatoryChargeOutput.setText(String.format("$%.2f", regulatoryCharge));
                    this.hstOutput.setText(String.format("$%.2f", hstCharge));
                    this.rebateOutput.setText(String.format("-$%.2f", rebateCharge));
                    this.totalOutput.setText(String.format("$%.2f", totalCharge));

                    break;
                }

            }
        }
    }

    private void calValue(){

        if(checkEmpty()) {
            double tempOnPeak = Double.valueOf(this.onPeak.getText().toString());
            double tempOffPeak = Double.valueOf(this.offPeak.getText().toString());
            double tempMidPeak = Double.valueOf(this.midPeak.getText().toString());

            onPeakCharge = 0.132 * tempOnPeak;
            offPeakCharge = 0.065 * tempOffPeak;
            midPeakCharge = 0.094 * tempMidPeak;
            hydroConsumptionCharge = onPeakCharge + offPeakCharge + midPeakCharge;

            hstCharge = hydroConsumptionCharge * 0.13;
            rebateCharge = hydroConsumptionCharge * 0.08;
            regulatoryCharge = hstCharge - rebateCharge;

            totalCharge = hydroConsumptionCharge + regulatoryCharge;
        }
        else
        {
            Log.d(TAG, "No Input");
        }

    }

    private Boolean checkEmpty(){
        boolean input = true;

        if(this.onPeak.getText().toString().isEmpty()){
            this.onPeak.setError("Can't be empty");
            input = false;
        }
        else if(this.offPeak.getText().toString().isEmpty()){
            this.offPeak.setError("Can't be empty");
            input = false;
        }
        else if(this.midPeak.getText().toString().isEmpty()){
            this.midPeak.setError("Can't be empty");
            input = false;
        }

        return input;
    }
}