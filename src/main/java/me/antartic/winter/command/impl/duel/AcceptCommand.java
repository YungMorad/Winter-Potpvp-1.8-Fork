package me.antartic.winter.command.impl.duel;

import com.google.common.collect.ImmutableList;

import me.antartic.winter.PotPvPLang;
import me.antartic.winter.PotPvPRP;
import me.antartic.winter.command.PotPvPCommand;
import me.antartic.winter.match.duel.DuelHandler;
import me.antartic.winter.match.duel.DuelInvite;
import me.antartic.winter.match.duel.PartyDuelInvite;
import me.antartic.winter.match.duel.PlayerDuelInvite;
import me.antartic.winter.match.Match;
import me.antartic.winter.match.MatchHandler;
import me.antartic.winter.match.MatchTeam;
import me.antartic.winter.party.Party;
import me.antartic.winter.party.PartyHandler;
import me.antartic.winter.validation.PotPvPValidation;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import xyz.refinedev.command.annotation.Command;
import xyz.refinedev.command.annotation.Sender;

public class AcceptCommand implements PotPvPCommand {

    @Command(name = "", usage = "<target>", desc = "Duel accept command")
    public void accept(@Sender Player sender, Player target) {
        if (sender == target) {
            sender.sendMessage(ChatColor.RED + "You can't accept a duel from yourself!");
            return;
        }

        PartyHandler partyHandler = PotPvPRP.getInstance().getPartyHandler();
        DuelHandler duelHandler = PotPvPRP.getInstance().getDuelHandler();

        Party senderParty = partyHandler.getParty(sender);
        Party targetParty = partyHandler.getParty(target);

        if (senderParty != null && targetParty != null) {
            // party accepting from party (legal)
            PartyDuelInvite invite = duelHandler.findInvite(targetParty, senderParty);

            if (invite != null) {
                acceptParty(sender, senderParty, targetParty, invite);
            } else {
                // we grab the leader's name as the member targeted might not be the leader
                String leaderName = PotPvPRP.getInstance().getUuidCache().name(targetParty.getLeader());
                sender.sendMessage(ChatColor.RED + "Your party doesn't have a duel invite from " + leaderName + "'s party.");
            }
        } else if (senderParty == null && targetParty == null) {
            // player accepting from player (legal)
            PlayerDuelInvite invite = duelHandler.findInvite(target, sender);

            if (invite != null) {
                acceptPlayer(sender, target, invite);
            } else {
                sender.sendMessage(ChatColor.RED + "You don't have a duel invite from " + target.getName() + ".");
            }
        } else if (senderParty == null) {
            // player accepting from party (illegal)
            sender.sendMessage(ChatColor.RED + "You don't have a duel invite from " + target.getName() + ".");
        } else {
            // party accepting from player (illegal)
            sender.sendMessage(ChatColor.RED + "Your party doesn't have a duel invite from " + target.getName() + "'s party.");
        }
    }

    private void acceptParty(@Sender Player sender, Party senderParty, Party targetParty, DuelInvite<?> invite) {
        MatchHandler matchHandler = PotPvPRP.getInstance().getMatchHandler();
        DuelHandler duelHandler = PotPvPRP.getInstance().getDuelHandler();

        if (!senderParty.isLeader(sender.getUniqueId())) {
            sender.sendMessage(PotPvPLang.NOT_LEADER_OF_PARTY);
            return;
        }

        if (!PotPvPValidation.canAcceptDuel(senderParty, targetParty, sender)) {
            return;
        }

        Match match = matchHandler.startMatch(
                ImmutableList.of(new MatchTeam(senderParty.getMembers()), new MatchTeam(targetParty.getMembers())),
                invite.getKitType(),
                false,
                true // see Match#allowRematches
        );

        if (match != null) {
            // only remove invite if successful
            duelHandler.removeInvite(invite);
        } else {
            senderParty.message(PotPvPLang.ERROR_WHILE_STARTING_MATCH);
            targetParty.message(PotPvPLang.ERROR_WHILE_STARTING_MATCH);
        }
    }

    private void acceptPlayer(@Sender Player sender, Player target, DuelInvite<?> invite) {
        MatchHandler matchHandler = PotPvPRP.getInstance().getMatchHandler();
        DuelHandler duelHandler = PotPvPRP.getInstance().getDuelHandler();

        if (!PotPvPValidation.canAcceptDuel(sender, target)) {
            return;
        }

        Match match = matchHandler.startMatch(
                ImmutableList.of(new MatchTeam(sender.getUniqueId()), new MatchTeam(target.getUniqueId())),
                invite.getKitType(),
                false,
                true // see Match#allowRematches
        );

        if (match != null) {
            // only remove invite if successful
            duelHandler.removeInvite(invite);
        } else {
            sender.sendMessage(PotPvPLang.ERROR_WHILE_STARTING_MATCH);
            target.sendMessage(PotPvPLang.ERROR_WHILE_STARTING_MATCH);
        }
    }

    @Override
    public String getCommandName() {
        return "accept";
    }

    @Override
    public String[] getAliases() {
        return new String[]{};
    }
}