package XML;
public class XMLPlayer
{
    private String name;
    private int count_of_games;
    private float average_Points;

    public XMLPlayer(String name, int count_of_games, float average_Points)
    {
        this.name = name;
        this.count_of_games = count_of_games;
        this.average_Points = average_Points;
    }

    public String getName()
    {
        return name;
    }
    public int getCount_of_games()
    {
        return count_of_games;
    }


    public int countPlus()
    {
        return count_of_games+5;
    }
    public float getAverage_Points()
    {
        return average_Points;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    public void setCount_of_games(int count_of_games)
    {
        this.count_of_games = count_of_games;
    }
    public void setAverage_Points(float average_Points)
    {
        this.average_Points = average_Points;
    }

}
