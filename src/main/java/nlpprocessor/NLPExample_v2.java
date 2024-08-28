package nlpprocessor;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class NLPExample_v2 {
    private static final Map<String, String> patternProcessorMap = new HashMap<>();

    static {
        // Map patterns to corresponding processor class names
        patternProcessorMap.put("build a linux pattern", "LinuxBuildProcessor");
        patternProcessorMap.put("build a windows pattern", "WindowsBuildProcessor");
    }

    public static void main(String[] args) {
        try {
            // Load the sentence detector model
            InputStream sentenceModelInput = new FileInputStream("en-sent.bin");
            SentenceModel sentenceModel = new SentenceModel(sentenceModelInput);
            SentenceDetectorME sentenceDetector = new SentenceDetectorME(sentenceModel);

            // Sample text
            String paragraph = "I want to build a linux pattern. Another statement: I need to build a windows pattern.";

            // Detect sentences
            String[] sentences = sentenceDetector.sentDetect(paragraph);
            System.out.println("Sentences:");
            for (String sentence : sentences) {
                System.out.println(sentence);
            }

            // Load the tokenizer model
            InputStream tokenizerModelInput = new FileInputStream("en-token.bin");
            TokenizerModel tokenizerModel = new TokenizerModel(tokenizerModelInput);
            TokenizerME tokenizer = new TokenizerME(tokenizerModel);

            // Tokenize each sentence and check for specific patterns
            System.out.println("\nTokens:");
            for (String sentence : sentences) {
                String[] tokens = tokenizer.tokenize(sentence);
                for (String token : tokens) {
                    System.out.println(token);
                }
                // Check for patterns and invoke respective processors
                for (Map.Entry<String, String> entry : patternProcessorMap.entrySet()) {
                    if (sentence.toLowerCase().contains(entry.getKey())) {
                        invokeProcessor(entry.getValue());
                        break;
                    }
                }
            }

            // Close input streams
            sentenceModelInput.close();
            tokenizerModelInput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void invokeProcessor(String className) {
        try {
            // Dynamically load the class
            Class<?> clazz = Class.forName(className);
            BuildProcessor instance = (BuildProcessor) clazz.getDeclaredConstructor().newInstance();
            // Invoke the generate method
            //instance.generate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
