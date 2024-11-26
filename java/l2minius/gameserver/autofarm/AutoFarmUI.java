package l2minius.gameserver.autofarm;

import l2minius.gameserver.model.Player;
import l2minius.gameserver.network.serverpackets.NpcHtmlMessage;

public class AutoFarmUI {

    private final Player player;
    private final AutoFarmingTask autoFarmingTask;

    public AutoFarmUI(Player player, AutoFarmingTask autoFarmingTask) {
        this.player = player;
        this.autoFarmingTask = autoFarmingTask;
    }

    public void open() {
        NpcHtmlMessage msg = new NpcHtmlMessage(0);
        StringBuilder html = new StringBuilder();



        msg.setHtml(html.toString());
        player.sendPacket(msg);
    }
}
