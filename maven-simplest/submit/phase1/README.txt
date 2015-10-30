We tidy our code into 3 folders:
frontend;
etl;
backend.

In Folder frontend, the HelloWorldEmbedded.java is for testing Q1. The Profiler.java is used to generate profile data for the report. Note that we use vertx 3 framework for the frontend.

In Folder etl, note that we use java for Mapper code and python for Reducer code. The TextProcessor.java is used to calculate sentiment score and for censoring data.

In Folder backend, Hbase and MySQL connection and query interfaces are presented in different files. For simplicity, we wrote a DataHandler.java for calling these interfaces.

