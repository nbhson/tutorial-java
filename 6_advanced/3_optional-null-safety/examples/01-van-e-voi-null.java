// ❌ Code cũ dễ bị NPE
public String getCity(User user) {
    return user.getAddress().getCity().toUpperCase();
    // NPE nếu user = null
    // NPE nếu address = null
    // NPE nếu city = null
}

// ✅ Cách cũ: Kiểm tra null thủ công (verbose)
public String getCity(User user) {
    if (user == null) {
        return "UNKNOWN";
    }
    Address address = user.getAddress();
    if (address == null) {
        return "UNKNOWN";
    }
    String city = address.getCity();
    return city != null ? city.toUpperCase() : "UNKNOWN";
}
