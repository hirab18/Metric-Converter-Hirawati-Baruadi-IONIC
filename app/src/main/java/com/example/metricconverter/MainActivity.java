package com.example.metricconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Spinner metricSpinner, fromUnitSpinner, toUnitSpinner;
    private EditText inputValue;
    private TextView resultText;
    private Button convertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        metricSpinner = findViewById(R.id.metric_spinner);
        fromUnitSpinner = findViewById(R.id.from_unit_spinner);
        toUnitSpinner = findViewById(R.id.to_unit_spinner);
        inputValue = findViewById(R.id.input_value);
        resultText = findViewById(R.id.result_text);
        convertButton = findViewById(R.id.convert_button);

        // Setup metric spinner
        String[] metrics = {"Panjang", "Massa", "Waktu", "Arus Listrik", "Suhu", "Intensitas Cahaya", "Jumlah Zat"};
        ArrayAdapter<String> metricAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, metrics);
        metricAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        metricSpinner.setAdapter(metricAdapter);

        // Listener ketika metrik dipilih
        metricSpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                String selectedMetric = metricSpinner.getSelectedItem().toString();
                updateUnitSpinners(selectedMetric);
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
            }
        });

        // Tombol konversi
        convertButton.setOnClickListener(v -> {
            try {
                double input = Double.parseDouble(inputValue.getText().toString());
                String fromUnit = fromUnitSpinner.getSelectedItem().toString();
                String toUnit = toUnitSpinner.getSelectedItem().toString();
                double result = convertMetric(input, fromUnit, toUnit);

                // Dapatkan singkatan dari satuan tujuan
                String unitAbbreviation = getUnitAbbreviation(toUnit);

                // Tampilkan hasil konversi dengan singkatan satuan
                resultText.setText("Hasil: " + result + " " + unitAbbreviation);
            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "Masukkan nilai yang valid!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Fungsi untuk memperbarui spinner satuan
    private void updateUnitSpinners(String selectedMetric) {
        String[] units;
        switch (selectedMetric) {
            case "Panjang":
                units = new String[]{"Meter", "Centimeter", "Kilometer", "Milimeter", "Mikrometer", "Nanometer"};
                break;
            case "Massa":
                units = new String[]{"Gram", "Kilogram", "Miligram", "Ton", "Pound", "Ounce"};
                break;
            case "Waktu":
                units = new String[]{"Detik", "Menit", "Jam", "Hari"};
                break;
            case "Arus Listrik":
                units = new String[]{"Ampere", "Milliampere", "Kiloampere"};
                break;
            case "Suhu":
                units = new String[]{"Celsius", "Fahrenheit", "Kelvin"};
                break;
            case "Intensitas Cahaya":
                units = new String[]{"Candela"};
                break;
            case "Jumlah Zat":
                units = new String[]{"Mol"};
                break;
            default:
                units = new String[]{};
                break;
        }

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromUnitSpinner.setAdapter(unitAdapter);
        toUnitSpinner.setAdapter(unitAdapter);
    }

    // Fungsi konversi metrik
    private double convertMetric(double value, String fromUnit, String toUnit) {
        double conversionRateFrom = getConversionRate(fromUnit);
        double conversionRateTo = getConversionRate(toUnit);

        // Khusus untuk konversi suhu
        if (fromUnit.equals("Celsius") || fromUnit.equals("Fahrenheit") || fromUnit.equals("Kelvin")) {
            return convertTemperature(value, fromUnit, toUnit);
        }

        return (value * conversionRateFrom) / conversionRateTo;
    }

    // Mengembalikan nilai konversi untuk satuan
    private double getConversionRate(String unit) {
        switch (unit) {
            // Panjang
            case "Meter":
                return 1;
            case "Centimeter":
                return 0.01;
            case "Kilometer":
                return 1000;
            case "Milimeter":
                return 0.001;
            case "Mikrometer":
                return 1e-6;
            case "Nanometer":
                return 1e-9;

            // Massa
            case "Gram":
                return 1;
            case "Kilogram":
                return 1000;
            case "Miligram":
                return 0.001;
            case "Ton":
                return 1_000_000;
            case "Pound":
                return 453.592;
            case "Ounce":
                return 28.3495;

            // Waktu
            case "Detik":
                return 1;
            case "Menit":
                return 60;
            case "Jam":
                return 3600;
            case "Hari":
                return 86400;

            // Arus Listrik
            case "Ampere":
                return 1;
            case "Milliampere":
                return 0.001;
            case "Kiloampere":
                return 1000;

            // Suhu (dalam hal ini kita perlu konversi khusus, tidak bisa hanya pakai rasio)
            case "Celsius":
            case "Fahrenheit":
            case "Kelvin":
                return 0; // Suhu akan dikonversi secara khusus
            default:
                return 1;
        }
    }

    // Fungsi untuk mendapatkan singkatan dari satuan
    private String getUnitAbbreviation(String unit) {
        switch (unit) {
            // Panjang
            case "Meter":
                return "m";
            case "Centimeter":
                return "cm";
            case "Kilometer":
                return "km";
            case "Milimeter":
                return "mm";
            case "Mikrometer":
                return "µm";
            case "Nanometer":
                return "nm";

            // Massa
            case "Gram":
                return "g";
            case "Kilogram":
                return "kg";
            case "Miligram":
                return "mg";
            case "Ton":
                return "t";
            case "Pound":
                return "lb";
            case "Ounce":
                return "oz";

            // Waktu
            case "Detik":
                return "s";
            case "Menit":
                return "min";
            case "Jam":
                return "h";
            case "Hari":
                return "d";

            // Arus Listrik
            case "Ampere":
                return "A";
            case "Milliampere":
                return "mA";
            case "Kiloampere":
                return "kA";

            // Suhu
            case "Celsius":
                return "°C";
            case "Fahrenheit":
                return "°F";
            case "Kelvin":
                return "K";

            // Intensitas Cahaya
            case "Candela":
                return "cd";

            // Jumlah Zat
            case "Mol":
                return "mol";

            default:
                return "";
        }
    }

    // Khusus untuk konversi suhu
    private double convertTemperature(double value, String fromUnit, String toUnit) {
        if (fromUnit.equals("Celsius") && toUnit.equals("Fahrenheit")) {
            return (value * 9/5) + 32;
        } else if (fromUnit.equals("Fahrenheit") && toUnit.equals("Celsius")) {
            return (value - 32) * 5/9;
        } else if (fromUnit.equals("Celsius") && toUnit.equals("Kelvin")) {
            return value + 273.15;
        } else if (fromUnit.equals("Kelvin") && toUnit.equals("Celsius")) {
            return value - 273.15;
        } else if (fromUnit.equals("Fahrenheit") && toUnit.equals("Kelvin")) {
            return (value - 32) * 5/9 + 273.15;
        } else if (fromUnit.equals("Kelvin") && toUnit.equals("Fahrenheit")) {
            return (value - 273.15) * 9/5 + 32;
        }
        return value; // jika konversi suhu tidak diperlukan
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
