/**
 * Copyright (C) 2008 Dai Odahara.
 */

package com.android.salesforce.frame;

import com.android.R;
import com.android.salesforce.sobject.AccountList;
import com.android.salesforce.viewer.ChartViewer;
import com.android.salesforce.viewer.DocumentViewer;

import android.app.TabActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.content.Intent;

/**
 * This class is a constrctor of tab frame.
 */
public class TabMenuMaker extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final TabHost tabHost = getTabHost();

		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("Home",
				getResources().getDrawable(R.drawable.icon3)).setContent(
				new Intent(this, AccountList.class)));

		/**
		 * This tab sets the intent flag so that it is recreated each time the
		 * tab is clicked.
		 */
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("Account",
				getResources().getDrawable(R.drawable.star_big_on)).setContent(
				new Intent(this, AccountList.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));

		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("Slides",
				getResources().getDrawable(R.drawable.icon)).setContent(
				new Intent(this, DocumentViewer.class)));

		/**
		 * This tab sets the intent flag so that it is recreated each time the
		 * tab is clicked.
		 */
		tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator("Charts",
				getResources().getDrawable(R.drawable.icon2)).setContent(
				new Intent(this, ChartViewer.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));

	}
}
