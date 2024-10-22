package Src.Domain.Server.Message;

import Src.Domain.Server.Message.RuffmanTree.Node;
import Src.Domain.Server.Message.RuffmanTree.RuffmanTree;
import java.util.Map;
import java.util.HashMap;

public class CompressionManager {
    public static CompressedObject codifyParameter(String value) {
        Map<Character, Integer> frequencyTable = createFrequencyTable(value);
        RuffmanTree ruffmanTree = new RuffmanTree(frequencyTable);

        CompressedObject compressedValue = new CompressedObject(codifyParameter(value, ruffmanTree.getcodesTable()), frequencyTable);

        return compressedValue;
    }

    public static String codifyParameter(String value, Map<Character, String> codesTable) {
        String encodedValue = "";

        for (char c : value.toCharArray()) {
            encodedValue += codesTable.get(c);
        }

        return encodedValue;
    }

    public static String decodeParameter(CompressedObject compressedObject) {
        RuffmanTree ruffmanTree = new RuffmanTree(compressedObject.getFrequencyTable());
        Node current = ruffmanTree.getNode();
        String message = "";

        for (char c : compressedObject.getValue().toCharArray()) {
            if (c == '0') {
                current = current.left;
            } else {
                current = current.right;
            }

            if (current.left == null && current.right == null) {
                message += current.getCharacter();
                current = ruffmanTree.getNode();
            }
        }

        return message;
    }

    public static Map<Character, Integer> createFrequencyTable(String value) {
        Map<Character, Integer> frequencyTable = new HashMap<>();

        for (char index : value.toCharArray()) {
            if (frequencyTable.get(index) == null) {
                frequencyTable.put(index, 1);
            } else {
                frequencyTable.put(index, frequencyTable.get(index) + 1);
            }
        }   

        return frequencyTable;
    }
}