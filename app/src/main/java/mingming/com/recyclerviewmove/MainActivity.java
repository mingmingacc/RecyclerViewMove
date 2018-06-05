package mingming.com.recyclerviewmove;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static mingming.com.recyclerviewmove.GridViewActivity.startGridViewActivity;
import static mingming.com.recyclerviewmove.ListViewActivity.startListViewActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showListView(View view) {
        startListViewActivity(this);
    }

    public void showGridView(View view) {
        startGridViewActivity(this);
    }
}
