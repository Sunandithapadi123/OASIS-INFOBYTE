package com.example.unitconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText inputValue;
    Spinner spinnerFrom, spinnerTo;
    Button convertButton;
    TextView resultText;

    // Define unit arrays
    String[] lengthUnits = {"Centimeters", "Meters", "Inches", "Feet"};
    String[] weightUnits = {"Grams", "Kilograms", "Pounds"};
    String[] volumeUnits = {"Milliliters", "Liters"};
    String[] defaultUnits = {"Select Unit"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        inputValue = findViewById(R.id.inputValue);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        convertButton = findViewById(R.id.convertButton);
        resultText = findViewById(R.id.resultText);

        // Set adapter for spinnerFrom (Spinner 1)
        ArrayAdapter<String> fromAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getAllUnits());
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(fromAdapter);

        // Set a default adapter for spinnerTo (Spinner 2)
        ArrayAdapter<String> toAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, defaultUnits);
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTo.setAdapter(toAdapter);

        // Update spinnerTo based on the selection in spinnerFrom
        spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedUnit = spinnerFrom.getSelectedItem().toString();
                updateToSpinner(selectedUnit);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No action needed
            }
        });

        // Set click listener for the convert button
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performConversion();
            }
        });
    }

    // Method to get all units (for spinnerFrom)
    private String[] getAllUnits() {
        return new String[]{"Centimeters", "Meters", "Inches", "Feet", "Grams", "Kilograms", "Pounds", "Milliliters", "Liters"};
    }

    // Method to update spinnerTo based on spinnerFrom selection
    private void updateToSpinner(String fromUnit) {
        ArrayAdapter<String> toAdapter;

        switch (fromUnit) {
            case "Centimeters":
            case "Meters":
            case "Inches":
            case "Feet":
                toAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lengthUnits);
                break;
            case "Grams":
            case "Kilograms":
            case "Pounds":
                toAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, weightUnits);
                break;
            case "Milliliters":
            case "Liters":
                toAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, volumeUnits);
                break;
            default:
                toAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, defaultUnits);
                break;
        }

        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTo.setAdapter(toAdapter);
    }

    private void performConversion() {
        String input = inputValue.getText().toString();

        if (input.isEmpty()) {
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
            return;
        }

        double value = Double.parseDouble(input);
        String fromUnit = spinnerFrom.getSelectedItem().toString();
        String toUnit = spinnerTo.getSelectedItem().toString();
        double result = 0.0;

        // Conversion logic
        switch (fromUnit) {
            case "Centimeters":
                if (toUnit.equals("Meters")) result = value / 100;
                else if (toUnit.equals("Inches")) result = value / 2.54;
                else if (toUnit.equals("Feet")) result = value / 30.48;
                break;
            case "Meters":
                if (toUnit.equals("Centimeters")) result = value * 100;
                else if (toUnit.equals("Inches")) result = value * 39.37;
                else if (toUnit.equals("Feet")) result = value * 3.281;
                break;
            case "Grams":
                if (toUnit.equals("Kilograms")) result = value / 1000;
                else if (toUnit.equals("Pounds")) result = value / 453.592;
                break;
            case "Kilograms":
                if (toUnit.equals("Grams")) result = value * 1000;
                else if (toUnit.equals("Pounds")) result = value * 2.205;
                break;
            case "Inches":
                if (toUnit.equals("Centimeters")) result = value * 2.54;
                else if (toUnit.equals("Meters")) result = value / 39.37;
                else if (toUnit.equals("Feet")) result = value / 12;
                break;
            case "Feet":
                if (toUnit.equals("Centimeters")) result = value * 30.48;
                else if (toUnit.equals("Meters")) result = value / 3.281;
                else if (toUnit.equals("Inches")) result = value * 12;
                break;
            case "Pounds":
                if (toUnit.equals("Grams")) result = value * 453.592;
                else if (toUnit.equals("Kilograms")) result = value / 2.205;
                break;
            case "Milliliters":
                if (toUnit.equals("Liters")) result = value / 1000;
                break;
            case "Liters":
                if (toUnit.equals("Milliliters")) result = value * 1000;
                break;
            default:
                result = value; // No conversion needed for same unit
                break;
        }

        resultText.setText("Result: " + result);
    }
}
