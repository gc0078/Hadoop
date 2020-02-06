import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

public class OTTUsersCount implements WritableComparable<OTTUsersCount> 
{
	Text appName;
	Text stateText;
	//Text phNo;    

    public OTTUsersCount(Text appName, Text stateText) 
    {
    	this.appName = appName;
    	this.stateText = stateText;
        //this.phNo = phNo;     
    }
    public OTTUsersCount() 
    {
    	this.appName = new Text();
    	this.stateText = new Text();
    	//this.phNo = new Text();         
    }

    public void write(DataOutput out) throws IOException 
    {
    	this.appName.write(out);
    	this.stateText.write(out);
    	//this.phNo.write(out);        
    }

    public void readFields(DataInput in) throws IOException 
    {
    	this.appName.readFields(in);
    	this.stateText.readFields(in);
    	//this.phNo.readFields(in);        
    }

    public int compareTo(OTTUsersCount pop) 
    {
        int intcnt = appName.compareTo(pop.appName);
        
        if (intcnt == 0) 
        {
          intcnt = stateText.compareTo(pop.stateText);        	
        } 
        /*if (intcnt == 0)
        {
          intcnt = phNo.compareTo(pop.phNo);
        }*/
        
        return intcnt;    
    }
  
    public String toString() {
        return appName.toString() + ":" + stateText.toString();// + ":" + phNo.toString();
    }
    
    public int hashCode(){
       return appName.hashCode()+31*stateText.hashCode();//+31*phNo.hashCode();
    }
}
