package com.lawazia.yourid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lawazia.visitorbook.R;

public class SetNameStep extends Activity {

    private String strMobile;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_set_name);
        strMobile =  getIntent().getStringExtra("MOBILE_NO");

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetNameStep.this, SetPINStep.class);
                intent.putExtra("MOBILE_NO", strMobile);
                intent.putExtra("NAME", ((EditText) findViewById(R.id.editName)).getText().toString());
                startActivity(intent);
            }
        });
    }
}
