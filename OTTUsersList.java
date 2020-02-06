import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.LazyOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class OTTUsersList 
{
	public static void main(String[] args) throws Exception 
	{		
	    Configuration conf = new Configuration();
	    Job job = Job.getInstance(conf, "Top-5 Applications");
	    Path output = new Path("/ott_data");
	    FileSystem hdfs = FileSystem.get(conf);	    
	    if (hdfs.exists(output)) {
	        hdfs.delete(output, true);
	    }	   	    
	    job.setJarByClass(OTTUsersList.class);
	    job.setMapperClass(OTTUsersMapper.class);
	    //job.setCombinerClass(OTTUsersReducer.class);	    
	    job.setReducerClass(OTTUsersReducer.class);
	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(LongWritable.class);
	    job.setPartitionerClass(OTTUsersPartitioner.class);
	    job.setNumReduceTasks(3);	    
	    LazyOutputFormat.setOutputFormatClass(job, TextOutputFormat.class);
		MultipleOutputs.addNamedOutput(job, "text", TextOutputFormat.class,Text.class,LongWritable.class);		
		job.getConfiguration().set("mapreduce.output.basename", "text");
	    //FileInputFormat.setMaxInputSplitSize(job, 10);
        //FileInputFormat.setMinInputSplitSize(job, 100);
	    FileInputFormat.addInputPath(job, new Path("/input/ott_users.txt"));
	    FileOutputFormat.setOutputPath(job, new Path("/ott_data"));
	    System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
