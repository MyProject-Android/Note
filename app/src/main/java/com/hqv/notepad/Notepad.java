package com.hqv.notepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Notepad extends AppCompatActivity {
    int id;
    String noiDungGhiChu;
    Content fragmentNoiDung;
    DBContext database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("dulieu");
        String thoiGian = bundle.getString("thoigian");
        noiDungGhiChu = bundle.getString("noidung");
        id = bundle.getInt("id");
//    Toast.makeText(this, noiDungGhiChu+"", Toast.LENGTH_SHORT).show();
        fragmentNoiDung = (Content) getSupportFragmentManager().findFragmentById(R.id.fragmentNoiDung);
        Update fragmentTimeUpdate = (Update) getSupportFragmentManager().findFragmentById(R.id.fragmentTime);
        fragmentNoiDung.GanNoiDung(noiDungGhiChu);
        fragmentTimeUpdate.GanNoiDung(thoiGian);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_luu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Calendar calendar = Calendar.getInstance();
        final String thoiGianUpdate;
        SimpleDateFormat dinhDangNgay = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dinhDangGio = new SimpleDateFormat("hh:mm a");
        thoiGianUpdate = "Thứ " + calendar.get(Calendar.DAY_OF_WEEK) + ", " + dinhDangNgay.format(calendar.getTime()) + ", " + dinhDangGio.format(calendar.getTime());

        switch (item.getItemId()) {
            case R.id.menuLuu: {
                Intent intent = new Intent(Notepad.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("time", thoiGianUpdate);
                bundle.putString("nd", fragmentNoiDung.getNoiDung());
                bundle.putInt("id1", id);
                intent.putExtra("dl", bundle);
                setResult(RESULT_OK, intent);
                finish();
                Toast.makeText(this, "Đã lưu", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        //lưu ghi chú
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //lưu ghi
    }
}