package WastedWars.src.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class representing the state of the Wasted Wars game, including the players.
 */
public class WastedWarsModel {
    private List<Player> players;

    public WastedWarsModel() {
        players = new ArrayList<>();
        players.add(new Player("Player1"));
        players.add(new Player("Player2"));
    }

    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Adds a new player to the game with the specified name.
     * @param name The name of the new player to add.
     */
    public void addPlayer(String name) {
        players.add(new Player(name));
    }

    /**
     * Removes a player from the game at the specified index.
     * @param index The index of the player to remove.
     */
    public void removePlayer(int index) {
        players.remove(index);
        for (int i = index; i < players.size(); i++) {
            players.get(i).setUsername("Player" + (i + 1));
        }
    }
}

