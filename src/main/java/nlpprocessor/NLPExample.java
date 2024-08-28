package nlpprocessor;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class NLPExample {
    public static void main(String[] args) {
        ConversationManager conversationManager = new ConversationManager();
        Scanner scanner = new Scanner(System.in);

        // Load OpenNLP models
        try (InputStream sentenceModelInput = new FileInputStream("en-sent.bin");
             InputStream tokenizerModelInput = new FileInputStream("en-token.bin")) {

            SentenceModel sentenceModel = new SentenceModel(sentenceModelInput);
            SentenceDetectorME sentenceDetector = new SentenceDetectorME(sentenceModel);

            TokenizerModel tokenizerModel = new TokenizerModel(tokenizerModelInput);
            TokenizerME tokenizer = new TokenizerME(tokenizerModel);

            System.out.println("How can I help you?");
            while (true) {
                String input = scanner.nextLine();
                String[] sentences = sentenceDetector.sentDetect(input);
                for (String sentence : sentences) {
                    String response = conversationManager.processInput(sentence);
                    System.out.println(response);
                    if (response.equals("Thank you for your responses!")) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        scanner.close();
    }
}
