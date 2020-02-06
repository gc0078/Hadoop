import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

public class OTTUsersReducer extends Reducer<Text,LongWritable,Text,LongWritable>
{	
   Map<Text,LongWritable> hMap = new HashMap<Text,LongWritable>();
   MultipleOutputs<Text,LongWritable> multipleOutputs;
   String appName,cName;
   OTTUsersArea oua = new OTTUsersArea();
   
   public void setup(Context context)
   {
	 multipleOutputs = new MultipleOutputs<Text,LongWritable>(context);
   }   
   
   public void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException,InterruptedException 
   {   
	 long sum = 0;
	  for (LongWritable val : values) 
      {            
        sum += val.get(); 
      }    	 
      appName = key.toString().split(":")[0];
      cName = key.toString().split(":")[1];
      hMap.put(new Text(appName), new LongWritable(sum));
      //System.out.println("HashMap values : "+hMap);    	
   
   }//end of reduce

   public void cleanup(Context context)throws IOException,InterruptedException
   {	
     //System.out.println("HashMap values in cleanup() : "+hMap);
	 List<Map.Entry<Text,LongWritable>> list = new LinkedList<>(hMap.entrySet());
	  
	  Collections.sort(list,new Comparator<Entry<Text,LongWritable>>()
	  {	  
	    public int compare(Entry<Text,LongWritable> o1,Entry<Text,LongWritable> o2)
	    {
	       int val = o1.getValue().compareTo(o2.getValue());
	       if(val>0)
	    	 return -1;
	       if(val<0)
	    	   return 1;
	       else 
	    	   return 0;
	    }
	  });
	  
	  int counter = 0;
	  for(Entry<Text,LongWritable> item: list)
	  {
		 //System.out.println("**** key,values:"+item);
		  if(counter++ == 5)		 
		    break;		 
		 //context.write(item.getKey(),item.getValue());
		 multipleOutputs.write(item.getKey(), item.getValue(),oua.getAreaName(cName));		 
		
	  }
	  
   }//end of cleanup() 
   
}//end of Reducer
