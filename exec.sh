alias greent='javac -d bin lib/*.java && java -cp bin Main $1'
javac -d bin lib/*.java
java -cp bin Main $1