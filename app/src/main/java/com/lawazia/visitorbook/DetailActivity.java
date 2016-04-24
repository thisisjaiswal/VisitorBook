package com.lawazia.visitorbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lawazia.yourid.EnterMobileStep;

import java.text.DateFormat;

public class DetailActivity extends ActionBarActivity {

    public static int SheetId;

    VisitorSQLiteHelper dbHelper;
    android.support.v7.app.ActionBar actionBar;
    Sheet sheet;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detail);
        actionBar = getSupportActionBar();
        dbHelper = VisitorSQLiteHelper.VisitorDB(this);
        sheet = dbHelper.getSheet(SheetId);
        actionBar.setTitle(sheet.getName());
        actionBar.setSubtitle(DateFormat.getDateInstance(DateFormat.FULL).format(sheet.getCreated()));
        adapter = new ArrayAdapter<Entry>(this, android.R.layout.simple_list_item_1, android.R.id.text1, dbHelper.getAllEntries(SheetId));
        ListView viewEntries = (ListView) findViewById(R.id.viewEntries);
        viewEntries.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //String sheetDate  = DateFormat.getDateInstance(DateFormat.FULL).format(sheet.getCreated());
        //String todayDate  = DateFormat.getDateInstance(DateFormat.FULL).format(new Date());

        //if(sheetDate.equals(todayDate)){
            getMenuInflater().inflate(R.menu.menu_detail, menu);
        //}
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
            addNew();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addNew() {
        startActivity(new Intent(DetailActivity.this, EnterMobileStep.class));
        /*() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Enter Mobile No");
        final EditText input = new EditText(this);
        alert.setView(input);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Entry entry = new Entry();
                entry.setSheetId(sheet.getId());
                entry.setYourName(input.getText().toString());
                dbHelper.addEntry(entry);ry);
                adapter.add(entry);
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        alert.show();
        */
    }
}
