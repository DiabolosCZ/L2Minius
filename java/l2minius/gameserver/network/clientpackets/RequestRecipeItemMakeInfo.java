package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.data.xml.holder.RecipeHolder;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.Recipe;
import l2minius.gameserver.network.serverpackets.RecipeItemMakeInfo;

public class RequestRecipeItemMakeInfo extends L2GameClientPacket
{
	private int _id;

	/**
	 * packet type id 0xB7
	 * format:		cd
	 */
	@Override
	protected void readImpl()
	{
		this._id = this.readD();
	}

	@Override
	protected void runImpl()
	{
		Player activeChar = this.getClient().getActiveChar();
		if (activeChar == null)
		{
			return;
		}

		Recipe recipeList = RecipeHolder.getInstance().getRecipeByRecipeId(this._id);
		if (recipeList == null)
		{
			activeChar.sendActionFailed();
			return;
		}

		this.sendPacket(new RecipeItemMakeInfo(activeChar, recipeList, 0xffffffff));
	}
}