package com.lawazia.yourid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lawazia.visitorbook.DetailActivity;
import com.lawazia.visitorbook.R;
import com.lawazia.visitorbook.Entry;
import com.lawazia.visitorbook.VisitorSQLiteHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class EnterPINStep extends Activity {

    private Button btnLogin;
    private String strMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_enter_pin);

        strMobile =  getIntent().getStringExtra("MOBILE_NO");


        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String pinEntered = ((EditText) findViewById(R.id.editPin)).getText().toString();

                AsyncHttpClient client = new AsyncHttpClient();
                client.get("http://www.lawazia.in/api/yourid/" + strMobile, new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        try {
                            String strResult = new String(responseBody, "UTF-8");
                            JSONObject jsonResult = new JSONObject(strResult);
                            YourInfo yourInfo = new YourInfo();
                            yourInfo.setId(jsonResult.getInt("Id"));
                            yourInfo.setMobile(jsonResult.getString("Id"));
                            yourInfo.setPin(jsonResult.getString("Pin"));
                            yourInfo.setName(jsonResult.getString("Name"));

                            if(pinEntered.equals(yourInfo.getPin())){
                                Entry entry = new Entry();
                                entry.setSheetId(DetailActivity.SheetId);
                                entry.setYourName(yourInfo.getName());
                                entry.setYourMobile(yourInfo.getMobile());
                                VisitorSQLiteHelper.VisitorDB(getApplicationContext()).addEntry(entry);
                                startActivity(new Intent(EnterPINStep.this, DetailActivity.class));
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "PIN is invalid", Toast.LENGTH_LONG);
                            }
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "ERROR!!!", Toast.LENGTH_LONG);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(getApplicationContext(), "ERROR!!!", Toast.LENGTH_LONG);
                    }
                });
            }
        });
    }
}
