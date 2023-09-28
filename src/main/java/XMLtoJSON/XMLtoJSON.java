package XMLtoJSON;

import Json.Json;
import Json.JsonPlayer;
import XML.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class XMLtoJSON extends DefaultHandler
{
    private static XMLNBA NBA = new XMLNBA();

    public static XMLNBA ParserXML(String path_to_file) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        XMLHandler handler = new XMLHandler();
        File f = new File(path_to_file);
        parser.parse(f, handler);

        return NBA;
    }

    private static class XMLHandler extends DefaultHandler
    {
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equals("Conference"))
            {
                String name = attributes.getValue("name");
                NBA.addConference(name);
            } else if (qName.equals("Club"))
            {
                String name = attributes.getValue("name");
                NBA.getConferences().get(NBA.returnLength() - 1).addClub(name);
            } else if (qName.equals("Position"))
            {
                String position = attributes.getValue("Position");
                NBA.getConferences().get(NBA.returnLength() - 1).getClubs()
                        .get(NBA.getConferences().get(NBA.returnLength() - 1).returnLength() - 1).addPosition(position);
            } else if (qName.equals("Player"))
            {
                String name = attributes.getValue("name");
                int count_of_games = Integer.parseInt(attributes.getValue("Count_of_games"));
                float average_Points = Float.parseFloat(attributes.getValue("Average_Points"));
                NBA.getConferences().get(NBA.returnLength() - 1).getClubs()
                        .get(NBA.getConferences().get(NBA.returnLength() - 1).returnLength() - 1).getPositions()
                        .get(NBA.getConferences().get(NBA.returnLength() - 1).getClubs()
                                .get(NBA.getConferences().get(NBA.returnLength() - 1).returnLength() - 1).returnLength() - 1)
                        .addPlayer(name, count_of_games, average_Points);
            }
        }
    }

    private static JsonPlayer getCurrentPlayer(String name, ArrayList<JsonPlayer> arrayList)
    {
        JsonPlayer player = null;
        for (JsonPlayer player1 : arrayList)
            {
            if (player1.getName().equals(name)) player = player1;
        }
        return player;
    }

    public static Json ConvetToJson()
    {
        Json jPlayers = new Json();

        for (int i = 0; i < NBA.returnLength(); i++)
        {
            XMLConference conference = NBA.getConferences().get(i);
            for (int j = 0; j < conference.returnLength(); j++)
            {
                XMLClub club = conference.getClubs().get(j);
                for (int k = 0; k < club.returnLength(); k++)
                {
                    XMLPosition position = club.getPositions().get(k);
                    for (int m = 0; m < position.returnLength(); m++)
                    {
                        XMLPlayer player = position.getPlayers().get(m);
                        JsonPlayer checker = getCurrentPlayer(player.getName(), jPlayers.getPlayers());
                        if (checker == null)
                        {
                            jPlayers.addPlayer(player.getName(), player.getCount_of_games(), player.getAverage_Points(), conference.getName());
                            JsonPlayer jsonPlayer = jPlayers.getPlayers().get(jPlayers.returnLength() - 1);

                            jsonPlayer.addClub(club.getName());
                            jsonPlayer.addPosition(position.getName());
                        } else {
                            checker.addClub(club.getName());
                            checker.addPosition(position.getName());
                        }
                    }
                }
            }

        }
        return jPlayers;
    }


    public static void createFile(Json json, String path) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(path), json);
    }

    public static XMLNBA getNBA()
    {
        return NBA;
    }
}