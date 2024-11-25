package l2minius.loginserver;

import java.io.File;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import l2minius.commons.net.nio.impl.SelectorConfig;
import l2minius.commons.net.nio.impl.SelectorThread;
import l2minius.commons.threading.RunnableImpl;
import l2minius.commons.versioning.Version;
import l2minius.loginserver.database.L2DatabaseFactory;
import l2minius.loginserver.gameservercon.GameServerCommunication;

public class AuthServer
{
	private static final Logger _log = LoggerFactory.getLogger(AuthServer.class);

	private static AuthServer authServer;
	public static final String PROJECT_REVISION = "Lineage ][ Minius";
	public static final String UPDATE_NAME = "High Five: Part 5";
	private Version version;
	private final GameServerCommunication _gameServerListener;
	private final SelectorThread<L2LoginClient> _selectorThread;

	public static AuthServer getInstance()
	{
		return authServer;
	}

	public AuthServer() throws Throwable
	{
		version = new Version(AuthServer.class);
		_log.info("============================================================================");
		_log.info("Název: ............................................... " + "LOGIN SERVER");
		_log.info("Revize projektu: ................................... " + PROJECT_REVISION);
		_log.info("Aktualizace: ............................................. " + UPDATE_NAME);
		_log.info("Číslo revize:.................................. " + version.getVersionNumber());
		_log.info("Revize sestavení: ..................................... " + version.getRevisionNumber());
		_log.info("Datum výstavby: ......................................... " + version.getBuildDate());
		_log.info("Verze kompilátoru: ................................... " + version.getBuildJdk());
		_log.info("Vytvořeno: ............................................. " + "© MiniusStudio");
		_log.info("Developeři: ............................................. " + "Diabolos");
		_log.info("============================================================================");

		Config.initCrypt();
		GameServerManager.getInstance();

		L2LoginPacketHandler loginPacketHandler = new L2LoginPacketHandler();
		SelectorHelper sh = new SelectorHelper();
		SelectorConfig sc = new SelectorConfig();
		_selectorThread = new SelectorThread<L2LoginClient>(sc, loginPacketHandler, sh, sh, sh);

		_gameServerListener = GameServerCommunication.getInstance();
		_gameServerListener.openServerSocket(Config.GAME_SERVER_LOGIN_HOST.equals("127.0.0.1") ? null : InetAddress.getByName(Config.GAME_SERVER_LOGIN_HOST), Config.GAME_SERVER_LOGIN_PORT);
		_gameServerListener.start();
		_log.info("Listening for gameservers on " + Config.GAME_SERVER_LOGIN_HOST + ":" + Config.GAME_SERVER_LOGIN_PORT);

		_selectorThread.openServerSocket(Config.LOGIN_HOST.equals("127.0.0.1") ? null : InetAddress.getByName(Config.LOGIN_HOST), Config.PORT_LOGIN);
		_selectorThread.start();
		_log.info("Listening for clients on " + Config.LOGIN_HOST + ":" + Config.PORT_LOGIN);

		if (Config.SCHEDULE_RESTART_SECONDS > 0L)
		{
			_log.info("Login Server will automatically restart in " + Config.SCHEDULE_RESTART_SECONDS + " seconds!");
			ThreadPoolManager.getInstance().schedule(new AuthShutdown(), TimeUnit.SECONDS.toMillis(Config.SCHEDULE_RESTART_SECONDS));
		}
	}

	public GameServerCommunication getGameServerListener()
	{
		return _gameServerListener;
	}

	public static void checkFreePorts() throws Throwable
	{
		ServerSocket ss = null;

		try
		{
			if (Config.LOGIN_HOST.equalsIgnoreCase("127.0.0.1"))
			{
				ss = new ServerSocket(Config.PORT_LOGIN);
			}
			else
			{
				ss = new ServerSocket(Config.PORT_LOGIN, 50, InetAddress.getByName(Config.LOGIN_HOST));
			}
		}
		finally
		{
			if (ss != null)
			{
				try
				{
					ss.close();
				}
				catch (Exception e)
				{
				}
			}
		}
	}

	public static void main(String[] args) throws Throwable
	{
		new File("./log/").mkdir();
		// Initialize config
		Config.load();
		// Check binding address
		checkFreePorts();
		// Initialize database
		Class.forName(Config.DATABASE_DRIVER).getDeclaredConstructor().newInstance();
		L2DatabaseFactory.getInstance().getConnection().close();

		authServer = new AuthServer();
	}

	private static class AuthShutdown extends RunnableImpl
	{
		@Override
		public void runImpl()
		{
			_log.info("Starting Auth Server Restart!");
			Runtime.getRuntime().exit(2);
		}
	}
}
