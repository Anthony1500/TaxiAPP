package com.example.taxiapp.ui.gallery;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.taxiapp.R;
import com.example.taxiapp.databinding.FragmentGalleryBinding;
import com.google.android.material.navigation.NavigationView;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private View bindinga;
    Button  boton_datos,btema;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);




        bindinga =inflater.inflate(R.layout.datosusua, container, false);
        bindinga =inflater.inflate(R.layout.tema, container, false);
        View root = binding.getRoot();


        //******************************************************************************************
        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView datousu= (TextView) root.findViewById(R.id.datousu);
        TextView idusu= (TextView) headerView.findViewById(R.id.idusu);

        datousu.setText(idusu.getText());
       //******************************************************************************************

        return root;


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(GalleryFragment.this)
                        .navigate(R.id.datos_llamado);
            }
        });
        binding.btnTema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(GalleryFragment.this)
                        .navigate(R.id.tema_llamado);
            }
        });
    }





}


