package mingming.com.recyclerviewmove;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mingming.com.recyclerviewmove.dicider.DividerListItemDecoration;
import mingming.com.recyclerviewmove.recyevent.OnRecyclerItemClickListener;
import mingming.com.recyclerviewmove.recyevent.RecyItemTouchHelperCallback;

public class ListViewActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    List<String> mStringList;
    RecyAdapter mRecyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecyclerView = findViewById(R.id.recy);
        initRecy();
    }

    private void initRecy() {
        if (mStringList == null) {
            mStringList = new ArrayList<>();
        }
        mStringList.addAll(DataManager.getData(15 - mStringList.size()));
        mRecyAdapter = new RecyAdapter(R.layout.activity_list_view,mStringList);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerListItemDecoration(this,LinearLayoutManager.VERTICAL));
        mRecyclerView.setHasFixedSize(true);

        RecyItemTouchHelperCallback itemTouchHelperCallback = new RecyItemTouchHelperCallback(mRecyAdapter);
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        //给RecyclerView设置item的长按监听事件
        mRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mRecyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                RecyAdapter.ViewHolder viewHolder1 = (RecyAdapter.ViewHolder) viewHolder;
                String tvString = viewHolder1.mTextView.getText().toString();
                Toast.makeText(ListViewActivity.this,"逗逗～" + tvString,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(RecyclerView.ViewHolder viewHolder) {
                Toast.makeText(ListViewActivity.this,""+"讨厌，不要老是摸人家啦...",Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(mRecyAdapter);
    }

    public static void startListViewActivity(Context context){
        context.startActivity(new Intent(context,ListViewActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
