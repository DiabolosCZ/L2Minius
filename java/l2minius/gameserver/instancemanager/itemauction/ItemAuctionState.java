package l2minius.gameserver.instancemanager.itemauction;

import l2minius.commons.lang.ArrayUtils;

public enum ItemAuctionState
{
	CREATED, STARTED, FINISHED;

	public static final ItemAuctionState stateForStateId(int stateId)
	{
		return ArrayUtils.valid(values(), stateId);
	}
}