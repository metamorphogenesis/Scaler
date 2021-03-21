package com.example.scaler;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private static double inMin, inMax, outMin, outMax, in, out;
    private static EditText inMinField, inMaxField, outMinField, outMaxField;
    private static TextView inputText, outputText;

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

        final SeekBar inputValueBar = findViewById(R.id.inputValueBar);
        inputValueBar.setMin(0);
        inputValueBar.setMax(1000);
        inputValueBar.setOnSeekBarChangeListener(this);

        scale();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        in = (inMax - inMin) / 1000 * seekBar.getProgress() + inMin;
        scale();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    public static void scale() {
        inMin = Double.parseDouble(String.valueOf(inMinField.getText()));
        inMax = Double.parseDouble(String.valueOf(inMaxField.getText()));
        outMin = Double.parseDouble(String.valueOf(outMinField.getText()));
        outMax = Double.parseDouble(String.valueOf(outMaxField.getText()));
        out = (outMax - outMin) / (inMax - inMin) * (in - inMin) + outMin;
        inputText.setText(String.format("%.2f", in));
        outputText.setText(String.format("%.2f", out));
    }
}