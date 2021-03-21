package com.hqv.notepad;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    float x1, y1, x2, y2;
    int REQUEST_CODE = 123;
    DBContext database;
    ListView lvGhiChu;
    ArrayList<Note> ghiChuArrayList;
    NoteAdapter adapter;
    EditText edtTenGhiChu;
    FloatingActionButton fabThem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new DBContext(
                this, "ghichu.sqlite", null, 1);
        lvGhiChu = findViewById(R.id.listViewGhiChu);
        ghiChuArrayList = new ArrayList<>();
        adapter = new NoteAdapter(this, R.layout.list_note, ghiChuArrayList);
        lvGhiChu.setAdapter(adapter);
        fabThem = findViewById(R.id.floatingActionButton);
        fabThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemGhiChu();
            }
        });
        lvGhiChu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, Notepad.class);
                Bundle bundle = new Bundle();
                bundle.putString("thoigian", ghiChuArrayList.get(position).getThoiGian());
                bundle.putString("noidung", ghiChuArrayList.get(position).getNoiDungGhiChu());
                bundle.putInt("id", ghiChuArrayList.get(position).getId());
                intent.putExtra("dulieu", bundle);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        //tạo bảng db
        database.queryData("CREATE TABLE IF NOT EXISTS GhiChu(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenGhiChu VARCHAR(200), ThoiGian DateTime, NoiDung TEXT )");
        //chèn db
//    database.queryData("INSERT INTO GhiChu VALUES(null,'Ghi Chú 1','2019-12-30 12:10','Hello')");
        //get db
        getDataGhiChu();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Bundle bundle = data.getBundleExtra("dl");
            String nd = bundle.getString("nd");
            String time = bundle.getString("time");
            int id = bundle.getInt("id1");
            database.queryData("UPDATE GhiChu SET NoiDung='" + nd + "',ThoiGian='" + time + "' WHERE Id='" + id + "'");
            getDataGhiChu();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void getDataGhiChu() {
        Cursor data = database.getData("SELECT * FROM GhiChu");
        ghiChuArrayList.clear();
        while (data.moveToNext()) {
            int id = data.getInt(0);
            String ten = data.getString(1);
            String thoiGian = data.getString(2);
            String noiDung = data.getString(3);
            ghiChuArrayList.add(new Note(id, ten, thoiGian, noiDung));
        }
        adapter.notifyDataSetChanged();
    }

    public void XoaGhiChu(final int id, String ten) {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn xóa ghi chú " + ten + " không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.queryData("DELETE FROM GhiChu WHERE Id='" + id + "'");
                getDataGhiChu();
            }
        });

        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();
    }

    public void SuaTenGhiChu(final String ten, final int id) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_note_dialog);
        final EditText edtTen = dialog.findViewById(R.id.editTextSuaGhiChu);
        Button btnSua = dialog.findViewById(R.id.buttonSua);
        Button btnHuy1 = dialog.findViewById(R.id.buttonHuy);
        edtTen.setText(ten);
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenMoi = edtTen.getText().toString().trim();
                database.queryData("UPDATE GhiChu SET TenGhiChu='" + tenMoi + "' WHERE id='" + id + "'");
                dialog.dismiss();
                getDataGhiChu();
            }
        });
        btnHuy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void ThemGhiChu() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.create_note_dialog);

        final EditText edtGhiChuMoi = dialog.findViewById(R.id.editTextGhiChuMoi);
        Button btnThem = dialog.findViewById(R.id.buttonThem);
        Button btnHuy = dialog.findViewById(R.id.buttonHuy);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenGhiChuMoi = edtGhiChuMoi.getText().toString();
                Calendar calendar = Calendar.getInstance();
                String thoiGian;
                SimpleDateFormat dinhDangNgay = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat dinhDangGio = new SimpleDateFormat("hh:mm");
                thoiGian = "Thứ " + calendar.get(Calendar.DAY_OF_WEEK) + ", " + dinhDangNgay.format(calendar.getTime()) + " at " + dinhDangGio.format(calendar.getTime());

                if (tenGhiChuMoi.equals("")) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập tên ghi chú!!!", Toast.LENGTH_SHORT).show();
                } else {
                    database.queryData("INSERT INTO GhiChu VALUES(null,'" + tenGhiChuMoi + "','" + thoiGian + "','')");
                    getDataGhiChu();
                    dialog.dismiss();

                }

            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void update(String thoigian, int id) {
        database.queryData("UPDATE GhiChu SET ThoiGian='" + thoigian + "' WHERE Id='" + id + "'");
        getDataGhiChu();
    }
}