import Convertor.Converter;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        Converter f = new Converter();
        if(args.length!=0)
        {
            if(args[0].contains(".json"))
                f.convertToXML(args[0],args[1]);
            else if(args[0].contains(".xml"))
                f.convertToJson(args[0], args[1]);
            else throw new Exception("Wrong input!");
        }
        else
        {
            f.convertToXML("F:\\ConverterFile\\Filik.json", "F:\\ConverterFile\\Basketball.xml");
            f.convertToJson("F:\\ConverterFile\\Basketball.xml", "F:\\ConverterFile\\Filik.json");
        }
    }
}
