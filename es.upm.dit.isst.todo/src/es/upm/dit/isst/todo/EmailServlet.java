package es.upm.dit.isst.todo;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.users.User;
import es.upm.dit.isst.todo.dao.TodoDAO;
import es.upm.dit.isst.todo.dao.TodoDAOImpl;
public class EmailServlet extends HttpServlet {
private static final long serialVersionUID = 1L;
public void doPost(HttpServletRequest req, HttpServletResponse resp)
throws IOException {
Properties props = new Properties();
Session email = Session.getDefaultInstance(props, null);
try {
MimeMessage message = new MimeMessage(email,
req.getInputStream());
String summary = message.getSubject();
String description1 = getText(message).substring(15,getText(message).length()-8);
String description = "";
String dateString = "";
String URLString = "";
String descriptionString = "";

String[] lineas = description1.split("&gt;");
String[] linea0 = lineas[0].split(": &lt;");
String[] linea1 = lineas[1].split(": &lt;");
String[] linea2 = lineas[2].split(": &lt;");


if (lineas.length < 3){
	description = description1;
} else{

	switch (linea0[0]){
	case "Date": dateString = linea0[1];
	case "<div>Date": dateString = linea0[1];
	case "</div><div>Date": dateString = linea0[1];
	case "Description": descriptionString = linea0[1];
	case "<div>Description": descriptionString = linea0[1];
	case "</div><div>Description": descriptionString = linea0[1];
	case "URL": URLString = linea0[1];
	case "<div>URL": URLString = linea0[1];
	case "</div><div>URL": URLString = linea0[1];
	
	}
	
	switch (linea1[0]){
	case "Date": dateString = linea1[1];
	case "<div>Date": dateString = linea1[1];
	case "</div><div>Date": dateString = linea1[1];
	case "Description": descriptionString = linea1[1];
	case "<div>Description": descriptionString = linea1[1];
	case "</div><div>Description": descriptionString = linea1[1];
	case "URL": URLString = linea1[1];
	case "<div>URL": URLString = linea1[1];
	case "</div><div>URL": URLString = linea1[1];

	}
	
	switch (linea2[0]){
	case "Date": dateString = linea2[1];
	case "<div>Date": dateString = linea2[1];
	case "</div><div>Date": dateString = linea2[1];
	case "Description": descriptionString = linea2[1];
	case "<div>Description": descriptionString = linea2[1];
	case "</div><div>Description": descriptionString = linea2[1];
	case "URL": URLString = linea2[1];
	case "<div>URL": URLString = linea2[1];
	case "</div><div>URL": URLString = linea2[1];

	}
	
	
	if (URLString.contains("a href")){
		
		String[] URLrecortado = URLString.split(">");
		URLString = URLrecortado[1].replace("</a", "");
		
		
		
	}
	
	description = descriptionString;
}

Address[] addresses = message.getFrom();
InternetAddress emailAddress = new
InternetAddress(addresses[0].toString());
User user = new User(emailAddress.getAddress(), "gmail.com");
TodoDAO dao = TodoDAOImpl.getInstance();
dao.add(user.getNickname(), summary, description, URLString, dateString);
} catch (Exception e) {
e.printStackTrace();
}
}
private boolean textIsHtml = false;
/**
* Return the primary text content of the message.
*/
private String getText(Part p) throws
MessagingException, IOException {
if (p.isMimeType("text/*")) {
String s = (String)p.getContent();
textIsHtml = p.isMimeType("text/html");
return s;
}
if (p.isMimeType("multipart/alternative")) {
// prefer html text over plain text
Multipart mp = (Multipart)p.getContent();
String text = null;
for (int i = 0; i < mp.getCount(); i++) {
Part bp = mp.getBodyPart(i);
if (bp.isMimeType("text/plain")) {
if (text == null)
text = getText(bp);
continue;
} else if (bp.isMimeType("text/html")) {
String s = getText(bp);
if (s != null)
return s;
} else {
return getText(bp);
}
}
return text;
} else if (p.isMimeType("multipart/*")) {
Multipart mp = (Multipart)p.getContent();
for (int i = 0; i < mp.getCount(); i++) {
String s = getText(mp.getBodyPart(i));
if (s != null)
return s;
}
}
return null;
}
}