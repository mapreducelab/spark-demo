name := "Spark-Emp"

lazy val settings = Seq(
    organization := "org.mapreducelab",
    isSnapshot := true,
    version := "0.0.1",
    crossPaths := false,
    scalaVersion := "2.11.8"

)

val  sparkVersion = "2.3.1"

lazy val dependencies = Seq(
    libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
    libraryDependencies += "org.apache.spark" %% "spark-streaming" % sparkVersion % "provided",
    libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
    libraryDependencies += "org.apache.spark" %% "spark-hive" % sparkVersion % "provided",
    libraryDependencies += "org.apache.spark" %% "spark-graphx" % sparkVersion % "provided",
    libraryDependencies += "org.apache.hadoop" % "hadoop-aws" % "2.7.6" % "provided",
    libraryDependencies += "com.amazonaws" % "aws-java-sdk" % "1.7.4" % "provided",
    libraryDependencies += "com.databricks" %% "spark-csv" % "1.5.0",
    libraryDependencies += "com.datastax.spark" %% "spark-cassandra-connector" % "2.0.7" % "provided",
    // libraryDependencies += "mysql" %% "mysql-connector-java" % "5.11.42" % "provided",
    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3",
    libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",
    libraryDependencies += "com.typesafe" % "config" % "1.3.2" % "provided",
    libraryDependencies += "org.alluxio" % "alluxio-core-client-fs" % "1.7.1",
    libraryDependencies += "com.github.mrpowers" %% "spark-fast-tests" % "0.11.0" % "test",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % Test withSources() withJavadoc(),
    libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.5" % Provided withSources() withJavadoc()

)

lazy val root = (project in file("."))
	.settings(settings : _*)
	.settings(dependencies)

assemblyMergeStrategy in assembly := {
    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
    case x => MergeStrategy.first
}