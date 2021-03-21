package com.example.scaler;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private static double inMin, inMax, outMin, outMax, in, out;
    private static TextView inputText, outputText;
    private static EditText inMinField, inMaxField, outMinField, outMaxField;
    private static SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inMinField = findViewById(R.id.inMinField);
        inMaxField = findViewById(R.id.inMaxField);
        outMinField = findViewById(R.id.outMinField);
        outMaxField = findViewById(R.id.outMaxField);
        inputText = findViewById(R.id.inputText);
        outputText = findViewById(R.id.outputText);
        seekBar = findViewById(R.id.inputValueBar);

        final SeekBar inputValueBar = findViewById(R.id.inputValueBar);
        inputValueBar.setMin(0);
        inputValueBar.setMax(1000);
        inputValueBar.setOnSeekBarChangeListener(this);

        inMinField.addTextChangedListener(inputTextWatcher);
        inMaxField.addTextChangedListener(inputTextWatcher);
        outMinField.addTextChangedListener(outputTextWatcher);
        outMaxField.addTextChangedListener(outputTextWatcher);

        calculateInput();
        scale();
    }

    TextWatcher inputTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                calculateInput();
            } catch (Exception e) {
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    TextWatcher outputTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                scale();
            } catch (Exception e) {
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        calculateInput();
        scale();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    public static void calculateInput() {
        try {
            if (inMinField.getText().length() > 0) {
                inMinField.setTextSize(18);
            } else {
                inMinField.setTextSize(14);
            }

            inMin = Double.parseDouble(String.valueOf(inMinField.getText()));
        } catch (Exception e) {
            inMinField.requestFocus();
        }

        try {
            if (inMaxField.getText().length() > 0) {
                inMaxField.setTextSize(18);
            } else {
                inMaxField.setTextSize(14);
            }


            inMax = Double.parseDouble(String.valueOf(inMaxField.getText()));
        } catch (Exception e) {
            inMaxField.requestFocus();
        }

        in = (inMax - inMin) / 1000 * seekBar.getProgress() + inMin;
        inputText.setText(String.format("%.2f", in));
    }

    public static void scale() {
        try {
            if (outMinField.getText().length() > 0) {
                outMinField.setTextSize(18);
            } else {
                outMinField.setTextSize(14);
            }

            outMin = Double.parseDouble(String.valueOf(outMinField.getText()));
        } catch (Exception e) {
            outMinField.requestFocus();
        }

        try {
            if (outMaxField.getText().length() > 0) {
                outMaxField.setTextSize(18);
            } else {
                outMaxField.setTextSize(14);
            }


            outMax = Double.parseDouble(String.valueOf(outMaxField.getText()));
        } catch (Exception e) {
            outMaxField.requestFocus();
        }

        out = (outMax - outMin) / (inMax - inMin) * (in - inMin) + outMin;
        outputText.setText(String.format("%.2f", out));
    }

}