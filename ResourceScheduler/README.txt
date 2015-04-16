Gabriel Popovici
######################
 Pre-requisites:
 Maven and JDK/JRE 1.7

 mvn --version
Apache Maven 3.2.5 (12a6b3acb947671f09b81f49094c53f426d8cea1; 2014-12-14T19:29:23+02:00)
Maven home: /usr/local/Cellar/maven/3.2.5/libexec
Java version: 1.7.0_67, vendor: Oracle Corporation
Java home: /Library/Java/JavaVirtualMachines/jdk1.7.0_67.jdk/Contents/Home/jre
Default locale: en_US, platform encoding: UTF-8
OS name: "mac os x", version: "10.9.5", arch: "x86_64", family: "mac"
######################
Build instructions:
 mvn clean install

Building for eclipse :
 mvn clean install eclipse:eclipse

######################
Exec main application
mvn exec:java

-----------------------

[INFO] --- exec-maven-plugin:1.4.0:java (default-cli) @ ResourceScheduler ---
########## BEGIN Resource Scheduler #############
Apr 16, 2015 5:37:35 PM com.jpmorgan.scheduler.QueueConsumer run
INFO: QueueConsumer: Begin Job.
Apr 16, 2015 5:37:35 PM com.jpmorgan.scheduler.QueueProducer run
INFO: QueueProducer: Begin Job.
Apr 16, 2015 5:37:35 PM com.jpmorgan.scheduler.QueueProducer run
INFO: QueueProducer: entering sleep
Apr 16, 2015 5:37:35 PM com.jpmorgan.scheduler.QueueConsumer run
INFO: QueueConsumer: entering sleep
Apr 16, 2015 5:37:36 PM com.jpmorgan.scheduler.QueueProducer run
INFO: QueueProducer: Wake up
Apr 16, 2015 5:37:36 PM com.jpmorgan.scheduler.QueueConsumer run
INFO: QueueConsumer: Wake up
Apr 16, 2015 5:37:36 PM com.jpmorgan.scheduler.QueueProducer run
INFO: QueueProducer: Waiting for messages ...
Apr 16, 2015 5:37:36 PM com.jpmorgan.scheduler.QueueConsumer run
INFO: QueueConsumer: Waiting for messages ...
Apr 16, 2015 5:37:36 PM com.jpmorgan.scheduler.QueueProducer run
INFO: QueueProducer: >> Message sequence: message1 under Group: G2
Apr 16, 2015 5:37:36 PM com.jpmorgan.scheduler.QueueConsumer run
INFO: QueueConsumer: << About to send Message sequence: message1 under Group: G2
Apr 16, 2015 5:37:36 PM com.jpmorgan.scheduler.QueueProducer run
INFO: QueueProducer: >> Message sequence: message3 under Group: G2
Apr 16, 2015 5:37:36 PM com.jpmorgan.manager.ResourceManager sendMessage
INFO: About to borrow Resource from Pool
Apr 16, 2015 5:37:36 PM com.jpmorgan.scheduler.QueueProducer run
INFO: QueueProducer: >> Message sequence: message2 under Group: G1
Apr 16, 2015 5:37:36 PM com.jpmorgan.manager.ResourceManager sendMessage
INFO: About to execute process under resource
Apr 16, 2015 5:37:36 PM com.jpmorgan.scheduler.QueueProducer run
INFO: QueueProducer: >> Message sequence: message4 under Group: G3
Apr 16, 2015 5:37:36 PM com.jpmorgan.scheduler.QueueProducer run
INFO: QueueProducer: End Job.
Apr 16, 2015 5:37:38 PM com.jpmorgan.manager.ResourceManager sendMessage
INFO: About to return Resource back to pool
Apr 16, 2015 5:37:38 PM com.jpmorgan.scheduler.QueueConsumer run
INFO: QueueConsumer: << Message Sent sequence: message1 under Group: G2
Apr 16, 2015 5:37:38 PM com.jpmorgan.scheduler.QueueConsumer run
INFO: QueueConsumer: << About to send Message sequence: message3 under Group: G2
Apr 16, 2015 5:37:38 PM com.jpmorgan.manager.ResourceManager sendMessage
INFO: About to borrow Resource from Pool
Apr 16, 2015 5:37:38 PM com.jpmorgan.manager.ResourceManager sendMessage
INFO: About to execute process under resource
Apr 16, 2015 5:37:40 PM com.jpmorgan.manager.ResourceManager sendMessage
INFO: About to return Resource back to pool
Apr 16, 2015 5:37:40 PM com.jpmorgan.scheduler.QueueConsumer run
INFO: QueueConsumer: << Message Sent sequence: message3 under Group: G2
Apr 16, 2015 5:37:40 PM com.jpmorgan.scheduler.QueueConsumer run
INFO: QueueConsumer: << About to send Message sequence: message2 under Group: G1
Apr 16, 2015 5:37:40 PM com.jpmorgan.manager.ResourceManager sendMessage
INFO: About to borrow Resource from Pool
Apr 16, 2015 5:37:40 PM com.jpmorgan.manager.ResourceManager sendMessage
INFO: About to execute process under resource
Apr 16, 2015 5:37:42 PM com.jpmorgan.manager.ResourceManager sendMessage
INFO: About to return Resource back to pool
Apr 16, 2015 5:37:42 PM com.jpmorgan.scheduler.QueueConsumer run
INFO: QueueConsumer: << Message Sent sequence: message2 under Group: G1
Apr 16, 2015 5:37:42 PM com.jpmorgan.scheduler.QueueConsumer run
INFO: QueueConsumer: << About to send Message sequence: message4 under Group: G3
Apr 16, 2015 5:37:42 PM com.jpmorgan.manager.ResourceManager sendMessage
INFO: About to borrow Resource from Pool
Apr 16, 2015 5:37:42 PM com.jpmorgan.manager.ResourceManager sendMessage
INFO: About to execute process under resource
Apr 16, 2015 5:37:44 PM com.jpmorgan.manager.ResourceManager sendMessage
INFO: About to return Resource back to pool
Apr 16, 2015 5:37:44 PM com.jpmorgan.scheduler.QueueConsumer run
INFO: QueueConsumer: << Message Sent sequence: message4 under Group: G3
Apr 16, 2015 5:37:44 PM com.jpmorgan.scheduler.QueueConsumer run
INFO: QueueConsumer: End Job.
-> Message: message1 processed: true
-> Message: message2 processed: true
-> Message: message3 processed: true
-> Message: message4 processed: true
Apr 16, 2015 5:37:44 PM com.jpmorgan.manager.ResourcePool awaitTermination
INFO: About to check if Resource is available for disposal
Apr 16, 2015 5:37:44 PM com.jpmorgan.manager.ResourcePool awaitTermination
INFO: Resource is available for disposal
########## END Resource Scheduler #############
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 9.791 s
[INFO] Finished at: 2015-04-16T17:37:44+03:00
[INFO] Final Memory: 7M/156M