package com.example.razorpaydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.razorpay.PaymentResultListener;
import com.razorpay.Razorpay;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    WebView payment_webview;
    Button btpayNow;
    Razorpay razorpay;
    String[] payment = {"card", "emi", "upi"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.addLogAdapter(new AndroidLogAdapter());

        btpayNow = findViewById(R.id.btpayNow);
        payment_webview = findViewById(R.id.payment_webview);


/*

        btpayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                razorpay = new Razorpay(MainActivity.this, "rzp_test_cJEAa6uhvGCgcQ");
              //  payment_webview.setVisibility(View.GONE);
                razorpay.setWebView(payment_webview);
                razorpay.getPaymentMethods(new Razorpay.PaymentMethodsCallback() {
                    @Override
                    public void onPaymentMethodsReceived(String result) {
                        try {
                            JSONObject paymentMethods = new JSONObject(result);
                            Logger.d(paymentMethods);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(String error){
                        Logger.d(error);
                    }
                });

            }
        });
*/

        btpayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                razorpay = new Razorpay(MainActivity.this, "rzp_test_cJEAa6uhvGCgcQ");
                payment_webview.setVisibility(View.GONE);
                razorpay.setWebView(payment_webview);


                try {

                    JSONObject data = new JSONObject();
                    data.put("order_id", "order_CevjVsVvP9sq6j");
                    data.put("currency", "INR");
                    data.put("amount", 150700); // pass in paise (amount: 1000 equals ₹10)
                    data.put("email", "somecustomer@somesite.com");
                    data.put("contact", "9876543210");
                    JSONObject notes = new JSONObject();
                    notes.put("custom_field", "abc");
                    data.put("notes", notes);
                    data.put("method", "All");
                    // Method specific fields
                   // data.put("bank", "HDFC");


                    // Make webview visible before submitting payment details
                    payment_webview.setVisibility(View.VISIBLE);

                    razorpay.submit(data, new PaymentResultListener() {
                        @Override
                        public void onPaymentSuccess(String razorpayPaymentId) {

                            Logger.d("onPaymentSuccess=>" + razorpayPaymentId);
                        }

                        @Override
                        public void onPaymentError(int code, String description) {
                            Logger.d("onPaymentError=>" + description);
                        }
                    });

                } catch (Exception e) {
                    Log.e("", "Error in submitting payment details", e);
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        if (razorpay != null) {
            razorpay.onBackPressed();
        }
        super.onBackPressed();
    }

}
