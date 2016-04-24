package com.lawazia.yourid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.loopj.android.http.*;

import com.lawazia.visitorbook.DetailActivity;
import com.lawazia.visitorbook.R;
import com.lawazia.visitorbook.VisitorSQLiteHelper;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class EnterMobileStep extends Activity {

    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_enter_mobile);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String mobileNo = ((EditText) findViewById(R.id.editMobile)).getText().toString();
                if(entryExists(mobileNo)){
                    Toast.makeText(getApplicationContext(), "This entry already exists", Toast.LENGTH_LONG);
                }
                else {
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.get("http://www.lawazia.in/api/yourid/" + mobileNo, new AsyncHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            try {
                                String resultString = new String(responseBody, "UTF-8");
                                if (resultString.equals("null")) {
                                    //Number does not exists
                                    //Send SMS for verification
                                    String smsCode = "ABC123";
                                    //Intent intent = new Intent(EnterMobileStep.this, EnterCodeStep.class);
                                    Intent intent = new Intent(EnterMobileStep.this, SetNameStep.class);
                                    intent.putExtra("MOBILE_NO", mobileNo);
                                    //intent.putExtra("SMS_CODE", smsCode);
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(EnterMobileStep.this, EnterPINStep.class);
                                    intent.putExtra("MOBILE_NO", mobileNo);
                                    startActivity(intent);
                                }
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

                }
            }
        });
    }

    private boolean entryExists(String mobileNo) {
return  VisitorSQLiteHelper.VisitorDB(this).entryExists(DetailActivity.SheetId,mobileNo);
        }

private boolean mobileNumberExists(String mobileNo) {
        return YourIdRepositoryFactory.Instance(this).getYourInfoByMobile(mobileNo) != null;
        }
        }
