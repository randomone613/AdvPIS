package WastedWars.src.MiniGames.QFAS;
import java.util.*;
import java.util.Random;
import java.util.Map;

//le but de ce mini jeu est d'afficher soit des questions de culture g ou des équations à résoudre
public class QFASModel { //question for a shot

    private Map<String, String> questions = new HashMap<>();
    private Random random = new Random();
    public String selectedQuestion;
    public String selectedAnswer;

    public QFASModel() {
        questions.put("What is the largest ocean in the world?", "Pacific");
        questions.put("Which country has the largest population?", "China");
        questions.put("What is the capital of France?", "Paris");
        questions.put("Which planet is known as the Red Planet?", "Mars");
        questions.put("In which city is the Colosseum located?", "Rome");
        questions.put("How many continents are there on Earth?", "Seven");
        questions.put("What is the longest river in the world?", "The Nile");
        questions.put("What is the chemical element with the symbol 'O'?", "Oxygen");
        questions.put("Which country is nicknamed the 'Land of the Rising Sun'?", "Japan");
        select_question();
    }

    public void select_question() {
        Object[] quest_list = questions.keySet().toArray();
        int index = random.nextInt(quest_list.length);  // select a random question from the list
        selectedQuestion = (String) quest_list[index];
        selectedAnswer = questions.get(selectedQuestion);
    }
}
