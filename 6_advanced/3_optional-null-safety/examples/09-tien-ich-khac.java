// Objects.requireNonNull
public void process(String input) {
    Objects.requireNonNull(input, "Input không được null");
    // Throws NullPointerException với message
}

// StringUtils (Apache Commons)
StringUtils.isBlank(str);   // null, "", "  " → true
StringUtils.defaultString(null, "default");

// Java 25: Scoped Values (alternative cho ThreadLocal)
// Không còn null issues trong multithreaded contexts
