package l2minius.gameserver;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import l2minius.commons.lang.StatsUtils;
import l2minius.commons.listener.Listener;
import l2minius.commons.listener.ListenerList;
import l2minius.commons.net.AdvIP;
import l2minius.commons.net.nio.impl.SelectorThread;
import l2minius.commons.versioning.Version;
import l2minius.gameserver.cache.CrestCache;
import l2minius.gameserver.cache.ImagesCache;
import l2minius.gameserver.dao.CharacterDAO;
import l2minius.gameserver.dao.EmotionsTable;
import l2minius.gameserver.dao.ItemsDAO;
import l2minius.gameserver.data.BoatHolder;
import l2minius.gameserver.data.xml.Parsers;
import l2minius.gameserver.data.xml.holder.EventHolder;
import l2minius.gameserver.data.xml.holder.ItemHolder;
import l2minius.gameserver.data.xml.holder.ResidenceHolder;
import l2minius.gameserver.data.xml.holder.StaticObjectHolder;
import l2minius.gameserver.data.xml.parser.ProxiesParser;
import l2minius.gameserver.database.DatabaseFactory;
import l2minius.gameserver.database.LoginDatabaseFactory;
import l2minius.gameserver.database.merge.ClanDataMerge;
import l2minius.gameserver.database.merge.DataMerge;
import l2minius.gameserver.donation.DonationReader;
import l2minius.gameserver.multverso.datatables.EnchantNamesTable;
//import fandc.datatables.CharacterMonthlyRanking;
import l2minius.gameserver.multverso.datatables.OfflineBuffersTable;
import l2minius.gameserver.multverso.facebook.ActionsExtractingManager;
import l2minius.gameserver.multverso.facebook.CompletedTasksHistory;
import l2minius.gameserver.multverso.facebook.FacebookAutoAnnouncement;
import l2minius.gameserver.multverso.facebook.FacebookProfilesHolder;
import l2minius.gameserver.multverso.facebook.OfficialPostsHolder;
import l2minius.gameserver.multverso.security.AntiFeedManager;
import l2minius.gameserver.multverso.streaming.AFKStreamersHandler;
import l2minius.gameserver.multverso.streaming.TwitchParser;
import l2minius.gameserver.multverso.tournament.TournamentHolder;
import l2minius.gameserver.multverso.votingengine.VotingRewardAPI;
import l2minius.gameserver.geodata.GeoEngine;
import l2minius.gameserver.handler.admincommands.AdminCommandHandler;
import l2minius.gameserver.handler.items.ItemHandler;
import l2minius.gameserver.handler.usercommands.UserCommandHandler;
import l2minius.gameserver.handler.voicecommands.VoicedCommandHandler;
import l2minius.gameserver.idfactory.IdFactory;
import l2minius.gameserver.instancemanager.AutoAnnounce;
import l2minius.gameserver.instancemanager.AutoSpawnManager;
import l2minius.gameserver.instancemanager.BloodAltarManager;
import l2minius.gameserver.instancemanager.CastleManorManager;
import l2minius.gameserver.instancemanager.CoupleManager;
import l2minius.gameserver.instancemanager.CursedWeaponsManager;
import l2minius.gameserver.instancemanager.DimensionalRiftManager;
import l2minius.gameserver.instancemanager.HellboundManager;
import l2minius.gameserver.instancemanager.L2TopManager;
import l2minius.gameserver.instancemanager.PetitionManager;
import l2minius.gameserver.instancemanager.PlayerMessageStack;
import l2minius.gameserver.instancemanager.QuestManager;
import l2minius.gameserver.instancemanager.RaidBossSpawnManager;
import l2minius.gameserver.instancemanager.SoDManager;
import l2minius.gameserver.instancemanager.SoIManager;
import l2minius.gameserver.instancemanager.SpawnManager;
import l2minius.gameserver.instancemanager.achievements_engine.AchievementsManager;
import l2minius.gameserver.instancemanager.games.FishingChampionShipManager;
import l2minius.gameserver.instancemanager.games.LotteryManager;
import l2minius.gameserver.instancemanager.games.MiniGameScoreManager;
import l2minius.gameserver.instancemanager.itemauction.ItemAuctionManager;
import l2minius.gameserver.instancemanager.naia.NaiaCoreManager;
import l2minius.gameserver.instancemanager.naia.NaiaTowerManager;
import l2minius.gameserver.kara.twitch.TwitchManager;
import l2minius.gameserver.kara.vote.VoteManager;
import l2minius.gameserver.listener.GameListener;
import l2minius.gameserver.listener.game.OnAbortShutdownListener;
import l2minius.gameserver.listener.game.OnConfigsReloaded;
import l2minius.gameserver.listener.game.OnShutdownCounterStartListener;
import l2minius.gameserver.listener.game.OnShutdownListener;
import l2minius.gameserver.listener.game.OnStartListener;
import l2minius.gameserver.masteriopack.rankpvpsystem.RPSConfig;
import l2minius.gameserver.model.PhantomPlayers;
import l2minius.gameserver.model.World;
import l2minius.gameserver.model.entity.Hero;
import l2minius.gameserver.model.entity.MonsterRace;
import l2minius.gameserver.model.entity.SevenSigns;
import l2minius.gameserver.model.entity.VoteRewardHopzone;
import l2minius.gameserver.model.entity.VoteRewardTopzone;
import l2minius.gameserver.model.entity.CCPHelpers.itemLogs.ItemLogList;
import l2minius.gameserver.model.entity.SevenSignsFestival.SevenSignsFestival;
import l2minius.gameserver.model.entity.achievements.AchievementNotification;
import l2minius.gameserver.model.entity.achievements.Achievements;
import l2minius.gameserver.model.entity.achievements.PlayerCounters;
import l2minius.gameserver.model.entity.auction.AuctionManager;
import l2minius.gameserver.model.entity.events.fightclubmanager.FightClubEventManager;
import l2minius.gameserver.model.entity.forum.ForumDatabaseHandler;
import l2minius.gameserver.model.entity.olympiad.Olympiad;
import l2minius.gameserver.model.entity.tournament.ActiveBattleManager;
import l2minius.gameserver.model.entity.tournament.BattleScheduleManager;
import l2minius.gameserver.network.FakeGameClient;
import l2minius.gameserver.network.GameClient;
import l2minius.gameserver.network.GamePacketHandler;
import l2minius.gameserver.network.loginservercon.AuthServerCommunication;
import l2minius.gameserver.network.telnet.TelnetServer;
import l2minius.gameserver.scripts.Scripts;
import l2minius.gameserver.security.HWIDBan;
import l2minius.gameserver.tables.AugmentationData;
import l2minius.gameserver.tables.ClanTable;
import l2minius.gameserver.tables.EnchantHPBonusTable;
import l2minius.gameserver.tables.FakePlayersTable;
import l2minius.gameserver.tables.FishTable;
import l2minius.gameserver.tables.LevelUpTable;
import l2minius.gameserver.tables.PetSkillsTable;
import l2minius.gameserver.tables.SkillTreeTable;
import l2minius.gameserver.taskmanager.BackupTaskManager;
import l2minius.gameserver.taskmanager.ItemsAutoDestroy;
import l2minius.gameserver.taskmanager.TaskManager;
import l2minius.gameserver.taskmanager.tasks.RestoreOfflineTraders;
import l2minius.gameserver.utils.Debug;
import l2minius.gameserver.utils.Strings;
import l2minius.gameserver.vote.RuVoteEngine;
import l2minius.gameserver.vote.VoteMain;
import net.sf.ehcache.CacheManager;

