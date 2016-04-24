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
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class SetPINStep extends Activity {

    private String strMobile;
    private String strName;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_set_pin);
        strMobile =  getIntent().getStringExtra("MOBILE_NO");
        strName =  getIntent().getStringExtra("NAME");

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String pinEntered = ((EditText) findViewById(R.id.editPin)).getText().toString();
                try {
                    JSONObject jsonParams = new JSONObject();
                    jsonParams.put("Mobile", strMobile);
                    jsonParams.put("Pin", pinEntered);
                    jsonParams.put("Name", strName);

                    StringEntity entity = new StringEntity(jsonParams.toString());

                    AsyncHttpClient client = new AsyncHttpClient();
                    client.post(getApplicationContext(), "http://www.lawazia.in/api/yourid", entity, "application/json", new AsyncHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            try {
                                String idString = new String(responseBody, "UTF-8");

                                YourInfo yourInfo = new YourInfo();
                                yourInfo.setMobile(strMobile);
                                yourInfo.setPin(pinEntered);
                                yourInfo.setName(strName);
                                yourInfo.setId(Integer.parseInt(idString));

                                Entry entry = new Entry();
                                entry.setSheetId(DetailActivity.SheetId);
                                entry.setYourName(yourInfo.getName());
                                entry.setYourMobile(yourInfo.getMobile());
                                VisitorSQLiteHelper.VisitorDB(getApplicationContext()).addEntry(entry);
                                startActivity(new Intent(SetPINStep.this, DetailActivity.class));

                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "ERROR!!!", Toast.LENGTH_LONG);
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Toast.makeText(getApplicationContext(), "ERROR!!!", Toast.LENGTH_LONG);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
