package com.gtk.money.pocketmoneymanager;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;



/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {


    private Button add , save;
    private TextView date;
    private EditText amtGet;
    private EditText amtSpend;
    private MoneyManager moneyManager;
    public SharedPreferences sharedpreferences;
    private PieChart pieChart;
    private ArrayList<MoneyManager> moneyManagerList;
    String json;
    int total_money;
    int total_spend;
    int bal;
    int current_date;
    SimpleDateFormat sdf;
    TextView amountYouSpend;
    TextView balAmountLeft;
    ProgressBar spend_prog;
    TextView getView;
    TextView spendView;
    Boolean findTodayData = false;
    private TextView histV;
    private TextView history;


    public Home() {
        // Required empty public constructor
        //fetch the today date
        sdf = new SimpleDateFormat("ddMMyyyy");
        current_date = Integer.parseInt(sdf.format(Calendar.getInstance().getTime()));
        Log.d("current_date" , "" + current_date);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment

        getObjectId(v);
        getDataFieldDisabled();

        if(json != null){
            Log.d("amt" , json);
            Gson gson = new Gson();
            moneyManagerList = gson.fromJson(json ,new TypeToken<ArrayList<MoneyManager>>(){}.getType());
            total_money = getTotalMoney(moneyManagerList);
            total_spend = getTotalMoneySpend(moneyManagerList);
            bal = total_money - total_spend;
        }else{
            moneyManagerList = new ArrayList<MoneyManager>();
        }

        setToptextView();

      /*  List<PieEntry> val = new ArrayList<>();
        val.add(new PieEntry(total_spend ,0));
        val.add(new PieEntry(bal , 1));

        PieDataSet pd = new PieDataSet(val , "Money");

        pd.setColors(ColorTemplate.JOYFUL_COLORS);

        ArrayList<String> xval = new ArrayList<>();
        xval.add("Total Spend");
        xval.add("Balanced");

        PieData data = new PieData(pd);

        data.setValueFormatter(new DefaultValueFormatter(0));
        pieChart.setData(data);

*/
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* if(json != null){
                    Gson gson = new Gson();
                    moneyManager = gson.fromJson(json , MoneyManager.class);
                }else*/
                getDataFieldEnabled();
                int today_date = sharedpreferences.getInt(Constants.TODAY_DAY , 0);

                if( current_date != today_date ){
                    Log.d("today_date" , "" +today_date);
                    today_date = current_date;
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putInt(Constants.TODAY_DAY , today_date);
                    editor.commit();
                    moneyManager = new MoneyManager();
                }else{
                   for(MoneyManager mm : moneyManagerList){
                       if(Integer.parseInt(sdf.format(mm.getDate())) == today_date) {
                           Log.d("test_comp_date" ,sdf.format(mm.getDate()) );
                           moneyManager = mm;
                           findTodayData = true;
                           break;
                       }
                   }
                }

                if (findTodayData){
                    history.setText("Added (Rs.) : " + moneyManager.getAmount_get() + "  --  Spend (Rs.): " + moneyManager.getAmount_spend() );
                    histV.setVisibility(View.VISIBLE);
                    history.setVisibility(View.VISIBLE);
                }

                date.setText(moneyManager.getDate().toString());
                amtGet.setText("0");
                amtSpend.setText("0");
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Amt" , amtGet.getText().toString()  );
                int amount_get = Integer.parseInt(amtGet.getText().toString());
                int amount_spend = Integer.parseInt(amtSpend.getText().toString());
                moneyManager.setAmount_get(moneyManager.getAmount_get() + amount_get);
                moneyManager.setAmount_spend(moneyManager.getAmount_spend() + amount_spend);
                if (!findTodayData)
                    moneyManagerList.add(moneyManager);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(moneyManagerList);
                editor.putString(Constants.MONEY_MANAGMENT_OBJECT , json);
                editor.commit();
                Toast.makeText(getActivity() ,  "Detail Save Successfully" , Toast.LENGTH_SHORT).show();
                getDataFieldDisabled();
                setToptextView();
            }
        });

        return v;
    }

    private void setToptextView() {
        amountYouSpend.setText("Money You Spend : " + total_spend);
        balAmountLeft.setText("Bal : " + bal);
        spend_prog.setMax(total_money);
        spend_prog.setProgress(total_spend);
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

    private void getObjectId(View v){
        add = (Button) v.findViewById(R.id.addDetails);
        save = (Button) v.findViewById(R.id.setDetails);
        date = (TextView) v.findViewById(R.id.date);
        amtGet = (EditText) v.findViewById(R.id.get);
        amtSpend = (EditText) v.findViewById(R.id.spend);
      //  pieChart = (PieChart) v.findViewById(R.id.pichart);
        amountYouSpend =  (TextView) v.findViewById(R.id.amountYouSpend);
        balAmountLeft = (TextView) v.findViewById(R.id.balAMountLeft);
        spend_prog = (ProgressBar) v.findViewById(R.id.spendProgress);
        getView = (TextView) v.findViewById(R.id.getView);
        spendView = (TextView) v.findViewById(R.id.spendView);
        histV = v.findViewById(R.id.historyText);
        history = v.findViewById(R.id.history);
        sharedpreferences = this.getActivity().getSharedPreferences(Constants.MY_PREFERENCES , Context.MODE_PRIVATE);
        json = sharedpreferences.getString(Constants.MONEY_MANAGMENT_OBJECT , null);
    }

    private void getDataFieldDisabled(){
        amtSpend.setEnabled(false);
        amtGet.setEnabled(false);
        getView.setEnabled(false);
        spendView.setEnabled(false);
        save.setEnabled(false);
    }

    private void getDataFieldEnabled(){
        amtSpend.setEnabled(true);
        amtGet.setEnabled(true);
        getView.setEnabled(true);
        spendView.setEnabled(true);
        save.setEnabled(true);
    }

}
