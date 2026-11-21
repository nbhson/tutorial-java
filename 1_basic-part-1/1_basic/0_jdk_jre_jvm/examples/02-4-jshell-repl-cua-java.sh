$ jshell
|  Welcome to JShell -- Version 25
|  For an introduction type: /help intro

jshell> 2 + 3
$1 ==> 5

jshell> "Hello".toUpperCase()
$2 ==> "HELLO"

jshell> int[] so = {5, 3, 8}
so ==> int[3] { 5, 3, 8 }

jshell> java.util.Arrays.sort(so)

jshell> so
so ==> int[3] { 3, 5, 8 }

jshell> int tong(int a, int b) {
   ...>     return a + b;
   ...> }
|  created method tong(int,int)

jshell> tong(10, 20)
$3 ==> 30

jshell> /exit
