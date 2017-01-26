package com.example.trungnguyen.notetakingapp;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditorActivity extends AppCompatActivity {
    public static final String NOTE_TEXT = "note_text";
    public static final int RESULT_CODE = 1;
    private EditText etNote;
    private String noteFilter;
    private String oldText;
    private int requestCodeTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Intent intent = getIntent();
        etNote = (EditText) findViewById(R.id.etNote);
        requestCodeTag = intent.getIntExtra(MainActivity.REQUEST_CODE_TAG, -1);
        if (requestCodeTag == MainActivity.REQUEST_CODE_EDIT && requestCodeTag != -1) {
            setTitle(getString(R.string.edit_note));
            Uri uri = intent.getParcelableExtra(NotesProvider.CONTENT_ITEM_TYPE);
            noteFilter = DBOpenHelper.NOTE_ID + "=" + uri.getLastPathSegment();

            Cursor cursor = getContentResolver().query(uri,
                    DBOpenHelper.ALL_COLUMNS, noteFilter, null, null);
            cursor.moveToFirst();
            oldText = cursor.getString(cursor.getColumnIndex(DBOpenHelper.NOTE_TEXT));
            etNote.setText(oldText);
            etNote.requestFocus();
        } else etNote.setText("");
    }

    private void updateNote(String newNote) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.NOTE_TEXT, newNote);
        getContentResolver().update(NotesProvider.CONTENT_URI, values, noteFilter, null);
//        Log.d("MainActivity", "Insert Note " + notesUri.getLastPathSegment());
    }

    private void insertNote(String noteText) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.NOTE_TEXT, noteText);
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        Date date = new Date();
        date.parse(Calendar.getInstance().getTime().toString());
        values.put(DBOpenHelper.NOTE_CREATED, formatter.format(date));
        Uri notesUri = getContentResolver().insert(NotesProvider.CONTENT_URI, values);
        Log.d("MainActivity", "Insert Note " + notesUri.getLastPathSegment());
    }

    private void deleteNote() {
        getContentResolver().delete(NotesProvider.CONTENT_URI, noteFilter, null);
    }

    @Override
    public void onBackPressed() {
        String noteText = etNote.getText().toString();
        switch (requestCodeTag) {
            case MainActivity.REQUEST_CODE_ADD_NEW:
                if (!isNoteTextEmpty(noteText))
                    insertNote(noteText);
                break;
            case MainActivity.REQUEST_CODE_EDIT:
                if (!oldText.equals(noteText) && !isNoteTextEmpty(noteText))
                    updateNote(noteText);
                else if (isNoteTextEmpty(noteText))
                    deleteNote();
                break;
            case -1:
                break;
        }
        super.onBackPressed();
    }

    private boolean isNoteTextEmpty(String noteText) {
        char[] noteArr = noteText.toCharArray();
        int count = 0;
        if (noteArr.length > 0)
            for (char i : noteArr) {
                if (i == ' ') {
                    count++;
                }
            }
        if (noteArr.length == 0)
            return true;
        else if (count == noteText.length())
            return true;
        else
            return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.editor_menu, menu);
        return true;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_delete_one) {
            deleteNote();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
