package l2minius.gameserver.handler.voicecommands.impl;

import java.util.List;

import l2minius.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2minius.gameserver.model.Player;
import l2minius.gameserver.scripts.Functions;
import l2minius.gameserver.autofarm.AutoFarmingTask;

public class AutoFarm implements IVoicedCommandHandler {

    private static final String[] _commandList = { "autofarm" };

    private static final int MAX_SKILLS = 5;

    @Override
    public String[] getVoicedCommandList() {
        return _commandList;
    }

    @Override
    public boolean useVoicedCommand(String command, Player player, String args) {
        if ("autofarm".equalsIgnoreCase(command)) {
            if (args != null) {
                String[] params = args.split(" ");
                switch (params[0].toLowerCase()) {
                    case "start":
                        startFarming(player);
                        break;
                    case "stop":
                        stopFarming(player);
                        break;
                    case "add":
                        if (params.length > 2) {
                            addSkill(player, params[1], Integer.parseInt(params[2]));
                        } else {
                            player.sendMessage("Invalid command. Use: .autofarm add <type> <skillId>");
                        }
                        break;
                    case "reset":
                        if (params.length > 1) {
                            resetSkills(player, params[1]);
                        } else {
                            player.sendMessage("Invalid command. Use: .autofarm reset <type>");
                        }
                        break;
                    default:
                        Functions.showFarm("autofarm/ui.htm", player);
                        break;
                }
            } else {
                Functions.showFarm("autofarm/ui.htm", player);
            }
            return true;
        }
        return false;
    }

    private void startFarming(Player player) {
        if (player.isAutoFarming()) {
            player.sendMessage("AutoFarming is already active.");
            return;
        }
        player.setAutoFarming(true); // Nastaví stav AutoFarming na aktivní
        AutoFarmingTask.startForPlayer(player); // Spuštění úlohy farmení
        player.sendMessage("AutoFarming started!");
    }

    private void stopFarming(Player player) {
        if (!player.isAutoFarming()) {
            player.sendMessage("AutoFarming is not active.");
            return;
        }
        player.setAutoFarming(false); // Zruší stav AutoFarming
        AutoFarmingTask.stopForPlayer(player); // Zastavení úlohy farmení
        player.sendMessage("AutoFarming stopped!");
    }

    private void addSkill(Player player, String skillType, int skillId) {
        List<Integer> skillList = getSkillList(player, skillType);
        if (skillList == null) {
            player.sendMessage("Invalid skill type.");
            return;
        }

        if (skillList.size() >= MAX_SKILLS) {
            player.sendMessage("You can only add up to " + MAX_SKILLS + " " + skillType + " skills.");
        } else if (!skillList.contains(skillId)) {
            skillList.add(skillId);
            player.getAutoFarmSettings().save(skillType, skillList); // Uložení do databáze
            player.sendMessage(skillType + " skill with ID " + skillId + " added.");
        } else {
            player.sendMessage("This skill is already in your " + skillType + " skills.");
        }
    }

    private void resetSkills(Player player, String skillType) {
        List<Integer> skillList = getSkillList(player, skillType);
        if (skillList == null) {
            player.sendMessage("Invalid skill type.");
            return;
        }

        skillList.clear();
        player.getAutoFarmSettings().save(skillType, skillList); // Uložení do databáze
        player.sendMessage(skillType + " skills have been reset.");
    }

    private List<Integer> getSkillList(Player player, String skillType) {
        return switch (skillType.toLowerCase()) {
            case "attack" -> player.getAutoFarmSettings().getAttackSkills();
            case "chance" -> player.getAutoFarmSettings().getChanceSkills();
            case "life" -> player.getAutoFarmSettings().getLifeSkills();
            case "self" -> player.getAutoFarmSettings().getSelfSkills();
            default -> null;
        };
    }
}
