package com.example.examples.task;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;
import com.flipkart.zjsonpatch.JsonPatch;

import java.io.BufferedReader;
import java.io.FileReader;

public class App {

    private static String readJsonFromFile(String filePath) throws Exception {
        StringBuilder content = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
            }
        }
        return content.toString();
    }

    public static String checkDifference(String newJson, String oldJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode newNode = mapper.readTree(newJson);
            JsonNode oldNode = mapper.readTree(oldJson);

            JsonNode patch = JsonDiff.asJson(oldNode, newNode);

            JsonNode patchedNode = JsonPatch.apply(patch, oldNode);

            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(patchedNode);
        } catch (Exception e) {
            e.printStackTrace();
            return "Something went wrong with JSON";
        }
    }

    public static void main(String[] args) {
        try {
            String oldJson = readJsonFromFile("old.json");
            String newJson = readJsonFromFile("new.json");

            String diffResult = checkDifference(newJson, oldJson);
            System.out.println(diffResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
