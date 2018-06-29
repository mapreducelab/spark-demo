package org.mapreducelab.spark.demo


import org.apache.spark.sql.SparkSession

object Emp {
  def main(args: Array[String]) {

      val accessKeyId = System.getenv("AWS_ACCESS_KEY_ID")
      val secretAccessKey = System.getenv("AWS_SECRET_ACCESS_KEY")
      val s3Endpoint = System.getenv("S3_ENDPOINT")

      println(s"\nENDPOINT => ${s3Endpoint}\n")

      val spark = SparkSession
        .builder
        .appName("Employee")
        .config("spark.hadoop.fs.s3a.impl","org.apache.hadoop.fs.s3a.S3AFileSystem")
        .config("spark.hadoop.fs.s3a.endpoint", s3Endpoint)
        .config("spark.hadoop.fs.s3a.access.key", accessKeyId)
        .config("spark.hadoop.fs.s3a.secret.key", secretAccessKey)
        .config("spark.hadoop.fs.s3a.connection.ssl.enabled", "false")
        .getOrCreate()


      val source = args(0)
      val target = args(1)

      val empdf = spark.read
       .format("com.databricks.spark.csv")
       .option("header", "true")
       .option("inferSchema", "true")
       .load(source)

      empdf.show()

      spark.stop()
  }
}
