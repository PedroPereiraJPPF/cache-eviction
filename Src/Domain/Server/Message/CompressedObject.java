package Src.Domain.Server.Message;
import java.util.Map;

public class CompressedObject {
    private String value;
    private Map<Character, Integer> frequencyTable;

    public CompressedObject(String value, Map<Character, Integer> frequencyTable) {
        this.value = value;
        this.frequencyTable = frequencyTable;
    }

    public Map<Character, Integer> getFrequencyTable() {
        return this.frequencyTable;
    }

    public String getValue() {
        return this.value;
    }
}
