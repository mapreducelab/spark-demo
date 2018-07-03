package org.mapreducelab.spark.demo

import org.mapreducelab.spark.util._
import com.typesafe.config._
import com.typesafe.scalalogging.LazyLogging
import scala.language.implicitConversions


package hr {

    sealed trait EmpConf extends Serializable with LazyLogging {
        @transient def config: Config
    }


    case class HrDataPoint (source: String, target: String)
}

package object hr {

   object HrDataPoint  {
        def apply(implicit config: Config) = {
            val source = if (validateConfigPath("emp-source")) config.getString("emp-source") else ""
            val target = if (validateConfigPath("emp-source")) config.getString("emp-source") else ""
            new HrDataPoint(source = source, target = target)
        }
    }


}
