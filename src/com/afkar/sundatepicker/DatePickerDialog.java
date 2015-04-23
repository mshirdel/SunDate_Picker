package com.afkar.sundatepicker;

import java.util.Calendar;

import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afkar.sundatepicker.month.MonthMainFragement;
import com.afkar.sundatepicker.tool.Date;
import com.afkar.sundatepicker.tool.JDF;
import com.afkar.sundatepicker.tool.Util;
import com.afkar.sundatepicker.year.YearMainFragement;

/*
 * Created by Alireza Afkar - 24/10/14
 */

public class DatePickerDialog extends DialogFragment implements OnClickListener {

	private static TextView dayTV;
	private static TextView monthTV;
	private static TextView yearTV;
	private static TextView dayNameTV;
	TextView doneTV;
	TextView cancelTV;

	public static LinearLayout dayMonth;
	FrameLayout frameLayout;
	FragmentManager fragmentManager;

	private static int mColor = 0;

	static int minYear;
	static int maxYear;

	private static GradientDrawable circle;
	private static Boolean mVibrate;
	private static Typeface mTypeFace = null;

	private static OnDateSetListener mCallBack;
	static int id;

	public static int maxMonth;

	public DatePickerDialog() {

	}

	public static DatePickerDialog newInstance(
			OnDateSetListener onDateSetListener, int requestID) {

		JDF jdf = new JDF();
		return newInstance(onDateSetListener, requestID, jdf.getIranianYear(),
				jdf.getIranianMonth(), jdf.getIranianDay());
	}

	public static DatePickerDialog newInstance(
			OnDateSetListener onDateSetListener, boolean darkTheme) {
		return newInstance(onDateSetListener, 0);
	}

	public static DatePickerDialog newInstance(
			OnDateSetListener onDateSetListener, int requestID, int year,
			int month, int day) {

		DatePickerDialog datePickerDialog = new DatePickerDialog();

		mCallBack = onDateSetListener;
		Date.setDate(year, month, day, false);
		minYear = new JDF().getIranianYear();
		maxYear = minYear + 2;
		mVibrate = true;
		id = requestID;
		mColor = 0;
		mTypeFace = null;
		maxMonth = 0;

		return datePickerDialog;
	}

	@Override
	public void onStart() {
		setRetainInstance(true);
		super.onStart();
	}

