package JSONtoXML;

import Json.Json;
import Json.JsonClub;
import Json.JsonPlayer;
import Json.JsonPosition;
import XML.*;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class JSONtoXML
{
    private static Json players = new Json();

    public static Json parseJSON(String path) throws IOException
    {
        File f = new File(path);
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(f);

        parser.nextToken();
        parser.nextToken();

        if (parser.nextToken() == JsonToken.START_ARRAY) //'['
        {
            //loop until token equal to "]"
            while (parser.nextToken() != JsonToken.END_ARRAY)
            {
                if (parser.getCurrentName() == null) // если не в ключе
                    continue;
                String cur = parser.getCurrentName();

                if(cur.equals("name"))
                {
                    players.addPlayer(" ",0,0," ");
                    parser.nextToken();
                    players.returnLastPlayer().setName(parser.getText());
                }
                else if(cur.equals("count_of_games"))
                {
                    parser.nextToken();
                    players.returnLastPlayer().setCount_of_games(Integer.parseInt((parser.getText())));
                }
                else if(cur.equals("average_Points"))
                {
                    parser.nextToken();
                    players.returnLastPlayer().setAverage_Points(Float.parseFloat(parser.getText()));
                }
                else if(cur.equals("conference"))
                {
                    parser.nextToken();
                    players.returnLastPlayer().setConference(parser.getText());
                }
                else if (cur.equals("clubs"))
                {
                    parser.nextToken();
                    parser.nextToken();

                    //loop until end of devStudios
                    while (parser.nextToken() != JsonToken.END_ARRAY)
                    {

                        //checker if we're looking at "{" / "}"
                        if (parser.getCurrentName() == null)
                            continue;
                        if (parser.getCurrentName().equals("name"))
                        {
                            parser.nextToken();
                            players.returnLastPlayer().addClub(parser.getText());
                        }
                    }
                }
                else if (cur.equals("positions"))
                {
                    parser.nextToken();
                    parser.nextToken();

                    //loop until end of devStudios
                    while (parser.nextToken() != JsonToken.END_ARRAY)
                    {

                        //checker if we're looking at "{" / "}"
                        if (parser.getCurrentName() == null)
                            continue;
                        if (parser.getCurrentName().equals("name"))
                        {
                            parser.nextToken();
                            players.returnLastPlayer().addPosition(parser.getText());
                        }
                    }
                }
            }
        }
        else return null;

        return players;
    }

    private static boolean checkConf(String name, ArrayList<XMLConference> Conf)
    {
        for(XMLConference v:Conf) // иду по всем записанным на данный момент конференциям и проверяю есть ли только что взятая
        {
            if(v.getName().equals(name)) return true; // если есть
        }
        return false;
    }

    private static XMLConference findConf(String name, ArrayList<XMLConference> Conf)
    {
        for (int i=Conf.size()-1;i>=0;i--)
        {
            if (Conf.get(i).getName().equals(name))
                return Conf.get(i);
        }
        return null;
    }

    private static boolean checkClub(String name, ArrayList<XMLClub> club)
    {
        for (XMLClub c : club)
        {
            if(c.getName().equals(name))
                return true;
        }
        return false;
    }
    private static XMLClub findClub(String name, ArrayList<XMLClub> club)
    {
        for (int i=club.size()-1;i>=0;i--){
            if (club.get(i).getName().equals(name))
                return club.get(i);
        }
        return null;
    }

    private static boolean checkPosition(String name, ArrayList<XMLPosition> position)
    {
        for (XMLPosition r : position)
        {
            if(r.getName().equals(name))
                return true;
        }
        return false;
    }
    private static XMLPosition findPosition(String name, ArrayList<XMLPosition> position)
    {
        for (int i=position.size()-1;i>=0;i--)
        {

            if (position.get(i).getName().equals(name))
                return position.get(i);
        }
        return null;
    }

    public static XMLNBA ConvertToXML()
    {
        XMLNBA NBA = new XMLNBA();

        NBA.addConference(players.getPlayers().get(0).getConference());

        for(int i=0;i<players.returnLength();i++)
        {
            JsonPlayer jsonPlayer =players.getPlayers().get(i);

            if (!checkConf(jsonPlayer.getConference(),NBA.getConferences()))
                NBA.addConference(jsonPlayer.getConference());

            XMLConference XMLconference = findConf(jsonPlayer.getConference(),NBA.getConferences()); // получаем конкретную конференцию конкретного игрока



            for (int j=0;j<jsonPlayer.getClubs().size();j++)
            {
                JsonClub jsonClub = jsonPlayer.getClubs().get(j);

                XMLClub XMLclub;
                //check if we need to create new
                if (!checkClub(jsonClub.getName(), XMLconference.getClubs()))
                {
                    //XMLvillage.setName(jsonNinja.getName());

                    //create new dev
                    XMLconference.addClub(jsonClub.getName());
                }
                XMLclub = findClub(jsonClub.getName(), XMLconference.getClubs()); // ???

                //add position
                for (int k=0;k<jsonPlayer.getPositions().size();k++)
                {
                    JsonPosition jsonPosition=jsonPlayer.getPositions().get(k);

                    XMLPosition xmlposition;
                    if(!checkPosition(jsonPosition.getName(),XMLclub.getPositions()))
                    {
                        //XMLclan.setName(jsonNinja.getName());

                        XMLclub.addPosition(jsonPosition.getName());
                    }
                    xmlposition=findPosition(jsonPosition.getName(),XMLclub.getPositions());//????

                    xmlposition.addPlayer(jsonPlayer.getName(), jsonPlayer.getCount_of_games(),
                            jsonPlayer.getAverage_Points());
                }
            }
        }

        return NBA;
    }

    private static void writeXml(OutputStream out, XMLNBA xmlnba) throws XMLStreamException
    {
        XMLOutputFactory output = XMLOutputFactory.newInstance();

        XMLStreamWriter writer = output.createXMLStreamWriter(out);

        writer.writeStartDocument("utf-8", "1.0");

        //header
        writer.writeStartElement("NBA");


        writer.writeStartElement("Conferences");


        // insides

        for (int i = 0; i < xmlnba.returnLength(); i++) {
            writer.writeStartElement("Conference");
            writer.writeAttribute("name", xmlnba.getConferences().get(i).getName());

            writer.writeStartElement("Clubs");


            for (int j = 0; j < xmlnba.getConferences().get(i).getClubs().size(); j++)
            {
                XMLClub club = xmlnba.getConferences().get(i).getClubs().get(j);

                writer.writeStartElement("Club");
                writer.writeAttribute("name", club.getName());

                writer.writeStartElement("Positions");

                for (int k = 0; k < club.getPositions().size(); k++) {
                    XMLPosition position = club.getPositions().get(k);

                    writer.writeStartElement("Position");
                    writer.writeAttribute("Position", position.getName());

                    writer.writeStartElement("Players");

                    for (int l = 0; l < position.getPlayers().size(); l++) {
                        XMLPlayer player = position.getPlayers().get(l);

                        writer.writeStartElement("Player");
                        writer.writeAttribute("name", player.getName());
                        writer.writeAttribute("Count_of_games", ((Integer)player.getCount_of_games()).toString());
                        writer.writeAttribute("Average_Points", ((Float)player.getAverage_Points()).toString());
                        writer.writeEndElement(); //end ninja

                    }
                    writer.writeEndElement();//end ninjas
                    writer.writeEndElement();//end rank

                }
                writer.writeEndElement();//end ranks
                writer.writeEndElement();//end clan
            }
            writer.writeEndElement();//end clans
            writer.writeEndElement();//end village

        }

        writer.writeEndElement();//end villages
        writer.writeEndElement();//end shinobies



        writer.flush();

        writer.close();
    }

    public static void createXML(XMLNBA xmlnba, String path)
    {
        try(FileOutputStream out = new FileOutputStream(path))
        {
            writeXml(out, xmlnba);
        } catch (IOException | XMLStreamException e)
        {
            e.printStackTrace();
        }
    }
}
