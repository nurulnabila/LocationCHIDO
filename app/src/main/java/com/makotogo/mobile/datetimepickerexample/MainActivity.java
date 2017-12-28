/*
 *     Copyright 2016 Makoto Consulting Group, Inc.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */

package com.makotogo.mobile.datetimepickerexample;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import org.joda.time.LocalDateTime;

import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    // State key
    private static final String STATE_LOCAL_DATE_TIME = "state.local.date.time";
    /**
     *
     */
    private LocalDateTime mLocalDateTime = new LocalDateTime();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Bundle exists if we are being recreated
        if (savedInstanceState != null) {
            mLocalDateTime = (LocalDateTime)savedInstanceState.getSerializable(STATE_LOCAL_DATE_TIME);
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) safeFindViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PlacePickerActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) safeFindViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) safeFindViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        // Create the Date/Time text view
        createTextViewDateTime();

        // Create the choose date/time button
        createButtonChooseDateTime();


    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(STATE_LOCAL_DATE_TIME, mLocalDateTime);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        mLocalDateTime = (LocalDateTime)savedInstanceState.getSerializable(STATE_LOCAL_DATE_TIME);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) safeFindViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            return true;
        } else if (id == R.id.action_about) {
            // Fire off About Dialog
            AboutDialog aboutDialog = new AboutDialog(this);
            aboutDialog.setTitle(R.string.action_about);
            aboutDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) safeFindViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void createTextViewDateTime() {
        TextView textView = (TextView)safeFindViewById(R.id.textview_datetime);
        textView.setText(formatDateString(mLocalDateTime));
    }

    private void updateDateTimeTextView() {
        TextView textView = (TextView) safeFindViewById(R.id.textview_datetime);
        textView.setText(formatDateString(mLocalDateTime));
    }

    private void createButtonChooseDateTime() {
        Button button = (Button)safeFindViewById(R.id.button_choosedatetime);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                // If there is already a Date displayed, use that.
                Date dateToUse = (mLocalDateTime == null) ? new LocalDateTime().toDate() : mLocalDateTime.toDate();
                DateTimePickerFragment datePickerFragment =
                        FragmentFactory.createDatePickerFragment(dateToUse, "The", DateTimePickerFragment.BOTH,
                                new DateTimePickerFragment.ResultHandler() {
                                    @Override
                                    public void setDate(Date result) {
                                        mLocalDateTime = new LocalDateTime(result.getTime());
                                        updateDateTimeTextView();
                                    }
                                });
                datePickerFragment.show(fragmentManager, DateTimePickerFragment.DIALOG_TAG);
            }
        });
    }



    private String formatDateString(LocalDateTime localDateTime) {
        return localDateTime.toString("MM/dd/yyyy hh:mm a");
    }

    private View safeFindViewById(int viewId) {
        final String METHOD = "createButtonChooseDateTime(" + viewId + ")";
        View ret = findViewById(viewId);
        if (ret == null) {
            Log.e(TAG, METHOD + "Something has gone horribly wrong.");
        }
        return ret;
    }

}
