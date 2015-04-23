package com.afkar.sundatepicker.year;

import java.util.Arrays;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.afkar.sundatepicker.R;
import com.afkar.sundatepicker.tool.Date;

/*
 * Created by Alireza Afkar - 24/10/14
 */

public class YearFragement extends Fragment {
	int[] years;

	public YearFragement(int[] years) {
		this.years = years;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.years_layout, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		ListView list = (ListView) view.findViewById(android.R.id.list);
		list.setSelector(getResources().getDrawable(R.drawable.transparent));
		list.setAdapter(new YearAdapter(getActivity(), years));
		int index=Arrays.binarySearch(years, Date.getYear());
		list.setSelection(index);
		super.onViewCreated(view, savedInstanceState);
	}
}