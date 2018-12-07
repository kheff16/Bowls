package edu.ucsb.cs.cs184.kheffernan.bowls.Utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.ucsb.cs.cs184.kheffernan.bowls.BowlsLocalObjects.MenuItem;
import edu.ucsb.cs.cs184.kheffernan.bowls.R;

/**
 * Created by Tatson on 28-Mar-17.
 * http://www.tatnorix.in/android-listview-checkbox-scroll-problem/
 */

public class MenuItemAdapter extends BaseAdapter {

    Context mContext;
    List<MenuItem> linkedList;
    protected LayoutInflater vi;

    private boolean[] checkBoxState = null;
    private HashMap<MenuItem, Boolean> checkedForMenuItem = new HashMap<>();


    public MenuItemAdapter(Context context, List<MenuItem> linkedList) {
        this.mContext = context;
        this.linkedList = linkedList;
        this.vi = (LayoutInflater)context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return linkedList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            convertView = vi.inflate(R.layout.row_layout, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.textView);
            holder.selectionBox = (CheckBox) convertView.findViewById(R.id.checkBox);
            convertView.setTag(holder);

        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        final MenuItem country = linkedList.get(position);
        checkBoxState = new boolean[linkedList.size()];

        holder.title.setText(country.getName());

        /** checkBoxState has the value of checkBox ie true or false,
         * The position is used so that on scroll your selected checkBox maintain its state **/
        if(checkBoxState != null)
            holder.selectionBox.setChecked(checkBoxState[position]);

        holder.selectionBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked()) {
                    checkBoxState[position] = true;
                    ischecked(position,true);
                }
                else {
                    checkBoxState[position] = false;
                    ischecked(position,false);
                }
            }
        });


        /**if country is in checkedForMenuItem then set the checkBox to true **/
        if (checkedForMenuItem.get(country) != null) {
            holder.selectionBox.setChecked(checkedForMenuItem.get(country));
        }

        /**Set tag to all checkBox**/
        holder.selectionBox.setTag(country);

        return convertView;
    }


    private class ViewHolder {
        TextView title;
        CheckBox selectionBox;
    }

    public void ischecked(int position,boolean flag )
    {
        checkedForMenuItem.put(this.linkedList.get(position), flag);
    }


    public LinkedList<String> getSelectedMenuItem(){
        LinkedList<String> List = new LinkedList<>();
        for (Map.Entry<MenuItem, Boolean> pair : checkedForMenuItem.entrySet()) {
            if(pair.getValue()) {
                List.add(pair.getKey().getName());
            }
        }
        return List;
    }
}
