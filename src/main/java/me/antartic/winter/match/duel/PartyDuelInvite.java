package me.antartic.winter.match.duel;

import me.antartic.winter.kit.kittype.KitType;
import me.antartic.winter.party.Party;

public final class PartyDuelInvite extends DuelInvite<Party> {

    public PartyDuelInvite(Party sender, Party target, KitType kitTypes) {
        super(sender, target, kitTypes);
    }

}