import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class GroupMR 
{
    public static class GroupMapper extends Mapper<LongWritable, Text, Country, LongWritable> 
    {        
        Country cntry = new Country();
        
        Text cntText = new Text();        
        Text stateText = new Text();
        LongWritable populat = new LongWritable();

        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException 
        {

            String line = value.toString();
            String[] keyvalue = line.split(",");
            cntText.set(new Text(keyvalue[1]));
            stateText.set(new Text(keyvalue[3]));
            populat.set(Long.parseLong(keyvalue[2]));
            Country cntry = new Country(cntText, stateText);
            context.write(cntry, populat);
        }
    }

    public static class GroupReducer extends Reducer<Country, LongWritable, Country, LongWritable> 
    {   
        public void reduce(Country key, Iterable<LongWritable> values, Context context) throws IOException,InterruptedException 
        {
        	long sum = 0;
        	  
        	for (LongWritable val : values) 
        	{            
              sum += val.get();
            }
            context.write(key, new LongWritable(sum));
        }
    }
 
    private static class Country implements WritableComparable<Country> 
    {
        Text country;
        Text state;

        public Country(Text country, Text state) {
            this.country = country;
            this.state = state;
        }
        public Country() {
            this.country = new Text();
            this.state = new Text();
        }

        public void write(DataOutput out) throws IOException {
            this.country.write(out);
            this.state.write(out);
        }
   
        public void readFields(DataInput in) throws IOException {
            this.country.readFields(in);
            this.state.readFields(in);
        }

        public int compareTo(Country pop) 
        {
            int intcnt = country.compareTo(pop.country);
            if (intcnt == 0) 
            {
            	intcnt = state.compareTo(pop.state);
            } 
            return intcnt;    
        }
      
        public String toString() {
            return country.toString() + ":" + state.toString();
        }
        
        public int hashCode(){
           return country.hashCode()+31*state.hashCode();
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException 
    {
        //FileUtils.deleteDirectory(new File("/output"));
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "GroupMR");
        job.setJarByClass(GroupMR.class);
        job.setMapperClass(GroupMapper.class);
        job.setReducerClass(GroupReducer.class);
        job.setOutputKeyClass(Country.class);
        job.setOutputValueClass(LongWritable.class);
        FileInputFormat.setMaxInputSplitSize(job, 10);
        FileInputFormat.setMinInputSplitSize(job, 100);
        FileInputFormat.addInputPath(job, new Path("/input/OTT-DATA.txt"));
        FileOutputFormat.setOutputPath(job, new Path("/output"));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}