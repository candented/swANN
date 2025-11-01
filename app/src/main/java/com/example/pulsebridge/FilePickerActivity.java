
package com.example.pulsebridge;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilePickerActivity extends ListActivity {

    private List<String> item = null;
    private List<String> path = null;
    private String root = "/";
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_picker_activity);
        listView = (ListView) findViewById(android.R.id.list);
        getDir(Environment.getExternalStorageDirectory().getPath());
    }

    private void getDir(String dirPath) {
        item = new ArrayList<String>();
        path = new ArrayList<String>();
        File f = new File(dirPath);
        File[] files = f.listFiles();

        if (!dirPath.equals(root)) {
            item.add(root);
            path.add(root);
            item.add("../");
            path.add(f.getParent());
        }

        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            path.add(file.getPath());
            if (file.isDirectory())
                item.add(file.getName() + "/");
            else
                item.add(file.getName());
        }

        Collections.sort(item);
        Collections.sort(path);

        ArrayAdapter<String> fileList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, item);
        setListAdapter(fileList);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        File file = new File(path.get(position));

        if (file.isDirectory()) {
            if (file.canRead()) {
                getDir(path.get(position));
            } else {
                // Handle folder not readable
            }
        } else {
            // Handle file selection
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result", file.getAbsolutePath());
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }
}
