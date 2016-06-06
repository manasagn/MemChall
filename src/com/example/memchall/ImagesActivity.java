package com.example.memchall;

import java.io.File;
import java.util.Vector;

import org.apache.commons.math.optimization.GoalType;

import utils.CommonUtils;
import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.webkit.WebStorage.Origin;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ImagesActivity extends Activity{
	private TextView timerValue;
	GridView gridview;
	TextView findimage;
	boolean isdone = true;
	int  i =0;
	private Vector<ImageView> mySDCardImages;
	private Vector<ImageView> selectedImages = new Vector<ImageView>(9);
	int[] randomindex;
	ImageView imageView ;
	int OrginalPosition;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		
		 gridview = (GridView) findViewById(R.id.gridview);
		 ImageAdapter orgiImages = new ImageAdapter(this,false,null);
	      gridview.setAdapter(orgiImages);
	      timerValue = (TextView) findViewById(R.id.timerValue);
	      findimage = (TextView) findViewById(R.id.findImage);
	      findimage.setVisibility(View.GONE);
	      mySDCardImages = orgiImages.mySDCardImages;
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		
		new CountDownTimer(15000, 1000) {

		     public void onTick(long millisUntilFinished) {
		    	 timerValue.setText("seconds remaining: " + millisUntilFinished / 1000);
		     }

		     public void onFinish() {
		         timerValue.setVisibility(View.GONE);
		         findimage.setVisibility(View.VISIBLE);
		         gridview.setAdapter(new ImageAdapter(ImagesActivity.this,true,null));
		        intializeSelectedImages();
		  	   showrandomPic();
		  	   
		  		   
		         gridview.setOnItemClickListener(new OnItemClickListener() {
		             public void onItemClick(AdapterView<?> parent, View v,
		                     int position, long id) {
		                System.out.println("position " + position);
		                if(position == OrginalPosition)
		                {
		                	System.out.println("Show the image");
		                	 ImageAdapter im = (ImageAdapter) parent.getAdapter();
		                	 System.out.println("vectorsize " + selectedImages.size());
		                	 selectedImages.set(position, mySDCardImages.get(position));
		                	 im =new ImageAdapter(ImagesActivity.this,true,selectedImages);
		                	 gridview.setAdapter(im);
		                	 if(i < 9 )
		                		 showrandomPic();
		                	 else
		                	 {
		                		 findimage.setVisibility(View.GONE);
		                		 imageView.setVisibility(View.GONE);
		                	 }
		                	
		                		 
		                	  
		                }
		             }
		         });
		  	   
		     }

			
		  }.start();
	}
	

	private void intializeSelectedImages() {
		// TODO Auto-generated method stub
	for(int i=0; i<9; i++)
		selectedImages.add(i, null);
	
	randomindex = CommonUtils.generateRandomNumberArray(mySDCardImages.size());
	for(int i=0; i<9; i++)
		System.out.println("Random display of finding " + randomindex[i]);
	}
	
	public void showrandomPic()
	{
		   System.out.println("Random Arry value " + randomindex[i]);
		  OrginalPosition = randomindex[i]-1;
		 System.out.println("OrginalPosition" + OrginalPosition);
		System.out.println("Imagwe" + mySDCardImages.get(randomindex[i]-1));
        imageView = (ImageView) findViewById(R.id.SingleView);
       imageView.setImageDrawable(mySDCardImages.get(randomindex[i]-1).getDrawable());
       i++;
	}
}
