# Tạo task mới (POST)
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Học Spring Boot","description":"Học REST API","priority":1}'

# Lấy tất cả tasks (GET)
curl http://localhost:8080/api/tasks

# Lấy task theo ID (GET)
curl http://localhost:8080/api/tasks/1

# Cập nhật task (PUT)
curl -X PUT http://localhost:8080/api/tasks/1 \
  -H "Content-Type: application/json" \
  -d '{"title":"Học Spring Boot Advanced","description":"Đã học REST API","priority":2}'

# Xóa task (DELETE)
curl -X DELETE http://localhost:8080/api/tasks/1
