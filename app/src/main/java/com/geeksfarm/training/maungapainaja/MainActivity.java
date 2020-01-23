package com.geeksfarm.training.maungapainaja;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lvTodos;
    FloatingActionButton fabAdd;
    EditText edtTodo;

    //1. Siapkan Data
    //String[] data = {"Dota 2","Sleep","Dota 2","Eat",}; // diganti menjadi ArrayList berikut :
    ArrayList<String> data = new ArrayList<String>();

    //3. Buat Adapter untuk List View
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1. Siapkan data
        createTodos();

        //2. Buat List View
        lvTodos = findViewById(R.id.lv_todos); // define list view

        // 3. Buat Adapter dan masukkan parameter yg dibutuhkan. (context, layout_content,tv,data)
        //      parameter data diambil dari langkah 1.
        arrayAdapter = new ArrayAdapter<String>(this,R.layout.todo_content,R.id.tv_todo,data);

        // 4. Set Adapter kepada List View
        lvTodos.setAdapter(arrayAdapter);

        // 5. Define FAB dan buat onClickListener nya.
        fabAdd = findViewById(R.id.fab_add_item);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //6. Method di bawah ini dibuat sendiri di bawah
                onClickFabAdd();
            }
        });

        // 7.1 Buat onItemLongClickListener di list view untuk hapus data
        lvTodos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //panggil method deleteItem()

                deleteItem(position);
                return false;
            }
        });

    }

    // 1. Siapkan Dummy Data
    private void createTodos(){
        data.add("Coding");
        data.add("Eat");
        data.add("Sleep");
        data.add("Traveling");
    }

    //6. Buat Method ketika FAB Add di click untuk menambahkan data
    private void onClickFabAdd(){
        //Cara pertama tambah edit text ke dialog
        //EditText edtTodo = new EditText(this);

        //Cara dua tambah edit text ke dialog
        //proses ini disebut dengan inflate layout
        View view = View.inflate(this,R.layout.dialog_add_view, null);

        //EditText ini dideklarisikan di atas di dalam class
        edtTodo = view.findViewById(R.id.edt_todo);

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Mau ngapain lagi nich?");
        dialog.setView(view);
        dialog.setPositiveButton("Tambah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                data.add(edtTodo.getText().toString()); // tambah data ke object ArrayList data.
                arrayAdapter.notifyDataSetChanged(); // merefresh list view
            }
        });
        dialog.setNegativeButton("Batal",null);
        dialog.create();
        dialog.show();
    }


    // 7.2 Buat method delete Item untuk menghapus data dari array list dan mengupdate list view
    private void deleteItem(int position){ // beri parameter position untuk mewadahi position dari list view
        // konstanta untuk menampung data position yang di passing dari onItemLongClickListener
        final int index = position;

        //Buat alert dialog
        AlertDialog.Builder dialog  = new AlertDialog.Builder(this);
        dialog.setTitle("Yakin akan dihapus ?");
        dialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //hapus item dari array list data berdasarkan index/position dari item di list view
                data.remove(index); // index didapat position parameter
                //suruh adapter untuk notify  ke List View kalau data telah berubah //merefresh list view
                arrayAdapter.notifyDataSetChanged();
            }
        });
        dialog.setNegativeButton("Tidak",null);
        dialog.create().show();


    }
}
