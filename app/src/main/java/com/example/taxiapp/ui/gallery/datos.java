package com.example.taxiapp.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.taxiapp.MainActivityTaxiMenu;
import com.example.taxiapp.R;
import com.example.taxiapp.databinding.FragmentAyudaBinding;
import com.example.taxiapp.subir_foto;
import com.example.taxiapp.ui.ayuda.ayudaViewModel;


public class datos extends Fragment {

        private FragmentAyudaBinding binding;
        Button atras;
        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {


                binding = FragmentAyudaBinding.inflate(inflater, container, false);
                View v = binding.getRoot();
      atras = (Button) v.findViewById(R.id.btn_perfilatras);
        atras.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                       // Intent intent = new Intent(datos.this,GalleryFragment.class);


                     //   startActivity(intent);

                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        //agrega el Fragment en el contenedor, en este caso el FrameLayout con id `FrameLayout`.
                        ft.add(androidx.navigation.fragment.R.id.nav_host_fragment_container, new GalleryFragment());
                        ft.commit();


                }
        });
                return v;
        }
        @Override
        public void onDestroyView() {
                super.onDestroyView();
                binding = null;
        }
        }