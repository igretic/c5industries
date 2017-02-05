package com.ivangretic.c5industries;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("C5 Industries");
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("About"));
        tabLayout.addTab(tabLayout.newTab().setText("Products"));
        tabLayout.addTab(tabLayout.newTab().setText("Contact"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    public void call1(View v) {
        Uri number = Uri.parse("tel:+3855552789");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }
    public void call2(View v) {
        Uri number = Uri.parse("tel:+3855552987");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }
    public void maps(View v) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=3880 Greenhouse Rd, Houston, Texas\"");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {


            case R.id.menu_save:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                final View view = factory.inflate(R.layout.about, null);
                builder.setView(view);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                alert.getButton(AlertDialog.BUTTON_POSITIVE) .setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void sendFeedback(View button) {


        final EditText nameField = (EditText) findViewById(R.id.EditTextName);
        String name = nameField.getText().toString();

        final EditText emailField = (EditText) findViewById(R.id.EditTextEmail);
        String email = emailField.getText().toString();

        final EditText feedbackField = (EditText) findViewById(R.id.EditTextFeedbackBody);
        String feedback = feedbackField.getText().toString();

        final Spinner feedbackSpinner = (Spinner) findViewById(R.id.SpinnerFeedbackType);
        String feedbackType = feedbackSpinner.getSelectedItem().toString();


        final CheckBox responseCheckbox = (CheckBox) findViewById(R.id.CheckBoxResponse);
        boolean bRequiresResponse = responseCheckbox.isChecked();

        // Take the fields and format the message contents
        String subject = formatFeedbackSubject(feedbackType);

        String message = formatFeedbackMessage(feedbackType, name,
                email, feedback, bRequiresResponse);

        // Create the message
        sendFeedbackMessage(subject, message);
    }
    protected String formatFeedbackSubject(String feedbackType) {

        String strFeedbackSubjectFormat = getResources().getString(
                R.string.feedbackmessagesubject_format);

        String strFeedbackSubject = String.format(strFeedbackSubjectFormat, feedbackType);

        return strFeedbackSubject;

    }

    protected String formatFeedbackMessage(String feedbackType, String name,
                                           String email, String feedback, boolean bRequiresResponse) {

        String strFeedbackFormatMsg = getResources().getString(
                R.string.feedbackmessagebody_format);

        String strRequiresResponse = getResponseString(bRequiresResponse);

        String strFeedbackMsg = String.format(strFeedbackFormatMsg,
                feedbackType, feedback, name, email, strRequiresResponse);

        return strFeedbackMsg;

    }


    protected String getResponseString(boolean bRequiresResponse)
    {
        if(bRequiresResponse==true)
        {
            return getResources().getString(R.string.feedbackmessagebody_responseyes);
        } else {
            return getResources().getString(R.string.feedbackmessagebody_responseno);
        }

    }

    public void sendFeedbackMessage(String subject, String message) {

        Intent messageIntent = new Intent(android.content.Intent.ACTION_SEND);

        String aEmailList[] = { "ivan.gr3tic@gmail.com" };
        messageIntent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);

        messageIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);

        messageIntent.setType("plain/text");
        messageIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);

        startActivity(messageIntent);
    }
}