package l2minius.gameserver.taskmanager.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import l2minius.gameserver.model.GameObjectsStorage;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.taskmanager.Task;
import l2minius.gameserver.taskmanager.TaskManager;
import l2minius.gameserver.taskmanager.TaskManager.ExecutedTask;
import l2minius.gameserver.taskmanager.TaskTypes;

public class TaskRecom extends Task
{
	private static final Logger _log = LoggerFactory.getLogger(TaskRecom.class);
	private static final String NAME = "sp_recommendations";

	@Override
	public String getName()
	{
		return NAME;
	}

	@Override
	public void onTimeElapsed(ExecutedTask task)
	{
		_log.info("Recommendation Global Task: launched.");
		for (Player player : GameObjectsStorage.getAllPlayersForIterate())
		{
			player.restartRecom();
		}
		_log.info("Recommendation Global Task: completed.");
	}

	@Override
	public void initializate()
	{
		TaskManager.addUniqueTask(NAME, TaskTypes.TYPE_GLOBAL_TASK, "1", "06:30:00", "");
	}
}