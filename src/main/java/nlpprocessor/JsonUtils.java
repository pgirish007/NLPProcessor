package nlpprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonUtils {
    private static Map<String, JsonNode> questionsMap = new HashMap<>();

    static {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(new File("questions.json"));
            rootNode.fields().forEachRemaining(entry -> questionsMap.put(entry.getKey(), entry.getValue()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getQuestion(String processorName, String step) {
        JsonNode processorNode = questionsMap.get(processorName);
        if (processorNode != null) {
            return processorNode.get(step).asText();
        }
        return null;
    }
}
