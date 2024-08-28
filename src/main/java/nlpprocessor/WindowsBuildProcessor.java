package nlpprocessor;

public class WindowsBuildProcessor implements BuildProcessor {
    @Override
    public String handleInitialStep() {
        return JsonUtils.getQuestion("WindowsBuildProcessor", "initial");
    }

    @Override
    public String handleServiceStep(String input) {
        String question = JsonUtils.getQuestion("WindowsBuildProcessor", "service");
        return String.format(question, input);
    }

    @Override
    public String handleFinalStep(String input) {
        generate();
        String message = JsonUtils.getQuestion("WindowsBuildProcessor", "complete");
        return String.format(message, input);
    }

    public void generate() {
        System.out.println("Generating Windows pattern...");
        // Add your Windows-specific logic here
    }
}