public class GameServer
{
	public static final int AUTH_SERVER_PROTOCOL = 2;
	public static final String PROJECT_REVISION = "Lineage ][ Minius";
	public static final String UPDATE_NAME = "High Five: Part 5";
	private static final Logger _log = LoggerFactory.getLogger(GameServer.class);
	public static Date server_started;

	public class GameServerListenerList extends ListenerList<GameServer>
	{
		public void onStart()
		{
			for (Listener<GameServer> listener : getListeners())
			{
				if (OnStartListener.class.isInstance(listener))
				{
					((OnStartListener) listener).onStart();
				}
			}
		}

		public void onShutdown(Shutdown.ShutdownMode shutdownMode)
		{
			for (Listener<GameServer> listener : getListeners())
			{
				if (OnShutdownListener.class.isInstance(listener))
				{
					((OnShutdownListener) listener).onShutdown(shutdownMode);
				}
			}
		}

		public void onAbortShutdown(Shutdown.ShutdownMode oldMode, int cancelledOnSecond)
		{
			for (Listener<GameServer> listener : getListeners())
			{
				if (OnAbortShutdownListener.class.isInstance(listener))
				{
					((OnAbortShutdownListener) listener).onAbortShutdown(oldMode, cancelledOnSecond);
				}
			}
		}

