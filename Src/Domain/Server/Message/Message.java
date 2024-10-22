package Src.Domain.Server.Message;
import java.util.Date;

// testar a abordagem de mandar todos os dados juntos separados por "/"

public class Message {
    private CompressedObject code, name, description, requestTime;

    public Message() {}

    public Message(String name, String description) {
        this.setName(name);
        this.setDescription(description);
    }

    public Message(String name, String description, Date requestTime) {
        this.setName(name);
        this.setDescription(description);
        this.setRequestTime(requestTime);
    }

    public Message(int code, String name, String description, Date requestTime) {
        this.setCode(code);
        this.setName(name);
        this.setDescription(description);
        this.setRequestTime(requestTime);
    }

    public CompressedObject getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = CompressionManager.codifyParameter(String.valueOf(code));
    }

    public void setName(String name) {
        this.name = CompressionManager.codifyParameter(name);
    }

    public CompressedObject getName() {
        return this.name;
    }

    public CompressedObject getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = CompressionManager.codifyParameter(description);
    }

    public CompressedObject getRequestTime() {
        return this.requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = CompressionManager.codifyParameter(requestTime.toString());
    }
}
