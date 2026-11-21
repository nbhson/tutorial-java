try(FileReader fr = new FileReader("E://file.txt")) {
   char [] a = new char[50];
   fr.read(a);   // reads the contentto the array
   for(char c : a)
   System.out.print(c);   // prints the characters one by one
} catch (IOException e) {
   e.printStackTrace();
}
