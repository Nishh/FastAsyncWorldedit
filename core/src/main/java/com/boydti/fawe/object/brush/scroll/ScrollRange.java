package com.boydti.fawe.object.brush.scroll;

import com.boydti.fawe.util.MathMan;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.command.tool.BrushTool;
import com.sk89q.worldedit.entity.Player;

public class ScrollRange extends ScrollAction {
    public ScrollRange(BrushTool tool) {
        super(tool);
    }

    @Override
    public boolean increment(Player player, int amount) {
        int max = WorldEdit.getInstance().getConfiguration().maxBrushRadius;
        int newSize = MathMan.wrap(tool.getRange() + amount, (int) (tool.getSize() + 1), max);
        tool.setRange(newSize);
        return true;
    }
}
