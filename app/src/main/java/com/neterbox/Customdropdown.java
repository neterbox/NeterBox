package com.neterbox;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * Created by DeLL on 19-01-2018.
 */

public class Customdropdown implements AdapterView.OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Toast.makeText(parent.getContext(),
                "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

}

