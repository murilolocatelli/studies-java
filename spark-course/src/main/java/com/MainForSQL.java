package com;

import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;

import java.util.Scanner;

import static org.apache.spark.sql.functions.col;

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

        FilterFunction<Row> func = row -> row.getAs("aaa").equals("Modern Art");

        //Dataset<Row> modernArtResults = dataset.filter("subject = 'Modern Art' and year >= 2007");
        //Dataset<Row> modernArtResults = dataset.filter((FilterFunction<Row>) row ->
        //        row.getAs("subject").equals("Modern Art") && Integer.parseInt(row.getAs("year")) >= 2007);

        Dataset<Row> modernArtResults = dataset
                .filter(col("subject").equalTo("Modern Art")
                        .and(col("year").geq (2007)));

        modernArtResults.show();

        dataset.createOrReplaceTempView("my_students_view");

        //Dataset<Row> viewResults = sparkSession.sql("select distinct(year) from my_students_view");
        Dataset<Row> viewResults = sparkSession.sql("select avg(score), max(score), min(score) from my_students_view where subject = 'French' and year >= 2007");

        viewResults.show();

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        sparkSession.close();
    }
}