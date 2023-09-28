import Convertor.Converter;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        Converter f = new Converter();
        f.convertToJson("Basketball.xml", "Filik.json");
    }
}
