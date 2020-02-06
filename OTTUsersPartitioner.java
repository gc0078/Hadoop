import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;


public class OTTUsersPartitioner extends Partitioner<Text,LongWritable>
{
	public int getPartition(Text key, LongWritable value, int numOfPartitions) 
	{
		String group = key.toString();
		String stateId[] = group.split(":");
		System.out.println("partition value: "+stateId[1]);
		
		if(stateId[1].equals("231"))
		{
		  return 0;
		}
		if(stateId[1].equals("409"))
		{
		  return 1;
		}
		
		return 2;
				
	}
}
