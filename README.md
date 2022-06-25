# Red Hat Fuse (Apache Camel) demo - Stock order processing

This project is aimed to demonstrate the capability of Red Hat Fuse / Apache Camel. The followings are being implemented.

1. Connect to a message queue using AMQP for order data collection
2. Convert message into a fixed length POJO
3. Filter messages based on data field
4. Enrich message content by calling another RESTful endpoint
5. Enrich message content by doing a database table lookup using SQL
6. Convert message back into JSON format
7. Then, send the processed message out to the destinated RESTful endpoint
