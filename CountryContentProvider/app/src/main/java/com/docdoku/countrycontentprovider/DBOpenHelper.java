package com.docdoku.countrycontentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

	static String NAME_DATABASE = "country.db";
	final static int DB_VERSION = 1;
	final static String COUNTRY_TABLE = "country";

	private static final String CREATE_TABLES
		= "CREATE TABLE country (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)";

	private static final String DROP_TABLES
		= "DROP TABLE country";

	public DBOpenHelper(Context context) {
		super(context, NAME_DATABASE, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLES);

        db.execSQL("INSERT INTO country VALUES (0, 'Allemagne')");
        db.execSQL("INSERT INTO country VALUES (1, 'Belgique')");
        db.execSQL("INSERT INTO country VALUES (2, 'Chine')");
        db.execSQL("INSERT INTO country VALUES (3, 'Danemark')");
        db.execSQL("INSERT INTO country VALUES (4, 'Espagne')");
        db.execSQL("INSERT INTO country VALUES (5, 'France')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DROP_TABLES);
		db.execSQL(CREATE_TABLES);

        db.execSQL("INSERT INTO country VALUES (0, 'Allemagne')");
        db.execSQL("INSERT INTO country VALUES (1, 'Belgique')");
        db.execSQL("INSERT INTO country VALUES (2, 'Chine')");
        db.execSQL("INSERT INTO country VALUES (3, 'Danemark')");
        db.execSQL("INSERT INTO country VALUES (4, 'Espagne')");
        db.execSQL("INSERT INTO country VALUES (5, 'France')");
	}
}
