package com.example.foodtrack.Fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.example.foodtrack.Activity.MainActivity;
import com.example.foodtrack.Activity.list_chat_user;
import com.example.foodtrack.Adapter.myorders_history_list_adapter_api;
import com.example.foodtrack.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class fragment_myorders_history_API extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    ArrayList<OrderAPIModel> arrayListOrder = new ArrayList<>();
    ListView listview_myorders_history;
    LinearLayout imageIfEmpty;
    ImageView chatIcon;
    ImageView backBtn;
    TextView toOngoing, rateBtn;

    public fragment_myorders_history_API() {
        // Required empty public constructor
    }

    public static fragment_myorders_history_API newInstance(String param1, String param2) {
        fragment_myorders_history_API fragment = new fragment_myorders_history_API();
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
        // Sample data
        String[] orderIds = {"#0000", "#0001", "#0002", "#0003", "#0004", "#0005"};
        String[] times = {"22-09-2024, 12:00 PM", "22-09-2024, 12:00 PM", "22-09-2024, 12:00 PM", "22-09-2024, 12:00 PM", "22-09-2024, 12:00 PM", "22-09-2024, 12:00 PM"};
        String[] names = {"Cheesecake việt quất", "Cơm tấm", "Trà chanh", "Cà phê capuccino", "Salad hoa quả", "Pallavi Biryani"};
        String[] imgLinks = {
                "https://example.com/dessert_ico.png",
                "https://example.com/com_tam.png",
                "https://example.com/drink2.png",
                "https://example.com/drink1.png",
                "https://example.com/icon_food1.png",
                "https://example.com/pallavi_biryani.png"
        };
        int[] statuses = {0, 0, 1, 1, 1, 1};
        String[] prices = {"20.000đ", "30.000đ", "50.000đ", "20.000đ", "40.000đ", "20.000đ"};
        int[] quantities = {1, 2, 1, 4, 1, 3};

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy, hh:mm a", Locale.getDefault());

        for (int i = 0; i < orderIds.length; i++) {
            try {
                Date createdAt = dateFormat.parse(times[i]);
                arrayListOrder.add(new OrderAPIModel(orderIds[i], createdAt, names[i], statuses[i] == 0 ? "Pending" : "Delivered", imgLinks[i], statuses[i], prices[i], quantities[i]));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myorders_history, container, false);
        Mapping(view);
        checkIfListEmpty();
        ControlButton();
        return view;
    }

    private void Mapping(View view) {
        listview_myorders_history = view.findViewById(R.id.listview_myorders);
        myorders_history_list_adapter_api listAdapter = new myorders_history_list_adapter_api(getContext(), arrayListOrder);
        listview_myorders_history.setAdapter(listAdapter);

        backBtn = view.findViewById(R.id.btn_back_myorders_history);
        chatIcon = view.findViewById(R.id.chatIcon);
        toOngoing = view.findViewById(R.id.btn_dangDen_myOrder);
        imageIfEmpty = view.findViewById(R.id.image_if_no_order_myOrders);
        rateBtn = view.findViewById(R.id.ratingBtn_item_myOrders);
    }

    private void checkIfListEmpty() {
        if (arrayListOrder.isEmpty()) {
            listview_myorders_history.setVisibility(View.GONE);
            imageIfEmpty.setVisibility(View.VISIBLE);
        } else {
            listview_myorders_history.setVisibility(View.VISIBLE);
            imageIfEmpty.setVisibility(View.GONE);
        }
    }

    private void ControlButton() {
        chatIcon.setOnClickListener(view -> {
            Intent chat = new Intent(getActivity(), list_chat_user.class);
            startActivity(chat);
        });

        toOngoing.setOnClickListener(view -> {
            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity != null) {
                mainActivity.ReplaceFragment(new fragment_myorders_ongoing());
            }
        });

        backBtn.setOnClickListener(view -> requireActivity().getSupportFragmentManager().popBackStack());
    }
}
