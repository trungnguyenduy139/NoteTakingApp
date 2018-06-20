package com.example.trungnguyen.notetakingapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import org.greenrobot.greendao.query.DeleteQuery;
import org.greenrobot.greendao.query.QueryBuilder;

/**
 * Created by Trung Nguyen on 1/23/2017.
 */
public class NotesProvider extends ContentProvider {

    private static final String AUTHORITY = "com.example.trungnguyen.notetakingapp.notesprovider";
    private static final String BASE_PATH = "notes";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    // Constant to identify the requested operation
    private static final int NOTES = 1;
    private static final int NOTES_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    public static final String CONTENT_ITEM_TYPE = "Note";

    private NoteDao mNoteDao;

//    SQLiteDatabase database;

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, NOTES);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", NOTES_ID);
    }

    @Override
    public boolean onCreate() {
//        DBOpenHelper helper = new DBOpenHelper(getContext());
//        database = helper.getWritableDatabase();

        SQLiteDatabase sqLiteDatabase = new DaoMaster.DevOpenHelper(getContext(), "NOTE")
                .getReadableDatabase();
        DaoMaster daoMaster = new DaoMaster(sqLiteDatabase);
        DaoSession daoSession = daoMaster.newSession();
        mNoteDao = daoSession.getNoteDao();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String selection, String[] strings1, String s1) {
//        return database.query(DBOpenHelper.TABLE_NOTES, DBOpenHelper.ALL_COLUMNS, selection,
//                null, null, null, DBOpenHelper.NOTE_CREATED + " DESC");

        QueryBuilder<Note> builder = mNoteDao.queryBuilder().orderDesc(NoteDao.Properties.MCreateDate);
        if (selection != null) {
            builder.where(NoteDao.Properties.Mid.eq(selection));
        }
        return builder.buildCursor().query();
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
//        long id = database.insert(DBOpenHelper.TABLE_NOTES, null, contentValues);
        String text = (String) contentValues.get(DBOpenHelper.NOTE_TEXT);
        String createDate = (String) contentValues.get(DBOpenHelper.NOTE_CREATED);
        Note note = new Note(text, createDate);
        long id = mNoteDao.insert(note);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        if (s == null) {
            mNoteDao.deleteAll();
            return 1;
        }
//        return database.delete(DBOpenHelper.TABLE_NOTES, s, strings);
        DeleteQuery<Note> builder = mNoteDao.queryBuilder().where(NoteDao.Properties.Mid.eq(s)).buildDelete();
        builder.executeDeleteWithoutDetachingEntities();
        return 1;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String selection, String[] strings) {
        String newNote = (String) contentValues.get(DBOpenHelper.NOTE_TEXT);
        Note note = mNoteDao.queryBuilder().where(NoteDao.Properties.Mid.eq(selection)).unique();
        note.setMNoteText(newNote);
        mNoteDao.update(note);
        return 1;
    }
}
