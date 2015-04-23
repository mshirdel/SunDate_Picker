package com.afkar.sundatepicker.month;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.afkar.sundatepicker.DatePickerDialog;
import com.afkar.sundatepicker.tool.Date;
import com.afkar.sundatepicker.tool.JDF;

/*
 * Created by Alireza Afkar - 24/10/14
 */

public class MonthPageAdapter extends FragmentPagerAdapter {
	public MonthPageAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return JDF.monthNames[position];
	}

	@Override
	public int getCount() {
		if (DatePickerDialog.maxMonth > 0
				&& new JDF().getIranianYear() == Date.getYear())
			return DatePickerDialog.maxMonth;
		else
			return JDF.monthNames.length;
	}

	@Override
	public Fragment getItem(int month) {
		return new MonthFragement(month);
	}
}