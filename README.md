# Red Hat Fuse (Apache Camel) demo - Stock order processing

This project is aimed to demonstrate the capability of **Red Hat Fuse / Apache Camel**. The followings are being implemented.

1. Connect to a message queue using AMQP for order data collection
2. Convert message into a fixed length POJO
3. Filter messages based on data field
4. Enrich message content by calling another RESTful endpoint
5. Enrich message content by doing a database table lookup using SQL
6. Convert message back into JSON format
7. Then, send the processed message out to the destinated RESTful endpoint

# Before you use

Make sure you have your AMQP broker and MySQL database ready. You will need to change the credentials in the Java class (`CamelRouter.java`) and Spring Boot `application.properties` file.

# How to run

You may simply run the `Application.java`. Or, if you prefer to use VS Code for editing and run, first make sure you download the `Extension Pack for Apache Camel by Red Hat` from VS Code marketplace, import the project into VS Code, then right click the `Application.java` and click Run.