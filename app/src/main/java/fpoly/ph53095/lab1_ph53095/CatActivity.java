package fpoly.ph53095.lab1_ph53095;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import fpoly.ph53095.lab1_ph53095.Adapter.CatAdapter;

import fpoly.ph53095.lab1_ph53095.DAO.CatDAO;
import fpoly.ph53095.lab1_ph53095.DTO.CatDTO;

public class CatActivity extends AppCompatActivity {
    private ListView lvCategory;
    private CatDAO catDAO;
    private ArrayList<CatDTO> catList;
    private CatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);

        lvCategory = findViewById(R.id.lv_category);
        catDAO = new CatDAO(this);

        // Kích hoạt DB
        catDAO.getALLCat().size();

        // Load dữ liệu
        catList = catDAO.getALLCat();
        adapter = new CatAdapter(this, catList);
        lvCategory.setAdapter(adapter);
    }
}