package com.example.memchall;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import utils.CommonUtils;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
   private Context mContext;
  // Integer[] mThumbIds;
   public Vector<ImageView> mySDCardImages;
   private boolean isFilp;
   Integer flipimage = R.drawable.ic_launcher;
   // Constructor
   public ImageAdapter(Context c, boolean b, Vector<ImageView> lists) {
      mContext = c;
      
      isFilp = b;
      if(!b){
    	  mySDCardImages = new Vector<ImageView>();
    	  loadImages();
      }
      
      else
    	  loadflips();
      
      if(lists != null)
      {
    	  mySDCardImages = new Vector<ImageView>(lists);
    	 
      }
   }
   
   private void loadflips() {
	// TODO Auto-generated method stub
	
}

public void loadImages() {
	// TODO Auto-generated method stub
	   List<Integer> drawablesId = new ArrayList<Integer>();
	   int picIndex=12345;
	   File sdDir = new File(Environment.getExternalStorageDirectory()+"/SavedImages");
	   File[] sdDirFiles = sdDir.listFiles();
	   int[] randomindex = CommonUtils.generateRandomNumberArray(sdDirFiles.length);
	   System.out.println("Random Arry lenght" + randomindex.length);
	   for(int i=0; i< sdDirFiles.length; i++)
	   {
		   System.out.println("array index" + i);
		   System.out.println("Random Arry value " + randomindex[i]);
		   File singleFile = sdDirFiles[randomindex[i]-1];
	      ImageView myImageView = new ImageView(mContext);
	      myImageView.setImageDrawable(Drawable.createFromPath(singleFile.getAbsolutePath()));
	      myImageView.setId(picIndex);
	      picIndex++;
	      drawablesId.add(myImageView.getId());
	      mySDCardImages.add(myImageView);
	     
	      if(i == 8)
	    	  break;
	   }
	 //  mThumbIds = (Integer[])drawablesId.toArray(new Integer[0]);
}

public int getCount() {
	 if(!isFilp)
      return mySDCardImages.size();
	 else
		 return 9;
   }

   public Object getItem(int position) {
      return null;
   }

   public long getItemId(int position) {
      return 0;
   }
   
   // create a new ImageView for each item referenced by the Adapter
   public View getView(int position, View convertView, ViewGroup parent) {
      ImageView imageView;
      
      if (convertView == null) {
         imageView = new ImageView(mContext);
         imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
         imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
         imageView.setPadding(8, 8, 8, 8);
      } 
      else 
      {
         imageView = (ImageView) convertView;
      }
      if(!isFilp)
    	  imageView.setImageDrawable(mySDCardImages.get(position).getDrawable());
      
      else{
    	  if(mySDCardImages == null)
    		  imageView.setImageResource(flipimage);
    	  else if(mySDCardImages.get(position) != null)
    	  {
    		  imageView.setImageDrawable(mySDCardImages.get(position).getDrawable());
    	  }
    	  else
    	  imageView.setImageResource(flipimage);
      }
      return imageView;
   }
   
   /* Keep all Images in array
   public Integer[] mThumbIds = {
		   R.drawable.sample_2, R.drawable.sample_3,
		      R.drawable.sample_4, R.drawable.sample_5,
		      R.drawable.sample_6, R.drawable.sample_7,
		      R.drawable.sample_0, R.drawable.sample_1,
		      R.drawable.sample_8
   };*/
}