package BigDataAssignment1.Part2;
// FileCopyWithProgress Copies a local file to a Hadoop filesystem, and shows progress
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

//FileCopyWithProgress
public class FileCopyFromLocalToHDFS {
  public static void main(String[] args) throws Exception {
    String localSrc = args[0];
    File source = new File(localSrc);
    File[] fileList = source.listFiles();
    String dst = args[1];
    for(int i = 0; i < fileList.length; i++){
    	
	    InputStream in = new BufferedInputStream(new FileInputStream(fileList[i].toString()));
	    
	    Configuration conf = new Configuration();
	    conf.addResource(new Path("/usr/local/hadoop-2.4.1/etc/hadoop/core-site.xml"));
	    conf.addResource(new Path("/usr/local/hadoop-2.4.1/etc/hadoop/hdfs-site.xml"));
	
	    FileSystem fs = FileSystem.get(URI.create(dst), conf);
	    OutputStream out = fs.create(new Path(dst +"/file"+ i + ".txt"), new Progressable() {
	      public void progress() {
	        System.out.print(".");
	      }
	    });
	    
	    IOUtils.copyBytes(in, out, 4096, true);
	    
	  }
  }
}

