package com.example.mihikakapoor.customvib;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //return true;
            //new AlertDialog.Builder(this);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            // set title
            alertDialogBuilder.setTitle("Instructions");

            // set dialog message
            alertDialogBuilder
                    .setMessage("How to Create a Custom Vibration Pattern: Set the durations (in milliseconds) of vibrations and pauses using the sliders or text fields. Sliders allow for durations with increments of 10 & text fields allow for any integer duration within the given range. Duration must be at least 0ms long and no more than 1000ms. Use + button to add an additional pair of sliders (one for the vibration and one for the pause). Value on these new sliders and text fields will originally be set duplicates of the respective previous ones. Use - button to remove most recently added pair of sliders & text fields (and to remove their set durations from the vibration pattern). Test using TEST button.")
                    .setCancelable(false)
                    .setNegativeButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }


        return super.onOptionsItemSelected(item);
    }
}
