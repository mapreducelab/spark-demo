# spark-demo

Add environment variable for blob storage:

```
export AWS_ACCESS_KEY_ID="xxx"
export AWS_SECRET_ACCESS_KEY="xxx"
export S3_ENDPOINT="http://<ENDPOINT HOST>:<ENDPOINT PORT>"
``` 

Run sbt assembly to create a package:

`sbt clean assembly`


Submit spark application:

```
spark-submit \
  --class <main-class> \
  --master <master-url> \
  <application-jar> \
  [application-arguments]
```

e.g.:

```
spark-submit \
  --class "org.mapreducelab.spark.demo.Emp" \
  --master "local[*]" \
  "spark-demo/target/Spark-Emp-assembly-0.0.1.jar" \
  "s3a://<BUCKET>/<PATH-TO-FOLDER>"
```

You can try to evaluate the public s3 files:

```
spark-submit \
  --class "org.mapreducelab.spark.demo.Emp" \
  --master "local[*]" \
  "mapreducelab/spark-demo/target/Spark-Emp-assembly-0.0.1.jar" \
  "s3a://mapreducelabs/spark/demo/hr/emp/emp.csv"
```
