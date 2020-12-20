package com.ghotsoftware.do_something.ui.bedone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ghotsoftware.do_something.CustomAdapter;
import com.ghotsoftware.do_something.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class BeDoneFragment extends Fragment {

    final String FILE_NAME = "BeDone.txt";
    ListView BeDoneListView;
    ArrayList<String> beDoneArrayList = new ArrayList<>();
    CustomAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_bedone, container, false);

        BeDoneListView = root.findViewById(R.id.BeDoneListView);

        beDoneArrayList = LoadData();

        adapter = new CustomAdapter(getContext(), beDoneArrayList, true, this);
        BeDoneListView.setAdapter(adapter);

        return root;
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
}