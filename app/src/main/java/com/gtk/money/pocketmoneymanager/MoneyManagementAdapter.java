package com.gtk.money.pocketmoneymanager;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by RISHAB on 21-09-2017.
 */

public class MoneyManagementAdapter extends ArrayAdapter<MoneyManager> {

    private ArrayList<MoneyManager> moneyManagerArrayList;
    View v;
    LayoutInflater inflater;

    private static class ViewHolder{
        TextView date;
        TextView get;
        TextView spend;
        TextView monthyear;
        SimpleDateFormat tdate , tmonth;

        ViewHolder(){
            tdate = new SimpleDateFormat("dd");
            tmonth = new SimpleDateFormat("E MM-yyyy HH:mm");
        }
    }


    public MoneyManagementAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<MoneyManager> objects) {
        super(context, resource, objects);
        moneyManagerArrayList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        v = convertView;
        ViewHolder viewHolder;

        if(v == null){
            viewHolder = new ViewHolder();
            inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.money_manager_listview ,  null);
            viewHolder.date = (TextView) v.findViewById(R.id.dateView);
            viewHolder.get = (TextView) v.findViewById(R.id.getAmountView);
            viewHolder.spend = (TextView) v.findViewById(R.id.spendAmountView);
            viewHolder.monthyear = (TextView) v.findViewById(R.id.monthYearView);
            v.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) v.getTag();
        }

        viewHolder.date.setText(viewHolder.tdate.format(moneyManagerArrayList.get(position).getDate()));
        viewHolder.monthyear.setText(viewHolder.tmonth.format(moneyManagerArrayList.get(position).getDate()));
        viewHolder.get.setText("" +moneyManagerArrayList.get(position).getAmount_get());
        viewHolder.spend.setText("" +moneyManagerArrayList.get(position).getAmount_spend());
        return v;
    }
}
