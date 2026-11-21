// Traditional switch (verbose)
String result;
switch (day) {
    case MONDAY:
    case TUESDAY:
        result = "Weekday";
        break;
    case SATURDAY:
    case SUNDAY:
        result = "Weekend";
        break;
    default:
        result = "Unknown";
}

// Modern switch expression (Java 14+)
String result = switch (day) {
    case MONDAY, TUESDAY -> "Weekday";
    case SATURDAY, SUNDAY -> "Weekend";
    default -> "Other";
};
