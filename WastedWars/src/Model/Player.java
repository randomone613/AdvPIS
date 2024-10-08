package WastedWars.src.Model;

public class Player {
    private String username;
    private int sip;

    public Player(String username) {
        this.username = username;
        sip = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getSip(){
        return sip;
    }

    public void addSip(int x){
        sip += x;
    }
}
