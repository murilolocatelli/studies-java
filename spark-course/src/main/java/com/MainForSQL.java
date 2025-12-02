package com;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class MainForSQL {
    public static void main(String[] args) {
        //Logger.getLogger("org.apache").setLevel(Level.WARN);
        //Logger.getLogger("org.sparkproject").setLevel(Level.WARN);

        System.setProperty("hadoop.home.dir", "c:/hadoop");

        SparkSession sparkSession = SparkSession.builder()
                .appName("Testing SQL")
                .master("local[*]")
                .getOrCreate();

        Dataset<Row> dataset = sparkSession.read().option("header", true).csv("src/main/resources/exams/students.csv");
        dataset.show();

        long count = dataset.count();
        System.out.println("Number of rows: " + count);

        sparkSession.close();
    }
}