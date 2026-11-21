# Chạy tất cả tests
./mvnw test

# Chạy test cụ thể
./mvnw test -Dtest=TaskServiceTest

# Chạy nhiều tests
./mvnw test -Dtest="TaskServiceTest,TaskRepositoryTest"

# Bỏ qua tests khi build
./mvnw clean package -DskipTests

# Generate coverage report (cần JaCoCo plugin)
./mvnw clean test jacoco:report
# Report tại: target/site/jacoco/index.html
