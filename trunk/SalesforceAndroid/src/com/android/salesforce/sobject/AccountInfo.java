/**
 * Copyright (C) 2008 Dai Odahara.
 */

package com.android.salesforce.sobject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.R;
import com.android.salesforce.frame.FieldHolder;
import com.android.salesforce.frame.SectionHolder;
import com.android.salesforce.util.SObjectDB;
import com.android.salesforce.util.StaticInformation;

public class AccountInfo extends SObject implements SObjectIF {
		private static final String TAG = "AccountInfo";
		private static final String SOBJECT_TYPE = "Account";
		public static String WebSite;
		public static String Description;
		public static String Phone;
		public static String Site;
				
		//private static final String pn = "com.android.salesforce.sobject.";
		
		@Override
	    protected void onCreate(Bundle icicle) {
			super.onCreate(icicle);
			//Toast.makeText(AccountInfo.this, "Tabnaized Frame is to be implemented", Toast.LENGTH_LONG).show();

	        setContentView(R.layout.detailinfo_array);
	        Bundle bundle = getIntent().getExtras();
			
	        //TextView top = (TextView) findViewById(R.id.list_top);
	        //top.setText("Account Infomation");
	        //top.setTextColor(0xFF708090);
			
	        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
	        layout.setSelected(true);
	        
	        String id = bundle.getString("Id");
	        Log.v(TAG,"id : " + id);
	        
	        HashMap tempDB = SObjectDB.SOBJECT_DB.get(SOBJECT_TYPE).get(id);
	        HashMap<String, HashMap<String, String>> uDB = SObjectDB.SYSTEM_DB;
	        //Map<String, String> nav = SObjectDB.IdAndNAV.get(id);
	        //Set<Entry<String, String>> se = nav.entrySet();

	        //Pattern p1 = Pattern.compile(".*Website.*");
	        //Pattern p2 = Pattern.compile(".*Phone.*");

	        //int sSize = SObjectDB.SECTIONS.size();
	        ArrayList<SectionHolder> shs = SObjectDB.SOBJECTS.get(SOBJECT_TYPE).sections;
	        for(SectionHolder sh : shs) {
	        	/** setting section header */
	        	TextView sv = new TextView(this);
	        	sv.setText(sh.name);
	        	sv.setTextSize(19);
	        	sv.setPadding(0, 3, 0, 0);
	        	sv.setBackgroundColor(0xEEFFFACD);
	        	sv.setTextColor(0xFF688E44);
	            LinearLayout.LayoutParams hl = new LinearLayout.LayoutParams(
	                    LinearLayout.LayoutParams.FILL_PARENT,
	                    LinearLayout.LayoutParams.WRAP_CONTENT
	            );
	            
	            layout.addView(sv, hl);
	            
	            ArrayList<FieldHolder> fhs = sh.fields;
	            for(FieldHolder fs : fhs) {
	            	/** setting label */
		        	TextView label = new TextView(this);
		        	label.setText(fs.label);
		        	label.setTextSize(18);
		        	label.setTextColor(0xFF688E23);
		            LinearLayout.LayoutParams fl = new LinearLayout.LayoutParams(
		                    LinearLayout.LayoutParams.FILL_PARENT,
		                    LinearLayout.LayoutParams.WRAP_CONTENT
		            );
		            
		            layout.addView(label, fl);
		            		
		            /** setting value */
		            TextView data = new TextView(this);
		            String an = fs.value;
		            if(an.equals("ParentId"))continue;
		            //TextView textView2 = (TextView) findViewById(R.id.item_value);
		            String v = (String)(tempDB.get(an));

		            data.setTextColor(0xFF000000);
		            data.setText(v);
		            Log.v(TAG, an);
		           // Linkify.addLinks(textView2, Linkify.ALL);
		            if(an.equals("Website")) Linkify.addLinks(data, Linkify.WEB_URLS);
		            else if(an.equals("Phone")) Linkify.addLinks(data, Linkify.PHONE_NUMBERS);
		            else if(an.equals("AnnualRevenue")) data.setText(String.valueOf(Double.valueOf(v).intValue()));
		         
		            else if(an.equals("CreatedById"))data.setText(uDB.get(v).get("Name"));
		            else if(an.equals("OwnerId"))data.setText(uDB.get(v).get("Name"));
		            else if(an.equals("LastModifiedById"))data.setText(uDB.get(v).get("Name"));
		            else if(an.equals("gMapParam__c")){
		            	if(v == "" || v == null)continue;
		            	Log.v(TAG, "param:" + v);
		            	String[] ss = v.split(",");
		            	Log.v(TAG, ss[0] + ":" + ss[1] + ":" + ss[2] + ":" + ss[3]);
		            	String[] st = ss[4].split(":");
		            	Log.v(TAG, st[0] + ":" + st[1]);
		            	String url = "http://www.google.co.jp/maps?q=" + ss[0] + "," + ss[1].trim() + "&z=" + st[1];
		            	data.setText(url);
		            	Linkify.addLinks(data, Linkify.WEB_URLS);
		            }
		            else if(v == "" || v == null) data.setText("-");
		            
		            data.setTextSize(18);

		            layout.addView(data, fl);
	        
	            }
	        }    
		}
	    
		/**
		private void test() {
	        for (Map.Entry<String, String> e : se){ 

	        	TextView textView1 = new TextView(this);
	        	//TextView textView1 = (TextView) findViewById(R.id.item_label);
	        	String label = SObjectDB.AccountLayoutNameToLabel.get(e.getKey());
	        	if(label.equals("Deleted"))continue;
	        	if(label.equals("System Modstamp"))continue;
	        	if(label.equals("Last Modified Date"))continue;
	        	if(label.equals("Created By ID"))continue;
	        	if(label.equals("Owner ID"))continue;
	        	if(label.equals("Last Modified By ID"))continue;
	        	if(label.equals("Created Date"))continue;
	        	
	        	//Log.v(TAG, "label : " + label);
	        	
	        	textView1.setText(label);
	        	textView1.setTextSize(18);
		        //top.setTextColor(0xE6E6FA00);
	        	//textView1.setTextColor(0x00FF0000);
	        	textView1.setTextColor(0xFF688E23);
	            //textView1.setFocusable(true);
	            //textView1.setAutoLinkMask(Linkify.ALL);
	            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
	                    LinearLayout.LayoutParams.FILL_PARENT,
	                    LinearLayout.LayoutParams.WRAP_CONTENT
	            );
	            
	            layout.addView(textView1, p);

	            TextView textView2 = new TextView(this);
	            //TextView textView2 = (TextView) findViewById(R.id.item_value);
	            textView2.setText(e.getValue());
	            textView2.setTextColor(0xFF000000);
	            //Log.v(TAG, e.getValue());
	           // Linkify.addLinks(textView2, Linkify.ALL);
	            if(label.equals("Website")) Linkify.addLinks(textView2, Linkify.WEB_URLS);
	            if(label.equals("Account Phone")) Linkify.addLinks(textView2, Linkify.PHONE_NUMBERS);
	            textView2.setTextSize(18);
	            //textView2.setFocusable(false);
	            layout.addView(textView2, p);
	        }
			Log.v(TAG, "Detail Infomation Loading End");
	    }
		*/
		
		public Field[] getFields() {
			try {
				Class cl = Class.forName(StaticInformation.SOBJECT_PACKAGE_NAME + "AccountInfo");
				return cl.getFields();
		
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
			}
			return null;
		}
}