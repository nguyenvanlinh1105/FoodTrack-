package com.example.foodtrack.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.foodtrack.API.APIService;
import com.example.foodtrack.Activity.MainActivity;
import com.example.foodtrack.Activity.list_chat_user;
import com.example.foodtrack.Adapter.list_drink_API_adapter;
import com.example.foodtrack.Adapter.myorders_ongoing_list_adapter;
import com.example.foodtrack.Adapter.myorders_ongoing_list_adapter_api;
import com.example.foodtrack.Model.API.SanPhamAPIModel;
import com.example.foodtrack.Model.ChiTietDonHangAPIModel;
import com.example.foodtrack.Model.ChiTietDonHangModel;
import com.example.foodtrack.Model.DonHangAPIModel;
import com.example.foodtrack.Model.DonHangModel;
import com.example.foodtrack.Model.SanPhamModel;
import com.example.foodtrack.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_myorders_ongoing_API extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    String idNguoiDung;

    ImageView backBtn, imageViewTranslate;
    TextView toLichSu, toDonHuy;
    ImageView chatIcon;
    ListView listview_myorders_ongoing;
    LinearLayout imageIfEmpty, line;

    ArrayList<DonHangModel> arrayListOrder = new ArrayList<>();
    List<DonHangAPIModel> arrayListOrderAPI = new ArrayList<>();



    public myorders_ongoing_list_adapter_api listAdapter;

    public fragment_myorders_ongoing_API() {
        // Required empty public constructor
    }

    public static fragment_myorders_ongoing_API newInstance(String param1, String param2) {
        fragment_myorders_ongoing_API fragment = new fragment_myorders_ongoing_API();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    private void initializeData() {
        List<SanPhamModel> sanPhamList = createSampleProducts();
        arrayListOrder = createSampleOrders(sanPhamList);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        SharedPreferences sharedPreferencesUser = getContext().getSharedPreferences("shareUserResponseLogin", Context.MODE_PRIVATE);
        idNguoiDung = sharedPreferencesUser.getString("idUser", "-1");

        getParentFragmentManager().setFragmentResultListener("cancelOrder", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(String requestKey, Bundle result) {
                boolean isCancelled = result.getBoolean("isCancelled", false);
                if (isCancelled) {
                    // Làm mới danh sách sau khi đơn hàng bị hủy
                    GetOrders(idNguoiDung);
                }
            }
        });

    }


    private List<SanPhamModel> createSampleProducts() {
        List<SanPhamModel> sanPhamList = new ArrayList<>();

        sanPhamList.add(new SanPhamModel("Cheesecake việt quất", 20000, R.drawable.dessert_ico, ""));
        sanPhamList.add(new SanPhamModel("Cơm tấm", 30000, R.drawable.com_tam, ""));

        return sanPhamList;
    }

    private ArrayList<DonHangModel> createSampleOrders(List<SanPhamModel> sanPhamList) {
        ArrayList<DonHangModel> donHangList = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            DonHangModel donHang = new DonHangModel();
            donHang.setIdDonHang("Order: #000" + i);
            donHang.setNgayTao(new Date());
            donHang.setTinhTrang("Đang giao hàng");

            List<ChiTietDonHangModel> chiTietDonHangs = new ArrayList<>();
            ChiTietDonHangModel chiTietDonHang = new ChiTietDonHangModel();
            chiTietDonHang.setSanPham(sanPhamList.get(i));
            chiTietDonHang.setSoLuongDat(i + 1);
            chiTietDonHangs.add(chiTietDonHang);

            donHang.setChiTietDonHangs(chiTietDonHangs);
            donHangList.add(donHang);
        }

        return donHangList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myorders_ongoing, container, false);
        //initializeData()
        Mapping(view);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_shipper);
        imageViewTranslate.startAnimation(animation);
//        checkIfListEmpty();
        GetOrders(idNguoiDung);
        ControlButton();
        return view;
    }

    public void Mapping(View view) {
        backBtn = (ImageView) view.findViewById(R.id.btn_back_myorders_ongoing);
        toLichSu = (TextView) view.findViewById(R.id.btn_lichSu_myOrders);
        toDonHuy = (TextView) view.findViewById(R.id.btn_donHuy_myOrders);
        chatIcon = (ImageView) view.findViewById(R.id.chatIcon);
        imageIfEmpty = (LinearLayout) view.findViewById(R.id.image_if_no_order_myOrders);
        imageViewTranslate = (ImageView) view.findViewById(R.id.imageViewTranslate);
        line = (LinearLayout)view.findViewById(R.id.linearLayout14);

        listview_myorders_ongoing = (ListView) view.findViewById(R.id.listview_myorders);

    }

    private class GetOrdersTask extends AsyncTask<String, Void, List<DonHangAPIModel>> {
        @Override
        protected List<DonHangAPIModel> doInBackground(String... params) {
            // Gọi API ở đây
            try {
                Response<List<DonHangAPIModel>> response = APIService.API_SERVICE.GetDonHangDangGiao(params[0]).execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<DonHangAPIModel> result) {
            super.onPostExecute(result);
            if (result != null && !result.isEmpty()) {
//                Gson gson = new GsonBuilder().setPrettyPrinting().create();
//                String json = gson.toJson(result);
//                Log.d("responseBody", "onPostExecute: " + json);
                arrayListOrderAPI = result;
               listAdapter = new myorders_ongoing_list_adapter_api(getContext(), result);
                listview_myorders_ongoing.setAdapter(listAdapter);
            } else {
//                UseFallbackData();
                listview_myorders_ongoing.setVisibility(View.GONE);
                imageIfEmpty.setVisibility(View.VISIBLE);
                imageViewTranslate.setVisibility(View.GONE);
                line.setVisibility(View.GONE);
            }
        }
    }

    private void GetOrders(String idNguoiDung) {
        new GetOrdersTask().execute(idNguoiDung);
    }


    private void UseFallbackData() {
        initializeData();
        myorders_ongoing_list_adapter listAdapter = new myorders_ongoing_list_adapter(getContext(), arrayListOrder);
        listview_myorders_ongoing.setAdapter(listAdapter);
    }


    public void ControlButton() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        toLichSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.ReplaceFragment(new fragment_myorders_history_API());
                }
            }
        });

        toDonHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.ReplaceFragment(new fragment_myorders_donhuy());
                }
            }
        });

        chatIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chat = new Intent(getActivity(), list_chat_user.class);
                startActivity(chat);
            }
        });

        listview_myorders_ongoing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity mainActivity = (MainActivity) getActivity();

                if (mainActivity != null) {

                    DonHangAPIModel selectedOrder = arrayListOrderAPI.get(i);

//                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
//                    String json = gson.toJson(selectedOrder);
//                    Log.d("selectedOrder", json);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("selectedOrder", selectedOrder);
//

//
                    fragment_myorders_ongoing_details detailsFragment = fragment_myorders_ongoing_details.newInstance();
                    detailsFragment.setArguments(bundle);

                    mainActivity.ReplaceFragment(detailsFragment);
                }
            }
        });


    }

    private void checkIfListEmpty() {
        if (arrayListOrderAPI.size()==0) {
            listview_myorders_ongoing.setVisibility(View.GONE);
            imageIfEmpty.setVisibility(View.VISIBLE);
            imageViewTranslate.setVisibility(View.GONE);
        } else {
            listview_myorders_ongoing.setVisibility(View.VISIBLE);
            imageViewTranslate.setVisibility(View.VISIBLE);
            imageIfEmpty.setVisibility(View.GONE);
        }
    }
}
