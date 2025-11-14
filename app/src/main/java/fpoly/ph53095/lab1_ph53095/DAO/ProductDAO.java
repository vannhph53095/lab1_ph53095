package fpoly.ph53095.lab1_ph53095.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import fpoly.ph53095.lab1_ph53095.DTO.ProductDTO;
import fpoly.ph53095.lab1_ph53095.dbHelper.MyDbHelper;

public class ProductDAO {
    private MyDbHelper dbHelper;
    private SQLiteDatabase db;
    private static final String TAG = "ProductDAO";

    public ProductDAO(Context context) {
        dbHelper = new MyDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }


    public long addProduct(ProductDTO product) {
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("price", product.getPrice());
        values.put("id_cat", product.getId_cat());
        return db.insert("tb_product", null, values);
    }


    // Cập nhật sản phẩm
    public boolean updateProduct(ProductDTO product) {
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("price", product.getPrice());
        values.put("id_cat", product.getId_cat());
        String[] whereArgs = {String.valueOf(product.getId())};
        return db.update("tb_product", values, "id = ?", whereArgs) > 0;
    }

    // Xóa sản phẩm
    public boolean deleteProduct(int id) {
        String[] whereArgs = {String.valueOf(id)};
        return db.delete("tb_product", "id = ?", whereArgs) > 0;
    }

    // Lấy tất cả sản phẩm
    public ArrayList<ProductDTO> getAllProducts() {
        ArrayList<ProductDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tb_product";
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                ProductDTO p = new ProductDTO();
                p.setId(cursor.getInt(0));
                p.setName(cursor.getString(1));
                p.setPrice(cursor.getDouble(2));
                p.setId_cat(cursor.getInt(3));
                list.add(p);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // Lấy sản phẩm theo id_cat
    public ArrayList<ProductDTO> getProductsByCatId(int id_cat) {
        ArrayList<ProductDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tb_product WHERE id_cat = ?";
        String[] args = {String.valueOf(id_cat)};
        Cursor cursor = db.rawQuery(sql, args);

        if (cursor.moveToFirst()) {
            do {
                ProductDTO p = new ProductDTO();
                p.setId(cursor.getInt(0));
                p.setName(cursor.getString(1));
                p.setPrice(cursor.getDouble(2));
                p.setId_cat(cursor.getInt(3));
                list.add(p);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // Lấy sản phẩm theo ID
    public ProductDTO getProductById(int id) {
        ProductDTO product = null;
        String sql = "SELECT * FROM tb_product WHERE id = ?";
        String[] args = {String.valueOf(id)};
        Cursor cursor = db.rawQuery(sql, args);

        if (cursor.moveToFirst()) {
            product = new ProductDTO();
            product.setId(cursor.getInt(0));
            product.setName(cursor.getString(1));
            product.setPrice(cursor.getDouble(2));
            product.setId_cat(cursor.getInt(3));
        }
        cursor.close();
        return product;
    }

}