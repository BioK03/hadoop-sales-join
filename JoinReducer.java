import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Reducer;

public class JoinReducer extends Reducer<ProductIdKey, JoinGenericWritable, NullWritable, Text>{


	@Override
	public void reduce(ProductIdKey key, Iterable<JoinGenericWritable> values, Context context) throws IOException, InterruptedException{
		
		String output = "";
		
		int n = 0;
		int globalOrderQuantity = 0;
		double globalTotal = 0d;
		
		// Fetch chaque produit et ses ventes (qui ont été triés)
		for (JoinGenericWritable v : values) {
			Writable record = v.get();
			
			// Si c'est un produit, on construit le début de la chaîne 
			if (key.recordType.equals(ProductIdKey.PRODUCT_RECORD)){
				ProductRecord pRecord = (ProductRecord)record;
				output += "Id : " + key.productId.toString() + ", ";
				output += "Name : " + pRecord.productName.toString() + ", ";
				output += "Number : " + pRecord.productNumber.toString() + ", ";
			}
			
			// Si c'est une vente, on l'ajoute aux ventes existantes
			if (key.recordType.equals(ProductIdKey.SALES_RECORD)) {
				SalesOrderRecord salesRecord = (SalesOrderRecord)record;
				globalOrderQuantity += Integer.parseInt(salesRecord.orderQty.toString());
				globalTotal += Double.parseDouble(salesRecord.lineTotal.toString());
			}
			
			n++;
		}
		
		// Puis on print la chaîne de début avec les stats
		if (globalOrderQuantity > 0) {
			context.write(NullWritable.get(), new Text(output.toString() + "Quantity : " + n + ", Average quantity : " + globalOrderQuantity / n + ", Total : " + globalTotal + "€"));
		}
	}
}