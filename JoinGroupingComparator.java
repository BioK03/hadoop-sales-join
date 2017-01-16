import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class JoinGroupingComparator extends WritableComparator {
    
	public JoinGroupingComparator() {
        super (ProductIdKey.class, true);
    }                             
	
	
	// Vérifie si les deux variables passées sont égales (si leur productId est égal)
    @SuppressWarnings("rawtypes")
	@Override
    public int compare (WritableComparable a, WritableComparable b){
        ProductIdKey first = (ProductIdKey) a;
        ProductIdKey second = (ProductIdKey) b;
                      
        return first.productId.compareTo(second.productId);
    }
}