package com.popokis.willyfogmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.popokis.models.Equivalence;

import java.util.List;

public class EquivalencesActivity extends AppCompatActivity {

    private ListView listEquivalencesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equivalences);

        Intent i = getIntent();
        List<Equivalence> list = (List<Equivalence>) i.getSerializableExtra("equivalences");

        listEquivalencesView = (ListView) findViewById(R.id.listEquivalenceView);
        listEquivalencesView.setAdapter(new EquivalenceSearchAdapter(this, list));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.action_profile:
                Intent proIntent = new Intent(this, ProfileActivity.class);
                startActivity(proIntent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
