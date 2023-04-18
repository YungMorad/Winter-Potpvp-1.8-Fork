package me.antartic.winter.party.menu.otherparties;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.antartic.winter.PotPvPRP;
import me.antartic.winter.lobby.LobbyHandler;
import me.antartic.winter.party.Party;
import me.antartic.winter.party.PartyHandler;
import me.antartic.winter.profile.setting.Setting;
import me.antartic.winter.profile.setting.SettingHandler;
import me.antartic.winter.util.menu.Button;
import me.antartic.winter.util.menu.pagination.PaginatedMenu;

public final class OtherPartiesMenu extends PaginatedMenu {

    public OtherPartiesMenu() {
        setPlaceholder(true);
        setAutoUpdate(true);
    }

    @Override
    public String getPrePaginatedTitle(Player player) {
        return "Other parties";
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        SettingHandler settingHandler = PotPvPRP.getInstance().getSettingHandler();
        PartyHandler partyHandler = PotPvPRP.getInstance().getPartyHandler();
        LobbyHandler lobbyHandler = PotPvPRP.getInstance().getLobbyHandler();

        Map<Integer, Button> buttons = new HashMap<>();
        List<Party> parties = new ArrayList<>(partyHandler.getParties());
        int index = 0;

        parties.sort(Comparator.comparing(p -> p.getMembers().size()));

        for (Party party : parties) {
            if (party.isMember(player.getUniqueId())) {
                continue;
            }

            if (!lobbyHandler.isInLobby(Bukkit.getPlayer(party.getLeader()))) {
                continue;
            }

            if (!settingHandler.getSetting(Bukkit.getPlayer(party.getLeader()), Setting.RECEIVE_DUELS)) {
                continue;
            }

            /* if (PotPvPRP.getInstance().getTournamentHandler().isInTournament(party)) {
                continue;
            } */

            buttons.put(index++, new OtherPartyButton(party));
        }

        return buttons;
    }

    // we lock the size of this inventory at full, otherwise we'll have
    // issues if it 'grows' into the next line while it's open (say we open
    // the menu with 8 entries, then it grows to 11 [and onto the second row]
    // - this breaks things)
    @Override
    public int size(Player player) {
        return 9 * 6;
    }

    @Override
    public int getMaxItemsPerPage(Player player) {
        return 9 * 5; // top row is dedicated to switching
    }
}