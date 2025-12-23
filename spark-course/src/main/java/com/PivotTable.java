package com;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.date_format;

public class PivotTable {
    public static void main(String[] args) {
        //Logger.getLogger("org.apache").setLevel(Level.WARN);
        //Logger.getLogger("org.sparkproject").setLevel(Level.WARN);

        System.setProperty("hadoop.home.dir", "c:/hadoop");

        SparkSession sparkSession = SparkSession.builder()
                .appName("Testing SQL")
                .master("local[*]")
                .getOrCreate();

        List<Row> inMemory = new ArrayList<>();
        inMemory.add(RowFactory.create("WARN", "2016-12-31 04:19:32"));
        inMemory.add(RowFactory.create("FATAL", "2016-12-31 03:22:34"));
        inMemory.add(RowFactory.create("WARN", "2016-12-31 03:21:21"));
        inMemory.add(RowFactory.create("INFO", "2015-4-21 14:32:21"));
        inMemory.add(RowFactory.create("FATAL","2015-4-21 19:23:20"));

        StructField[] structFields = new StructField[] {
                new StructField("level", DataTypes.StringType,false, Metadata.empty()),
                new StructField("datetime",DataTypes.StringType, false, Metadata.empty())
        };

        StructType structType = new StructType(structFields);

        Dataset<Row> results = sparkSession.createDataFrame(inMemory, structType);

        results.createOrReplaceTempView("logging_table");

        results = results.select(col("level"),
                date_format(col("datetime"), "MMMM").alias("month"),
                date_format(col("datetime"), "M").alias("monthnum").cast(DataTypes.IntegerType) );

        Object[] months = new Object[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        List<Object> columns = Arrays.asList(months);

        results = results.groupBy("level").pivot("month", columns).count();

        results.show();

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        sparkSession.close();
    }
}