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
        addQuestions();
        select_question();
    }

    public void addQuestions(){
        questions.put("What is the largest ocean in the world?", "Pacific");
        questions.put("Which country has the largest population?", "China");
        questions.put("What is the capital of France?", "Paris");
        questions.put("Which planet is known as the Red Planet?", "Mars");
        questions.put("In which city is the Colosseum located?", "Roma");
        questions.put("How many continents are there on Earth?", "6");
        questions.put("What is the longest river in the world?", "Nile");
        questions.put("What is the chemical element with the symbol 'O'?", "Oxygen");
        questions.put("Which country is nicknamed the 'Land of the Rising Sun'?", "Japan");
        questions.put("What is the capital city of Australia?", "Canberra");
        questions.put("Which gas do plants absorb from the atmosphere?", "CO2");
        questions.put("What is the name of the largest desert in the world?", "Sahara");
        questions.put("Who is known as the father of modern physics?", "Einstein");
        questions.put("Which element has the chemical symbol 'H'?", "Hydrogen");
        questions.put("What is the capital of Japan?", "Tokyo");
        questions.put("In what year did the Titanic sink?", "1912");
        questions.put("What is the hardest natural substance on Earth?", "Diamond");
        questions.put("Which planet is closest to the Sun?", "Mercury");
        questions.put("What is the boiling point of water in degrees Celsius?", "100");
        questions.put("Who wrote the play 'Romeo and Juliet'?", "Shakespeare");
        questions.put("Which mammal is known to have the most powerful bite?", "Hippopotamus");
        questions.put("What is the currency of the United States?", "Dollar");
        questions.put("What is the capital of Canada?", "Ottawa");
        questions.put("In which year did World War II end?", "1945");
        questions.put("What is the largest land animal?", "Elephant");
        questions.put("Which country is famous for the Great Wall?", "China");
        questions.put("What is the smallest country in the world?", "Vatican");
        questions.put("What is the main ingredient in guacamole?", "Avocado");
        questions.put("Which planet is known for its rings?", "Saturn");
        questions.put("Who discovered penicillin?", "Fleming");
        questions.put("What is the largest bone in the human body?", "Femur");
        questions.put("What is the primary language spoken in Brazil?", "Portuguese");
        questions.put("What type of animal is a Komodo dragon?", "Lizard");
        questions.put("What is the most spoken language in the world?", "Mandarin");
    }

    public void select_question() {
        Object[] quest_list = questions.keySet().toArray();
        int index = random.nextInt(quest_list.length + 1);  // select a random question from the list

        if (index >= quest_list.length) {  // Use >= to avoid index out of bounds
            int x = random.nextInt(8) + 2;  // Generates a random integer between 2 and 9
            int y = random.nextInt(90) + 10;  // Generates a random integer between 10 and 99
            selectedQuestion = x + " * " + y + " ?";  // Create the question string
            selectedAnswer = String.valueOf(x * y);  // Calculate the answer and convert to String
        } else {
            selectedQuestion = (String) quest_list[index];  // Select a question from the predefined list
            selectedAnswer = questions.get(selectedQuestion);  // Get the corresponding answer
        }
    }
}
