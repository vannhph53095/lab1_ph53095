// File: fpoly/ph53095/lab1_ph53095/dbHelper/MyDbHelper.java
package fpoly.ph53095.lab1_ph53095.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "androi2demosql.db";
    private static final int DATABASE_VERSION = 3;

    public MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng tb_cat
        String sqlCat = "CREATE TABLE tb_cat ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "name TEXT NOT NULL );";
        db.execSQL(sqlCat);

        // THÊM DỮ LIỆU MẪU (CHỈ 1 LẦN)
        db.execSQL("INSERT INTO tb_cat (name) VALUES ('Máy Tính');");
        db.execSQL("INSERT INTO tb_cat (name) VALUES ('Điện Thoại');");
        db.execSQL("INSERT INTO tb_cat (name) VALUES ('Phụ Kiện');");
        db.execSQL("INSERT INTO tb_cat (name) VALUES ('Tivi');");
        db.execSQL("INSERT INTO tb_cat (name) VALUES ('Máy Giặt');");

        // Tạo bảng tb_product
        String sqlProduct = "CREATE TABLE tb_product ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "name TEXT NOT NULL, " +
                "price REAL NOT NULL DEFAULT 0, " +  // ← Sửa: NUMERIC → REAL
                "id_cat INTEGER, " +
                "FOREIGN KEY (id_cat) REFERENCES tb_cat(id) );";
        db.execSQL(sqlProduct);

        // THÊM DỮ LIỆU MẪU CHO tb_product
        db.execSQL("INSERT INTO tb_product (name, price, id_cat) VALUES ('Laptop Dell XPS 13', 25000000, 1);");
        db.execSQL("INSERT INTO tb_product (name, price, id_cat) VALUES ('MacBook Air M2', 32000000, 1);");
        db.execSQL("INSERT INTO tb_product (name, price, id_cat) VALUES ('iPhone 15 Pro', 28000000, 2);");
        db.execSQL("INSERT INTO tb_product (name, price, id_cat) VALUES ('Samsung S24 Ultra', 30000000, 2);");
        db.execSQL("INSERT INTO tb_product (name, price, id_cat) VALUES ('Tai nghe AirPods Pro', 6500000, 3);");
        db.execSQL("INSERT INTO tb_product (name, price, id_cat) VALUES ('Sạc nhanh 65W', 850000, 3);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS tb_product");
            db.execSQL("DROP TABLE IF EXISTS tb_cat");
            onCreate(db); // Tạo lại + thêm dữ liệu mẫu
        }
    }

}