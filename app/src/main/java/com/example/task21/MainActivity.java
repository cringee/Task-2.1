package com.example.task21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
Spinner conversionSpinner_parent ,conversionSpinner_child, conversionSpinner_child2;
EditText valueToConvert;
TextView resultTextView;

ArrayAdapter<String> adapter_parent;
ArrayAdapter<String> adapter_child;

String[] conversion = {"Length", "Weight","Temperature"};
String[] lengthSource = {"inch","foot","yard","mile"};
String[] weightSource = {"pound","ounce","ton"};
String[] temperatureSource = {"Celsius", "Fahrenheit", "Kelvin"};

    private static final Map<String, ConversionFactor> conversionFactors = new HashMap<>();

    static {
        // Length conversions
        conversionFactors.put("inch-foot", new ConversionFactor(0.083333));
        conversionFactors.put("inch-yard", new ConversionFactor(0.027778));
        conversionFactors.put("inch-mile", new ConversionFactor(1.5783e-5));
        conversionFactors.put("foot-inch", new ConversionFactor(12.0));
        conversionFactors.put("foot-yard", new ConversionFactor(0.333333));
        conversionFactors.put("foot-mile", new ConversionFactor(0.000189394));
        conversionFactors.put("yard-inch", new ConversionFactor(36.0));
        conversionFactors.put("yard-foot", new ConversionFactor(3.0));
        conversionFactors.put("yard-mile", new ConversionFactor(0.000568182));
        conversionFactors.put("mile-inch", new ConversionFactor(63360.0));
        conversionFactors.put("mile-foot", new ConversionFactor(5280.0));
        conversionFactors.put("mile-yard", new ConversionFactor(1760.0));

        // Weight conversions
        conversionFactors.put("pound-ounce", new ConversionFactor(16.0));
        conversionFactors.put("pound-ton", new ConversionFactor(0.0005));
        conversionFactors.put("ounce-pound", new ConversionFactor(0.0625));
        conversionFactors.put("ounce-ton", new ConversionFactor(0.00003125));
        conversionFactors.put("ton-pound", new ConversionFactor(2000.0));
        conversionFactors.put("ton-ounce", new ConversionFactor(32000.0));

        // Temperature conversions
        conversionFactors.put("Celsius-Fahrenheit", new ConversionFactor(1.8, 32.0));
        conversionFactors.put("Celsius-Kelvin", new ConversionFactor(1.0, 273.15));
        conversionFactors.put("Fahrenheit-Celsius", new ConversionFactor(0.555556, -17.7778));
        conversionFactors.put("Fahrenheit-Kelvin", new ConversionFactor(0.555556, 255.372));
        conversionFactors.put("Kelvin-Celsius", new ConversionFactor(1.0, -273.15));
        conversionFactors.put("Kelvin-Fahrenheit", new ConversionFactor(1.8, -459.67));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        conversionSpinner_parent=findViewById(R.id.conversionSpinner_parent);
        conversionSpinner_child=findViewById(R.id.conversionSpinner_child);
        conversionSpinner_child2=findViewById(R.id.conversionSpinner_child2);

        valueToConvert=findViewById(R.id.valueToConvert);
        resultTextView = findViewById(R.id.resultTextView);


        adapter_parent=new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item,conversion);

        conversionSpinner_parent.setAdapter(adapter_parent);

        //child spinner starts

        conversionSpinner_parent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    adapter_child=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,lengthSource);
                }
                if(i == 1){
                    adapter_child=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,weightSource);
                }
                if(i == 2){
                    adapter_child=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,temperatureSource);
                }
                conversionSpinner_child.setAdapter(adapter_child);
                conversionSpinner_child2.setAdapter(adapter_child);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //child spinner ends

    }

    public static double convert(Context context, Double value, String sourceUnit, String destUnit) {
        String conversionKey = sourceUnit + "-" + destUnit;

        if (conversionFactors.containsKey(conversionKey)) {
            ConversionFactor factor = conversionFactors.get(conversionKey);
            return value * factor.getFactor() + factor.getOffset();
        } else {
            String errorMessage = "Conversion from " + sourceUnit + " to " + destUnit + " is not allowed..";
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
            return Double.NaN;
        }
    }

    public void calculateClick(View view)
    {
        String conversion_parent = conversionSpinner_child.getSelectedItem().toString();
        String currentSourceValue = conversionSpinner_child.getSelectedItem().toString();
        String currentDestinationValue = conversionSpinner_child2.getSelectedItem().toString();
        double result = convert(this,Double.parseDouble(valueToConvert.getText().toString()),currentSourceValue,currentDestinationValue);
        resultTextView.setText(Double.toString(result));
        

    }
}