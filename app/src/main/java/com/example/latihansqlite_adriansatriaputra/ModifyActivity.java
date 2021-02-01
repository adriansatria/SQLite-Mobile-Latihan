package com.example.latihansqlite_adriansatriaputra;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ModifyActivity extends AppCompatActivity {
    private EditText modTitle;
    private EditText modDesc;
    private long id;
    private boolean isItemDeleted = false;
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_modify);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbManager = new DatabaseManager(this);
        dbManager.open();

        modTitle = findViewById(R.id.modTitle);
        modDesc = findViewById(R.id.modDesc);

        Bundle intentData = getIntent().getExtras();
        final String myID = intentData.getString("Id");
        final String myTitle = intentData.getString("Title");
        final String myDescription = intentData.getString("Desc");
        modTitle.setText(myTitle);
        modDesc.setText(myDescription);
        id = Long.parseLong(myID);

        FloatingActionButton fabDelete = findViewById(R.id.fabDelete);
        FloatingActionButton fabUpdate = findViewById(R.id.fabUpdate);
        fabDelete.setOnClickListener(v -> {
                dbManager.delete(Integer.parseInt(myID));
                setItemDeleted(true);
                returnHome();
        });
        fabUpdate.setOnClickListener(v -> {
            String newTitle = modTitle.getText().toString();
            String newDesc = modDesc.getText().toString();
            dbManager.update(Integer.parseInt(myID), newTitle, newDesc);
            returnHome();
        });
    }

    private void returnHome() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if(isItemDeleted){
            intent.putExtra("itemDeleted", true);
        }
        startActivity(intent);
    }

    public void setItemDeleted(boolean itemDeleted) {
        isItemDeleted = itemDeleted;
    }
}
