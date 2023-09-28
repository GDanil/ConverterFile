package Json;
import java.util.ArrayList;
public class JsonPlayer {
    private String name;
    private int count_of_games;
    private float average_Points;
    private String conference;

    private ArrayList<JsonClub> clubs;
    private ArrayList<JsonPosition> positions;

    public JsonPlayer(String name, int count_of_games, float average_Points, String Conference)
    {
        this.name = name;
        this.count_of_games = count_of_games;
        this.average_Points = average_Points;
        this.conference=Conference;
        this.clubs=new ArrayList<JsonClub>();
        this.positions=new ArrayList<JsonPosition>();
    }
    public String getName()
    {
        return name;
    }
    public int getCount_of_games()
    {
        return count_of_games;
    }
    public float getAverage_Points()
    {
        return average_Points;
    }

    public String getConference()
    {
        return conference;
    }

    public ArrayList<JsonPosition> getPositions() {
        return positions;
    }

    public ArrayList<JsonClub> getClubs() { return clubs; }


    public void setName(String name) {
        this.name = name;
    }
    public void setCount_of_games(int count_of_games) {
        this.count_of_games = count_of_games;
    }
    public void setAverage_Points(float average_Points)
    {
        this.average_Points = average_Points;
    }
    public void setConference(String Conference)
    {
        this.conference = Conference;
    }
    public void addClub(String name){
        clubs.add(new JsonClub(name));
    }
    public void addPosition(String name){
        positions.add(new JsonPosition(name));
    }
}
