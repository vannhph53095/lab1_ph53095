package fpoly.ph53095.lab1_ph53095.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.ph53095.lab1_ph53095.DTO.CatDTO;
import fpoly.ph53095.lab1_ph53095.R;

public class CatAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CatDTO> list;
    private OnItemInteractionListener listener;

    public interface OnItemInteractionListener {
        void onEditClick(CatDTO category);
        void onDeleteClick(CatDTO category);
    }

    public CatAdapter(Context context, ArrayList<CatDTO> list, OnItemInteractionListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
            holder = new ViewHolder();
            holder.tvId = convertView.findViewById(R.id.tv_id);
            holder.tvName = convertView.findViewById(R.id.tv_name);
            holder.imgEdit = convertView.findViewById(R.id.img_edit);
            holder.imgDelete = convertView.findViewById(R.id.img_delete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CatDTO cat = list.get(position);

        holder.tvId.setText(String.valueOf(cat.getId()));
        holder.tvName.setText(cat.getName());

        holder.imgEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditClick(cat);
            }
        });

        holder.imgDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(cat);
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        TextView tvId, tvName;
        ImageView imgEdit, imgDelete;
    }
}
