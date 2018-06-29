name := "Spark-Emp"

lazy val settings = Seq(
  organization := "org.mapreducelab",
  isSnapshot := true,
  version := "0.0.1",
  crossPaths := false,
  scalaVersion := "2.11.8"
)

val sparkVersion = "2.3.1"
lazy val dependencies = Seq(
   libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
   libraryDependencies += "org.apache.spark" %% "spark-streaming" % sparkVersion % "provided",
   libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
   libraryDependencies += "org.apache.spark" %% "spark-hive" % sparkVersion % "provided",
   libraryDependencies += "org.apache.spark" %% "spark-graphx" % sparkVersion % "provided",
   libraryDependencies += "org.apache.hadoop" % "hadoop-aws" % "2.7.6" % "provided",
   libraryDependencies += "com.databricks" %% "spark-csv" % "1.5.0"
)

lazy val root = (project in file("."))
	.settings(settings : _*)
	.settings(dependencies)

assemblyMergeStrategy in assembly := {
    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
    case x => MergeStrategy.first
}