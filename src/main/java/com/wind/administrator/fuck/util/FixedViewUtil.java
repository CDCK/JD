package com.wind.administrator.fuck.util;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class FixedViewUtil {

	public static void setGridViewHeightBasedOnChildren(GridView listView,int col) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i += col) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
			totalHeight += listView.getVerticalSpacing();
			if (i==listAdapter.getCount()-1) {
				totalHeight += listView.getVerticalSpacing();
			}
		}
		LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight;
		((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
		listView.setLayoutParams(params);
	}
	
	public static void setListViewHeightBasedOnChildren(ListView lv){  
		ListAdapter listAdapter = lv.getAdapter();
	    int listViewHeight = 0;  
	    int adaptCount = listAdapter.getCount();  
	    for(int i=0;i<adaptCount;i++){  
	        View temp = listAdapter.getView(i,null,lv);  
	        temp.measure(0,0);
			listViewHeight += temp.getMeasuredHeight();
	        listViewHeight += 10;
	    }
		listViewHeight -= 10;
		LayoutParams layoutParams = lv.getLayoutParams();
	    layoutParams.width = LayoutParams.MATCH_PARENT;  
	    layoutParams.height = listViewHeight;  
	    lv.setLayoutParams(layoutParams);  
	}

}
