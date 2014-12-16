package enviromine.client.gui;
import java.io.StringReader;

import org.apache.commons.lang3.StringEscapeUtils;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import enviromine.client.gui.UpdatePage.WordPressPost;


public class MySAXApp extends DefaultHandler
{

    public MySAXApp ()
    {
    	super();
    }

    public static void main (String page)	throws Exception
    {
    	XMLReader xr = XMLReaderFactory.createXMLReader();
    	MySAXApp handler = new MySAXApp();
    	xr.setContentHandler(handler);
    	xr.setErrorHandler(handler);

    				// Parse each file provided on the
    				// command line.

    	    //FileReader r = new FileReader(page);
    	    xr.parse(new InputSource(new StringReader(page)));
    	
    	
    	
    }
    boolean item = false;
    boolean bTitle = false;
    boolean bLink = false;
    boolean bCreator = false;
    boolean bPubDate = false;
    boolean bDescription = false;
    
    public void startElement (String uri, String name,  String qName, Attributes atts)
    {
    	if(qName.equalsIgnoreCase("item"))
    	{
    		item = true;
    	}
    	
    	if(qName.equalsIgnoreCase("title") && item)
    	{
    		System.out.print(qName +":");
    		bTitle = true;
    	}  
    	else if(qName.equalsIgnoreCase("link") && item)
    	{
    		System.out.print(qName +":");
    		bLink = true;
    	}
    	else if(qName.equalsIgnoreCase("dc:creator") && item)
    	{
    		System.out.print(qName +":");
    		bCreator = true;
    	}    	
    	else if(qName.equalsIgnoreCase("pubDate") && item)
    	{
    		System.out.print(qName +":");
    		bPubDate = true;
    	}
    	else if(qName.equalsIgnoreCase("description") && item)
    	{
    		System.out.print(qName +":");
    		bDescription = true;
    	}
    	/*
    	if ("".equals (uri))
    		System.out.println("Start element: " + qName);
    	else
    		System.out.println("Start element: {" + uri + "}" + name);
    		*/
    }

    int count = 0;
    public void endElement (String uri, String name, String qName)
    {
    	if(qName.equalsIgnoreCase("item") && count <= 10 && item)
    	{
    		System.out.println(item +":"+ count +":"+ title  +":"+ link  +":"+ creator  +":"+  pubDate  +":"+ description);
    		item = false;
    		
    		UpdatePage test = new UpdatePage();
    		//WordPressPost var = new WordPressPost(title, link, creator, pubDate, description);
    		
    		WordPressPost post = test.new WordPressPost(title, description, link, pubDate, creator);
    		
    		UpdatePage.Posts.add(count, post);
    		
    		title = ""; link = ""; creator = ""; pubDate = ""; description = "";
    		
    		count++;
    		
    		for(WordPressPost postit : UpdatePage.Posts)
    		{
    			
    			
    			System.out.println(postit.title);
    			
    		}
    	}
    	
    }
    
    
    private String title;
    private String link;
    private String creator;
    private String pubDate;
    private String description;
    
    public void characters (char ch[], int start, int length)
    {
    	
    	if(bTitle)
    	{
    		String vart = new String(ch, start, length);
    		System.out.println(vart);
    		title = vart;
    		bTitle = false;
    	}
    	else if(bLink)
    	{
    		String vart = new String(ch, start, length);
    		System.out.println(vart);
    		link = vart;
    		bLink = false;
    	}
    	else if (bCreator)
    	{
    		String vart = new String(ch, start, length);
    		System.out.println(vart);
    		creator = vart;
    		bCreator = false;
    	}
    	else if(bPubDate)
    	{
    		String vart = new String(ch, start, length);
    		System.out.println(vart);
    		pubDate = vart;
    		bPubDate = false;
    	}
    	else if(bDescription)
    	{
    		String vart = new String(ch, start, length);
    	     String nohtml = vart.toString().replaceAll("\\<.*?>","");
    	     System.out.println(StringEscapeUtils.unescapeHtml4(nohtml));
    	     description = nohtml;
    		//System.out.println(nohtml);
    		bDescription = false;
    	}
    	/*
	System.out.print("Characters:    \"");
	for (int i = start; i < start + length; i++) {
	    switch (ch[i]) {
	    case '\\':
		System.out.print("\\\\");
		break;
	    case '"':
		System.out.print("\\\"");
		break;
	    case '\n':
		System.out.print("\\n");
		break;
	    case '\r':
		System.out.print("\\r");
		break;
	    case '\t':
		System.out.print("\\t");
		break;
	    default:
		System.out.print(ch[i]);
		break;
	    }
	}
	System.out.print("\"\n");*/
    }
}