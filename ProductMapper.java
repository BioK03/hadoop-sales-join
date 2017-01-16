import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

// Mappe les produits depuis le csv
public class ProductMapper extends Mapper<LongWritable, Text, ProductIdKey, JoinGenericWritable>{
   
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        
		String[] recordFields = value.toString().split("\\t");
        int productId = Integer.parseInt(recordFields[0]);
        String productName = recordFields[1];
        String productNumber = recordFields[2];
        
        // Parse les éléments en ProductIdKey et Product                                               
        ProductIdKey recordKey = new ProductIdKey(productId, ProductIdKey.PRODUCT_RECORD);
        ProductRecord record = new ProductRecord(productName, productNumber);
        
        
        JoinGenericWritable genericRecord = new JoinGenericWritable(record);
        context.write(recordKey, genericRecord);
    }
}