package com.test.service.titan.header

import org.apache.spark.sql.{ SparkSession}
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

object TestSparkQuery extends App {


  val spark = SparkSession.builder().appName("test query app").config("spark.master", "local[*]").getOrCreate();

  val df3 = spark.read.option("header", "true").csv("test.csv")



  df3.show(5)
  df3.groupBy("Product").count().show(5)



}
