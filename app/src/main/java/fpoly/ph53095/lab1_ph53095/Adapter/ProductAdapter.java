package fpoly.ph53095.lab1_ph53095.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.ph53095.lab1_ph53095.DAO.CatDAO;
import fpoly.ph53095.lab1_ph53095.DTO.CatDTO;
import fpoly.ph53095.lab1_ph53095.DTO.ProductDTO;
import fpoly.ph53095.lab1_ph53095.R;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ProductDTO> list;
    private CatDAO catDAO;

    public interface OnItemClickListener {
        void onDeleteClick(ProductDTO product, int position);
        void onEditClick(ProductDTO product, int position);
    }

    private OnItemClickListener clickListener;

    public ProductAdapter(Context context, ArrayList<ProductDTO> list) {
        this.context = context;
        this.list = list;
        this.catDAO = new CatDAO(context);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductDTO p = list.get(position);
        holder.tvId.setText("ID: " + p.getId());
        holder.tvName.setText(p.getName());
        holder.tvPrice.setText("Giá: " + String.format("%,.0f", p.getPrice()) + " VNĐ");

        CatDTO cat = catDAO.getCatById(p.getId_cat());
        if (cat != null) {
            holder.tvCategoryName.setText("Thể loại: " + cat.getName());
        }

        holder.imgDelete.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onDeleteClick(p, holder.getAdapterPosition());
            }
        });

        holder.imgEdit.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onEditClick(p, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvName, tvPrice, tvCategoryName;
        ImageView imgDelete, imgEdit;

        public ViewHolder(@NonNull View v) {
            super(v);
            tvId = v.findViewById(R.id.tv_product_id);
            tvName = v.findViewById(R.id.tv_product_name);
            tvPrice = v.findViewById(R.id.tv_product_price);
            tvCategoryName = v.findViewById(R.id.tv_category_name);
            imgDelete = v.findViewById(R.id.img_delete_product);
            imgEdit = v.findViewById(R.id.img_edit_product);
        }
    }
}
