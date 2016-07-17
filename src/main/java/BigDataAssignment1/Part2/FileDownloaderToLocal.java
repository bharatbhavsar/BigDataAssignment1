package BigDataAssignment1.Part2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class FileDownloaderToLocal {
 
	static FileDownloaderToLocal obj = new FileDownloaderToLocal();
	static HashSet<String> myUrls = new HashSet<String>(); 
	static String searchString;
	static String myPath;
	static String userID;
	static String timeStamp;
	static String dateUntil;
	static String dateFrom;
	
	private static final String API_KEY = "AuKHfiUerHEonMgXNUBQG3Nz5";
	private static final String API_SECRET = "0teS0sFhwYhbErBM5wzRnxczqBDtzoKaLWnO0RGqsrV5U3Jna6";
	private static final String ACCESS_TOKEN = "606077263-MBnZmra36l1TQLCRi7RdyFP2il9DdsRru1ZE0ZQk";
	private static final String ACCESS_SECRET = "YfvvebJoYiM6vs8WcUuptaE452vKGSFViIR0O7sbnMQUR";
	
	public static void main(String[] args) throws IOException{
		if(args.length != 1){
			System.out.println("Please enter Filename!");
		}else{
			searchString = args[0]; //search topic
			
			//Use this HashSet to download each URL to local file
			try {
				obj.fileDownloader();
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	
	void fileDownloader() throws IOException, TwitterException{
		
		String s = new File(".").getAbsolutePath().toString();
		timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		
	    myPath = s.toString()+"/" + timeStamp;
	    
	    System.out.println(myPath);
	    new File(myPath).mkdir();
	    
	    ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey(API_KEY);
        cb.setOAuthConsumerSecret(API_SECRET);
        cb.setOAuthAccessToken(ACCESS_TOKEN);
        cb.setOAuthAccessTokenSecret(ACCESS_SECRET);
		
	    Twitter twitter = new TwitterFactory(cb.build()).getInstance();
	    Query query = new Query(searchString);
	    QueryResult result;
	    query.setCount(3200);
	    
	    
	    
	    for(int i = 0; i < 6; i++){
	    	cal.add(Calendar.DATE, -i-1);
	    	dateFrom = format1.format(cal.getTime());
	    	query.setSince(dateFrom);
	    	cal.add(Calendar.DATE, -i);
	    	dateUntil = format1.format(cal.getTime());
	    	query.setSince(dateUntil);
	    	result = twitter.search(query);
	    	File currentFile = new File(myPath +"/file" + i +".txt");
	    	if (!currentFile.exists()) {
	    		currentFile.createNewFile();
			}
	    	FileWriter fw = new FileWriter(currentFile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			for (Status status : result.getTweets()) 
	        {
				bw.write(status.getText());
				bw.newLine();
		       	
	        }
			bw.close();
			int j=i+1;
			System.out.println("Timeline number " + j + " downloaded");
	    }
	}
}
