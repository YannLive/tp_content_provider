package com.docdoku.countrycontentresolver;

import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String PROVIDER_PERMISSION = "com.docdoku.training.provider.permission.READ";
    private static final int MY_PERMISSIONS_REQUEST_PROVIDER = 1;

    private static final Uri uri = Uri.parse("content://com.docdoku.training.provider/country");
    private static int counter = 6;

    private Button mButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = findViewById(R.id.add_country);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("_id", counter);
                contentValues.put("name", "Algeria");
                getContentResolver().insert(uri, contentValues);
                counter++;
            }
        });
        if (ContextCompat.checkSelfPermission(this, PROVIDER_PERMISSION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{PROVIDER_PERMISSION},
                    MY_PERMISSIONS_REQUEST_PROVIDER);
        } else {
            getSupportLoaderManager().initLoader(0, null, this);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_PROVIDER: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getSupportLoaderManager().initLoader(0, null, this);
                } else {
                    onBackPressed();
                }
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] proj = {"name"};
        CursorLoader cursorLoader = new CursorLoader(this, uri, proj,
                null, null, "name");
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ListView listView = findViewById(R.id.list);
        data.setNotificationUri(getContentResolver(), uri);
        CountriesAdapter adapter = new CountriesAdapter(data, this);
        listView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
