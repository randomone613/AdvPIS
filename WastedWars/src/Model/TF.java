package WastedWars.src.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

//name : Twisted Fingers

public class TF {
    private List<String> requiredKeys; // List of keys that must be pressed
    private List<String> pressedKeys; // Keys pressed by the user
    private final Random random = new Random();

    // Define key sets for left and right hands
    private final String[] leftHandKeys = {"A", "Z", "E", "R", "T", "Q", "S", "D", "F", "G"};
    private final String[] rightHandKeys = {"Y", "U", "I", "O", "P", "H", "J", "K", "L", "M", "X", "C", "V", "B", "N"};

    public TF(int keyCount) {
        requiredKeys = new ArrayList<>();
        pressedKeys = new ArrayList<>();
        selectKeys(keyCount);
    }

    // Method to randomly select keys from both hands
    private void selectKeys(int keyCount) {
        // Determine how many keys to select for each hand (2 or 3 keys)
        int leftHandCount = 2 + random.nextInt(2); // Randomly select 2 or 3 for the left hand
        int rightHandCount = 2 + random.nextInt(2); // Randomly select 2 or 3 for the right hand

        // Shuffle and select keys for the left hand
        List<String> leftHandList = new ArrayList<>(List.of(leftHandKeys));
        Collections.shuffle(leftHandList);
        requiredKeys.addAll(leftHandList.subList(0, leftHandCount));

        // Shuffle and select keys for the right hand
        List<String> rightHandList = new ArrayList<>(List.of(rightHandKeys));
        Collections.shuffle(rightHandList);
        requiredKeys.addAll(rightHandList.subList(0, rightHandCount));
    }

    public List<String> getRequiredKeys() {
        return requiredKeys;
    }

    public void addPressedKey(String key) {
        if (!pressedKeys.contains(key)) {
            pressedKeys.add(key);
        }
    }

    public boolean isRightHand(String key) {
        for (String rightKey : rightHandKeys) {
            if (rightKey.equals(key)) {
                return true;
            }
        }
        return false;
    }

    public boolean isGameWon() {
        return pressedKeys.containsAll(requiredKeys);
    }
}
