package org.mapreducelab.spark.demo.hr

import java.io.File
import com.typesafe.scalalogging.LazyLogging
import org.apache.spark.sql.SparkSession
import org.mapreducelab.spark.util._
import com.typesafe.config._
import scala.util.Try

object Emp extends LazyLogging {
  def main(args: Array[String]) {

      if(System.getenv("SPARK_HOME") == null) {
          println("SPARK_HOME is not defined in the ENV settings.")
          sys.exit(1)
      }


      val configPath = Try(args(0)).getOrElse(new java.io.File(".").getCanonicalPath + s"${FS}src${FS}main${FS}resources${FS}hr${FS}emp.conf")

      logger.info(s"configPath: $configPath")

      val configFactory = ConfigFactory.parseFile(new File(configPath))

      implicit val hrConf = ConfigFactory.load(configFactory).getConfig("hr")



      val spark = SparkSession
        .builder
        .appName("Employee")
        .master(getSparkMaster)
        .config(getSparkSessionConfig(hrConf))
        .getOrCreate()


      logger.debug(s"spark.conf.getAll: ${spark.conf.getAll}")

      val hrDataPoint = HrDataPoint.apply

      val empdf = spark.read
       .format("com.databricks.spark.csv")
       .option("header", "true")
       .option("inferSchema", "true")
       .load(hrDataPoint.source)

      empdf.show()

      spark.stop()
  }
}
