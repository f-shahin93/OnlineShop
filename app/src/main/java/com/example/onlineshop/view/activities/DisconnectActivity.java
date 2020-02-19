package com.example.onlineshop.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ActivityDiscconnectBinding;
import com.example.onlineshop.utils.ShopConstants;

public class DisconnectActivity extends AppCompatActivity {

    private ActivityDiscconnectBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_discconnect);


        mBinding.buttonDisconnect.setOnClickListener(view -> {
            boolean result = isOnline(DisconnectActivity.this);

            if (result) {
                setResult(ShopConstants.REQUEST_CODE_DISCONNECT);
                finish();
                //onBackPressed();
            } else {
                Toast.makeText(getApplicationContext(), "لطفا مجددا تلاش کنید!", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public boolean isOnline(Context context) {
        try {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            // باید نال را هم بررسی کنید چون در حالت پرواز نال خواهد بود
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, DisconnectActivity.class);
        return intent;
    }
}
