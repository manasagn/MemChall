package com.example.memchall;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.io.FilenameUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;





import utils.CommonUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity  {

	Button btn1;
	TextView waittext;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 btn1 = (Button)findViewById(R.id.start_btn);
		 waittext = (TextView)findViewById(R.id.textView2);
		 waittext.setVisibility(View.GONE);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		btn1.setVisibility(View.VISIBLE);
		waittext.setVisibility(View.INVISIBLE);
		
	}
	/** Called when the user touches the button */
	public void sendMessage(View view) {
	    // Do something in response to button click
		
		 if(CommonUtils.SavedImagesExists())
		 {
			 Toast.makeText(this, "Starting the challenge", Toast.LENGTH_SHORT).show();
			 Intent intent = new Intent(MainActivity.this,ImagesActivity.class);
				startActivity(intent);	
				 
		 }
			 
		 else
		 {
			 Toast.makeText(this, "Downloading images and Starting the challenge", Toast.LENGTH_SHORT).show();
			 new DownloadFlickerImages().execute();
		 }
	}
	
	
	private class DownloadFlickerImages extends  AsyncTask<Void, Void, Void> {

		public DownloadFlickerImages()
		{
		
		}
		
		@Override
		protected void onPreExecute()
		{	
			btn1.setVisibility(View.INVISIBLE);
			waittext.setVisibility(View.VISIBLE);
		}
		
		
		@Override
		protected Void doInBackground(Void... params) {
			BufferedReader in = null;
			StringBuilder reslt=null;

		    try {
		      
		       String inputLine="";
				  
				
		    	URL url = new URL("https://api.flickr.com/services/feeds/photos_public.gne");
				
				
				
				HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
				
				con.setRequestProperty
		        ("Content-Type","application/soap+xml; charset=utf-8");
				// con.setRequestProperty
		        // ("Host", ServiceHOST);
				 con.setRequestMethod("GET");
				 con.setDoInput(true);
				 con.setDoOutput(true);
				 con.setConnectTimeout(10 * 1000);
				  // con.setRequestProperty("SOAPAction", 
					//	   soapAction1);
				con.setChunkedStreamingMode(0);  
				//con.setSSLSocketFactory(createCertificate());			
				 
				  // con.setRequestProperty("Content-Length",String.valueOf(request.length()));
				   OutputStream reqStream = con.getOutputStream();
				   
				  // reqStream.write(request.getBytes());
				   reqStream.flush();
				   
				   reqStream.close();
				   con.connect();
				  
				   InputStream inptStream=con.getInputStream();
				  
				   reslt = new StringBuilder();
				   InputStreamReader isr = new InputStreamReader(inptStream);
			         in = new BufferedReader(isr);
			       
			
			        while ((inputLine = in.readLine()) != null)
			        {
			        	reslt.append(inputLine);
			            
			        }
	
			        //XML parsing to get href
		        Document elm=  CommonUtils.getDomElement(reslt.toString());
			
			 List<String> ImagesURI = new ArrayList<String>();
			     if(elm != null)
			     {
			         NodeList nList = elm.getElementsByTagName("entry");
			         for (int i = 0; i < nList.getLength(); i++) 
			         {			        	
			             Node nNode = nList.item(i);				            
			             Element eElement = (Element) nNode;			            
			             Element cElement =  (Element) eElement.getElementsByTagName("link").item(1);	
			            // System.out.println("href : " + cElement.getAttribute("rel"));
			             if(cElement.getAttribute("rel").equals("enclosure")){
			             System.out.println("href : " + cElement.getAttribute("href"));
			             }
			             ImagesURI.add(cElement.getAttribute("href"));
			         }
			     }
			     
			     //Downloading the images and saved in SD card
			     String[] imgExtensions = new String[] {"jpg", "jpeg", "bmp", "tiff", "gif", "png"};
			      
			     for (String img : ImagesURI)
		            {
		                if (!img.isEmpty())
		                {
		                	int index = img.lastIndexOf("/");		                	
		                   try {
								String filename = img.substring(index+1);
								 System.out.println("img name : " + filename);
								 String ext =  android.webkit.MimeTypeMap.getFileExtensionFromUrl(filename).toLowerCase();
							      
							        if ( Arrays.asList(imgExtensions).contains(ext)){					        	
							        
								if(CommonUtils.DownloadImage(img,"Images",filename ))
									System.out.println("downloaded");
							        }
							} catch (IOException e) {
								
								
							}
		                }
		                
		          
		            }
			      
			   
		    }  catch (IOException e) {
				//CommonUtils.LogException(e);
			} 
			finally {
		        if (in != null) {
		            try {
		                in.close();
		               
		            } catch (Exception e) {
		            	//CommonUtils.LogException(e);
		            }
		        }
		    }
			return null;
	    	 
			//return null;
		}
	
		@Override
		protected void onPostExecute(Void result) {
			if(CommonUtils.SavedImagesExists())
			 {
			Intent intent = new Intent(MainActivity.this,ImagesActivity.class);
			startActivity(intent);				
			 }
			else
			{
				 Toast.makeText(MainActivity.this, "Network issues, Try again", Toast.LENGTH_SHORT).show();
				btn1.setVisibility(View.VISIBLE);
				 waittext.setVisibility(View.GONE);
			}
			
		}	
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
