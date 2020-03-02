package edu.pdx.cs410J.android.examples;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

public class InternalDataStorageExample extends AppCompatActivity {

    private EditText fileName;
    private EditText fileContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_data_storage_example);
        this.fileName = findViewById(R.id.FileName);
        this.fileContents = findViewById(R.id.FileContents);
    }

    public void loadFile(View view) throws IOException {
        String fileName = getFileName(view);

        if (fileName != null) {
            File dir = getApplicationContext().getFilesDir();
            File file = new File(dir, fileName);
            if (!file.exists()) {
                String message = getString(R.string.file_does_not_exist, fileName);
                Snackbar.make(view, message, 1000).show();

            } else {
                StringWriter writer = new StringWriter();
                FileReader reader = new FileReader(file);
                char[] buffer = new char[256];
                while (reader.read(buffer) != -1) {
                    writer.write(buffer);
                }

                this.fileContents.setText(writer.toString(), TextView.BufferType.EDITABLE);
            }
        }
    }

    private String getFileName(View view) {
        String fileName = this.fileName.getText().toString();
        if ("".equals(fileName)) {
            String message = getString(R.string.no_file_specified);
            Snackbar.make(view, message, 1000).show();
            return null;

        } else {
            return fileName;
        }
    }

    public void saveFile(View view) throws IOException {
        String fileName = getFileName(view);

        if (fileName != null) {
            String text = fileContents.getText().toString();
            if ("".equals(text)) {
                String message = getString(R.string.empty_file_not_allowed);
                Snackbar.make(view, message, 1000).show();

            } else {
                File dir = getApplicationContext().getFilesDir();
                File file = new File(dir, fileName);
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(text);
                    writer.flush();
                }

                String message = getString(R.string.file_save_successful, fileName);
                Snackbar.make(view, message, 1000).show();
            }
        }
    }
}
