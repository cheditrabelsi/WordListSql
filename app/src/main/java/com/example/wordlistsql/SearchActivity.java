package com.example.wordlistsql;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity that searches the database for a string entered by the user.
 * - Displays any strings matching the search term in a text view.
 * - If the search term is empty, prints all the words in the database.
 * - If the word is not found, displays "No result."
 */
public class SearchActivity extends AppCompatActivity {

    private static final String TAG = EditWordActivity.class.getSimpleName();

    private WordListOpenHelper mDB;

    private EditText mEditWordView;
    private TextView mTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mDB = new WordListOpenHelper(this);

        mEditWordView = findViewById(R.id.search_word);
        mTextView = findViewById(R.id.search_result);
    }

    // Click handler for Search button.
    public void showResult(View view) {
        String word = mEditWordView.getText().toString();
        mTextView.setText("Result for " + word + ":\n\n");

        // Search for the word in the database.
        Cursor cursor = mDB.search(word);
        // You must move the cursor to the first item.
        cursor.moveToFirst();
        // Only process a non-null cursor with rows.
        if (cursor != null && cursor.getCount() > 0) {
            int index;
            String result;
            // Iterate over the cursor, while there are entries.
            do {
                // Don't guess at the column index. Get the index for the named column.
                index = cursor.getColumnIndex(WordListOpenHelper.KEY_WORD);
                // Get the value from the column for the current cursor.
                result = cursor.getString(index);
                // Add result to what's already in the text view.
                mTextView.append(result + "\n");
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            mTextView.append(getString(R.string.no_result));
        }
    }
}
