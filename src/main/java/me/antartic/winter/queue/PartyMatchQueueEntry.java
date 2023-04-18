package me.antartic.winter.queue;

import com.google.common.base.Preconditions;

import me.antartic.winter.party.Party;

import java.util.Set;
import java.util.UUID;

import lombok.Getter;

/**
 * Represents a {@link me.antartic.winter.party.Party} waiting
 * in a {@link MatchQueue}
 */
public final class PartyMatchQueueEntry extends MatchQueueEntry {

    @Getter private final Party party;

    PartyMatchQueueEntry(MatchQueue queue, Party party) {
        super(queue);

        this.party = Preconditions.checkNotNull(party, "party");
    }

    @Override
    public Set<UUID> getMembers() {
        return party.getMembers();
    }

}