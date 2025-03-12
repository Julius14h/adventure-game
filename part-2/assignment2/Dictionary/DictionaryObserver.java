package Dictionary;

import java.util.ArrayList;
import java.util.List;

// Observer interface
interface Observer {
    void update(String word);
}

// Subject class
class SearchBar {
    private List<Observer> observers = new ArrayList<>();

    // Method to add observers
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    // Method to remove observers (if needed)
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    // Method to notify observers of a new word
    public void notifyObservers(String word) {
        for (Observer observer : observers) {
            observer.update(word);
        }
    }

    // Simulated method for user entering a word in the search bar
    public void enterWord(String word) {
        System.out.println("User entered word: " + word);
        notifyObservers(word);
    }
}
