package nlpprocessor;

public class LinuxBuildProcessor implements BuildProcessor {
    @Override
    public String handleInitialStep() {
        return JsonUtils.getQuestion("LinuxBuildProcessor", "initial");
    }

    @Override
    public String handleServiceStep(String input) {
        String question = JsonUtils.getQuestion("LinuxBuildProcessor", "service");
        return String.format(question, input);
    }

    @Override
    public String handleFinalStep(String input) {
        generate();
        String message = JsonUtils.getQuestion("LinuxBuildProcessor", "complete");
        return String.format(message, input);
    }

    public void generate() {
        System.out.println("Generating Linux pattern...");
        // Add your Linux-specific logic here
    }
}

