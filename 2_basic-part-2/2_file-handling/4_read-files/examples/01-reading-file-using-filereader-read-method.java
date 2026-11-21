// get an existing file
File file = new File("d://test//testFile1.txt");

// read content
FileReader reader = new FileReader(file);

int c;
while ((c = reader.read()) != -1) {
   char ch = (char) c;
   System.out.print(ch);
}
