package com.example.trungnguyen.notetakingapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Trung Nguyen on 1/24/2017.
 */
public class NotesCursorAdapter extends CursorAdapter {
    public NotesCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(
                R.layout.note_list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String noteText = cursor.getString(
                cursor.getColumnIndex(DBOpenHelper.NOTE_TEXT));
        String noteCreated = cursor.getString(
                cursor.getColumnIndex(DBOpenHelper.NOTE_CREATED));
        int pos = noteText.indexOf(10);
        if (pos != -1) {
            noteText = noteText.substring(0, pos) + " ...";
        }

        TextView tvNote = (TextView) view.findViewById(R.id.tvNote);
        TextView tvCreated = (TextView) view.findViewById(R.id.tvCreated);
        tvNote.setText(noteText);
        tvCreated.setText(noteCreated);
    }
}
