package me.antartic.winter.party.event;

import me.antartic.winter.party.Party;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import lombok.Getter;

/**
 * Called when a {@link Party} is created.
 * @see me.antartic.winter.party.command.PartyCreateCommand
 * @see me.antartic.winter.party.PartyHandler#getOrCreateParty(Player)
 */
public final class PartyCreateEvent extends PartyEvent {

    @Getter private static HandlerList handlerList = new HandlerList();

    public PartyCreateEvent(Party party) {
        super(party);
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}