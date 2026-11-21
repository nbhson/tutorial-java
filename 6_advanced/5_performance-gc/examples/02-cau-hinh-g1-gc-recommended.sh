# Cấu hình JVM cho production
java -Xms4g -Xmx4g \
     -XX:+UseG1GC \
     -XX:MaxGCPauseMillis=200 \
     -XX:+ParallelRefProcEnabled \
     -XX:G1HeapRegionSize=4m \
     -XX:InitiatingHeapOccupancyPercent=45 \
     -jar myapp.jar
