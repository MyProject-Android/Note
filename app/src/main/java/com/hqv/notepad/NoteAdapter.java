package com.hqv.notepad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NoteAdapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<Note> ghiChuList;

    public NoteAdapter(MainActivity context, int layout, List<Note> ghiChuList) {
        this.context = context;
        this.layout = layout;
        this.ghiChuList = ghiChuList;
    }

    @Override
    public int getCount() {
        return ghiChuList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        EditText edtTenGhiChu;
        TextView txtThoiGian;
        ImageView imgDelete, imgEdit;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout,null);

            holder.edtTenGhiChu=convertView.findViewById(R.id.editTextTenGhiChu);
            holder.txtThoiGian=convertView.findViewById(R.id.textViewThoiGian);
            holder.imgDelete=convertView.findViewById(R.id.imageViewDelete);
            holder.imgEdit=convertView.findViewById(R.id.imageViewEdit);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        final Note ghiChu=ghiChuList.get(position);
        holder.edtTenGhiChu.setText(ghiChu.getTenGhiChu());
        holder.txtThoiGian.setText(ghiChu.getThoiGian());

        //set sự kiện sửa tên ghi chú
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.SuaTenGhiChu(ghiChu.getTenGhiChu(),ghiChu.getId());
            }
        });
        //set sự kiện xóa ghi chú
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.XoaGhiChu(ghiChu.getId(),ghiChu.getTenGhiChu());
            }
        });
        return convertView;
    }
}
