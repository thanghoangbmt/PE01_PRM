package com.thangha.pe01_thanghase130566;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import provider.BooksContentProvider;

public class OwnContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_content);
    }

    public void clickToAdd(View view) {
        EditText txtTitle = (EditText) findViewById(R.id.edtTitle);
        EditText txtIsbn = (EditText) findViewById(R.id.edtISBN);

        if (txtTitle.getText().toString() == null || txtTitle.getText().toString().isEmpty()) {
            Toast.makeText(getBaseContext(), "Please enter title!", Toast.LENGTH_LONG).show();
            return;
        }

        if (txtIsbn.getText().toString() == null || txtIsbn.getText().toString().isEmpty()) {
            Toast.makeText(getBaseContext(), "Please enter ISBN!", Toast.LENGTH_LONG).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put(BooksContentProvider.KEY_TITLE, txtTitle.getText().toString());
        values.put(BooksContentProvider.KEY_ISBN, txtIsbn.getText().toString());
        try {
            Uri uri = getContentResolver().insert(BooksContentProvider.CONTENT_URI, values);
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
            clickToView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickToView(View view) {
        Uri allTitle = BooksContentProvider.CONTENT_URI;

        Cursor c = null;
        CursorLoader loader = new CursorLoader(this, allTitle, null, null, null, BooksContentProvider.KEY_TITLE + " desc");
        c = loader.loadInBackground();

        if (c.moveToFirst()) {
            String result = "All Book:\n";
            do {
                int id = c.getColumnIndex(BooksContentProvider.KEY_ID);
                int title = c.getColumnIndex(BooksContentProvider.KEY_TITLE);
                int isbn = c.getColumnIndex(BooksContentProvider.KEY_ISBN);

                result += c.getString(id) + " - "
                        + c.getString(title) + " - "
                        + c.getString(isbn) + "\n";
            } while (c.moveToNext());
            TextView txt = (TextView) findViewById(R.id.txtResult);
            txt.setText(result);
        }
    }

    public void clickToUpdate(View view) {
        EditText txtId = (EditText) findViewById(R.id.edtId);
        EditText txtTitle = (EditText) findViewById(R.id.edtTitle);
        EditText txtIsbn = (EditText) findViewById(R.id.edtISBN);

        if (txtId.getText().toString() == null || txtId.getText().toString().isEmpty()) {
            Toast.makeText(getBaseContext(), "Please enter id!", Toast.LENGTH_LONG).show();
            return;
        }

        if (txtTitle.getText().toString() == null || txtTitle.getText().toString().isEmpty()) {
            Toast.makeText(getBaseContext(), "Please enter title!", Toast.LENGTH_LONG).show();
            return;
        }

        if (txtIsbn.getText().toString() == null || txtIsbn.getText().toString().isEmpty()) {
            Toast.makeText(getBaseContext(), "Please enter ISBN!", Toast.LENGTH_LONG).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put(BooksContentProvider.KEY_ID, txtId.getText().toString());
        values.put(BooksContentProvider.KEY_TITLE, txtTitle.getText().toString());
        values.put(BooksContentProvider.KEY_ISBN, txtIsbn.getText().toString());
        try {
            int result = getContentResolver().update(BooksContentProvider.CONTENT_URI, values,
                    BooksContentProvider.KEY_ID + "=" + txtId.getText().toString(), null);
            Toast.makeText(getBaseContext(), result > 0 ? "Update success!" : "Update failed!", Toast.LENGTH_LONG).show();
            clickToView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickToDelete(View view) {
        EditText txtId = (EditText) findViewById(R.id.edtId);
        if (txtId.getText().toString() == null || txtId.getText().toString().isEmpty()) {
            Toast.makeText(getBaseContext(), "Please enter id!", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            int result = getContentResolver().delete(BooksContentProvider.CONTENT_URI,
                    BooksContentProvider.KEY_ID + "=" + txtId.getText().toString(), null);
            Toast.makeText(getBaseContext(), result > 0 ? "Delete success!" : "Delete failed!"
                    , Toast.LENGTH_LONG).show();
            clickToView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}