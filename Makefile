interpreter/target/interpreter-1.0-SNAPSHOT-jar-with-dependencies.jar: interpreter/src/main/java/com/laracey/lox/interpreter codegen/src/main/java/com/laracey/lox/codegen
	mvn package && mvn -pl interpreter assembly:single

clean:
	mvn clean