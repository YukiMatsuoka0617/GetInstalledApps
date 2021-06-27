package com.example.getinstalledapps;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import android.widget.ExpandableListView;


public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExpandableListView expandableListView = findViewById(R.id.expandable_list_view);

        CustomExpandableListAdapter adapter = new CustomExpandableListAdapter(this,
                getPackageManager(),
                MakeDataForExpandableList.getInstance(this).getParentList(),
                MakeDataForExpandableList.getInstance(this).getChildList());

        expandableListView.setAdapter(adapter);
    }
}