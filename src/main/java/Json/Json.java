package Json;
import java.util.ArrayList;
public class Json
{
    private ArrayList<JsonPlayer> players;

    public Json() {
        this.players = new ArrayList<>();
    }

    public ArrayList<JsonPlayer> getPlayers() {
        return players;
    }

    public void addPlayer(String name, int count_of_games, float average_Points,  String Conference)
    {
        players.add(new JsonPlayer(name, count_of_games, average_Points, Conference));
    }

    public int returnLength()
    {
        return players.size();
    }

    public JsonPlayer returnLastPlayer()
    {
        if (players.size() > 0)
            return players.get(players.size() - 1);
        else
            return null;
    }
}
