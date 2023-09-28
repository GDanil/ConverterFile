package XML;
import java.util.ArrayList;
import java.util.Optional;

public class XMLPosition
{
    private String name;
    private ArrayList<XMLPlayer> players;

    public XMLPosition(String name)
    {
        this.name = name;
        this.players = new ArrayList<>();
    }
    public void setName(String name){
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public ArrayList<XMLPlayer> getPlayers()
    {
        return players;
    }

    public void playersWith1000Game() ///////////
    {
        players.stream().filter(players ->players.getCount_of_games() > 1000).forEach(System.out::println);
    }
    public void first3palyers() ///////////
    {
        players.stream().limit(3).forEach(System.out::println);
    }

    public void CountGames()
    {
        players.stream().map(XMLPlayer::getCount_of_games).forEach(System.out::println);
    }


    public void addPlayer(String name, int count_of_games, float average_Points)
    {
        players.add(new XMLPlayer(name, count_of_games, average_Points));
    }

    public int returnLength()
    {
        return players.size();
    }
}
