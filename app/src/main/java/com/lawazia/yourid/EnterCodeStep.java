package com.lawazia.yourid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lawazia.visitorbook.R;

public class EnterCodeStep extends Activity {
    private Button btnLogin;
    private String strMobile;
    private String codeSent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_enter_code);

        strMobile =  getIntent().getStringExtra("MOBILE_NO");
        codeSent =  getIntent().getStringExtra("SMS_CODE");

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codeEntered = ((EditText) findViewById(R.id.editCode)).getText().toString();
                if(codeEntered.equals(codeSent)){
                    Intent intent = new Intent(EnterCodeStep.this, SetNameStep.class);
                    intent.putExtra("MOBILE_NO", strMobile);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"Code is invalid",Toast.LENGTH_SHORT);
                }
            }
        });
    }



}
