# sbt-jacoco - Code Coverage via JaCoCo in sbt (fork)

This is a fork of [sbt-jacoco](https://github.com/sbt/sbt-jacoco), an [sbt](http://scala-sbt.org/) plugin for code coverage analysis via [JaCoCo](http://www.eclemma.org/jacoco/).

See the [docs](http://scala-sbt.org/sbt-jacoco) for details on configuration options.


## Differences between fork and original repository

Original repository was forked due to issues during testing of our enterprise application. We are using Play Framework, which generates `Routes.class` from router configuration in `routes` config file. However, we have crossed a point when this file is so large that JaCoCo fails to instrument it. Running `sbt jacoco` would fail with following exception:

<details>
<summary>Stack trace</summary>

```java
java.io.IOException: Error while instrumenting Routes$$anonfun$routes$1.class.
    at org.jacoco.core.instr.Instrumenter.instrumentError(Instrumenter.java:175)
    at org.jacoco.core.instr.Instrumenter.instrument(Instrumenter.java:125)
    at org.jacoco.core.instr.Instrumenter.instrument(Instrumenter.java:150)
    at com.github.sbt.jacoco.data.InstrumentationUtils$$anonfun$instrumentClasses$4$$anonfun$apply$3.apply(InstrumentationUtils.scala:64)
    at com.github.sbt.jacoco.data.InstrumentationUtils$$anonfun$instrumentClasses$4$$anonfun$apply$3.apply(InstrumentationUtils.scala:61)
    at resource.AbstractManagedResource$$anonfun$5.apply(AbstractManagedResource.scala:88)
    at scala.util.control.Exception$Catch$$anonfun$either$1.apply(Exception.scala:124)
    at scala.util.control.Exception$Catch$$anonfun$either$1.apply(Exception.scala:124)
    at scala.util.control.Exception$Catch.apply(Exception.scala:102)
    at scala.util.control.Exception$Catch.either(Exception.scala:124)
    at resource.AbstractManagedResource.acquireFor(AbstractManagedResource.scala:88)
    at resource.ManagedResourceOperations$class.apply(ManagedResourceOperations.scala:26)
    at resource.AbstractManagedResource.apply(AbstractManagedResource.scala:50)
    at resource.ManagedResourceOperations$class.acquireAndGet(ManagedResourceOperations.scala:25)
    at resource.AbstractManagedResource.acquireAndGet(AbstractManagedResource.scala:50)
    at resource.ManagedResourceOperations$class.foreach(ManagedResourceOperations.scala:53)
    at resource.AbstractManagedResource.foreach(AbstractManagedResource.scala:50)
    at com.github.sbt.jacoco.data.InstrumentationUtils$$anonfun$instrumentClasses$4.apply(InstrumentationUtils.scala:61)
    at com.github.sbt.jacoco.data.InstrumentationUtils$$anonfun$instrumentClasses$4.apply(InstrumentationUtils.scala:60)
    at scala.collection.mutable.ResizableArray$class.foreach(ResizableArray.scala:59)
    at scala.collection.mutable.ArrayBuffer.foreach(ArrayBuffer.scala:47)
    at com.github.sbt.jacoco.data.InstrumentationUtils$.instrumentClasses(InstrumentationUtils.scala:60)
    at com.github.sbt.jacoco.BaseJacocoPlugin$$anonfun$taskValues$8.apply(BaseJacocoPlugin.scala:98)
    at com.github.sbt.jacoco.BaseJacocoPlugin$$anonfun$taskValues$8.apply(BaseJacocoPlugin.scala:98)
    at scala.Function1$$anonfun$compose$1.apply(Function1.scala:47)
    at sbt.$tilde$greater$$anonfun$$u2219$1.apply(TypeFunctions.scala:40)
    at sbt.std.Transform$$anon$4.work(System.scala:63)
    at sbt.Execute$$anonfun$submit$1$$anonfun$apply$1.apply(Execute.scala:228)
    at sbt.Execute$$anonfun$submit$1$$anonfun$apply$1.apply(Execute.scala:228)
    at sbt.ErrorHandling$.wideConvert(ErrorHandling.scala:17)
    at sbt.Execute.work(Execute.scala:237)
    at sbt.Execute$$anonfun$submit$1.apply(Execute.scala:228)
    at sbt.Execute$$anonfun$submit$1.apply(Execute.scala:228)
    at sbt.ConcurrentRestrictions$$anon$4$$anonfun$1.apply(ConcurrentRestrictions.scala:159)
    at sbt.CompletionService$$anon$2.call(CompletionService.scala:28)
    at java.util.concurrent.FutureTask.run(FutureTask.java:266)
    at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
    at java.util.concurrent.FutureTask.run(FutureTask.java:266)
    at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
    at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
    at java.lang.Thread.run(Thread.java:745)
Caused by: java.lang.ArrayIndexOutOfBoundsException: 65536
    at org.objectweb.asm.ClassReader.readLabel(ClassReader.java:2443)
    at org.objectweb.asm.ClassReader.createLabel(ClassReader.java:2459)
    at org.objectweb.asm.ClassReader.readStackMapFrame(ClassReader.java:3118)
    at org.objectweb.asm.ClassReader.readCode(ClassReader.java:1849)
    at org.objectweb.asm.ClassReader.readMethod(ClassReader.java:1275)
    at org.objectweb.asm.ClassReader.accept(ClassReader.java:679)
    at org.objectweb.asm.ClassReader.accept(ClassReader.java:391)
    at org.jacoco.core.instr.Instrumenter.instrument(Instrumenter.java:103)
    at org.jacoco.core.instr.Instrumenter.instrument(Instrumenter.java:123)
    at org.jacoco.core.instr.Instrumenter.instrument(Instrumenter.java:150)
    at com.github.sbt.jacoco.data.InstrumentationUtils$$anonfun$instrumentClasses$4$$anonfun$apply$3.apply(InstrumentationUtils.scala:64)
    at com.github.sbt.jacoco.data.InstrumentationUtils$$anonfun$instrumentClasses$4$$anonfun$apply$3.apply(InstrumentationUtils.scala:61)
    at resource.AbstractManagedResource$$anonfun$5.apply(AbstractManagedResource.scala:88)
    at scala.util.control.Exception$Catch$$anonfun$either$1.apply(Exception.scala:124)
    at scala.util.control.Exception$Catch$$anonfun$either$1.apply(Exception.scala:124)
    at scala.util.control.Exception$Catch.apply(Exception.scala:102)
    at scala.util.control.Exception$Catch.either(Exception.scala:124)
    at resource.AbstractManagedResource.acquireFor(AbstractManagedResource.scala:88)
    at resource.ManagedResourceOperations$class.apply(ManagedResourceOperations.scala:26)
    at resource.AbstractManagedResource.apply(AbstractManagedResource.scala:50)
    at resource.ManagedResourceOperations$class.acquireAndGet(ManagedResourceOperations.scala:25)
    at resource.AbstractManagedResource.acquireAndGet(AbstractManagedResource.scala:50)
    at resource.ManagedResourceOperations$class.foreach(ManagedResourceOperations.scala:53)
    at resource.AbstractManagedResource.foreach(AbstractManagedResource.scala:50)
    at com.github.sbt.jacoco.data.InstrumentationUtils$$anonfun$instrumentClasses$4.apply(InstrumentationUtils.scala:61)
    at com.github.sbt.jacoco.data.InstrumentationUtils$$anonfun$instrumentClasses$4.apply(InstrumentationUtils.scala:60)
    at scala.collection.mutable.ResizableArray$class.foreach(ResizableArray.scala:59)
    at scala.collection.mutable.ArrayBuffer.foreach(ArrayBuffer.scala:47)
    at com.github.sbt.jacoco.data.InstrumentationUtils$.instrumentClasses(InstrumentationUtils.scala:60)
    at com.github.sbt.jacoco.BaseJacocoPlugin$$anonfun$taskValues$8.apply(BaseJacocoPlugin.scala:98)
    at com.github.sbt.jacoco.BaseJacocoPlugin$$anonfun$taskValues$8.apply(BaseJacocoPlugin.scala:98)
    at scala.Function1$$anonfun$compose$1.apply(Function1.scala:47)
    at sbt.$tilde$greater$$anonfun$$u2219$1.apply(TypeFunctions.scala:40)
    at sbt.std.Transform$$anon$4.work(System.scala:63)
    at sbt.Execute$$anonfun$submit$1$$anonfun$apply$1.apply(Execute.scala:228)
    at sbt.Execute$$anonfun$submit$1$$anonfun$apply$1.apply(Execute.scala:228)
    at sbt.ErrorHandling$.wideConvert(ErrorHandling.scala:17)
    at sbt.Execute.work(Execute.scala:237)
    at sbt.Execute$$anonfun$submit$1.apply(Execute.scala:228)
    at sbt.Execute$$anonfun$submit$1.apply(Execute.scala:228)
    at sbt.ConcurrentRestrictions$$anon$4$$anonfun$1.apply(ConcurrentRestrictions.scala:159)
    at sbt.CompletionService$$anon$2.call(CompletionService.scala:28)
    at java.util.concurrent.FutureTask.run(FutureTask.java:266)
    at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
    at java.util.concurrent.FutureTask.run(FutureTask.java:266)
    at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
    at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
    at java.lang.Thread.run(Thread.java:745)
[error] (test:fullClasspath) java.io.IOException: Error while instrumenting Routes$$anonfun$routes$1.class.
```
</details>

We were not the first to discover this issue - [solution was already proposed](https://github.com/sbt/sbt-jacoco/pull/102) and recently merge to version `3.3.0`. Unfortunately for us, this release (understandably) dropped support for sbt `0.13`. As we are in the middle of migration to Spring Boot, migrating to higher sbt and Scala versions proved to be too costly. As a solution, we have forked original repository, reverted to version `3.2.0` and backported fix for this to create custom `3.2.1` version.


## Applying fix

To apply mentioned fix in your project, you need to specify sequence of excluded classes from JaCoCo instrumentation in your `sbt.build` file. For our issue with Play Framework, we have used the following setting:

```scala
jacocoInstrumentationExcludes := Seq(
  "router.Routes*",
  "*routes$javascript*",
  "*.routes"
)
```


## Testing locally

If you want to test this library locally before publishing to artifactory, you can build this project using the following command:

```console
sbt "++2.10.6" "^^0.13.17" clean publishLocal
```

Subsequently in your main project, register this library in your `plugins.sbt` file:

```scala
addSbtPlugin("com.github.matusmak" % "sbt-jacoco" % "3.2.1")
```

If you haven't done already, make sure that your local repository is included in your list of resolvers:

```scala
resolvers ++= Seq(
  Resolver.mavenLocal
)
```

All that is left to do is to sync sbt changes in your project.