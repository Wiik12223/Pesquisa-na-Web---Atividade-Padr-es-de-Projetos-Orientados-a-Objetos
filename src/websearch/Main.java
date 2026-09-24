import java.io.File;

/**
 * Launch the web-search example
 */
public class Main {
    public static void main(String[] args) {
        // Run the program from the repository root.
        File inputTextFile = new File("src/Hamlet.txt");

        // Build object graph
        WebSearchModel model = new WebSearchModel(inputTextFile);
        Snooper snoop = new Snooper(model);

        // Execute
        model.pretendToSearch();
    }
}
