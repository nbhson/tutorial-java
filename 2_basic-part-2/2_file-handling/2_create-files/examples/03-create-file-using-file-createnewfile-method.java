File file = new File("d://test//testFile1.txt");

//Create the file
if (file.createNewFile()) { 
   System.out.println("File is created!");
} else {
   System.out.println("File already exists.");
}
