package WastedWars.src.Model;

import java.util.ArrayList;
import java.util.List;

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

    public void addPlayer(String name) {
        players.add(new Player(name));
    }

    public void removePlayer(int index) {
        players.remove(index);
        // Update usernames
        for (int i = index; i < players.size(); i++) {
            players.get(i).setUsername("Player" + (i + 1));
        }
    }
}

