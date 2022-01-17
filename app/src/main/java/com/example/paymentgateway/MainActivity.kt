package com.example.paymentgateway

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class MainActivity : AppCompatActivity(), PaymentResultListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Checkout.preload(applicationContext)
        val button: Button = findViewById(R.id.btn_pay)
        button.setOnClickListener {
            startPayment()
        }
    }
    private fun startPayment() {
        val activity:Activity = this
        val co = Checkout()
        co.setKeyID("rzp_test_B9SQjIxMQie1VU")
        val samount: EditText = findViewById(R.id.editText)
        val amount: String = samount.text.toString()

        try {
            val options = JSONObject()
            options.put("name","Omkar Nirala")
            options.put("description","Demoing Charges")
            //You can omit the image option to fetch the image from dashboard
            options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("theme.color", "#f50202");
            options.put("currency","INR");
            //options.put("order_id", "order_666666");
            options.put("amount",amount)//pass amount in currency subunits

            val retryObj = JSONObject()
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            val prefill = JSONObject()
            prefill.put("email","omkarnirala@gmail.com")
            prefill.put("contact","7488637101")

            options.put("prefill",prefill)
            co.open(activity,options)
        }catch (e: Exception){
            Toast.makeText(activity,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this,"Payment is successful : " + p0,Toast.LENGTH_LONG).show()
        Log.d("Status", "onPaymentSuccess: "+p0)
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this,"Payment Failed due to error : " + p1,Toast.LENGTH_LONG).show()
        Log.d("Status", "onPaymentError: "+ p1)
    }
}