package com.test.service.titan.header

import java.sql.Date
import java.text.SimpleDateFormat
import java.time.LocalDateTime

import org.apache.log4j.Logger
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.functions._

object ServiceHeader extends App {
  val driver = "org.postgresql.Driver"
  val url = "jdbc:postgresql://localhost:5432/rtdms"
  val user = "postgres"
  val password = "temppass"
  //val usersDF = spark.read.format("avro").load("/Users/kumaram/Desktop/data/serviceheaderaug/","/Users/kumaram/Desktop/data/serviceheader/")

  val spark = SparkSession.builder()
    .appName("Aggregations and Grouping")
    .config("spark.master", "local[*]")
    .getOrCreate()



  //service.test_service.service_repairorder_inspection.export+14+0000027688.avro
  //STEP ONE: READING ALL SOURCE FILE OG GIVEN DAY.( WE CAN READ MULTIPLE DAY FILE ALSO IF REQUIRED)
  val usersDF = spark.read.format("avro").load("/Users/kumaram/Desktop/data/serviceheaderaug/","/Users/kumaram/Desktop/data/serviceheader/")


 /// println(" before partition =="+usersDF.printSchema())


  //DEFAULT NUMBER OF PARTITION CREATED BY SPARK
  println(" DEFAULT NUMBER OF PARTITION CREATED BY SPARK =="+usersDF.rdd.getNumPartitions)

  println("========================================")
  println("START TIME  ="+ LocalDateTime.now())
  println("========================================")
  println("                 ")

//  // FLAT THE AVRO SCHEMA TO TAKE REQUIRED COLUMNS :  WE CAN CONFIGURE OUTPUT HERE
//  val updatedf =  usersDF.select("meta.*","serviceRepairOrderHeader.*").drop("meta").drop("serviceRepairOrderHeader").drop("recordKeys")
//  .drop("ingestConfigName").drop("metadataVersion").drop("recordTrailId")
//   updatedf.show(5)
//
//
//
//  //TOTAL NUMBER OF RECORDS ON 7 AUG
//  val totalRecords = updatedf.count()
//  println("TOTAL NUMBER OF RECORDS ON 7 AUG  ="+ totalRecords )
//
//  println(" Initial number  partition =="+updatedf.rdd.getNumPartitions)
//
//
//  // DONE PARTITION SO THAT SPARK OPERATION WILL BE FAST , IN FUTURE WILL DO PARTITION BY STORE ID
//  val partitiobyKey  = updatedf.repartition(col("CNumber"),col("Account")).persist()
//
//  // NUMBER OF PARTITION NOW
//  println("After doing  partition =="+partitiobyKey.rdd.getNumPartitions)
//
//  val txt = partitiobyKey.coalesce(10)
//  println("After doing  partition =="+partitiobyKey.coalesce(10))
//
//  println("getNumPartitions  coalesce spartition =="+txt.rdd.getNumPartitions)
//
//
//  //TOTAL NUMBER OF FILES  ON 7 AUG CAME FROM SOURCE
//    val fileCount = partitiobyKey.groupBy(col("CNumber"), col("Account")).count()
//    fileCount.show(5);
//    println("EXPECTED NUMBER OF SOURCE FILES  ="+ fileCount.count() )
//
//
//  // DEDUPE : IT WILL DEDUPE AS PER CONFIGURATION
//  val uniqueRecord = partitiobyKey.groupBy(col("LoopCompanyID"), col("DmsRepairOrderId"))
//  val uniqueCount  =  uniqueRecord.count().count()
//
//  println("                 ")
//  ////UNiQUE COUNT ON 7th AUG
//  println("UNiQUE COUNT ON 7th AUG  ="+ uniqueCount )
//
//  //DUPLICATE RECORD ON 7th AUG
//  println("                 ")
//  println("DUPLICATE RECORD ON 7th AUG ="+ (totalRecords - uniqueCount ))
//
//  println("                 ")
//
//  println("========================================")
//  println( "BEFORE WRITING TO FILE TIME  ="+ LocalDateTime.now())
//  println("========================================")
//
//  // WE CAN RIGHT INTO THE CURATION ZONE
// // updatedf.write.mode("overwrite").partitionBy("CNumber").parquet("/tmp/bycnumber");

//
  // depending our need we can do OEM , we can drive new field and FTP location
  import io.delta.tables._
  usersDF.write.mode("overwrite").format("delta").save("/tmp/dealerfolder");

  println("========================================")
  println( "AFTER WRITING TO FILE TIME  ="+ LocalDateTime.now())
  println("========================================")
//


//   // FINAL STEP  : WE STORE IN POSTGRES
//    updatedf.select("Account","Address2","AddressScrubbed","AdvisorHrsSold").write.format("jdbc")
//      .option("driver", driver)
//      .option("url", url)
//      .option("user", user)
//      .option("password", password)
//      .option("dbtable", "service")
//      .mode("append")
//      .save()

  println("========================================")
  println(" TOTAL TIME TAKEN  ="+ LocalDateTime.now())
  println("========================================")




}
