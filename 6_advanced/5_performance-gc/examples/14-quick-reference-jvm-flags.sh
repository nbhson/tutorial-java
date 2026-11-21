# Memory
-Xms512m              # Initial heap size
-Xmx4g                # Maximum heap size
-Xss2m                # Stack size per thread
-XX:MetaspaceSize=256m # Metaspace size
-XX:MaxMetaspaceSize=512m

# GC
-XX:+UseG1GC          # Use G1 garbage collector
-XX:+UseZGC           # Use ZGC (Java 11+)
-XX:+UseSerialGC      # Use Serial GC
-XX:MaxGCPauseMillis=200  # Target max GC pause

# JIT
-XX:+TieredCompilation    # Faster startup, better long-run perf
-XX:CompileThreshold=1000 # Methods compiled after 1000 invocations

# Logging
-Xlog:gc*:file=gc.log    # GC logging
-XX:+PrintGCDetails       # Detailed GC logs (legacy)
