// ✗ cu: xau dai, nhieu \n va \"
String json = "{\n" +
              "  \"name\": \"An\",\n" +
              "  \"age\": 25\n" +
              "}";

// ✅ moi: text block — giong y chang ghi trong source code
String json2 = """
        {
          "name": "An",
          "age": 25
        }
        """;

// ky tu dac biet:
// \""" → dau ngoac kep literral
// \n  → xuong dong
// \\  → dau \
// trim: stripTrailingIndent() neu can
