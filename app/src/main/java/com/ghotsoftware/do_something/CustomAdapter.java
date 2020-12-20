package com.ghotsoftware.do_something;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ghotsoftware.do_something.ui.todo.ToDoFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    Context context;
    ArrayList<String> todos;
    Boolean isDone = false;
    Fragment fragment;

    public CustomAdapter(@NonNull Context context, ArrayList<String> a, boolean _isDone, Fragment fragment) {
        super(context, R.layout.fragment_listitem, a);
        this.fragment = fragment;
        isDone = _isDone;
        this.context = context;
        this.todos = a;
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.fragment_listitem, null);

        String[] texts = todos.get(position).split(",");

        TextView text = v.findViewById(R.id.missionTxt);
        TextView EndText = v.findViewById(R.id.EndTextView);
        Button acceptBtn = v.findViewById(R.id.AcceptBtn);

        text.setText(texts[0]);
        //EndText.setText("Bitiş Tarihi : " + texts[1]);

        if(isDone){

            RelativeLayout layout = v.findViewById(R.id.ItemBackground);
            if(texts[2].equals("true")){
                layout.setBackgroundColor(R.color.green);
            }else{
                layout.setBackgroundColor(R.color.red);
            }

            acceptBtn.setVisibility(View.INVISIBLE);
        }
        else{
            text.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ((ToDoFragment)fragment).DeleteData(position);

                    return true;
                }
            });

            acceptBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ToDoFragment)fragment).SaveDataToBeDone(position, todos.get(position), true);
                }
            });
        }

        return v;
    }
}
