package com.example.taxiapp.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.taxiapp.R;
import com.example.taxiapp.databinding.TemaBinding;

//import com.example.taxiapp.ui.gallery.tview;
import com.example.taxiapp.databinding.TemaBinding;

public class tema extends Fragment {

    private TemaBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = TemaBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
