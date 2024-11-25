package npc.model;

import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.instances.NpcInstance;
import l2minius.gameserver.model.entity.Reflection;
import instances.MiniusBossInstance;
import l2minius.gameserver.templates.npc.NpcTemplate;
import l2minius.gameserver.utils.ReflectionUtils;

public class MiniusGatekeeperInstance extends NpcInstance {
    private static final int INSTANCE_ID = 8003; // ID for MiniusBossInstance

    public MiniusGatekeeperInstance(int objectId, NpcTemplate template) {
        super(objectId, template);
    }

    @Override
    public void onBypassFeedback(Player player, String command) {
        if (command.equalsIgnoreCase("enterInstance")) {
            if (player.getParty() == null) {
                player.sendMessage("You need to be in a party to enter this instance.");
                return;
            }

            if (!player.getParty().isLeader(player)) {
                player.sendMessage("Only the party leader can enter the instance.");
                return;
            }

            int partySize = player.getParty().getMemberCount();
            if (partySize < 4 || partySize > 9) {
                player.sendMessage("This instance requires a party of 4 to 9 players.");
                return;
            }

            Reflection reflection = player.getActiveReflection();
            if (reflection != null && reflection.getInstancedZoneId() == INSTANCE_ID) {
                player.teleToLocation(reflection.getReturnLoc(), reflection);
            } else {
                Reflection newInstance = ReflectionUtils.enterReflection(player, new MiniusBossInstance(), INSTANCE_ID);
                if (newInstance != null) {
                    player.sendMessage("You have entered the MiniusBoss instance.");
                } else {
                    player.sendMessage("Failed to create the instance.");
                }
            }
        } else {
            super.onBypassFeedback(player, command);
        }
    }
}
