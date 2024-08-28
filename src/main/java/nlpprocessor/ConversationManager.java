package nlpprocessor;

import java.util.HashMap;
import java.util.Map;

public class ConversationManager {
    private ConversationState state;
    private Map<String, String> context;
    private BuildProcessor currentProcessor = null;

    private static final Map<String, String> patternProcessorMap = new HashMap<>();

    static {
        // Map patterns to corresponding processor class names
        patternProcessorMap.put("build a linux pattern", "LinuxBuildProcessor");
        patternProcessorMap.put("build linux", "LinuxBuildProcessor");
        patternProcessorMap.put("raise a linux pattern", "LinuxBuildProcessor");
        patternProcessorMap.put("raise linux", "LinuxBuildProcessor");
        patternProcessorMap.put("build a windows pattern", "WindowsBuildProcessor");
    }

    public ConversationManager() {
        state = ConversationState.INITIAL;
        context = new HashMap<>();
    }

    public String processInput(String input) {
        switch (state) {
            case INITIAL:
                return handleInitial(input);
            case SERVICE:
                return handleService(input);
            case COMPLETE:
                return handleComplete(input);
            default:
                return "Thank you for your responses!";
        }
    }

    private String handleInitial(String input) {
        // Check for patterns and invoke respective processors
    	try {
	        for (Map.Entry<String, String> entry : patternProcessorMap.entrySet()) {
	            if (input.toLowerCase().contains(entry.getKey())) {
	                Class<?> clazz = Class.forName("nlpprocessor." + entry.getValue());
	                currentProcessor = (BuildProcessor) clazz.getDeclaredConstructor().newInstance();
	                context.put("pattern", entry.getValue());
	                state = ConversationState.SERVICE;
	                break;
	            }
	        }
	        if (currentProcessor == null)
	        	throw new RuntimeException(String.format("No object definition found for '%s'", input));
	        return currentProcessor.handleInitialStep();
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	return "No data found";
       
    }

    private String handleService(String input) {
        context.put("service", input);
        state = ConversationState.COMPLETE;
        return currentProcessor.handleServiceStep(input);
    }

    private String handleComplete(String input) {
        context.put("version", input);
        state = ConversationState.COMPLETE;
        return currentProcessor.handleFinalStep(input);
    }
}
