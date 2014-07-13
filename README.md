Java Object Oriented Querying (JOOQ) Test demo application.

Demo application showing how to setup a database, an IntelliJ project using Maven.
Just three plug-ins and one dependency are needed: the JOOQ Maven Plug-in to generate our meta-model from an existing database, the Build Helper Plug-in for Maven to add the generated-sources-directory as a source directory to the Maven build environment, the Exec Maven Plug-in to execute the Java programs in a separate process and the dependency for the JDBC connector – in this case: MySQL.

Some default operations are shown, all is described at my blog: http://blog.bertvanlangen.com/software-development/java-object-oriented-querying-jooq/

