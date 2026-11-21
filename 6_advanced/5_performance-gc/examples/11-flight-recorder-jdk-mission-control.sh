# Start with JFR enabled
java -XX:StartFlightRecording=dumponexit=true,filename=recording.jfr \
     -jar myapp.jar

# Analyze
jfr print --events CPULoad,GC ./recording.jfr
