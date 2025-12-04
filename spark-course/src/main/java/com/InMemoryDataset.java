package com;

import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.apache.spark.sql.functions.col;

public class InMemoryDataset {
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
        results = sparkSession.sql("select level, date_format(datetime, 'MMMM') as month from logging_table");
        //results.show();

        results.createOrReplaceTempView("logging_table");
        results = sparkSession.sql("select level, month, count(1) as total from logging_table group by level, month");
        results.show();

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        sparkSession.close();
    }
}