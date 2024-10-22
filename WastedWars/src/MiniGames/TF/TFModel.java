package WastedWars.src.MiniGames.TF;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TFModel {
    private List<String> requiredKeys;
    private List<String> pressedKeys;
    private final Random random = new Random();
    private final String[] leftHandKeys = {"A", "Z", "E", "R", "T", "Q", "S", "D", "F", "G"};
    private final String[] rightHandKeys = {"Y", "U", "I", "O", "P", "H", "J", "K", "L", "M", "X", "C", "V", "B", "N"};

    public TFModel(int keyCount) {
        requiredKeys = new ArrayList<>();
        pressedKeys = new ArrayList<>();
        selectKeys(keyCount);
    }

    public void selectKeys(int keyCount) {
        requiredKeys = new ArrayList<>();

        int leftHandCount = 2 + random.nextInt(2);
        int rightHandCount = 2 + random.nextInt(2);

        List<String> leftHandList = new ArrayList<>(List.of(leftHandKeys));
        Collections.shuffle(leftHandList);
        requiredKeys.addAll(leftHandList.subList(0, leftHandCount));

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

    public List<String> getPressedKeys() {
        return pressedKeys;
    }

    public void removePressedKey(String key) {
        pressedKeys.remove(key);
    }

    public boolean areAllKeysPressed() {
        return pressedKeys.containsAll(requiredKeys);
    }

    public void resetPressedKeys() {
        pressedKeys.clear();
    }
}