	@Override
	public View onCreateView(LayoutInflater layoutInflater,
			ViewGroup container, Bundle savedInstanceState) {

		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

		final View view = layoutInflater.inflate(R.layout.main_layout,
				container, false);

		if (mColor == 0)
			mColor = getResources().getColor(R.color.blue);

		circle = new GradientDrawable();
		circle.setCornerRadius(getResources().getDimension(
				R.dimen.circle_radius));
		circle.setColor(mColor);
		circle.setAlpha(50);

		frameLayout = (FrameLayout) view.findViewById(R.id.frame_container);
		fragmentManager = getChildFragmentManager();

		dayTV = (TextView) view.findViewById(R.id.day);
		monthTV = (TextView) view.findViewById(R.id.month);
		yearTV = (TextView) view.findViewById(R.id.year);
		dayNameTV = (TextView) view.findViewById(R.id.dayName);
		doneTV = (TextView) view.findViewById(R.id.done);
		cancelTV = (TextView) view.findViewById(R.id.cancel);
		dayMonth = (LinearLayout) view.findViewById(R.id.dayMonthBack);

		if (mTypeFace != null) {
			dayTV.setTypeface(mTypeFace);
			monthTV.setTypeface(mTypeFace);
			yearTV.setTypeface(mTypeFace);
			dayNameTV.setTypeface(mTypeFace);
			doneTV.setTypeface(mTypeFace);
			cancelTV.setTypeface(mTypeFace);
		}

		doneTV.setTextColor(mColor);
		cancelTV.setTextColor(mColor);

		((LinearLayout) view.findViewById(R.id.blue_card))
				.setBackgroundColor(mColor);

		dayMonth.setOnClickListener(this);
		yearTV.setOnClickListener(this);
		doneTV.setOnClickListener(this);
		cancelTV.setOnClickListener(this);

		updateDisplay(Date.getYear(), Date.getMonth(), Date.getDay());

		((LinearLayout) view.findViewById(R.id.dayMonthBack)).performClick();

		return view;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.year) {
			dayTV.setAlpha(0.5f);
			monthTV.setAlpha(0.5f);
			yearTV.setAlpha(1f);
			switchFragment(new YearMainFragement(minYear, maxYear));
		} else if (v.getId() == R.id.dayMonthBack) {
			dayTV.setAlpha(1f);
			monthTV.setAlpha(1f);
			yearTV.setAlpha(0.5f);
			switchFragment(new MonthMainFragement());
		} else if (v.getId() == R.id.done) {
			if (mCallBack != null) {
				Calendar calendar = Calendar.getInstance();
				JDF j = new JDF();
				j.setIranianDate(Date.getYear(), Date.getMonth(), Date.getDay());
				try {
					calendar = j.getGregorianCalendar(Date.getYear(),
							Date.getMonth(), Date.getDay());
				} catch (Exception e) {
				}
				mCallBack.onDateSet(id, calendar, Date.getYear(),
						Date.getMonth(), Date.getDay());
				Util.tryVibrate(getActivity());
			}
			dismiss();
		} else if (v.getId() == R.id.cancel) {
			dismiss();
		}
	}

	void switchFragment(final Fragment fragment) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.setCustomAnimations(android.R.anim.fade_in,
				android.R.anim.fade_out);
		transaction.replace(R.id.frame_container, fragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	/*
	 * Setters
	 */
	public void setMainColor(int color) {
		mColor = color;
	}

	public void setYearRange(int _minYear, int _maxYear) {
		minYear = _minYear;
		maxYear = _maxYear;
	}

	public void setInitialDate(int year, int month, int day) {
		Date.setDate(year, month, day, false);
	}

	public void setInitialDate(Calendar calendar) {
		JDF jdf = new JDF();
		jdf.setGregorianDate(calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH) + 1,
				calendar.get(Calendar.DAY_OF_MONTH));
		Date.setDate(jdf.getIranianYear(), jdf.getIranianMonth(),
				jdf.getIranianDay(), false);
	}

	public void setVibrate(boolean vibrate) {
		mVibrate = vibrate;
	}

	public void setTypeFace(Typeface typeface) {
		mTypeFace = typeface;
	}

	public void setRequestID(int requestID) {
		id = requestID;
	}

	public void setFutureDisabled(Boolean disabled) {
		if (disabled) {
			JDF jdf = new JDF();
			maxMonth = jdf.getIranianMonth();
			maxYear = jdf.getIranianYear();

			if (minYear > maxYear)
				minYear = maxYear - 1;

			if (Date.getMonth() > jdf.getIranianMonth())
				Date.setMonth(jdf.getIranianMonth());
			if (Date.getDay() > jdf.getIranianDay())
				Date.setDay(jdf.getIranianDay());
			if (Date.getYear() > jdf.getIranianYear())
				Date.setYear(jdf.getIranianYear());
		} else
			maxMonth = 0;
	}

	/*
	 * End of Setters
	 */

	/*
	 * Getters
	 */
	public static GradientDrawable getCircle() {
		return circle;
	}

	public static int getColor() {
		return mColor;
	}

	public static boolean checkVibrate() {
		return mVibrate;
	}

	public static Typeface getTypeFace() {
		return mTypeFace;
	}

	public static int getRequestID() {
		return id;
	}

	/*
	 * End of Getters
	 */

	public static void updateDisplay(int year, int month, int day) {
		try {
			DatePickerDialog.dayTV.setText("" + day);
			DatePickerDialog.monthTV.setText(JDF.monthNames[month - 1]);
			DatePickerDialog.yearTV.setText("" + year);
			DatePickerDialog.dayNameTV.setText(new JDF().getIranianDayName(
					year, month, day));
		} catch (Exception e) {
		}
	}

	public static abstract interface OnDateSetListener {
		public abstract void onDateSet(int id, Calendar calendar, int year,
				int month, int day);
	}
}
