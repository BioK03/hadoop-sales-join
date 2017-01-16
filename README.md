<img src="http://gblogs.cisco.com/fr-datacenter/wp-content/uploads/sites/14/2013/09/hadoop-elephant_logo.png" alt="Hadoop Logo" height="200"/>

# hadoop-sales-join

This repo is an example of Hadoop Map-Combiner-Reduce operation with a join over CSV files.

Driver is a Java program that gathers data from Product.csv and SalesOrderDetail.csv ( from [Microsoft](https://technet.microsoft.com/en-us/library/ms124894%28v=sql.100%29.aspx) )

The goal of this is to make stats about sales using a join between the two files over the productId column.

## File structure

The Product.csv is structured like this :

Id | Name | Number | Make Flag | Finished Goods Flag | Color | Safety Stock Level | ...
--- | --- | --- | --- | --- | --- | --- | ---

The SalesOrderDetail.csv is structures like this :

Sales Order id | Sales Order Detail id | Tracking Number | Order Quantity | Product id | Special Offer Id | Unit Price | ...
--- | --- | --- | --- | --- | --- | --- | ---

## Execute the project
With hadoop installed, you must put both files on the hadoop disk :

`hadoop fs -put Product.csv /test`

`hadoop fs -put SalesOrderDetail.csv /test`

Next, after having compiled the project (with Maven for example : `mvn clean package`), you will execute the project :

`hadoop jar NameOfYourJar.jar Driver /test/Product.csv /test/SalesOrderDetail.csv /results`

You can see the results using <img src="http://gethue.com/wp-content/uploads/2014/03/hue_logo_300dpi_huge.png" height="15"/> (Hue) for example.

## Raw results

Here is a sample of the results, with, for each product, its name, product number, the quantity sold, the average quantity per order, and the total
```
Id : 707, Name : Sport-100 Helmet, Red, Number : HL-U509-R, Quantity : 3084, Average quantity : 2, Total : 157772.39439200202€
Id : 708, Name : Sport-100 Helmet, Black, Number : HL-U509, Quantity : 3008, Average quantity : 2, Total : 160869.51783600022€
Id : 709, Name : Mountain Bike Socks, M, Number : SO-B909-M, Quantity : 189, Average quantity : 5, Total : 6060.388199999999€
Id : 710, Name : Mountain Bike Socks, L, Number : SO-B909-L, Quantity : 45, Average quantity : 2, Total : 512.9999999999999€
Id : 711, Name : Sport-100 Helmet, Blue, Number : HL-U509-B, Quantity : 3091, Average quantity : 2, Total : 165406.6170490031€
Id : 712, Name : AWC Logo Cap, Number : CA-1098, Quantity : 3383, Average quantity : 2, Total : 51229.4456229981€
Id : 713, Name : Long-Sleeve Logo Jersey, S, Number : LJ-0192-S, Quantity : 430, Average quantity : 0, Total : 21445.7100000001€
Id : 714, Name : Long-Sleeve Logo Jersey, M, Number : LJ-0192-M, Quantity : 1219, Average quantity : 2, Total : 115249.21497600155€
Id : 715, Name : Long-Sleeve Logo Jersey, L, Number : LJ-0192-L, Quantity : 1636, Average quantity : 4, Total : 198754.97535999876€
Id : 716, Name : Long-Sleeve Logo Jersey, XL, Number : LJ-0192-X, Quantity : 1077, Average quantity : 2, Total : 95611.19708000106€
Id : 717, Name : HL Road Frame - Red, 62, Number : FR-R92R-62, Quantity : 219, Average quantity : 2, Total : 394255.5723999998€
Id : 718, Name : HL Road Frame - Red, 44, Number : FR-R92R-44, Quantity : 220, Average quantity : 2, Total : 395182.6992999997€
Id : 719, Name : HL Road Frame - Red, 48, Number : FR-R92R-48, Quantity : 45, Average quantity : 2, Total : 89872.17360000002€
Id : 722, Name : LL Road Frame - Black, 58, Number : FR-R38B-58, Quantity : 393, Average quantity : 2, Total : 177635.90400000016€
Id : 723, Name : LL Road Frame - Black, 60, Number : FR-R38B-60, Quantity : 53, Average quantity : 2, Total : 24844.6922€
Id : 725, Name : LL Road Frame - Red, 44, Number : FR-R38R-44, Quantity : 375, Average quantity : 2, Total : 194692.59910400014€
Id : 726, Name : LL Road Frame - Red, 48, Number : FR-R38R-48, Quantity : 289, Average quantity : 2, Total : 132125.25219999987€
Id : 727, Name : LL Road Frame - Red, 52, Number : FR-R38R-52, Quantity : 49, Average quantity : 2, Total : 20104.443400000004€
```