		public void onShutdownScheduled()
		{
			for (Listener<GameServer> listener : getListeners())
			{
				if (OnShutdownCounterStartListener.class.isInstance(listener))
				{
					((OnShutdownCounterStartListener) listener).onCounterStart();
				}
			}
		}

		public void onConfigsReloaded()
		{
			for (Listener<GameServer> listener : getListeners())
			{
				if (OnConfigsReloaded.class.isInstance(listener))
				{
					((OnConfigsReloaded) listener).onConfigsReloaded();
				}
			}
		}
	}

	public static GameServer _instance;

	private final SelectorThread<GameClient> _selectorThreads[];
	private TelnetServer statusServer;
	private final Version version;
	private final GameServerListenerList _listeners;

	private final int _serverStarted;

	public SelectorThread<GameClient>[] getSelectorThreads()
	{
		return _selectorThreads;
	}

	public int time()
	{
		return (int) (System.currentTimeMillis() / 1000);
	}

	public int uptime()
	{
		return time() - _serverStarted;
	}

	public GameServer() throws Exception
	{
		version = new Version(GameServer.class);
		_log.info("============================================================================");
		_log.info("Nazev: .................................................................... " + "GAME SERVER");
		_log.info("Revize projektu: . ........................................................ " + PROJECT_REVISION);
		_log.info("Aktualizace: ............................................................. " + UPDATE_NAME);
		_log.info("Cislo revize:....................................................... " + version.getVersionNumber());
		_log.info("Revize sestaveni: ........................................................ " + version.getRevisionNumber());
		_log.info("Datum vystavby: ............................................................. " + version.getBuildDate());
		_log.info("Verze kompilatoru: ........................................................ " + version.getBuildJdk());
		_log.info("Vytvoreno: .................................................................... " + "MiniusStudio");
		_log.info("Developeri: .................................................................... " + "Diabolos");
		_log.info("============================================================================");

		_instance = this;
		_serverStarted = time();
		_listeners = new GameServerListenerList();
		new File(Config.DATAPACK_ROOT + "/log/").mkdir();

		// Initialize config
		Config.load();
		ConfigHolder.getInstance().reload();
		Debug.initListeners();

		// Check binding address
		checkFreePorts();

//		// We check with internet if the current external ip is the one that is activated for this source, else, we exit the program // license ichsan
//		if (!Config.EXTERNAL_HOSTNAME.equalsIgnoreCase(Patovicador.getInstance().despatovicar(LOCAL_SERVER_IP_ENCRIPTED)))
//		{
//			try
//			{
//				// El URL esta encriptado con la key "xxxYYYxxx" que es la default que cree
//				URL url = new URL(Patovicador.getInstance().despatovicar(WEB_HTML_ENCRIPTED));
//				try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream())))
//				{
//					String result = in.readLine();
//					if (result == null || result.isEmpty())
//						throw new Exception();
//
//					// La llave de desencriptacion es la misma IP, por lo que la ip se encripta con llave de ip, y luego esa llave queda para desencriptar el resto de los textos
//					Patovicador.getInstance().setPatollave(result);
//					result = Patovicador.getInstance().patovicar(result);
//
//					// Agregamos soporte para domains
//					String currentIp = SERVER_IP_UNENCRIPTED;
//					try
//					{
//						currentIp = InetAddress.getByName(currentIp).getHostAddress();
//					}
//					catch (Exception e) {}
//
//					if (!result.equalsIgnoreCase(Patovicador.getInstance().patovicar(currentIp)))
//						throw new Exception();
//				}
//			}
//			catch (Exception e)
//			{
//				System.out.println("Wrong License");
//				System.exit(1);
//			}
//		}

		// Initialize database
		_log.info("EXTERNI IP =============================" + Config.EXTERNAL_HOSTNAME);
		_log.info("INTERNI IP =============================" + Config.INTERNAL_HOSTNAME);
		_log.info("Maximalni pocet hracu ====================== " + Config.MAXIMUM_ONLINE_USERS);
		_log.info("Cas restartu  =======================" + Config.RESTART_AT_TIME, Shutdown.ShutdownMode.RESTART);

		Class.forName(Config.DATABASE_DRIVER).getDeclaredConstructor().newInstance();
		DatabaseFactory.getInstance().getConnection().close();
		LoginDatabaseFactory.getInstance().getConnection().close();
		printSection("Nacitani konfigurace ochrany");
		IdFactory idFactory = IdFactory.getInstance();
		if (!idFactory.isInitialized())
		{
			_log.error("Could not read object IDs from DB. Please Check Your Data.", new Exception("Could not initialize the ID factory"));
			throw new Exception("Could not initialize the ID factory");
		}

		CacheManager.getInstance();

		ThreadPoolManager.getInstance();

		_log.info("===============[Loading Scripts]==================");
		Scripts.getInstance();
		GeoEngine.load();
		VoteMain.load();
		printSection("Twitch Manager");
		TwitchManager.getInstance();
		printSection("Vote Manager");
		VoteManager.getInstance();
		// FakePlayers.getInstance();

		// add fixed
		printSection("AntiFeedManager");
		AntiFeedManager.getInstance();
		printSection("QuestManager");
		_log.info("QuestManager : loaded " + QuestManager.getQuests().size() + " quest's");
		QuestManager.updateQuestNames();

		FakePlayersTable.getInstance();
		Strings.reload();
		GameTimeController.getInstance();
		printSection("Lineage World");
		World.init();
		printSection("");
		Parsers.parseAll();
		printSection("Banned HWIDS");
		HWIDBan.LoadAllHWID();
		ItemsDAO.getInstance();
		printSection("Clan Crests");
		CrestCache.getInstance();
		printSection("Loading Images");
		ImagesCache.getInstance();
		printSection("");
		CharacterDAO.getInstance();
		ClanTable.getInstance();
		printSection("Fish Table");
		FishTable.getInstance();
		printSection("Skills");
		SkillTreeTable.getInstance();
		EnchantNamesTable.getInstance();
		printSection("Augmentation Data");
		AugmentationData.getInstance();
		EnchantHPBonusTable.getInstance();
		printSection("Level Up Table");
		LevelUpTable.getInstance();
		PetSkillsTable.getInstance();
		printSection("Item Logs");
		ItemLogList.getInstance().loadAllLogs();
		printSection("Auctioneer");
		ItemAuctionManager.getInstance();
		printSection("Masterio Pack");
		RPSConfig.load();
		printSection("Merge System Loaded");
		DataMerge.getInstance();
		ClanDataMerge.getInstance();
		Scripts.getInstance().init();
		_log.info("===============[Spawn Manager]==================");
		SpawnManager.getInstance().spawnAll();
		printSection("Boats");
		BoatHolder.getInstance().spawnAll();
		StaticObjectHolder.getInstance().spawnAll();
		RaidBossSpawnManager.getInstance();
		printSection("Dimensional Rift");
		DimensionalRiftManager.getInstance();
		Announcements.getInstance();
		LotteryManager.getInstance();
		PlayerMessageStack.getInstance();
		if (Config.AUTODESTROY_ITEM_AFTER > 0)
		{
			ItemsAutoDestroy.getInstance();
		}
		MonsterRace.getInstance();
		printSection("Seven Signs");
		SevenSigns.getInstance();
		SevenSignsFestival.getInstance();
		SevenSigns.getInstance().updateFestivalScore();
		AutoSpawnManager.getInstance();
		SevenSigns.getInstance().spawnSevenSignsNPC();
		_log.info("===================================================================");
		_log.info("===================[Loading Olympiad System]=======================");
		if (Config.ENABLE_OLYMPIAD)
		{
			Olympiad.load();
			Hero.getInstance();
		}
		_log.info("===================[Olympiad System Loaded]=======================");
		_log.info("===================================================================");
		PetitionManager.getInstance();
		CursedWeaponsManager.getInstance();
		printSection("Loaded Small Achievement System");
		AchievementsManager.getInstance();
		if (!Config.ALLOW_WEDDING)
		{
			CoupleManager.getInstance();
			_log.info("CoupleManager initialized");
		}
		printSection("ItemHandler");
		ItemHandler.getInstance();
		ItemHandler.getInstance().log(); // fixed

		printSection("Admin Commands");
		AdminCommandHandler.getInstance().log();
		printSection("Players Commands");
		UserCommandHandler.getInstance().log();
		VoicedCommandHandler.getInstance().log();
		TaskManager.getInstance();
		_log.info("======================[Tournament By Kara`]==========================");
		TournamentHolder.init();
		_log.info("======================[Loading Castels & Clan Halls]==========================");
		ResidenceHolder.getInstance().callInit();
		EventHolder.getInstance().callInit();
		CastleManorManager.getInstance();
		printSection("");
		Runtime.getRuntime().addShutdownHook(Shutdown.getInstance());
		printSection("Auto Cleaner");
		_log.info("IdFactory: Free ObjectID's remaining: " + IdFactory.getInstance().size());
		printSection("");
		CoupleManager.getInstance();
		if (Config.ALT_FISH_CHAMPIONSHIP_ENABLED)
		{
			FishingChampionShipManager.getInstance();
		}
		printSection("Hellbound");
		HellboundManager.getInstance();

		NaiaTowerManager.getInstance();
		NaiaCoreManager.getInstance();
		printSection("");
		SoDManager.getInstance();
		SoIManager.getInstance();
		BloodAltarManager.getInstance();
		AuctionManager.getInstance();
		if (Config.ALLOW_DROP_CALCULATOR)
		{
			_log.info("Preparing Drop Calculator");
			ItemHolder.getInstance().getDroppableTemplates();
		}
		MiniGameScoreManager.getInstance();
		if (Config.ALLOW_HOPZONE_VOTE_REWARD)
		{
			VoteRewardHopzone.getInstance();
		}
		if (Config.ALLOW_TOPZONE_VOTE_REWARD)
		{
			VoteRewardTopzone.getInstance();
		}
		L2TopManager.getInstance();
		// AutoRaidEventManager.getInstance();
		if (Config.BUFF_STORE_ENABLED)
		{
			printSection("Offline Buffers");
			OfflineBuffersTable.getInstance().restoreOfflineBuffers();
		}

		if (Config.ENABLE_PLAYER_COUNTERS)
		{
			PlayerCounters.checkTable();
			AchievementNotification.getInstance();

			if (Config.ENABLE_ACHIEVEMENTS)
			{
				Achievements.getInstance();
			}
		}
		if (Config.ENABLE_EMOTIONS)
		{
			EmotionsTable.init();
			_log.info("Emotions Loaded....");
		}

		CharacterDAO.getInstance().markTooOldChars();
		printSection("DataBase Cleaner Loaded");
		CharacterDAO.getInstance().checkCharactersToDelete();
		FightClubEventManager.getInstance();
		BattleScheduleManager.getInstance();
		ActiveBattleManager.startScheduleThread();

		GamePacketHandler gph = new GamePacketHandler();
		FakeGameClient.setGamePacketHandler(gph);
		InetAddress serverAddr = Config.GAMESERVER_HOSTNAME.equalsIgnoreCase("*") ? null : InetAddress.getByName(Config.GAMESERVER_HOSTNAME);
		int arrayLen = Config.GAMEIPS.isEmpty() ? Config.PORTS_GAME.length : Config.PORTS_GAME.length + Config.GAMEIPS.size();
		_selectorThreads = new SelectorThread[arrayLen];
		for (int i = 0; i < Config.PORTS_GAME.length; i++)
		{
			try
			{
				_selectorThreads[i] = new SelectorThread<GameClient>(Config.SELECTOR_CONFIG, gph, gph, gph, null);
				_selectorThreads[i].openServerSocket(serverAddr, Config.PORTS_GAME[i]);
				_selectorThreads[i].start();
			}
			catch (IOException ioe)
			{
				_log.error("Cannot bind address: " + serverAddr + ":" + Config.PORTS_GAME[i], ioe);
			}
		}
		if (!Config.GAMEIPS.isEmpty()) // AdvIP support. server.ini ports are ignored and accepted only IPs and ports from advipsystem.ini
		{
			int i = Config.PORTS_GAME.length; // Start from the last spot.
			for (AdvIP advip : Config.GAMEIPS)
			{
				try
				{
					_selectorThreads[i] = new SelectorThread<GameClient>(Config.SELECTOR_CONFIG, gph, gph, gph, null);
					_selectorThreads[i].openServerSocket(InetAddress.getByName(advip.channelAdress), advip.channelPort);
					_selectorThreads[i++].start();
					_log.info("AdvIP: Channel " + advip.channelId + " is open on: " + advip.channelAdress + ":" + advip.channelPort);
				}
				catch (IOException ioe)
				{
					_log.error("Cannot bind address: " + advip.channelAdress + ":" + advip.channelPort, ioe);
				}
			}
		}

		if (Config.SERVICES_OFFLINE_TRADE_RESTORE_AFTER_RESTART)
		{
			ThreadPoolManager.getInstance().schedule(new RestoreOfflineTraders(), 100000L);
		}
		ThreadPoolManager.getInstance().scheduleAtFixedRate(new AutoAnnounce(), 60000, 60000);
		FacebookProfilesHolder.getInstance();
		printSection("Loaded Facebook System");
		OfficialPostsHolder.getInstance();
		CompletedTasksHistory.getInstance();
		ActionsExtractingManager.getInstance().load();
		FacebookAutoAnnouncement.load();
		if (ConfigHolder.getBool("AllowStreamingAFKSystem") && ConfigHolder.getInt("StreamingAFKSystemDelayBetweenMsgs") > 0)
		{
			AFKStreamersHandler.getInstance();
			printSection("Loaded Stream System");
		}

		if (ConfigHolder.getBool("AllowStreamingSystem") && ConfigHolder.getLong("StreamCheckTwitchDelay") > 0)
		{
			TwitchParser.getInstance();
		}

		if (ConfigHolder.getBool("AllowForum"))
		{
			_log.info("===============[Forum]==================");
			ForumDatabaseHandler.getInstance();
		}
		ProxiesParser.getInstance().load();
		printSection("Loaded Proxy System");
		DonationReader.getInstance();
		printSection("Loaded AUTO - Donation System");
		RuVoteEngine.startThread();
		BackupTaskManager.startThread();

		VotingRewardAPI.getInstance();

		if (Config.PHANTOM_PLAYERS_ENABLED)
		{
			PhantomPlayers.init();
		}

		getListeners().onStart();
		if (Config.IS_TELNET_ENABLED)
		{
			statusServer = new TelnetServer();
		}
		else
		{
			_log.info("Telnet server is currently disabled.");
		}
		Shutdown.getInstance().schedule(Config.RESTART_AT_TIME, Shutdown.ShutdownMode.RESTART, Config.BACKUP_DURING_AUTO_RESTART);
		printSection("");
		_log.info(">>>>>>>>>>>>>>> GameServer Started <<<<<<<<<<<<<<");
		_log.info("Maximum Numbers of Connected Players: " + Config.MAXIMUM_ONLINE_USERS);

		GameServer._log.info("====================================================================");
		final String memUsage = new StringBuilder().append(StatsUtils.getMemUsage()).toString();
		for (final String line : memUsage.split("\n"))
		{
			GameServer._log.info(line);
		}
		GameServer._log.info("====================================================================");

		AuthServerCommunication.getInstance().start();
		server_started = new Date();
	}

