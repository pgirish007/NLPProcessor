package nlpprocessor;

public interface BuildProcessor {
    String handleInitialStep();
    String handleServiceStep(String input);
    String handleFinalStep(String input);
}
