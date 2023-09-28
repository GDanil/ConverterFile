package XML;
import java.util.ArrayList;
public class XMLNBA
{
    private ArrayList<XMLConference> conferences;

    public XMLNBA(){
        conferences = new ArrayList<XMLConference>();
    }

    public void addConference(String name){
        conferences.add(new XMLConference(name));
    }

    public ArrayList<XMLConference> getConferences(){
        return conferences;
    }

    public int returnLength(){
        return conferences.size();
    }
}
