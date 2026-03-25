package api.utilities;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T getTestDataByPath(String filePath, String jsonPath, Class<T> clazz) {
        try {
            JsonNode currentNode = objectMapper.readTree(new File(filePath));

            String[] keys = jsonPath.split("\\.");

            for (String key : keys) {
                currentNode = currentNode.get(key);
                if (currentNode == null) {
                    throw new RuntimeException("No test data found for path: " + jsonPath);
                }
            }

            return objectMapper.treeToValue(currentNode, clazz);

        } catch (IOException e) {
            throw new RuntimeException("Failed to read json file: " + filePath, e);
        }
    }
}