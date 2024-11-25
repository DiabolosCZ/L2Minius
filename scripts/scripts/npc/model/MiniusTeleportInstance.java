package npc.model;

import l2minius.gameserver.model.Player;
import l2minius.gameserver.model.instances.NpcInstance;
import l2minius.gameserver.model.entity.Reflection;
import instances.MiniusBossInstance;
import l2minius.gameserver.templates.npc.NpcTemplate;
import l2minius.gameserver.utils.ReflectionUtils;

public class MiniusTeleportInstance extends NpcInstance {
    private static final int INSTANCE_ID = 8003; // ID instance MiniusBossInstance

    public MiniusTeleportInstance(int objectId, NpcTemplate template) {
        super(objectId, template);
    }

    @Override
    public void onBypassFeedback(Player player, String command) {
        if (command.equalsIgnoreCase("enterInstance")) {
            if (!checkPlayerConditions(player)) {
                return;
            }

            Reflection reflection = player.getActiveReflection();
            if (reflection != null && reflection.getInstancedZoneId() == INSTANCE_ID) {
                player.teleToLocation(reflection.getReturnLoc(), reflection);
            } else {
                Reflection newInstance = createNewInstance(player);
                if (newInstance != null) {
                    player.sendMessage("Vstoupili jste do instance MiniusBoss.");
                } else {
                    player.sendMessage("Nepodařilo se vytvořit instanci.");
                }
            }
        } else {
            super.onBypassFeedback(player, command);
        }
    }

    private boolean checkPlayerConditions(Player player) {
        if (player.getParty() == null) {
            player.sendMessage("Musíte být ve skupině, abyste mohli vstoupit do instance.");
            return false;
        }

        if (!player.getParty().isLeader(player)) {
            player.sendMessage("Pouze vedoucí skupiny může vstoupit do instance.");
            return false;
        }

        int partySize = player.getParty().getMemberCount();
        if (partySize < 4 || partySize > 9) {
            player.sendMessage("Tato instance vyžaduje skupinu o velikosti 4 až 9 hráčů.");
            return false;
        }

        return true;
    }

    private Reflection createNewInstance(Player player) {
        return ReflectionUtils.enterReflection(player, new MiniusBossInstance(), INSTANCE_ID);
    }
}
