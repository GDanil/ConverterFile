package XML;
import java.util.ArrayList;
public class XMLConference
{
    private String name;
    private ArrayList<XMLClub> clubs;

    public XMLConference(String name)
    {
        this.name = name;
        this.clubs = new ArrayList<XMLClub>();
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void addClub(String name)
    {
        clubs.add(new XMLClub(name));
    }

    public ArrayList<XMLClub> getClubs()
    {
        return clubs;
    }

    public int returnLength()
    {
        return clubs.size();
    }
}
