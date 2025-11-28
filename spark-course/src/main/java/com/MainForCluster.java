package com;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

public class MainForCluster {
    public static void main(String[] args) {
        //Logger.getLogger("org.apache").setLevel(Level.WARN);
        //Logger.getLogger("org.sparkproject").setLevel(Level.WARN);

        SparkConf sparkConf = new SparkConf();
        sparkConf.setAppName("Spark Course");

        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);

        // reading from disk
        JavaRDD<String> initialRdd = sparkContext.textFile("s3://murilo-vpp-spark-demos/input.txt");

        JavaRDD<String> lettersOnlyRdd = initialRdd.map( sentence -> sentence.replaceAll("[^a-zA-Z\\s]", "").toLowerCase() );

        JavaRDD<String> removedBlankLines = lettersOnlyRdd.filter( sentence -> sentence.trim().length() > 0 );

        JavaRDD<String> justWords = removedBlankLines.flatMap(sentence -> Arrays.asList(sentence.split(" ")).iterator());

        JavaRDD<String> blankWordsRemoved = justWords.filter(word -> word.trim().length() > 0);

        //JavaRDD<String> justInterestingWords = blankWordsRemoved.filter(word -> Util.isNotBoring(word));

        JavaPairRDD<String, Long> pairRdd = blankWordsRemoved.mapToPair(word -> new Tuple2<String, Long>(word, 1L));

        JavaPairRDD<String, Long> totals = pairRdd.reduceByKey((value1, value2) -> value1 + value2);

        JavaPairRDD<Long, String> switched = totals.mapToPair(tuple -> new Tuple2<Long, String> (tuple._2, tuple._1 ));

        JavaPairRDD<Long, String> sorted = switched.sortByKey(false);

        System.out.println(sorted.getNumPartitions() + " partitions");

        List<Tuple2<Long,String>> results = sorted.take(10);
        results.forEach(System.out::println);

        sparkContext.close();
    }
}