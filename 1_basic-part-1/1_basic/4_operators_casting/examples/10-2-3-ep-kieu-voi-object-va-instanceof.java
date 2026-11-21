Object obj = "Hello Java";

// ✗ cu: ep kieu truc tiep — neu sai kieu nem ClassCastException
String s = (String) obj;

// ✅ moi: kiem tra truoc (hoac dung pattern matching)
if (obj instanceof String str) {
    System.out.println(str.length()); // str da la String, khong can ep
}
