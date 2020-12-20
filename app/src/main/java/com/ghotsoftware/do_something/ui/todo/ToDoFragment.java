package com.ghotsoftware.do_something.ui.todo;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ghotsoftware.do_something.AddNewStuff;
import com.ghotsoftware.do_something.CustomAdapter;
import com.ghotsoftware.do_something.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.MODE_APPEND;

public class ToDoFragment extends Fragment {

    final String FILE_NAME = "ToDo.txt";
    ArrayList<String> todoArrayList = new ArrayList<String>();
    FloatingActionButton addStuffBtn;
    CustomAdapter adapter;
    ListView toDoListView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_todo, container, false);

        toDoListView = root.findViewById(R.id.ToDoListView);

        todoArrayList = LoadData();

        adapter = new CustomAdapter(getContext(), todoArrayList, false, this);
        toDoListView.setAdapter(adapter);

        addStuffBtn = root.findViewById(R.id.floatingActionButton);
        addStuffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = onCreateDialog();
                dialog.show();
            }
        });

        return root;
    }

    protected Dialog onCreateDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View v = inflater.inflate(R.layout.activity_add_new_stuff, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Yeni Bir Olay Ekle");

        builder.setView(v);

        AlertDialog alertDialog = builder.create();
        TextView text = v.findViewById(R.id.StuffTxt);
        CalendarView calendarView = v.findViewById(R.id.calendarView);

        v.findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String row = text.getText() + "," + calendarView.getDate();
                todoArrayList.add(row);

                SaveData(row, false);

                adapter = new CustomAdapter(getContext(), todoArrayList, false, ToDoFragment.this);
                toDoListView.setAdapter(adapter);

                alertDialog.dismiss();
            }
        });

        return alertDialog;
    }

    public void DeleteData(int id){

        todoArrayList.remove(id);
        StringBuilder builder = new StringBuilder();
        builder.append("");
        for (String string:todoArrayList) {
            builder.append(string);
            builder.append("\n");
        }
        Log.d("delete dataa", builder.toString());
        adapter = new CustomAdapter(getContext(), todoArrayList, false, ToDoFragment.this);
        toDoListView.setAdapter(adapter);

        SaveData(builder.toString(), false);
    }

    private void SaveData(String row, boolean append) {
        try {
            FileOutputStream fOut = getActivity().openFileOutput(FILE_NAME, ((append)?MODE_APPEND:Context.MODE_PRIVATE));
            fOut.write(row.getBytes());
            fOut.close();
            Log.d("Save Succesfully", "Save Succesfully.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> LoadData(){
        FileInputStream fis = null;
        ArrayList<String> ReadedData = new ArrayList<>();
        try {
            fis = getActivity().openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;

            while ((text = br.readLine()) != null) {
                //Log.d("yazi", text);
            //    if(!text.equals(""))
            //        continue;

                ReadedData.add(text);
            }
            return ReadedData;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public void SaveDataToBeDone(int id, String row, boolean isCompleted) {
        try {
            row += "," + ((isCompleted)?"true":"false") + "\n";
            FileOutputStream fOut = getContext().openFileOutput("BeDone.txt",  MODE_APPEND);
            fOut.write(row.getBytes());
            fOut.close();
            Log.d("Save Succesfully", "Save Succesfully.");
            DeleteData(id);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}