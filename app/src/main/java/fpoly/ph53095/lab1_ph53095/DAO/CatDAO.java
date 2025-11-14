// File: fpoly/ph53095/lab1_ph53095/DAO/CatDAO.java
package fpoly.ph53095.lab1_ph53095.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.ph53095.lab1_ph53095.DTO.CatDTO;
import fpoly.ph53095.lab1_ph53095.dbHelper.MyDbHelper;

public class CatDAO {
    private MyDbHelper dbHelper;
    private SQLiteDatabase db;

    public CatDAO(Context context) {
        dbHelper = new MyDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // Thêm thể loại
    public long addCat(CatDTO cat) {
        ContentValues values = new ContentValues();
        values.put("name", cat.getName());
        return db.insert("tb_cat", null, values);
    }

    // Cập nhật thể loại
    public boolean updateCat(CatDTO cat) {
        ContentValues values = new ContentValues();
        values.put("name", cat.getName());
        String[] whereArgs = {String.valueOf(cat.getId())};
        return db.update("tb_cat", values, "id = ?", whereArgs) > 0;
    }
    //

    // Xóa thể loại (có kiểm tra ràng buộc FK)
    public boolean deleteCat(int id) {
        // Kiểm tra xem có sản phẩm nào dùng id_cat này không
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM tb_product WHERE id_cat = ?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst() && cursor.getInt(0) > 0) {
            cursor.close();
            return false; // Không xóa được vì có sản phẩm liên quan
        }
        cursor.close();

        String[] whereArgs = {String.valueOf(id)};
        return db.delete("tb_cat", "id = ?", whereArgs) > 0;
    }

    // Lấy tất cả thể loại
    public ArrayList<CatDTO> getALLCat() {
        ArrayList<CatDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tb_cat";
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                CatDTO cat = new CatDTO();
                cat.setId(cursor.getInt(0));
                cat.setName(cursor.getString(1));
                list.add(cat);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}