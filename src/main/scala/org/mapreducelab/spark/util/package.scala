package org.mapreducelab.spark

import java.io.File
import com.typesafe.config._
import com.typesafe.scalalogging.LazyLogging
import org.apache.spark.SparkConf
import scala.collection.JavaConversions._
import scala.language.implicitConversions
import scala.util.Try


package object util extends LazyLogging{

    val FS = File.separator
    val SPARK_CONFIG_ACCESS_KEY = "spark.hadoop.fs.s3a.access.key"
    val SPARK_CONFIG_SECRET_KEY = "spark.hadoop.fs.s3a.secret.key"
    val SPARK_CONFIG_ENDPOINT = "spark.hadoop.fs.s3a.endpoint"
    val AWS_ACCESS_KEY_ID = "AWS_ACCESS_KEY_ID"
    val AWS_SECRET_ACCESS_KEY = "AWS_SECRET_ACCESS_KEY"
    val S3_ENDPOINT = "S3_ENDPOINT"

    def stripQuotes(s: String) = s.stripMargin.replaceAll("\n", " ").replaceAll("\"","")

    def configValue2String(configValue: ConfigValue) =  stripQuotes(configValue.unwrapped().toString)

    def validateConfigPath(path: String)(implicit config: Config) = config.hasPath(path)

    def validateEnv(key: String) = System.getenv(key) match {
        case s: String if s.length > 0 => true
        case _ => false
    }

    def getSparkMaster(implicit config: Config) =  validateConfigPath("spark.master") match {
        case true => config.getString("spark.master")
        case false => "local"
    }

    def getSparkSessionConfig (implicit config: Config) = {

        val sparkConf = new SparkConf()

        if (validateConfigPath("spark.session-config")) {
            val sparkSessionConfig = config.getConfig("spark.session-config")
            val conf = sparkSessionConfig.entrySet().iterator().toSeq.map(entry => (stripQuotes(entry.getKey),configValue2String(entry.getValue)))
            sparkConf.setAll(conf)
        }

        if (!sparkConf.contains(SPARK_CONFIG_ACCESS_KEY) && validateEnv(AWS_ACCESS_KEY_ID) ) {
            sparkConf.set(SPARK_CONFIG_ACCESS_KEY,Try(System.getenv(AWS_ACCESS_KEY_ID)).getOrElse(""))
        }

        if (!sparkConf.contains(SPARK_CONFIG_SECRET_KEY) && validateEnv(AWS_SECRET_ACCESS_KEY) ) {
            sparkConf.set(SPARK_CONFIG_SECRET_KEY,Try(System.getenv(AWS_SECRET_ACCESS_KEY)).getOrElse(""))
        }

        if (!sparkConf.contains(SPARK_CONFIG_ENDPOINT) && validateEnv(S3_ENDPOINT) ) {
            sparkConf.set(SPARK_CONFIG_ENDPOINT,Try(System.getenv(S3_ENDPOINT)).getOrElse(""))
        }

        sparkConf
    }

}
