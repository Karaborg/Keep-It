package com.example.karaborg.keep;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListActivity extends AppCompatActivity {

    int delete = 0;
    int backPressed = 0;

    ListView listView;
    String userName;

    DatabaseSection DS = new DatabaseSection(this);
    DatabaseSubSection DSS = new DatabaseSubSection(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = (ListView) findViewById(R.id.listView);

        // GET USER NAME
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                userName = null;
            } else {
                userName = extras.getString("USER_NAME_HERE");
            }
        } else {
            userName = (String) savedInstanceState.getSerializable("USER_NAME_HERE");
        }

        listSection(userName);

    }

    public void listSection(final String userName) {

        setTitle("Section List");

        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, DS.sections(userName));
        listView.setAdapter(arrayAdapter);
        DS.close();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (delete == 0) {

                    //Toast.makeText(ListActivity.this, "MESSAGE: " + arrayAdapter.getItem(position), Toast.LENGTH_SHORT).show();
                    listSubSection(arrayAdapter.getItem(position).toString());

                }else if (delete == 1) {

                    DS.deleteSection(arrayAdapter.getItem(position).toString());
                    delete = 0;
                    listSection(userName);

                }
            }
        });
        backPressed = 0;
    }

    public void listSubSection(final String subName) {

        setTitle("Sub Section List");

        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, DSS.Subsections(subName, userName));
        listView.setAdapter(arrayAdapter);
        DS.close();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (delete == 0) {

                    //Toast.makeText(ListActivity.this, "MESSAGE: " + arrayAdapter.getItem(position), Toast.LENGTH_SHORT).show();
                    go(arrayAdapter.getItem(position).toString());

                }else if (delete == 1) {

                    DSS.deleteSubSection(arrayAdapter.getItem(position).toString());
                    delete = 0;
                    listSubSection(subName);

                }
                //listSection(userName);
            }
        });
        backPressed = 1;
    }

    public void go (String choosenSection) {

        Intent intent = new Intent(this, SubSectionActivity.class);
        String beenHere = "old";
        intent.putExtra("BEEN_HERE", beenHere);
        intent.putExtra("SECTION", choosenSection);
        intent.putExtra("USER_NAME", userName);
        startActivity(intent);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_to_sectionlist, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.add_section) {

            Intent intent = new Intent(this, SectionActivity.class);
            intent.putExtra("USER_NAME_HERE", userName);
            startActivity(intent);
            finish();

        }else if (item.getItemId() == R.id.add_subsection) {

            Intent intent = new Intent(this, SubSectionActivity.class);
            String beenHere = "new";
            intent.putExtra("BEEN_HERE", beenHere);
            intent.putExtra("USER_NAME", userName);
            startActivity(intent);
            finish();

        }else if (item.getItemId() == R.id.delete) {

            delete = 1;
            Toast.makeText(ListActivity.this, "Click something to delete", Toast.LENGTH_SHORT).show();

        }else if (item.getItemId() == R.id.add_note){

            Toast.makeText(ListActivity.this, "Coming Soon...", Toast.LENGTH_SHORT).show();
            //finish();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (backPressed == 1){
            listSection(userName);
        }else if (backPressed == 0){

            AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);

            // Set a title for alert dialog
            builder.setTitle("Are you sure you want to exit?");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    backPressed = 9;
                    ListActivity.super.onBackPressed();
                    finish();
                }
            });

            // Set the alert dialog no button click listener
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

        }
    }

}
