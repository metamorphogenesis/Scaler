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
    private static double inMin, inMax, outMin, outMax, in;
    private static boolean inMinErr, inMaxErr, outMinErr, outMaxErr;
    private TextView inputText, outputText;
    private EditText inMinField, inMaxField, outMinField, outMaxField;
    private SeekBar seekBar;

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

        final SeekBar inputValueBar = seekBar;
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
            calculateInput();
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
            scale();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!inMinErr && !inMaxErr && !outMinErr && !outMaxErr) {
            calculateInput();
            scale();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    public void calculateInput() {
        try {
            if (inMinField.getText().length() > 0) {
                inMinField.setTextSize(18);
            } else {
                inMinField.setTextSize(14);
            }

            inMin = Double.parseDouble(String.valueOf(inMinField.getText()));
            inMinErr = false;
        } catch (Exception e) {
            inMinErr = true;
            inMinField.requestFocus();
            inputText.setTextColor(getResources().getColor(R.color.errorColor, getTheme()));
        }

        try {
            if (inMaxField.getText().length() > 0) {
                inMaxField.setTextSize(18);
            } else {
                inMaxField.setTextSize(14);
            }

            inMax = Double.parseDouble(String.valueOf(inMaxField.getText()));
            inMaxErr = false;
        } catch (Exception e) {
            inMaxErr = true;
            inMaxField.requestFocus();
            inputText.setTextColor(getResources().getColor(R.color.errorColor, getTheme()));
        }

        in = (inMax - inMin) / 1000 * seekBar.getProgress() + inMin;
        inputText.setText(String.format("%.2f", in));
        inputText.setTextColor(getResources().getColor(R.color.inputColor, getTheme()));
    }

    public void scale() {
        try {
            if (outMinField.getText().length() > 0) {
                outMinField.setTextSize(18);
            } else {
                outMinField.setTextSize(14);
            }

            outMin = Double.parseDouble(String.valueOf(outMinField.getText()));
            outMinErr = false;
        } catch (Exception e) {
            outMaxErr = true;
            outMinField.requestFocus();
            outputText.setTextColor(getResources().getColor(R.color.errorColor, getTheme()));
        }

        try {
            if (outMaxField.getText().length() > 0) {
                outMaxField.setTextSize(18);
            } else {
                outMaxField.setTextSize(14);
            }

            outMax = Double.parseDouble(String.valueOf(outMaxField.getText()));
            outMaxErr = false;
        } catch (Exception e) {
            outMaxErr = true;
            outMaxField.requestFocus();
            outputText.setTextColor(getResources().getColor(R.color.errorColor, getTheme()));
        }

        double out = (outMax - outMin) / (inMax - inMin) * (in - inMin) + outMin;
        outputText.setText(String.format("%.2f", out));
        outputText.setTextColor(getResources().getColor(R.color.outputColor, getTheme()));
    }

}