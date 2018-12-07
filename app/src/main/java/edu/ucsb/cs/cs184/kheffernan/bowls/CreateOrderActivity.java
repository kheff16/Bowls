package edu.ucsb.cs.cs184.kheffernan.bowls;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsLocalObjects.MenuItem;
import edu.ucsb.cs.cs184.kheffernan.bowls.Utilities.MenuItemAdapter;

public class CreateOrderActivity extends AppCompatActivity {

    private ListView listView;
    private Button button;

    private HashSet<String> menuItemHashSet = new HashSet<>();
    private List<MenuItem> menuItemLinkedList = new LinkedList<>();
    private  LinkedList<String> selectdmenuItem = new LinkedList<>();
    private MenuItemAdapter adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        listView = (ListView)findViewById(R.id.list_view);
        button = (Button)findViewById(R.id.button);


        for (int i=0; i < 10; i++) {
            menuItemHashSet.add("menuItem" +" "+ String.valueOf(i + 1));
        }

        getData();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectdmenuItem = adapter.getSelectedMenuItem();
                StringBuilder str = new StringBuilder();
                if(selectdmenuItem.size() != 0){
                    Iterator iterator = selectdmenuItem.iterator();
                    while (iterator.hasNext()){
                        str.append(iterator.next().toString());
                    }
                    Toast.makeText(CreateOrderActivity.this, str, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getData(){
        for(String s : menuItemHashSet){
            MenuItem menuItem = new MenuItem();
            menuItem.setName(s);
            menuItemLinkedList.add(menuItem);
        }

        adapter = new MenuItemAdapter(this, menuItemLinkedList);
        listView.setAdapter(adapter);

    }



}
