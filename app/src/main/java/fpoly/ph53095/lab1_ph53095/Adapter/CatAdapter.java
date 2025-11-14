
package fpoly.ph53095.lab1_ph53095.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.ph53095.lab1_ph53095.DTO.CatDTO;
import fpoly.ph53095.lab1_ph53095.R;

public class CatAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CatDTO> list;

    public CatAdapter(Context context, ArrayList<CatDTO> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        }


        CatDTO cat = list.get(position);

        TextView tvId = convertView.findViewById(R.id.tv_id);
        TextView tvName = convertView.findViewById(R.id.tv_name);

        tvId.setText(String.valueOf(cat.getId()));
        tvName.setText(cat.getName());

        return convertView;
    }
}