	public static void printSection(String s)
	{
		if (s.isEmpty())
		{
			s = "==============================================================================";
		}
		else
		{
			s = "=[ " + s + " ]";
			while (s.length() < 78)
			{
				s = "-" + s;
			}
		}
		_log.info(s);
	}

	public GameServerListenerList getListeners()
	{
		return _listeners;
	}

	public static GameServer getInstance()
	{
		return _instance;
	}

	public <T extends GameListener> boolean addListener(T listener)
	{
		return _listeners.add(listener);
	}

	public <T extends GameListener> boolean removeListener(T listener)
	{
		return _listeners.remove(listener);
	}

	public static void checkFreePorts()
	{
		boolean binded = false;
		while (!binded)
		{
			for (int PORT_GAME : Config.PORTS_GAME)
			{
				try
				{
					ServerSocket ss;
					if (Config.GAMESERVER_HOSTNAME.equalsIgnoreCase("127.0.0.1"))
					{
						ss = new ServerSocket(PORT_GAME);
					}
					else
					{
						ss = new ServerSocket(PORT_GAME, 50, InetAddress.getByName(Config.GAMESERVER_HOSTNAME));
					}
					ss.close();
					binded = true;
				}
				catch (Exception e)
				{
					_log.warn("Port " + PORT_GAME + " is allready binded. Please free it and restart server.");
					binded = false;
					try
					{
						Thread.sleep(1000);
					}
					catch (InterruptedException e2)
					{
					}
				}
			}
		}
	}

	public static void main(String[] args) throws Exception
	{
		new GameServer();
	}

	public Version getVersion()
	{
		return version;
	}

	public TelnetServer getStatusServer()
	{
		return statusServer;
	}

//	private static String WEB_HTML_ENCRIPTED;
//	static
//	{
//		WEB_HTML_ENCRIPTED = "qR3LAktic6T7TCzrXxXKzZ1+7pPOFjyBhwnwQEw25mw=";
//		// Esto sale de la direccion http://checkip.amazonaws.com, pero encriptandolo con la key original
//	}
//
//	private static String SERVER_IP_UNENCRIPTED;
//	static
//	{
//		SERVER_IP_UNENCRIPTED = "127.0.0.1";
//		// Esta es la ip registrada al sistema, no encriptada, para quitarle un poco de dificultad
//	}
//
//	private static String LOCAL_SERVER_IP_ENCRIPTED;
//	static
//	{
//		LOCAL_SERVER_IP_ENCRIPTED = "h8slj0HGUCLCX7LEhbggaw==";
//		// Vendria a ser 127.0.0.1, usada para evitar chequeos cuando se corre el server en la pc propia. Encriptada con la key original
//	}
}