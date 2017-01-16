import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

// Mappe les ventes depuis le csv
public class SalesOrderMapper extends Mapper<LongWritable, Text, ProductIdKey, JoinGenericWritable>{

	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {                           
		
		String[] recordFields = value.toString().split("\\t");
		
		int productId = Integer.parseInt(recordFields[4]);
		int orderQty = Integer.parseInt(recordFields[3]);
		double lineTotal = Double.parseDouble(recordFields[8]);
		
		// Parse les données en un ProductIdKey et un SalesOrderRecord
		ProductIdKey recordKey = new ProductIdKey(productId, ProductIdKey.SALES_RECORD);
		SalesOrderRecord record = new SalesOrderRecord(orderQty, lineTotal);

		JoinGenericWritable genericRecord = new JoinGenericWritable(record);
		context.write(recordKey, genericRecord);
	}
}