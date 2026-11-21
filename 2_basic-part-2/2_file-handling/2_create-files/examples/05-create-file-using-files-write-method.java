String data = "Test data";
Files.write(Paths.get("d://test/testFile3.txt"), data.getBytes());

List<String> lines = Arrays.asList("1st line", "2nd line");
Files.write(Paths.get("file6.txt"), lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
