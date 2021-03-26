package com.example.scaler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private static double inMin, inMax, outMin, outMax, in, out;
    private static boolean inMinErr, inMaxErr, outMinErr, outMaxErr;
    private TextView inputText, outputText, inputCaution, inputValueText;
    private EditText inMinField, inMaxField, outMinField, outMaxField;
    private SeekBar inputValueBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        inMinField = findViewById(R.id.inMinField);
        inMaxField = findViewById(R.id.inMaxField);
        outMinField = findViewById(R.id.outMinField);
        outMaxField = findViewById(R.id.outMaxField);
        inputText = findViewById(R.id.inputText);
        outputText = findViewById(R.id.outputText);
        inputCaution = findViewById(R.id.inputCaution);
        inputValueText = findViewById(R.id.inputValueLabel);
        inputValueBar = findViewById(R.id.inputValueBar);
        inputValueBar.setMax(1000);
        inputValueBar.setOnSeekBarChangeListener(this);

        inMinField.addTextChangedListener(inputTextWatcher);
        inMaxField.addTextChangedListener(inputTextWatcher);
        outMinField.addTextChangedListener(outputTextWatcher);
        outMaxField.addTextChangedListener(outputTextWatcher);

        calculate();
    }

    TextWatcher inputTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            getInput();
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

    //TODO: fix output refreshing when input get fixed
    //TODO: hide soft keyboard when tap out of the field
    //TODO: change app icon
    //TODO: make splashscreen
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!inMinErr && !inMaxErr && !outMinErr && !outMaxErr) {
            calculate();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    private void getInput() {
        if (inMinField.getText().length() > 0) {
            inMinField.setTextSize(18);
        } else {
            inMinField.setTextSize(14);
        }

        if (inMaxField.getText().length() > 0) {
            inMaxField.setTextSize(18);
        } else {
            inMaxField.setTextSize(14);
        }

        try {
            inMin = Double.parseDouble(String.valueOf(inMinField.getText()));
            inMinErr = false;
        } catch (Exception e) {
            inMinErr = true;
            inMinField.requestFocus();
        }

        try {
            inMax = Double.parseDouble(String.valueOf(inMaxField.getText()));
            inMaxErr = false;
        } catch (Exception e) {
            inMaxErr = true;
            inMaxField.requestFocus();
        }

        if (inMax == inMin) {
            inMaxErr = true;
            in = inMax;
            inMaxField.requestFocus();
            inputCaution.setVisibility(View.VISIBLE);
        } else {
            in = (inMax - inMin) / 1000 * inputValueBar.getProgress() + inMin;
            inputCaution.setVisibility(View.INVISIBLE);
        }

        inputText.setText(String.format("%.2f", in));

        if (inMinErr || inMaxErr) {
            inputText.setTextColor(getResources().getColor(R.color.errorColor, getTheme()));
        } else {
            inputText.setTextColor(getResources().getColor(R.color.inputColor, getTheme()));
        }
    }

    private void scale() {
        if (outMinField.getText().length() > 0) {
            outMinField.setTextSize(18);
        } else {
            outMinField.setTextSize(14);
        }

        if (outMaxField.getText().length() > 0) {
            outMaxField.setTextSize(18);
        } else {
            outMaxField.setTextSize(14);
        }

        try {
            outMin = Double.parseDouble(String.valueOf(outMinField.getText()));
            outMinErr = false;
        } catch (Exception e) {
            outMinErr = true;
            outMinField.requestFocus();
        }

        try {
            outMax = Double.parseDouble(String.valueOf(outMaxField.getText()));
            outMaxErr = false;
        } catch (Exception e) {
            outMaxErr = true;
            outMaxField.requestFocus();
        }

        if (!inMinErr && !inMaxErr) {
            out = (outMax - outMin) / (inMax - inMin) * (in - inMin) + outMin;
            outputText.setText(String.format("%.2f", out));
        }

        if (outMinErr || outMaxErr) {
            outputText.setTextColor(getResources().getColor(R.color.errorColor, getTheme()));
        } else {
            outputText.setTextColor(getResources().getColor(R.color.outputColor, getTheme()));
        }
    }

    public void calculate() {
        getInput();
        scale();
    }

}