import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.WritableComparable;

// Sert de point commun aux produits et aux ventes
public class ProductIdKey implements WritableComparable <ProductIdKey> {
	
	// Sert de type pour les deux classes : produit = PRODUC_RECORD, vente = DATA_RECORD
	public static final IntWritable PRODUCT_RECORD = new IntWritable(0);
	public static final IntWritable SALES_RECORD = new IntWritable(1);
	
	public IntWritable productId = new IntWritable();
	public IntWritable recordType = new IntWritable();
	
	
	
	
	public ProductIdKey () {}

	public ProductIdKey(int productId, IntWritable type) {
	    this.productId.set(productId);
	    this.recordType = type;
	}

	public void readFields(DataInput in) throws IOException {
		this.productId.readFields(in);
		this.recordType.readFields(in);
	}

	public void write(DataOutput out) throws IOException {
		this.productId.write(out);
	    this.recordType.write(out);
		
	}
	
	// Permet de comparer deux productId
	public int compareTo(ProductIdKey other) {
		
		if (this.productId.equals(other.productId )) {
	        return this.recordType.compareTo(other.recordType);
	    } else {
	        return this.productId.compareTo(other.productId);
	    }
	}
	
	
	
	

}
