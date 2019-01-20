# Sample Rest Service
This is an example of a simple rest service that can take some user details, encrypt them and add them to a database.

### Getting Started
The application uses JAX-RS from JEE7 and Maven to build it.

#### Prerequisites
You will therefore need the following:

* [Java 8 JDK](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html)
* [Apache Maven](https://maven.apache.org/)

Test that Java is installed and available by typing java -version on a command line. Also test Maven using mvn -version.

Note that the version of Maven must be 3.3.9 or above due to issues with the JAR build.

Note if you are using Windows and have [chocolatey](https://chocolatey.org/) or are willing to install it you can install using:

```DOS
choco install jdk8
choco install maven
```

#### Building
Once you have the source code on your local machine bring up a command line and navigate to the root directory which contains the file pom.xml.

##### WAR File
To build a WAR file that can be installed on a [JEE7 Full Certified Application Server](https://en.wikipedia.org/wiki/Java_Platform,_Enterprise_Edition) enter:

```DOS
mvn clean install
```

This will build the war file and run unit test.

##### JAR File with OpenLiberty Server
To build a jar file enter the following:

```DOS
mvn clean install -P embed-liberty
```

This will build the war and package it with an open liberty server with the correct features. Tests will also be run.

#### Deployment
##### WAR File
If you have built the WAR you need to set up a server. The application was developed with WebSphere Liberty 18.0.0.4. You can install it in a number of ways, more details are at the [WASdev](https://developer.ibm.com/wasdev/getstarted/) site.

##### JAR File
The easiest way to run the server is using the jar. This should be named sample-rest-liberty.jar and will be located in the target directory once built.

To start the server:

java -jar sample-rest-liberty.jar

This will start the server, you should get a message 'The server rest-app-server is ready to run a smarter planet'.

Post 9080 is used for the server so will need to free and not used by other services.

### Accessing the Service

As this is a demo the service works over http.

The default api root is:

> http://localhost:9080/sample-rest/api

To add a user post json (header application/json) to http://localhost:9080/sample-rest/api/users

The service accepts four pieces of information:

* firstName - mandatory must be no more than 50 characters
* lastName - mandatory must be no more than 50 characters
* email - optional must be a valid e-mail (determined by regular expression) and must be no more than 30 characters
* age - optional must be between 0 and 150

Large or invalid json pay-loads (malformed or containing possible malicious items such as script tags) will be rejected and a plain text reason given.

Example data:

> {
> 	"firstName" : "Jim",
> 	"lastName" : "Smith",
> 	"email" : "jim.smith@test.com",
> 	"age" : 21
> }

If the data is accepted a location header is returned.

Example return header:

> Location: http://localhost:9080/sample-rest/api/users/1

Use the location with GET to retrieve the data and DELETE to delete it. No update is supported.

All users can be retrieved by using GET to:

> http://localhost:9080/sample-rest/api/users

The data is encrypted in the data store and can be viewed using:

> http://localhost:9080/sample-rest/api/users?encrypted=true

### Database

As this is just an example the data is held in a Sqlite database file named database. When running the jar it will extract the server to a temporary directory, the directory is shown in the log output. It should end in wlp. The database can be found in the server root e.g. somepath/wlp/usr/servers/rest-app-server/database.

To view the data any viewer for sqlite files, such as this [chrome extension](https://add0n.com/sqlite-manager.html) should be suitable. The database table is user. Note that you may have to reload the database to view changes.

The location of the database can be adjusted by setting a system property. As the server from the jar seems to uses it's own jvm it can't be directly set from the command line.

> e.g. -Dcom.github.awferg.restex.provider.JdbiSqlite.databaseLocation=c:/dev/sqlite/test.db

### Built With

* [Eclipse](https://www.eclipse.org/eclipseide/) - The IDE
* [Maven](https://maven.apache.org/) - Dependency Management
* [WebSphere Liberty](https://developer.ibm.com/wasdev) - Development Server
* [Open Liberty](https://openliberty.io/) - Used as bundled server
* [Logback](https://logback.qos.ch/) - Application logging
* [JDBI](http://jdbi.org/) - Database access
* [Sqlite-JDBC](https://github.com/xerial/sqlite-jdbc/) - [Sqlite:https://sqlite.org/] JDBC Driver
* [OWASP JSON Sanitizer](https://www.owasp.org/index.php/OWASP_JSON_Sanitizer) - for demo of input checking