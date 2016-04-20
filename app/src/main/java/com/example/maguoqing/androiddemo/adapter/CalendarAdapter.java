package com.example.maguoqing.androiddemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.maguoqing.androiddemo.R;
import com.example.maguoqing.androiddemo.model.ClipItem;
import com.example.maguoqing.androiddemo.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by magq on 16/4/19.
 */
public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.Itemholder> {

    private int mPageNumber;
    private List<ClipItem> datas;
    private Context mContext;

    private Calendar calStartDate = Calendar.getInstance();
    private int iMonthViewCurrentMonth = 0;
    private Calendar today = Calendar.getInstance();

    public void setPageNumber(int pageNumber) {
        this.mPageNumber = pageNumber;
        this.datas = getDatas();
    }

    public ArrayList<ClipItem> getDatas() {
        ArrayList<ClipItem> items = new ArrayList<>();
        UpdateStartDateForMonth();
        for (int i = 1; i <= 42; i++) {
            items.add(new ClipItem(calStartDate.getTime()));
            calStartDate.add(Calendar.DAY_OF_MONTH, 1);
        }
        return items;
    }

    private void UpdateStartDateForMonth() {
        calStartDate = Utils.getSelectCalendar(mPageNumber);
        calStartDate.set(Calendar.DATE, 1);
        iMonthViewCurrentMonth = calStartDate.get(Calendar.MONTH);

        int iDay = 0;
        int iFirstDayOfWeek = Calendar.MONDAY;
        int iStartDay = iFirstDayOfWeek;
        if (iStartDay == Calendar.MONDAY) {
            iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
            if (iDay < 0)
                iDay = 6;
        }
        if (iStartDay == Calendar.SUNDAY) {
            iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
            if (iDay < 0)
                iDay = 6;
        }
        calStartDate.add(Calendar.DAY_OF_WEEK, -iDay);

        calStartDate.add(Calendar.DAY_OF_MONTH, -1);

    }

    @Override
    public Itemholder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_calendar_month, parent, false);
        v.setMinimumHeight(parent.getHeight() / 6);
        return new Itemholder(v);
    }

    @Override
    public void onBindViewHolder(Itemholder holder, int position) {
        ClipItem item = datas.get(position);

        int day = item.getDate().getDate();
        holder.tvDay.setText(String.valueOf(day));

        if (equalsDate(item.getDate(), today.getTime())) {
            holder.tvDay.setBackgroundColor(mContext.getResources().getColor(R.color.answer_blue));
            holder.tvDay.setTextColor(mContext.getResources().getColor(R.color.white));

            TextView txtToDay = new TextView(mContext);
            txtToDay.setGravity(Gravity.CENTER_HORIZONTAL);
            txtToDay.setBackgroundResource(R.color.answer_green);
            txtToDay.setTextColor(mContext.getResources().getColor(R.color.white));
            txtToDay.setTextSize(10);
            txtToDay.setText("TODAY!");
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 2, 0, 0);
            holder.llContent.addView(txtToDay, 0, lp);

            TextView test = new TextView(mContext);
            test.setGravity(Gravity.CENTER_HORIZONTAL);
            test.setBackgroundResource(R.color.answer_red);
            test.setTextColor(mContext.getResources().getColor(R.color.white));
            test.setTextSize(10);
            test.setText("TEST");
            holder.llContent.addView(test, 1, lp);
        }

        if (item.getDate().getMonth() != iMonthViewCurrentMonth) {
            holder.llContainer.setBackgroundResource(R.drawable.layout_divider_gray);
        }
    }

    private Boolean equalsDate(Date date1, Date date2) {
        if (date1.getYear() == date2.getYear()
                && date1.getMonth() == date2.getMonth()
                && date1.getDate() == date2.getDate()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class Itemholder extends RecyclerView.ViewHolder {

        private LinearLayout llContainer;
        private TextView tvDay;
        private LinearLayout llContent;

        public Itemholder(View itemView) {
            super(itemView);
            llContainer = (LinearLayout) itemView.findViewById(R.id.ll_container);
            tvDay = (TextView) itemView.findViewById(R.id.tv_day);
            llContent = (LinearLayout) itemView.findViewById(R.id.ll_content);
        }
    }
}
