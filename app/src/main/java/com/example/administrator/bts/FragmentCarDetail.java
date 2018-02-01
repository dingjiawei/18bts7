package com.example.administrator.bts;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentCarDetail.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentCarDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCarDetail extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    TextView tvOwner;
    TextView tvCarNumber;
    TextView tvBrand;
    TextView tvCarType;
    TextView tvContract;
    TextView tvCertificate;
    TextView tvDatePurchase;
    TextView tvLocationNow;

    public FragmentCarDetail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentCarDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCarDetail newInstance(String param1, String param2) {
        FragmentCarDetail fragment = new FragmentCarDetail();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static FragmentCarDetail getInstance() {

        return  newInstance("cardetail", "2");
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment_car_detail, container, false);

        tvOwner = v.findViewById(R.id.tv_detail_owner);
        tvCarNumber = v.findViewById(R.id.tv_detail_carnumber);
        tvBrand = v.findViewById(R.id.tv_brand);
        tvCarType = v.findViewById(R.id.tv_cartype);
        tvContract = v.findViewById(R.id.tv_contract);
        tvCertificate = v.findViewById(R.id.tv_certificate);
        tvDatePurchase = v.findViewById(R.id.tv_date_purchase);
        tvLocationNow = v.findViewById(R.id.tv_location_now);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void updateView(String response) {

        String strOwner = "";
        String strCarNumber = "";
        String strCarbrand = "";
        String strCarType = "1";
        String strContract = "";
        String strCertificateID = "";
        String strCertificateType = "";
        String strLocationNow = "";


        if( null != response ){

            String[] rsp = response.split("parmdivider");

            if( null != rsp && rsp.length > 2){
                if( rsp[1].equals("blacklist") ) { //data come from black list page
                    try {
                        JSONArray array = new JSONObject(rsp[0]).getJSONArray("data");
                        int positon = Integer.parseInt(rsp[2]);

                        JSONObject obj = ((JSONObject) array.get(positon)).getJSONObject("carOwnerVO");

                        strOwner = obj.getString("full_name");
                        strCarNumber = obj.getString("plate_number");
                        strCarbrand = obj.getString("car_brand");
                        strCarType = obj.getString("car_type");
                        strContract = obj.getString("mobile");
                        strCertificateID = obj.getString("certificate_id");
                        strCertificateType = obj.getString("certificate_type");
                        strLocationNow = obj.getString("street_address");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                if(rsp[1].equals("nearbylist")){
//                    JSONArray array = new JSONObject(rsp[0]).getJSONArray("data");
//                    String positon = ;
                    response = sendRequestWithHttpURLConnection("http://47.96.38.194/bts/api/api_getCarDetailByTerminalNo.do?carOwnerVO.TermID=" + rsp[2]);
                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(response);
                        strOwner = obj.getString("full_name");
                        strCarNumber = obj.getString("plate_number");
                        strCarbrand = obj.getString("car_brand");
                        strCarType = obj.getString("car_type");
                        strContract = obj.getString("mobile");
                        strCertificateID = obj.getString("certificate_id");
                        strCertificateType = obj.getString("certificate_type");
                        strLocationNow = obj.getString("street_address");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            tvOwner.setText(strOwner);
            tvCarNumber.setText(strCarNumber);
            tvBrand.setText(strCarbrand);
            if( null != strCarType ){
                if(strCarType.equals("1")){tvCarType.setText("电动车");}else
                if(strCarType.equals("2")){tvCarType.setText("摩托车");}
            }
            tvContract.setText(strContract);
            tvCertificate.setText("身份证:" + strCertificateID);
//          tvDatePurchase = v.findViewById(R.id.tv_date_purchase);
            tvLocationNow.setText(strLocationNow);
        }
    }
    }
    private String sendRequestWithHttpURLConnection(final String strUrl ) {
        //开启线程来发起网络请求
                StringBuilder response = new StringBuilder();
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(strUrl);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);

                    InputStream in = connection.getInputStream();
                    //下面对获取到的输入流进行读取
                    BufferedReader bufr = new BufferedReader(new InputStreamReader(in));
                    String line = null;
                    while ((line = bufr.readLine()) != null) {
                        response.append(line);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
                return response.toString();
            }

}
