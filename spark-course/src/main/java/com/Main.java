package com;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //Logger.getLogger("org.apache").setLevel(Level.WARN);
        //Logger.getLogger("org.sparkproject").setLevel(Level.WARN);
        List<Integer> inputData = new ArrayList<>();
        inputData.add(1);
        inputData.add(2);
        inputData.add(3);
        inputData.add(4);
        inputData.add(5);

        SparkConf sparkConf = new SparkConf();
        sparkConf.setAppName("Spark Course");
        sparkConf.setMaster("local[*]");

        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);

        JavaRDD<Integer> myRDD = sparkContext.parallelize(inputData);

        // reduce
        Integer sum = myRDD.reduce(Integer::sum);
        System.out.println("Sum: " + sum);

        // map
        JavaRDD<Double> sqrtRdd = myRDD.map(Math::sqrt);

        // The collect method is used here to bring all the results to a local execution.
        // Without it, the results would be computed on the distributed nodes, but not brought back to the driver node.
        // This is useful for debugging and testing, but should be avoided in production code as it can cause memory issues.
        // Additionally, if the RDD contains non-serializable objects, without the collect method, the execution could cause a non-serializable exception.
        sqrtRdd.collect().forEach(System.out::println);

        // tuple
        JavaRDD<Tuple2> myTupleRdd = myRDD.map(value -> new Tuple2<>(value, Math.sqrt(value)));

        List<String> inputData2 = new ArrayList<>();
        inputData2.add("WARN: Tuesday 4 September 0405");
        inputData2.add("ERROR: Tuesday 4 September 0408");
        inputData2.add("FATAL: Wednesday 5 September 1632");
        inputData2.add("ERROR: Friday 7 September 1854");
        inputData2.add("WARN: Saturday 8 September 1942");

        sparkContext.parallelize(inputData2)
                .mapToPair(rawValue -> new Tuple2<>(rawValue.split(":")[0] , 1L  ))
                .reduceByKey((value1, value2) -> value1 + value2)
                .foreach(tuple -> System.out.println(tuple._1 + " has " + tuple._2 + " instances"));

        // groupbykey version - not recommended due to performance (see later) AND the iterable
        // is awkward to work with.
//        sparkContext.parallelize(inputData2)
//                .mapToPair(rawValue -> new Tuple2<>(rawValue.split(":")[0] , 1L  ))
//                .groupByKey()
//                .foreach( tuple -> System.out.println(tuple._1 + " has " + Iterables.size(tuple._2) + " instances") );

        // reading from disk
        JavaRDD<String> initialRdd = sparkContext.textFile("src/main/resources/subtitles/input.txt");

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