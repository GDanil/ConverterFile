package XML;
import java.util.ArrayList;
public class XMLClub
{
    private String name;
    private ArrayList<XMLPosition> positions;

    public XMLClub(String name)
    {
        this.name = name;
        this.positions = new ArrayList<>();
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public ArrayList<XMLPosition> getPositions()
    {
        return positions;
    }

    public void addPosition(String name)
    {
        positions.add(new XMLPosition(name));
    }

    public int returnLength()
    {
        return positions.size();
    }
}
