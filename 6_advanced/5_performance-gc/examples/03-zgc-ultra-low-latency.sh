# Java 17+ với ZGC
java -Xms8g -Xmx8g \
     -XX:+UseZGC \
     -XX:+ZGenerational \
     -jar myapp.jar
