package com.lawazia.visitorbook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends ActionBarActivity {

    VisitorSQLiteHelper dbHelper;
    Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);
        LinearLayout root = (LinearLayout) findViewById(R.id.root);
        book = getBookInfo();
        getSupportActionBar().setTitle(book.getName());
        dbHelper = VisitorSQLiteHelper.VisitorDB(this);

        List<Sheet> sheets = dbHelper.getAllSheets(book.getId());
        Map<String,List<Sheet>> dateWiseSheetMap = new HashMap<String,List<Sheet>>();
        for(Sheet sheet: sheets){
            List<Sheet> sheets1 = dateWiseSheetMap.get(sheet.getCreatedString());
            if(sheets1==null){
                sheets1 = new ArrayList<Sheet>();
                sheets1.add(sheet);
                dateWiseSheetMap.put(sheet.getCreatedString(), sheets1);
            }
            else {
                sheets1.add(sheet);
            }
        }

        for (String created: dateWiseSheetMap.keySet()) {

            TextView textView = new TextView(this);
            textView.setTextSize(20);
            textView.setText(created);
            root.addView(textView);

            ListView viewSheets = new ListView(this);
            viewSheets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    openDetail(((Sheet) parent.getItemAtPosition(position)).getId());
                }
            });
            viewSheets.setAdapter(new ArrayAdapter<Sheet>(this, android.R.layout.simple_list_item_1, android.R.id.text1, dateWiseSheetMap.get(created)));
            root.addView(viewSheets);
        }
    }

    private Book getBookInfo() {
        Book book = new Book();
        book.setId(1);
        book.setName("CrazyCuckoo");
        return book;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
     public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_new) {
            createNew();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createNew() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Create New");
        alert.setMessage("Enter Name");
        final EditText input = new EditText(this);
        alert.setView(input);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Sheet sheet = new Sheet();
                sheet.setBookId(book.getId());
                sheet.setName(input.getText().toString());
                openDetail(dbHelper.addSheet(sheet));//dbHelper.getSheet(sheetId));
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        alert.show();
    }

    private void openDetail(int sheetId) {
        DetailActivity.SheetId = sheetId;
        startActivity(new Intent(HomeActivity.this, DetailActivity.class));
    }
}
