import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class OTTUsersMapper extends Mapper<LongWritable, Text, Text, LongWritable> 
{ 
	OTTUsersCount ouc = new OTTUsersCount();
    
	Text appText = new Text();
	Text stateText = new Text();
	//Text phNo = new Text();    
    LongWritable usage = new LongWritable();

    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException 
    {
        String line = value.toString();
        String[] keyvalue = line.split(",");
        //phNo.set(new Text(keyvalue[0]));
        appText.set(new Text(keyvalue[1]));
        usage.set(Long.parseLong(keyvalue[2]));
        stateText.set(new Text(keyvalue[3]));        
        OTTUsersCount ouc = new OTTUsersCount(appText, stateText);        
        context.write(new Text(ouc.toString()), usage);
    }
}
