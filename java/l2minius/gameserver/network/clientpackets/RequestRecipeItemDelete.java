package l2minius.gameserver.network.clientpackets;

import l2minius.gameserver.data.xml.holder.RecipeHolder;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.Recipe;
import l2minius.gameserver.network.serverpackets.RecipeBookItemList;

public class RequestRecipeItemDelete extends L2GameClientPacket
{
	private int _recipeId;

	@Override
	protected void readImpl()
	{
		this._recipeId = this.readD();
	}

	@Override
	protected void runImpl()
	{
		Player activeChar = this.getClient().getActiveChar();
		if (activeChar == null)
		{
			return;
		}

		if (activeChar.getPrivateStoreType() == Player.STORE_PRIVATE_MANUFACTURE)
		{
			activeChar.sendActionFailed();
			return;
		}

		Recipe rp = RecipeHolder.getInstance().getRecipeByRecipeId(this._recipeId);
		if (rp == null)
		{
			activeChar.sendActionFailed();
			return;
		}

		activeChar.unregisterRecipe(this._recipeId);
		activeChar.sendPacket(new RecipeBookItemList(activeChar, rp.isDwarvenRecipe()));
	}
}