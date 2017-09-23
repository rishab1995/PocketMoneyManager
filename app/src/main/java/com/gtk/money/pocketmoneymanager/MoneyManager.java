package com.gtk.money.pocketmoneymanager;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by RISHAB on 20-09-2017.
 */

public class MoneyManager {

    private Date date;
    private int amount_spend;
    private int amount_get;

    MoneyManager(){
        date = Calendar.getInstance().getTime();
        amount_get = 0;
        amount_spend = 0;
    }

    public void setAmount_get(int amount_get){
        this.amount_get = amount_get;
    }

    public Date getDate() {
        return date;
    }

    public int getAmount_spend() {
        return amount_spend;
    }

    public void setAmount_spend(int amount_spend) {
        this.amount_spend = amount_spend;
    }

    public int getAmount_get() {
        return amount_get;
    }
}
