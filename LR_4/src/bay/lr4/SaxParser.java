package bay.lr4;

import cards.Cards;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class SaxParser extends DefaultHandler {
    Cards card = new Cards();
    ArrayList<Cards> cards = new ArrayList<>();
    String thisElement = "";

    public ArrayList<Cards> getResult(){ return cards; };

    @Override
    public void startDocument() throws SAXException {
        System.out.println("----> Start parse XML...");
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("----> Stop parse XML...");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        thisElement = qName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        thisElement = "";
        if(card.score!=0) {
            cards.add(card);
            card = new Cards();
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        if(thisElement.equals("name")){
            card.name=new String(ch,start,length);
        }
        else if(thisElement.equals("score")){
            card.score=new Integer(new String(ch, start, length));
        }
    }
}
