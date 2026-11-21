// get an existing file
File file = new File("d://test//testFile1.txt");

// Write Content
FileWriter writer = new FileWriter(file);
writer.write("Test data");
writer.close();
