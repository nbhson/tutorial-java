public class Logger {
    // Here, the format variable of the Logger class is private, so there's no way for other classes to retrieve or set its value directly.
    private String format;


    // So, to make this variable available to the outside world, we defined two public methods: getFormat(), which returns the value of format, and setFormat(String), which sets its value.
    public String getFormat() {
        return this.format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
