package com.github.aoirint.madplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class IconicListActivity extends AppCompatActivity {
    List<SimpleIconicItem> iconicItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iconic_list);

        Intent intent = getIntent();
        ArrayList<SimpleIconicItem> iconicItems = intent.getParcelableArrayListExtra("iconicItems");
        this.iconicItems = iconicItems;

        RecyclerView listView = findViewById(R.id.listView);
        IconicListAdapter adapter = new IconicListAdapter(this, iconicItems);
        LinearLayoutManager layout = new LinearLayoutManager(this);

        listView.setHasFixedSize(true);
        listView.setLayoutManager(layout);
        listView.setAdapter(adapter);


        findViewById(R.id.backButton).setOnClickListener(view -> {
            finish();
        });

        findViewById(R.id.saveButton).setOnClickListener(view -> {
            saveAndFinish();
        });

    }

    void saveAndFinish() {
        finish();
    }

}
