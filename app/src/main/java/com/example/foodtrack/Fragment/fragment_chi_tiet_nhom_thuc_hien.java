package com.example.foodtrack.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.service.controls.Control;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.foodtrack.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_chi_tiet_nhom_thuc_hien#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_chi_tiet_nhom_thuc_hien extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView btn_back;

    public fragment_chi_tiet_nhom_thuc_hien() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_chi_tiet_nhom_thuc_hien.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_chi_tiet_nhom_thuc_hien newInstance(String param1, String param2) {
        fragment_chi_tiet_nhom_thuc_hien fragment = new fragment_chi_tiet_nhom_thuc_hien();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chi_tiet_nhom_thuc_hien, container, false);
        Mapping(view);
        ControlButton(view);
        return view;
    }

    private void Mapping(View view) {
        btn_back = view.findViewById(R.id.btn_back_chi_tiet_nhom_thuc_hien);
    }

    private void ControlButton(View view) {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}