package com.websystique.springmvc.service;





import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

import org.apache.commons.io.FileUtils;





import org.iq80.leveldb.WriteBatch;

import com.loom.util.UUIDGenerator;

import com.websystique.springmvc.model.UserData;



	public class WorkerThread implements Callable<String> {
	
		String line;
		WriteBatch batch;
		
		public WorkerThread(String line, WriteBatch batch ){
	        this.line = line;
	       
			this.batch = batch; 
	    }

	    public String call() throws IOException {
	    	//Handle the hit...
	    	
	    	String parts[] = line.split(";");
	    	String emailid = parts[0];
	    	String username = parts[1];
	    	String operation = parts[2];
	    	String uuidfordeletion = parts[3];
	    	UserData data = new UserData();
	    	data.setUser_id(username);
	        data.setEmail_id(emailid);
	    	byte[] serialiseddata = LevelDBUtil.serialize(data);
		    String uuid = UUIDGenerator.generate(data.getEmail_id());
		    if(operation.equals("1")){
		    batch.put(uuid.getBytes(),serialiseddata);
		    batch.put(emailid.getBytes(),uuid.getBytes());
		    }
		    if(operation.equals("2"))
		    batch.delete(uuidfordeletion.getBytes());	
	 
	        return uuid+";"+emailid;
	    
	    }
			
	   
    
		
	 
	
	
}