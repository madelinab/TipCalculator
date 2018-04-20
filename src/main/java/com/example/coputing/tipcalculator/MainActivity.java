package com.example.coputing.tipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity implements TextWatcher, SeekBar.OnSeekBarChangeListener{
    //declare your variables for the widgets
    private EditText editTextBillAmount;
    private TextView textViewBillAmount;

    private SeekBar seekBarTipCounter;
    private TextView textViewTipAmount;

    private TextView textView_displayTip;
    private TextView textView_displayTotal;

    //declare the variables for the calculations
    private double billAmount = 0.0;
    private double percent = 0.15;
    private int progress_value = 15;//starts with the default percentage


    //set the number formats to be used for the $ amounts, and % amounts
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //add listeners to widgets
        editTextBillAmount = (EditText) findViewById(R.id.editText_BillAmount);//this finds your editText and assigns it to the variable
        editTextBillAmount.addTextChangedListener((TextWatcher) this);//adds the listener for the editText

        textViewBillAmount = (TextView) findViewById(R.id.textView_BillAmount);//finds the textView and assigns it to the variable
        textView_displayTip = (TextView) findViewById(R.id.textView_totalTipAmountDisplay);
        textView_displayTotal = (TextView)findViewById(R.id.textView_totalAmountDisplay) ;

        //** SEAK BAR **//
        seekBarMethod();


    }

    public void seekBarMethod(){
        seekBarTipCounter = (SeekBar) findViewById(R.id.seekBar_tipAmount);
        textViewTipAmount = (TextView) findViewById(R.id.textView_tipAmountPercentage);
        seekBarTipCounter.setMax(30);
        seekBarTipCounter.setProgress(progress_value);

        //display percentage of tip on UI
        textViewTipAmount.setText(seekBarTipCounter.getProgress() + "%");//the default value is 15%


        seekBarTipCounter.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        progress_value = i;
                        //textViewTipAmount.setText(progress_value + "%");

                        percent = progress_value / 100.0;
                        Log.d("MainActivity", "percent = " + percent);

                        textViewTipAmount.setText(percentFormat.format(percent));
                       // Toast.makeText(MainActivity.this, "SeekBar in Progress", Toast.LENGTH_LONG).show();

                        Log.d("MainActivity", "going to START the calculate() method!!!");

                        calculate();

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        //Toast.makeText(MainActivity.this, "SeekBar in StartTracking", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        textViewTipAmount.setText(percentFormat.format(percent));
                        //Toast.makeText(MainActivity.this, "SeekBar in StopTracking", Toast.LENGTH_LONG).show();

                    }
                }
        );

    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        //surround risky calculations with try catch(what if BillAmount is 0?
        //charSequence is converted to a String and parsed to a double for you

        try {
            Log.d("MainActivity", "inside onTextChanged method: charSequence = " + charSequence);

            billAmount = Double.parseDouble(charSequence.toString()) /100;
            Log.d("MainActivity", "Bill Amount = " + billAmount);


            //setText on the textView
            textViewBillAmount.setText(currencyFormat.format(billAmount));

        } catch (NumberFormatException e) {
            ///e.printStackTrace();
            textViewBillAmount.setText("");
            billAmount = 0.0;

        }



        //perform tip and total calculation and update UI by calling calculate
        calculate();

    }



    @Override
    public void afterTextChanged(Editable editable) {

    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        progress_value = i;
        //textViewTipAmount.setText(progress_value + "%");

        this.percent = progress_value / 100.0;
        Log.d("MainActivity", "percent = " + percent);

        textViewTipAmount.setText(percentFormat.format(percent));
        Toast.makeText(MainActivity.this, "SeekBar in Progress", Toast.LENGTH_LONG).show();

        Log.d("MainActivity", "going to START the calculate() method!!!");

        calculate();




    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Toast.makeText(MainActivity.this, "SeekBar in StartTracking", Toast.LENGTH_LONG).show();


    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        textViewTipAmount.setText(percentFormat.format(percent));
        Toast.makeText(MainActivity.this, "SeekBar in StopTracking", Toast.LENGTH_LONG).show();


    }

    private void calculate() {
        Log.d("MainActivity", "IM INSIDE THE calculate() METHOD!!!");

       // format percent and display in percentTextView
        textViewTipAmount.setText(percentFormat.format(percent));

        Log.d("MainActivity", "REMINDER:::  percent = " + percent);

        // calculate the tip and total
       double tip = billAmount * percent;
      //use the tip example to do the same for the Total
        Log.d("MainActivity", "tip = " + tip);

       // display tip and total formatted as currency
       //user currencyFormat instead of percentFormat to set the textViewTip
       textView_displayTip.setText(currencyFormat.format(tip));


       //use the tip example to do the same for the Total
        double total = tip + billAmount;
        Log.d("MainActivity", "total = " + total);

        textView_displayTotal.setText(currencyFormat.format(total));

    }


}
