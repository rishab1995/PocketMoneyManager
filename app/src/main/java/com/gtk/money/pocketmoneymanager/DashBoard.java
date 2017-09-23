package com.gtk.money.pocketmoneymanager;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoard extends Fragment {

    private MoneyManager moneyManager;
    public SharedPreferences sharedpreferences;
    ArrayList<MoneyManager> moneyManagerList;
    ListView lv;
    String json;
    int total_money;
    int total_spend;
    TextView total_amount;
    TextView balanced_amount;

    public DashBoard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dash_board, container, false);
        // Inflate the layout for this fragment
        lv = (ListView) v.findViewById(R.id.list_details);
        total_amount = (TextView) v.findViewById(R.id.amount);
        balanced_amount = (TextView) v.findViewById(R.id.balanced);

        sharedpreferences = this.getActivity().getSharedPreferences(Constants.MY_PREFERENCES , Context.MODE_PRIVATE);
        json = sharedpreferences.getString(Constants.MONEY_MANAGMENT_OBJECT , null);
        if(json != null){
            Log.d("amt" , json);
            Gson gson = new Gson();
            moneyManagerList = gson.fromJson(json ,new TypeToken<ArrayList<MoneyManager>>(){}.getType());

            total_money = getTotalMoney(moneyManagerList);
            total_spend = getTotalMoneySpend(moneyManagerList);

            total_amount.setText("" + total_money);
            int bal = total_money - total_spend;
            balanced_amount.setText("" + bal);

          /*  SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            String today_date = sdf.format(moneyManagerList.get(1).getDate());
            Log.d("test " , today_date);*/

          //  Log.d("test" , "" + moneyManagerList.get(1).getAmount_get());
            ArrayList<MoneyManager> reversemoneyManagerList = moneyManagerList;
            Collections.reverse(reversemoneyManagerList);
            MoneyManagementAdapter adpater = new MoneyManagementAdapter(getContext() , R.layout.money_manager_listview , reversemoneyManagerList);
            lv.setAdapter(adpater);
        }else{
            moneyManagerList = new ArrayList<MoneyManager>();
        }

        return v;
    }

    private int getTotalMoney(ArrayList<MoneyManager> moneyManagerList) {

        int money =0;
        for (MoneyManager m : moneyManagerList)
            money = money + m.getAmount_get();
        return money;
    }

    private int getTotalMoneySpend(ArrayList<MoneyManager> moneyManagerList) {

        int money =0;
        for (MoneyManager m : moneyManagerList)
            money = money + m.getAmount_spend();
        return money;

    }


}
