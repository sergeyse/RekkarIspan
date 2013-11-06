package mailer;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.swing.JOptionPane;

import csv.DTOPontunVasar;

import microsoft.exchange.webservices.data.EmailMessage;
import microsoft.exchange.webservices.data.ExchangeCredentials;
import microsoft.exchange.webservices.data.ExchangeService;
import microsoft.exchange.webservices.data.ExchangeVersion;
import microsoft.exchange.webservices.data.FindFoldersResults;
import microsoft.exchange.webservices.data.Folder;
import microsoft.exchange.webservices.data.FolderView;
import microsoft.exchange.webservices.data.Importance;
import microsoft.exchange.webservices.data.Item;
import microsoft.exchange.webservices.data.MessageBody;
import microsoft.exchange.webservices.data.PostItem;
import microsoft.exchange.webservices.data.ServiceLocalException;
import microsoft.exchange.webservices.data.Task;
import microsoft.exchange.webservices.data.WebCredentials;
import microsoft.exchange.webservices.data.WellKnownFolderName;

public class MailSender {

	Set<DTOPontunVasar> pairsToMailOut;

public 	MailSender(Set<DTOPontunVasar> pairsToMailOut) {

		this.pairsToMailOut = pairsToMailOut;
	}

	/**
	 * @param args
	 */

	public void sendMailConfirmed() {

		ExchangeService service = new ExchangeService(
				ExchangeVersion.Exchange2010_SP2);

		ExchangeCredentials credentials = new WebCredentials("gsafgreidsla", "");
		service.setCredentials(credentials);

		Date date = new Date();

		Format formatter = new SimpleDateFormat("dd.MM.yyyy' at ' HH:mm:ss");
		String s = formatter.format(date);
		String pairsMessage = " ";
		for (DTOPontunVasar pair : pairsToMailOut) {

			String pontun = pair.getPontunarN();
			String rekka = pair.getVasaN();
			pairsMessage = pontun + " --- " + rekka + " ; " + "\n"
					+ pairsMessage;
		}

		try {
			service.setUrl(new URI("https://postur.ispan.is/ews/Exchange.asmx"));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * FolderView fview = new FolderView (Integer.MAX_VALUE);
		 * FindFoldersResults findResults = null; try { findResults =
		 * service.findFolders(WellKnownFolderName.PublicFoldersRoot, fview); }
		 * catch (Exception e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 * 
		 * for(Folder folderItem : findResults.getFolders()) { //Do something
		 * with the item as shown System.out.println("id==========" +
		 * folderItem.getId()); //System.out.println("sub==========" + ((Item)
		 * item).getSubject()); try { System.out.println("id==========" +
		 * folderItem.getChildFolderCount()+ folderItem.getDisplayName()); }
		 * catch (ServiceLocalException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } }
		 */

		/*
		 * PostItem post = new PostItem(service); post.setBody(new
		 * MessageBody("Test From JAVA: Body Content"));
		 * post.setImportance(Importance.High);
		 * post.setSubject("Test From JAVA: Subject"); String id
		 * =((Folder)findResults1.getFolders().get(0)).getId().toString();
		 * System.out.println("Id : " +id); post.save(new FolderId(id));
		 */

		EmailMessage msg = null;
		try {
			msg = new EmailMessage(service);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			msg.setImportance(Importance.High);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			msg.setSubject("Buinn að setja á rekka!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			msg.setBody(MessageBody
					.getMessageBodyFromText("Bъin aр skэrsla н dag    -  "
							+ pairsMessage + "  MK \r\n         Lagerpъkinn."));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			msg.getToRecipients().add("sergiy@glerskalinn.is");
		} catch (ServiceLocalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			msg.send();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Send ! ");
		// JOptionPane.showMessageDialog(null, "Tilkynning hefur veriр sent.");
		/*Task task = null;
		try {
			task = new Task(service);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			task.setSubject("Task to test in JAVA");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			task.setBody(MessageBody
					.getMessageBodyFromText("Test body from JAVA"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			task.setStartDate(new Date(2015 - 1900, 5 - 1, 20, 17, 00));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			task.save();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
	}

}
