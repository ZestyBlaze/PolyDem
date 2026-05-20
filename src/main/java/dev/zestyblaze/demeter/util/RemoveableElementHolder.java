package dev.zestyblaze.demeter.util;

import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.elements.VirtualElement;

public class RemoveableElementHolder extends ElementHolder {
    private int liveTicks;
    private final int ticksUntilRemove;
    private final VirtualElement[] elements;

    public RemoveableElementHolder(int ticksUntilRemove, VirtualElement... element) {
        this.ticksUntilRemove = ticksUntilRemove;
        this.elements = element;
    }

    @Override
    public void tick() {
        super.tick();

        liveTicks++;

        if (liveTicks >= ticksUntilRemove) {
            for (VirtualElement element : elements) {
                removeElement(element);
            }
        }
    }
}
