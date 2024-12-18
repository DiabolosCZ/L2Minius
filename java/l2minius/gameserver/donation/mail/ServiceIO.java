package l2minius.gameserver.donation.mail;

import java.util.Properties;

import javax.mail.Session;
import javax.mail.Store;

import l2minius.gameserver.Config;

public class ServiceIO
{
	private final Session _session;
	private final Store _store;

	public ServiceIO() throws Exception
	{
		final Properties properties = new Properties();
		properties.put("mail.smtp.host", "wes1-smtp.wedos.net");
		properties.put("mail.smtp.socketFactory.port", 465);
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", 465);
		/*
		 * properties.setProperty("mail.store.protocol", "imaps");
		 * properties.put("mail.imap.ssl.enable", "true");
		 */
		_session = Session.getDefaultInstance(properties, null);
		_store = _session.getStore("imaps");
		_store.connect("wes1-imap.wedos.net", Config.MAIL_USER, Config.MAIL_PASS);
	}

	public Session getSession()
	{
		return _session;
	}

	public Store getStore()
	{
		return _store;
	}
}
