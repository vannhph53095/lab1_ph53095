package fpoly.ph53095.lab1_ph53095;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import fpoly.ph53095.lab1_ph53095.Adapter.CatAdapter;
import fpoly.ph53095.lab1_ph53095.DAO.CatDAO;
import fpoly.ph53095.lab1_ph53095.DTO.CatDTO;

public class CatActivity extends AppCompatActivity implements CatAdapter.OnItemInteractionListener {
    private ListView lvCategory;
    private CatDAO catDAO;
    private ArrayList<CatDTO> catList;
    private CatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);

        lvCategory = findViewById(R.id.lv_category);
        FloatingActionButton fabAddCat = findViewById(R.id.fab_add_cat);

        catDAO = new CatDAO(this);
        catList = catDAO.getALLCat();
        adapter = new CatAdapter(this, catList, this);
        lvCategory.setAdapter(adapter);

        fabAddCat.setOnClickListener(v -> showAddEditDialog(null));
    }

    private void showAddEditDialog(CatDTO category) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit_cat, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        EditText edtCatName = view.findViewById(R.id.edt_cat_name);
        Button btnSave = view.findViewById(R.id.btn_save);
        Button btnCancel = view.findViewById(R.id.btn_cancel);

        if (category != null) {
            edtCatName.setText(category.getName());
        }

        btnSave.setOnClickListener(v -> {
            String name = edtCatName.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tên thể loại", Toast.LENGTH_SHORT).show();
                return;
            }

            if (category == null) { // Add new
                CatDTO newCat = new CatDTO();
                newCat.setName(name);
                long id = catDAO.addCat(newCat);
                if (id > 0) {
                    catList.clear();
                    catList.addAll(catDAO.getALLCat());
                    adapter.notifyDataSetChanged();
                    Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            } else { // Edit
                category.setName(name);
                if (catDAO.updateCat(category)) {
                    catList.clear();
                    catList.addAll(catDAO.getALLCat());
                    adapter.notifyDataSetChanged();
                    Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void showDeleteConfirmDialog(CatDTO category) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa thể loại")
                .setMessage("Bạn có chắc muốn xóa thể loại \"" + category.getName() + "\"? Thao tác này sẽ xóa tất cả sản phẩm thuộc thể loại này.")
                .setPositiveButton("Xóa", (d, w) -> {
                    if (catDAO.deleteCat(category.getId())) {
                        catList.clear();
                        catList.addAll(catDAO.getALLCat());
                        adapter.notifyDataSetChanged();
                        Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Xóa thất bại. Vẫn còn sản phẩm trong thể loại này.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    @Override
    public void onEditClick(CatDTO category) {
        showAddEditDialog(category);
    }

    @Override
    public void onDeleteClick(CatDTO category) {
        showDeleteConfirmDialog(category);
    }
}
