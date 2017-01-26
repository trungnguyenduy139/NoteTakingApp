package com.example.trungnguyen.notetakingapp;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {
    public static final int REQUEST_CODE_ADD_NEW = 100;
    public static final int REQUEST_CODE_EDIT = 101;
    public static final String REQUEST_CODE_TAG = "request_code_tag";
//    public static final String REQUEST_CODE_TAG_2 = "request_code_tag_2";
//    private static final String NOTE_TEXT = "note_text";

    ListView lvNote;
    CursorAdapter cursorAdapter;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvNote = (ListView) findViewById(R.id.lvNote);
//        insertNote("New note");

//        Cursor cursor = getContentResolver().query(NotesProvider.CONTENT_URI, DBOpenHelper.ALL_COLUMNS,
//                null, null, null, null);

//        String[] from = {DBOpenHelper.NOTE_TEXT};
//        int[] to = {R.id.tvNote};
        cursorAdapter = new NotesCursorAdapter(this, null, 0);
        lvNote.setAdapter(cursorAdapter);
        getLoaderManager().initLoader(0, null, this);
        lvNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intentEdit = new Intent(MainActivity.this, EditorActivity.class);
                Uri uri = Uri.parse(NotesProvider.CONTENT_URI + "/" + id);
                intentEdit.putExtra(NotesProvider.CONTENT_ITEM_TYPE, uri);
                intentEdit.putExtra(REQUEST_CODE_TAG, REQUEST_CODE_EDIT);
                startActivityForResult(intentEdit, REQUEST_CODE_EDIT);
            }
        });
    }

//    private void insertNote(String noteText) {
//        ContentValues values = new ContentValues();
//        values.put(DBOpenHelper.NOTE_TEXT, noteText);
//        Uri notesUri = getContentResolver().insert(NotesProvider.CONTENT_URI, values);
//        Log.d("MainActivity", "Insert Note " + notesUri.getLastPathSegment());
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_create_new:
//                insertNote("New Noteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                ;
                Intent intentAddNew = new Intent(MainActivity.this, EditorActivity.class);
                intentAddNew.putExtra(REQUEST_CODE_TAG, REQUEST_CODE_ADD_NEW);
                startActivityForResult(intentAddNew, REQUEST_CODE_ADD_NEW);
                break;
            case R.id.action_deleteAll:
                deleteAllNote();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAllNote() {
        DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int button) {
                if (button == DialogInterface.BUTTON_POSITIVE) {
                    getContentResolver().delete(NotesProvider.CONTENT_URI, null, null);
                    restartLoader();

                    Toast.makeText(MainActivity.this,
                            getString(R.string.all_deleted),
                            Toast.LENGTH_SHORT).show();
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.are_you_sure))
                .setPositiveButton(getString(android.R.string.yes), dialog)
                .setNegativeButton(getString(android.R.string.no), dialog)
                .show();
    }

    private void restartLoader() {
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public android.content.Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this, NotesProvider.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor cursor) {
        cursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == EditorActivity.RESULT_CODE) {
//            switch (requestCode) {
//                case REQUEST_CODE_ADD_NEW:
//                    String newNote = data.getStringExtra(EditorActivity.NOTE_TEXT);
//                    if (!newNote.equals("")) {
//                        insertNote(newNote);
//                        restartLoader();
//                    }
//                    break;
//                case REQUEST_CODE_EDIT:
//                    restartLoader();
//                    break;
//            }
        restartLoader();
    }
}
