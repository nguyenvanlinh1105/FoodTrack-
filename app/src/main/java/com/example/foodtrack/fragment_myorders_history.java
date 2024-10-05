package com.example.foodtrack;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_myorders_history#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_myorders_history extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<String> orderId = new ArrayList<>();
    ArrayList<String> time = new ArrayList<>();
    ArrayList<Integer> img = new ArrayList<>();
    ArrayList<String> rate = new ArrayList<>();
    ArrayList<Integer> status = new ArrayList<>();
    ArrayList<String> orderStatus = new ArrayList<>();


    ListView listview_myorders_history;
    ImageView chatIcon;
    ImageView backBtn;
    TextView toOngoing;

    public fragment_myorders_history() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_myorders_history.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_myorders_history newInstance(String param1, String param2) {
        fragment_myorders_history fragment = new fragment_myorders_history();
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
        initializeData();
    }

    private void initializeData() {
        for (int i = 0; i < 6; i++)
            orderId.add("Order: #000" + i);
        for (int i = 0; i < 6; i++)
            time.add("22-9-2024, 12:00 p.m");

        img.add(R.drawable.dessert_ico);
        img.add(R.drawable.com_tam);
        img.add(R.drawable.drink2);
        img.add(R.drawable.drink1);
        img.add(R.drawable.icon_food1);
        img.add(R.drawable.pallavi_biryani);

        status.add(0);
        status.add(0);
        status.add(1);
        status.add(1);
        status.add(1);
        status.add(1);

        for (int i = 0; i < 6; i++)
            orderStatus.add("Đã giao vào 22-9");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myorders_history, container, false);
        Mapping(view);
        ControlButton();
        return view;
    }

    private void Mapping(View view) {
        listview_myorders_history = (ListView) view.findViewById(R.id.listview_myorders_history);
        fragment_myorders_history_list_adapter listAdapter = new fragment_myorders_history_list_adapter(getContext(), orderId, time, img, rate, status, orderStatus);
        listview_myorders_history.setAdapter(listAdapter);

        backBtn = (ImageView) view.findViewById(R.id.btn_back_myorders_history);
        chatIcon = (ImageView) view.findViewById(R.id.chatIcon);
        toOngoing = (TextView) view.findViewById(R.id.btn_dangDen_myOrder);
    }

    private void ControlButton() {
        chatIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chat = new Intent(getActivity(), list_chat_user.class);
                startActivity(chat);
            }
        });

        toOngoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.ReplaceFragment(new fragment_myorders_ongoing());
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.ReplaceFragment(new profile_fragment());
                }

            }
        });
    }
}