package utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;




import java.net.URL;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FilenameUtils;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.util.Log;

public class CommonUtils {

	
	private static String SD_FOLDER = "/sdcard/X500E" ;
	public static String getSDFolder() {
		File folder = new File(SD_FOLDER);
		if (!folder.exists() && !folder.mkdir()) {
			return null;
		}
		return SD_FOLDER;
	}
	
	public static PackageInfo getPackageInfo(Context context) {
		PackageInfo info=null;
        try
        {	
        	 PackageManager manager = context.getPackageManager();
        	info = manager.getPackageInfo( context.getPackageName(), 0);
        } 
        catch (NameNotFoundException e) 
        {          
        	
        }
       return info;

	}

	public static boolean DownloadImage(String imageUrl, String folderName, String destinationFile)throws IOException
	{
		// TODO Auto-generated method stub
		URL url = new URL(imageUrl);
		File folder = new File(Environment.getExternalStorageDirectory()+"/SavedImages");
        if(!folder.exists()){
            if(folder.mkdirs())
               Log.i("created", "creatd");
        }
        String filePath = folder + File.separator +destinationFile;   

		//log.debug("DownloadImage" + filePath);		
		InputStream in = new BufferedInputStream(url.openStream());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int n = 0;
		while (-1!=(n=in.read(buf)))
		{
		   out.write(buf, 0, n);
		}
		out.close();
		in.close();
		byte[] response = out.toByteArray();
		FileOutputStream fos = new FileOutputStream(filePath);
		fos.write(response);
		fos.close();
		
		return true;
	}
	
	public static Document getDomElement(String xml){
	    Document doc = null;
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    try {

	        DocumentBuilder db = dbf.newDocumentBuilder();

	        InputSource is = new InputSource();
	            is.setCharacterStream(new StringReader(xml));
	            doc = db.parse(is); 

	        } catch (ParserConfigurationException e) {
	            Log.e("Error: ", e.getMessage());
	            return null;
	        } catch (SAXException e) {
	            Log.e("Error: ", e.getMessage());
	            return null;
	        } catch (IOException e) {
	            Log.e("Error: ", e.getMessage());
	            return null;
	        }
	            // return DOM
	        return doc;
	}
	
	public static boolean SavedImagesExists()
	{
		File folder = new File(Environment.getExternalStorageDirectory()+"/SavedImages");
        if(folder.exists() && folder.listFiles().length > 9){
        	return true;
        }
		return false;
	}

	public static int generateRandom(int max) {
		// TODO Auto-generated method stub
		 //note a single Random object is reused here
	    Random randomGenerator = new Random();
	    
	      return randomGenerator.nextInt(max+1);
	      
	}

	public static int[] generateRandomNumberArray(int length) {
		int [] A = new int[9];
        for(int i = 0; i< A.length; ){
            if(i == A.length){
                break;
            }
            int b = generateRandom(length);
            if(f(A,b) == false){
                A[i++] = b;
            } 
        }
        return A;
	}

	private static boolean f(int[] A, int n) {
		for(int i=0; i< A.length; i++){
            if(A[i] == n){
                return true;
            }
        }
        return false;
	}
}
