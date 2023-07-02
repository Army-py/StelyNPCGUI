package fr.army.stelynpcgui.npc;

import net.citizensnpcs.api.event.NPCClickEvent;

public abstract class NPC {

    private final String name;

    public NPC(String name) {
        this.name = name;
    }

    public abstract void onClick(NPCClickEvent clickEvent);

    public String getName() {
        return name;
    }
}
