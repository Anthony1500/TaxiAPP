package com.example.taxiapp.ui.gallery;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.taxiapp.R;
import com.example.taxiapp.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private View bindinga;
    Button  boton_datos;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);




        bindinga =inflater.inflate(R.layout.datosusuario, container, false);

        View root = binding.getRoot();

        boton_datos=(Button)root.findViewById(R.id.btn_datos);
        boton_datos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), datos.class);
                startActivity(i);
            }
        });

        return root;


    }




    }





