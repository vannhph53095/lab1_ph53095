package fpoly.ph53095.lab1_ph53095;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import fpoly.ph53095.lab1_ph53095.Adapter.ProductAdapter;
import fpoly.ph53095.lab1_ph53095.DAO.CatDAO;
import fpoly.ph53095.lab1_ph53095.DAO.ProductDAO;
import fpoly.ph53095.lab1_ph53095.DTO.CatDTO;
import fpoly.ph53095.lab1_ph53095.DTO.ProductDTO;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rc_product;
    private ProductDAO productDAO;
    private CatDAO catDAO;
    private ProductAdapter productAdapter;
    private ArrayList<ProductDTO> productList;
    private ArrayList<CatDTO> catList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rc_product = findViewById(R.id.rc_product);
        FloatingActionButton fab_add_product = findViewById(R.id.fab_add_product);
        Button btn_open_category = findViewById(R.id.btn_open_category);

        productDAO = new ProductDAO(this);
        catDAO = new CatDAO(this);
        productDAO.getAllProducts(); // Kích hoạt DB

        catList = catDAO.getALLCat();

        if (catList.isEmpty()) {
            Toast.makeText(this, "Chưa có thể loại! Vui lòng thêm trước.", Toast.LENGTH_LONG).show();
        } else {
            loadProductsByCat(catList.get(0).getId());
        }

        btn_open_category.setOnClickListener(v -> startActivity(new Intent(this, CatActivity.class)));

        fab_add_product.setOnClickListener(v -> {
            if (catList.isEmpty()) {
                Toast.makeText(this, "Bạn cần thêm thể loại trước!", Toast.LENGTH_SHORT).show();
            } else {
                showAddProductDialog();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        catList = catDAO.getALLCat();
        if (!catList.isEmpty()) {
            loadProductsByCat(catList.get(0).getId());
        }
    }

    private void loadProductsByCat(int catId) {
        productList = productDAO.getProductsByCatId(catId);
        productAdapter = new ProductAdapter(this, productList);
        rc_product.setLayoutManager(new LinearLayoutManager(this));
        rc_product.setAdapter(productAdapter);

        productAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(ProductDTO product, int position) {
                showDeleteConfirmDialog(product, position);
            }

            @Override
            public void onEditClick(ProductDTO product, int position) {
                showEditProductDialog(product, position);
            }
        });
    }


    private void showAddProductDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit_product, null);
        builder.setView(view);

        TextInputEditText edtName = view.findViewById(R.id.edt_product_name);
        TextInputEditText edtPrice = view.findViewById(R.id.edt_product_price);
        TextInputEditText edtCatId = view.findViewById(R.id.edt_category);
        Button btnSave = view.findViewById(R.id.btn_save);
        Button btnCancel = view.findViewById(R.id.btn_cancel);


        if (!catList.isEmpty()) {
            edtCatId.setText(String.valueOf(catList.get(0).getId()));
        }

        AlertDialog dialog = builder.create();

        btnSave.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String priceStr = edtPrice.getText().toString().trim();
            String catIdStr = edtCatId.getText().toString().trim();

            if (name.isEmpty() || priceStr.isEmpty() || catIdStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double price = Double.parseDouble(priceStr);
                int catId = Integer.parseInt(catIdStr);


                boolean validCat = catList.stream().anyMatch(c -> c.getId() == catId);
                if (!validCat) {
                    Toast.makeText(this, "ID thể loại không hợp lệ!", Toast.LENGTH_SHORT).show();
                    return;
                }

                ProductDTO p = new ProductDTO(name, price, catId);
                long id = productDAO.addProduct(p);
                if (id > 0) {
                    p.setId((int) id);
                    productList.add(p);
                    productAdapter.notifyDataSetChanged();
                    Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(this, "Thêm thất bại!", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Giá hoặc ID phải là số!", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void showEditProductDialog(ProductDTO product, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit_product, null);
        builder.setView(view);

        TextInputEditText edtName = view.findViewById(R.id.edt_product_name);
        TextInputEditText edtPrice = view.findViewById(R.id.edt_product_price);
        TextInputEditText edtCatId = view.findViewById(R.id.edt_category);
        Button btnSave = view.findViewById(R.id.btn_save);
        Button btnCancel = view.findViewById(R.id.btn_cancel);

        edtName.setText(product.getName());
        edtPrice.setText(String.valueOf(product.getPrice()));
        edtCatId.setText(String.valueOf(product.getId_cat()));

        AlertDialog dialog = builder.create();

        btnSave.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String priceStr = edtPrice.getText().toString().trim();
            String catIdStr = edtCatId.getText().toString().trim();

            if (name.isEmpty() || priceStr.isEmpty() || catIdStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double price = Double.parseDouble(priceStr);
                int catId = Integer.parseInt(catIdStr);

                boolean validCat = catList.stream().anyMatch(c -> c.getId() == catId);
                if (!validCat) {
                    Toast.makeText(this, "ID thể loại không hợp lệ!", Toast.LENGTH_SHORT).show();
                    return;
                }

                ProductDTO updatedProduct = new ProductDTO(name, price, catId);
                updatedProduct.setId(product.getId());

                if (productDAO.updateProduct(updatedProduct)) {
                    productList.set(position, updatedProduct);
                    productAdapter.notifyItemChanged(position);
                    Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Giá hoặc ID phải là số!", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void showDeleteConfirmDialog(ProductDTO product, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa sản phẩm")
                .setMessage("Xóa \"" + product.getName() + "\"?")
                .setPositiveButton("Xóa", (d, w) -> {
                    if (productDAO.deleteProduct(product.getId())) {
                        productList.remove(position);
                        productAdapter.notifyItemRemoved(position);
                        productAdapter.notifyItemRangeChanged(position, productList.size());
                        Toast.makeText(this, "Đã xóa!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Xóa thất bại!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
