import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class Driver extends Configured implements Tool {

	public int run(String[] args) throws Exception
	{
		// vérifier les paramètres
		if (args.length != 3) {
			System.err.println("Usage: driver <input path1> <input path2> <outputpath>");
			System.exit(-1);
		}

		// Création du job via la configuration
		Configuration conf = this.getConf();
		Job job = Job.getInstance(conf, "Variance Job");
		
		//Driver est la classe principale
		job.setJarByClass(Driver.class);
		
		// Input = Text, Output = Text
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		// ProductIdKey est la classe dans laquelle nos données d'entrée sont parsées
		job.setMapOutputKeyClass(ProductIdKey.class);
		
		job.setMapOutputValueClass(JoinGenericWritable.class);
		
		// Ajout des fichiers à leur mapper respectif
		MultipleInputs.addInputPath(job, new Path(args[0]), TextInputFormat.class, SalesOrderMapper.class);
		MultipleInputs.addInputPath(job, new Path(args[1]), TextInputFormat.class, ProductMapper.class);
		
		// Join Reducer est notre classe Reducer
		job.setReducerClass(JoinReducer.class);
		
		// Instanciation des compateurs permettant de joindre les données
		job.setSortComparatorClass(JoinSortingComparator.class);
		job.setGroupingComparatorClass(JoinGroupingComparator.class);
		
		// Sortie texte
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);

		// Création du fichier de sortie
		FileOutputFormat.setOutputPath(job, new Path(args[2]));
		boolean status = job.waitForCompletion(true);
		if (status) {
			return 0;
		} else {
			return 1;
		}  
		
		
		
	}
	
	/**
     * point d'entrée du programme
     * @param args contient deux éléments : dossier à traiter et dossier des résultats
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        Driver driver = new Driver();
        int exitCode = ToolRunner.run(driver, args);
        System.exit(exitCode);
    }
	
	
}
