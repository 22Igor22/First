package bay.lr4;

import admin.Admin;
import bank.Bank;
import cards.Cards;
import cards.types.BronzeCard;
import cards.types.GoldCard;
import cards.types.SylverCard;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.xml.DOMConfigurator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    static {
        new DOMConfigurator().doConfigure("D:\\Универ\\3\\СТВвInternet\\Лабы\\LR_4\\log\\log4j.xml", LogManager.getLoggerRepository());
    }

    private static final Logger LOG = Logger.getLogger(Main.class);


    public static boolean checkXMLforXSD(String pathXml, String pathXsd)
            throws Exception {

        try {
            File xml = new File(pathXml);
            File xsd = new File(pathXsd);

            if (!xml.exists()) {
                System.out.println("Не найден XML " + pathXml);
            }

            if (!xsd.exists()) {
                System.out.println("Не найден XSD " + pathXsd);
            }

            if (!xml.exists() || !xsd.exists()) {
                return false;
            }

            SchemaFactory factory = SchemaFactory
                    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(pathXsd));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(pathXml));
            return true;
        } catch (SAXException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static Integer totalCards = 0;
    public static Integer totalBanks = 0;

    public static void main(String[] args) throws Exception {
        LOG.info("Starting program");
        try {
            System.out.println();
            Bank VTB = new Bank("VTB");
            GoldCard card1 = new GoldCard("Nastya", 700);
            SylverCard card2 = new SylverCard("Igor", 0);
            BronzeCard card3 = new BronzeCard("Nikita", 200);
            VTB.Add(card1);
            VTB.Add(card2);
            VTB.Add(card3);
            VTB.Put("Nikita", 800);
            VTB.Take("Nastya", 700);
            VTB.toString();

            //----------------------------------------------------------------------------------------------
            System.out.println("\t\t* Валидация XML по схеме:");
            String pathXml = new String("D:\\Универ\\3\\СТВвInternet\\Лабы\\LR_4\\files\\example.xml");
            String pathXsd = new String("D:\\Универ\\3\\СТВвInternet\\Лабы\\LR_4\\files\\example.xsd");
            System.out.println("XML соответствует XSD: " + (Main.checkXMLforXSD(pathXml, pathXsd)));
            System.out.println("---------------------------------------------");
            //----------------------------------------------------------------------------------------------

            System.out.println("\t* Парс объектов из XML -> DOM парсер: ");
            Bank BelarusBank = new Bank("BelarusBank");
            File fXmlFile = new File("D:\\Универ\\3\\СТВвInternet\\Лабы\\LR_4\\files\\example.xml");
            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            //optional, but recommended
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" +
                    doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("Card");
            totalCards = nList.getLength();
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Cards card = new BronzeCard("a", 0);
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" +
                        nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    card.name = eElement.getElementsByTagName("name").item(0).getTextContent();
                    System.out.println("name : " +
                            eElement.getElementsByTagName("name").item(0).getTextContent());
                    card.score = Integer.parseInt(eElement.getElementsByTagName("score").item(0).getTextContent());
                    System.out.println("score : " +
                            eElement.getElementsByTagName("score").item(0).getTextContent());
                }
                BelarusBank.Add(card);
            }
            System.out.println("Cards total count: " + totalCards);
            BelarusBank.toString();
            totalCards = 0;

//----------------------------------------------------------------------------------------------
            Bank bank = new Bank("Bank");
            System.out.println("\t* Парс объектов из XML -> SAX парсер:");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            SaxParser saxp = new SaxParser();
            parser.parse(new File(pathXml), saxp);

            Cards cardsXML;
            for (Cards c:
                 saxp.getResult()) {
                cardsXML = c;
                bank.Add(c);
            }
            bank.toString();

/*          System.out.println("\t\t* Сериализация в XML");
            FileOutputStream out = new FileOutputStream("files/Serialize.xml");
            XMLEncoder xmlEncoder = new XMLEncoder(out);
            xmlEncoder.writeObject(bank.cardsList);
            xmlEncoder.close();

            System.out.println("\t\t* Десериализация из XML");
            FileInputStream in = new FileInputStream("files/Serialize.xml");
            XMLDecoder xmlDecoder = new XMLDecoder(in);
            Cards assortXML2 = (Cards) xmlDecoder.readObject();
            xmlDecoder.close();
            System.out.println(assortXML2);
            System.out.println("---------------------------------------------");
            */

            System.out.println("\t\t* Сериализация в JSON");
            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(bank);
            System.out.println(json);
            FileOutputStream jsonOut = new FileOutputStream("D:\\Универ\\3\\СТВвInternet\\Лабы\\LR_4\\files\\Serialize.json");
            byte[] outText = json.getBytes(StandardCharsets.UTF_8);
            jsonOut.write(outText, 0, outText.length);

            System.out.println("\t\t* Десериализация из JSON");
            Scanner scanner = new Scanner(new File("D:\\Универ\\3\\СТВвInternet\\Лабы\\LR_4\\files\\Serialize.json"));
            String result = "";
            while (scanner.hasNext())
                result += scanner.nextLine();
            scanner.close();
            Bank Json = new Bank("JSON");
            Json = gson.fromJson(result, Bank.class);
            System.out.println(Json);

//----------------------------------------------------------------------------------------------
            System.out.println("---000---");
            Admin admin = new Admin();
            admin.sortByBalance(BelarusBank);
            System.out.println("---000---");
//----------------------------------------------------------------------------------------------
            System.out.println("\t\t* Stream API");

            System.out.println("\t* Карты с балансом в 1000");
            Stream<Cards> resultStream1 = VTB.getCards().stream().filter(w -> w.score == 1000);
            for (var elem : resultStream1.collect(Collectors.toList())) {
                System.out.println(elem.name + " " + elem.score);
            }

            Stream<Cards> resultStream2 = bank.getCards().stream();
            System.out.println("\t* Всего карт в банке: " + resultStream2.count());
        } catch (Exception e) {
            LOG.fatal("Fatal error! " + e.getMessage());
        }
    }
}
