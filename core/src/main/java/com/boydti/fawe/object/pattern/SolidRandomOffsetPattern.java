package com.boydti.fawe.object.pattern;

import com.boydti.fawe.FaweCache;
import com.boydti.fawe.object.PseudoRandom;
import com.sk89q.worldedit.MutableBlockVector;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.blocks.BlockType;
import com.sk89q.worldedit.function.pattern.AbstractPattern;
import com.sk89q.worldedit.function.pattern.Pattern;

public class SolidRandomOffsetPattern extends AbstractPattern {
    private final PseudoRandom r  = new PseudoRandom();
    private final int dx, dy, dz, dx2, dy2, dz2;
    private final Pattern pattern;
    private final MutableBlockVector mutable = new MutableBlockVector();
    boolean[] solid;

    public SolidRandomOffsetPattern(Pattern pattern, int dx, int dy, int dz) {
        this.pattern = pattern;
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
        this.dx2 = dx * 2 + 1;
        this.dy2 = dy * 2 + 1;
        this.dz2 = dz * 2 + 1;
        solid = new boolean[Character.MAX_VALUE + 1];
        for (int id = 0; id < 4096; id++) {
            for (int data = 0; data < 16; data++) {
                if (!BlockType.canPassThrough(id, data)) {
                    solid[FaweCache.getCombined(id, data)] = true;
                }
            }
        }
    }

    @Override
    public BaseBlock apply(Vector position) {
        mutable.mutX((position.getX() + r.nextInt(dx2) - dx));
        mutable.mutY((position.getY() + r.nextInt(dy2) - dy));
        mutable.mutZ((position.getZ() + r.nextInt(dz2) - dz));
        BaseBlock block = pattern.apply(mutable);
        if (solid[FaweCache.getCombined(block)]) {
            return block;
        } else {
            return pattern.apply(position);
        }
    }
}