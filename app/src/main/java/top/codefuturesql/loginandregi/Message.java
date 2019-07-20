package top.codefuturesql.loginandregi;

public class Message {
    public final String message;
    public final String sendtime;
    public final double longitude;
    public final double latitude;

    public Message(String message, String sendtime, double longitude, double latitude) {
        this.message = message;
        this.sendtime = sendtime;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
