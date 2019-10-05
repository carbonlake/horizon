## REST service for Apache CarbonData

Two services are supported
1. Simple query service 
- simply but fully parallel query: scan, projection, filter, limit
- support local or distributed deployment option.

2. Standard SQL service
- SparkSQL + CarbonData Extension is supported.
- depends on Spark cluster deployment

### Simple query service
When started with `Horizon.java`, REST API for simple query is supported: create/drop/load/select table
Use following Client API to interact with the service.
```java
  /**
   * Create a Table
   * @param create create table request
   * @throws CarbonException if network or disk IO error occurs
   */
  void createTable(CreateTableRequest create) throws IOException;

  /**
   * Drop a Table, and remove all data in it
   * @param drop drop table request
   * @throws IOException if network or disk IO error occurs
   */
  void dropTable(DropTableRequest drop) throws IOException;

  /**
   * Load data into a Table
   * @param load load table request
   * @throws IOException if network or disk IO error occurs
   */
  void loadData(LoadRequest load) throws IOException;

  /**
   * Scan a Table and return matched rows
   * @param select select request, supported operations: scan, projection, filter, limit
   * @return matched rows
   * @throws IOException if network or disk IO error occurs
   */
  List<CarbonRow> select(SelectRequest select) throws IOException;
```

### Standard SQL query service
When started with `SqlHorizon.java`, REST API for full SQL (based on SparkSQL + CarbonData extension) is support.
Use following client API to interact with the service.
```java
  /**
   * Execute a SQL statement
   * @param sqlString SQL statement
   * @return matched rows
   * @throws IOException if network or disk IO error occurs
   */
  List<CarbonRow> sql(String sqlString) throws IOException;
```
