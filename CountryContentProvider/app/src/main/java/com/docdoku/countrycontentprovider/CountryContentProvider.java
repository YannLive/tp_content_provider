package com.docdoku.countrycontentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.Arrays;
import java.util.LinkedList;

public class CountryContentProvider extends ContentProvider {

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final String AUTHORITY = "com.docdoku.training.provider";
    private static final int COUNTRIES = 1;
    private static final int COUNTRIES_ID = 2;

    static {
        sURIMatcher.addURI(AUTHORITY, DBOpenHelper.COUNTRY_TABLE, COUNTRIES);
        sURIMatcher.addURI(AUTHORITY, DBOpenHelper.COUNTRY_TABLE + "/#", COUNTRIES_ID);
    }


    private DBOpenHelper dbHelper;
    private SQLiteDatabase db;


    @Override
    public boolean onCreate() {
        dbHelper = new DBOpenHelper(getContext());
        db = dbHelper.getWritableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        switch (sURIMatcher.match(uri)) {
            case COUNTRIES:
                db.beginTransaction();
                cursor = db.query(DBOpenHelper.COUNTRY_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
                db.setTransactionSuccessful();
                db.endTransaction();
                return cursor;
            case COUNTRIES_ID:
                db.beginTransaction();
                String selectionFull;

                if (selection == null) {
                    selectionFull = COUNTRIES_ID + "=?";
                } else {
                    selectionFull = COUNTRIES_ID + "=?" + "and " + selection;
                }
                cursor = db.query(DBOpenHelper.COUNTRY_TABLE, projection, selectionFull, selectionArgs, null, null, sortOrder);
                db.endTransaction();
                return cursor;
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (sURIMatcher.match(uri)) {
            case COUNTRIES:
                db.beginTransaction();
                long generatedId = db.insert(DBOpenHelper.COUNTRY_TABLE, null, values);
                db.setTransactionSuccessful();
                db.endTransaction();
                getContext().getContentResolver().notifyChange(uri, null);
                return Uri.withAppendedPath(uri, generatedId + "");

            case COUNTRIES_ID:

        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows = 0;

        switch (sURIMatcher.match(uri)) {
            case COUNTRIES:
                db.beginTransaction();
                rows = db.delete(DBOpenHelper.COUNTRY_TABLE, selection, selectionArgs);
                db.setTransactionSuccessful();
                db.endTransaction();
                break;

            case COUNTRIES_ID:
                String countryId = uri.getLastPathSegment();
                db.beginTransaction();
                String selectionFull;

                if (selection == null) {
                    selectionFull = COUNTRIES_ID + "=?";
                } else {
                    selectionFull = COUNTRIES_ID + "=?" + "and " + selection;
                }

                LinkedList<String> lst = new LinkedList<String>();
                lst.addAll(Arrays.asList(selectionArgs));
                lst.addFirst(countryId);
                String[] selectionArgsFull = lst.toArray(new String[lst.size()]);
                rows = db.delete(DBOpenHelper.COUNTRY_TABLE, selectionFull, selectionArgsFull);
                db.setTransactionSuccessful();
                db.endTransaction();
                break;
        }

        return rows;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
