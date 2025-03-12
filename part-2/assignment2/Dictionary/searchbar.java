package Dictionary;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;


// Concrete observer displaying the definition in the console
class ConsoleDefinitionDisplay implements Observer {
    @Override
    public void update(String word) {
        // Simulated method to fetch and display definition
        String definition = fetchDefinitionFromDictionary(word);
        System.out.println("Definition of '" + word + "': " + definition);
    }

    // Simulated method to fetch definition from a dictionary
    private String fetchDefinitionFromDictionary(String word) {
        // Here, you'd have your logic to fetch the definition of the word from the dictionary
        // This could involve accessing a database, making an API call, etc.
        // For demonstration purposes, returning a simple definition
        return "This is the definition of '" + word + "'.";
    }
}

// Another concrete observer displaying the definition in a GUI
class GUIDefinitionDisplay implements Observer {
    @Override
    public void update(String word) {
        // Simulated method to update a GUI element with the definition
        // In a real application, this method would update the GUI component with the definition fetched
        String definition = fetchDefinitionFromDictionary(word);

        // Example: updateGUIComponent(definition);
        System.out.println("GUI Display: Definition of '" + word + "': " + definition);


    }

    // Simulated method to fetch definition from a dictionary
    public String fetchDefinitionFromDictionary(String word) {
        return searchWordInFile("Dictionary/Oxford English Dictionary.txt", word);

    }

    public static String searchWordInFile(String filePath, String searchWord) {
        StringBuilder foundLines = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;


            while ((line = br.readLine()) != null) {
                String[] sentence = StripDef(line);
                String input = searchWord.toLowerCase().replaceAll("[^a-zA-Z]", "");

                if (input.equals(sentence[0].toLowerCase().replaceAll("[^a-zA-Z]", ""))) {
                    return sentence[1];
                }

            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        return "Definition not found";
    }


    public static String[] StripDef(String line) {
        String[] sentence = line.split(" ", 2);


        return sentence;

    }
}
