# JConsole - GUI monitoring
jconsole

# VisualVM - Profiler (cần JDK)
jvisualvm

# JShell - Interactive shell
jshell

# Print GC details
java -Xlog:gc*:file=gc.log:time:filecount=5,filesize=10m myapp.jar

# Analyze GC logs
jstat -gcutil <pid> 1000  # GC statistics every 1s
