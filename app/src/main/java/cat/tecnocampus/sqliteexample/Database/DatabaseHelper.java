package cat.tecnocampus.sqliteexample.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import cat.tecnocampus.sqliteexample.Database.Model.ProgrammingLanguage;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "programminglanguages_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ProgrammingLanguage.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ProgrammingLanguage.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public long insertLanguage(String language, String difficulty, String description) {
        //Since we are inserting to our db, we need to getWritableDatabase
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //values to add
        ContentValues values = new ContentValues();
        values.put(ProgrammingLanguage.COLUMN_LANGUAGE, language);
        values.put(ProgrammingLanguage.COLUMN_DIFFICULTY,difficulty);
        values.put(ProgrammingLanguage.COLUMN_DESCRIPTION,description);
        long id = sqLiteDatabase.insert(ProgrammingLanguage.TABLE_NAME, null, values);
        sqLiteDatabase.close();
        return id;
    }
    public int updateLanguage(ProgrammingLanguage language) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ProgrammingLanguage.COLUMN_LANGUAGE, language.getName());
        values.put(ProgrammingLanguage.COLUMN_DIFFICULTY,language.getDifficulty());
        values.put(ProgrammingLanguage.COLUMN_DESCRIPTION,language.getDescription());
        // updating row
        int updatingRow = sqLiteDatabase.update(ProgrammingLanguage.TABLE_NAME, values, ProgrammingLanguage.COLUMN_ID + " = ?",
                new String[]{String.valueOf(language.getId())});
        sqLiteDatabase.close();
        return updatingRow;
    }

    public void deleteLanguage(ProgrammingLanguage language) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(ProgrammingLanguage.TABLE_NAME, ProgrammingLanguage.COLUMN_ID + " = ?",
                new String[]{String.valueOf(language.getId())});
        sqLiteDatabase.close();
    }
    public ProgrammingLanguage getProgrammingLanguage(long id){
        //Since we are not inserting anything, we will need to read from db
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        //If we do not find the desired language we will return an empty object
        ProgrammingLanguage programmingLanguage = new ProgrammingLanguage();
        Cursor cursor = sqLiteDatabase.query(ProgrammingLanguage.TABLE_NAME,
                new String[]{ProgrammingLanguage.COLUMN_ID, ProgrammingLanguage.COLUMN_LANGUAGE,ProgrammingLanguage.COLUMN_DIFFICULTY,ProgrammingLanguage.COLUMN_DESCRIPTION, ProgrammingLanguage.COLUMN_TIMESTAMP},
                ProgrammingLanguage.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null){
            //We found the desired language
            cursor.moveToFirst();
            programmingLanguage.setId(cursor.getInt(cursor.getColumnIndex(ProgrammingLanguage.COLUMN_ID)));
            programmingLanguage.setName(cursor.getString(cursor.getColumnIndex(ProgrammingLanguage.COLUMN_LANGUAGE)));
            programmingLanguage.setDifficulty(cursor.getString(cursor.getColumnIndex(ProgrammingLanguage.COLUMN_DIFFICULTY)));
            programmingLanguage.setDescription(cursor.getString(cursor.getColumnIndex(ProgrammingLanguage.COLUMN_DESCRIPTION)));
            programmingLanguage.setTimestamp(cursor.getString(cursor.getColumnIndex(ProgrammingLanguage.COLUMN_TIMESTAMP)));
            cursor.close();
        }
        return programmingLanguage;
    }

    public List<ProgrammingLanguage> getProgrammingLanguages(){
        List<ProgrammingLanguage> languages = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + ProgrammingLanguage.TABLE_NAME + " ORDER BY " +
                ProgrammingLanguage.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ProgrammingLanguage language = new ProgrammingLanguage();
                language.setId(cursor.getInt(cursor.getColumnIndex(ProgrammingLanguage.COLUMN_ID)));
                language.setName(cursor.getString(cursor.getColumnIndex(ProgrammingLanguage.COLUMN_LANGUAGE)));
                language.setDifficulty(cursor.getString(cursor.getColumnIndex(ProgrammingLanguage.COLUMN_DIFFICULTY)));
                language.setDescription(cursor.getString(cursor.getColumnIndex(ProgrammingLanguage.COLUMN_DESCRIPTION)));
                language.setTimestamp(cursor.getString(cursor.getColumnIndex(ProgrammingLanguage.COLUMN_TIMESTAMP)));
                languages.add(language);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return languages;
    }

    public int getDataCount(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT  * FROM " + ProgrammingLanguage.TABLE_NAME, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
}
