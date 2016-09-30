package com.example.epochjscalendar;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class CalendarViewActivity extends Activity {

        private static final String tag = "CalendarViewActivity";
        protected static final String MESSAGE_DATE="dateSelected";
        private ImageView calendarToJournalButton;
        private Button calendarDateButton;
        private WebView webview;
        private Date selectedCalDate;

        private final Handler jsHandler = new Handler();

        /** Called when the activity is first created. */
        @Override
        public void onCreate(Bundle savedInstanceState) {
                Log.d(tag, "Creating View ...");
                super.onCreate(savedInstanceState);

                // Set the View Layer
                Log.d(tag, "Setting-up the View Layer");
                setContentView(R.layout.calendar_view);

                // Go to CreateJournalEntry
                calendarToJournalButton = 
                	(ImageView) this.findViewById(R.id.calendarToJournalButton);
                calendarToJournalButton.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Log.d(tag, "Re-directing -&gt; CreateEntryScreen ...");
                                Intent intent = 
                                    new Intent(getApplicationContext(), 
                                        CreateEntryScreen.class);
                                //CalDate muss gesetzt werden bei der Auswahl im JavaScript
                                String message = getSelectedCalDate().toLocaleString();                                
                                intent.putExtra(CalendarViewActivity.MESSAGE_DATE, message);
                                
                                startActivity(intent);
                            }
                    });

                // User-Selected Calendar Date
                calendarDateButton = (Button) this.findViewById(R.id.calendarDateButton);

                // Get access to the WebView holder
                webview = (WebView) this.findViewById(R.id.webview);

                // Get the settings
                WebSettings settings = webview.getSettings();

                // Enable JavaScript
                settings.setJavaScriptEnabled(true);
                
                // Enable ZoomControls visibility
                settings.setSupportZoom(true);

                // Add JavaScript Interface
                webview.addJavascriptInterface(new MyJavaScriptInterface(), "android");
                
                // Set the Chrome Client
                webview.setWebChromeClient(new MyWebChromeClient());
                
                // Load the URL of the HTML file
                webview.loadUrl("file:///android_asset/calendarview.html");
                //webview.loadUrl("file:///android_asset/calendarview_missingTest_fgl.html");

            }

        public void setCalendarButton(Date selectedCalDate) {
                Log.d(tag, jsHandler.obtainMessage().toString());
                calendarDateButton.setText(
                    DateUtils.convertDateToSectionHeaderFormat(selectedCalDate.getTime()));
        }

        /**
         * 
         * @param selectedCalDate
         */
        public void setSelectedCalDate(Date selectedCalDate) {
                this.selectedCalDate = selectedCalDate;
            }

        /**
         * 
         * @return
         */
        public Date getSelectedCalDate()
            {
                return selectedCalDate;
            }

        /**
         * JAVA-&gt;JAVASCRIPT INTERFACE
         * 
         * @author wagied
         * 
         */
        final class MyJavaScriptInterface
            {
                private Date jsSelectedDate;
                MyJavaScriptInterface()
                    {
                        // EMPTY;
                    }

               
                public void onDayClick()
                    {                	
                        jsHandler.post(new Runnable()
                            {
                                public void run()
                                    {
                                        // Java telling JavaScript to do things
                                        webview.loadUrl("javascript: popup();");
                                    }
                            });
                    }

                /**
                 * NOTE: THIS FUNCTION IS BEING SET IN JAVASCRIPT User-selected Date in
                 * WebView
                 * 
                 * @param dateStr
                 */
                @JavascriptInterface
                public void setSelectedDate(String dateStr)
                    {
                	//von FGL ergänzt 20160925
                	//@JavascriptInterface is introduced in JellyBean. 
                	//You need to set the Project Build Target to API Level 17 From Eclipse IDE, 
                	//go to Project->Properties. Select Android and set the target The APK will work on older versions of android.
                        Toast.makeText(getApplicationContext(), dateStr, 
                            Toast.LENGTH_SHORT).show();
                        Log.d(tag, "User Selected Date: JavaScript -&gt; Java : " + dateStr);//FGL Erfolg: Dies wird in LogCat ausgegeben

                        // Set the User Selected Calendar date
                        setJsSelectedDate(new Date(Date.parse(dateStr)));
                        Log.d(tag, "java.util.Date Object: " + 
                            Date.parse(dateStr));
                    }
                private void setJsSelectedDate(Date userSelectedDate)
                    {
                        jsSelectedDate = userSelectedDate;
                        setSelectedCalDate(userSelectedDate);//FGL: DAMIT WIRD DAS DATUM DER HAUPTACTIVITY ÜBERGEBEN
                    }
                public Date getJsSelectedDate()
                    {
                        return jsSelectedDate;
                    }
                
                @JavascriptInterface
                public void setCalendarButton(Date selectedCalDate) {
                	//FGL: in diese Interfaceklasse aufgenommen, da sie mit window.android aus der HTML Seite aufgerufen wird.
                    Log.d(tag, "setCalendarButton: " + jsHandler.obtainMessage().toString());
                    if(calendarDateButton==null){
                    	Log.d(tag, "calendarDateButton is null");
                    }else{
                    	Log.d(tag, "calendarDateButton not null");   
                    	
                    	if(selectedCalDate==null){
                        	Log.d(tag, "selectedCalDate is null");
                        	
                        	Date objDate = getJsSelectedDate();
                        	if(objDate==null){
                        		Log.d(tag, "auch getJsSelectedDate() is null");
                        	}else{
                        		Log.d(tag, "getJsSelectedDate() not null");
                        		
                        		//FGL: Versuch als Lösung für die Fehlermeldung
                        		//     "CalledFrom WrongThreadException: Only the original thread that created a view hierarchie can touch its views." 
                        		 runOnUiThread(new Runnable() {
                                     @Override
                                     public void run() {
                                    	//FGL: Achtung: Bei Geräten mit kleiner Auflöscunt erscheint der Button nicht.
                                 		//     Dann gibt es deswegen eine NullPointer Exception.
                                 		//calendarDateButton.setText("TEST");
                                    	 Date objDate = getJsSelectedDate();
                                 		calendarDateButton.setText(DateUtils.convertDateToSectionHeaderFormat(objDate.getTime()));
                                     }
                                 });
                        		
                        		
                        	}                        	 
                        }else{
                        	Log.d(tag, "selectedCalDate not null");
                        	
                        	 calendarDateButton.setText(
                                     DateUtils.convertDateToSectionHeaderFormat(selectedCalDate.getTime()));
                        }
                    }
                                                          
                }
            }

        /**
         * Alert pop-up for debugging purposes
         * 
         * @author wdavid01
         * 
         */
        final class MyWebChromeClient extends WebChromeClient
            {
                @Override
                public boolean onJsAlert(WebView view, String url, 
                    String message, JsResult result)
                    {
                        Log.d(tag, message);
                        result.confirm();
                        return true;
                    }
            }

        @Override
        public void onDestroy()
            {
                Log.d(tag, "Destroying View!");
                super.onDestroy();
            }
    }
