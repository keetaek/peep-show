package com.kakaw.peepshow.dao.dto;

/**
 * Created by keetaekhong on 12/25/14.
 */
public class DropInfoDTO {
    private HiddenItemDTO[] hidden;
    private DroppedItemDTO[] dropped;

    public HiddenItemDTO[] getHidden() {
        return hidden;
    }

    public void setHidden(HiddenItemDTO[] hidden) {
        this.hidden = hidden;
    }

    public DroppedItemDTO[] getDropped() {
        return dropped;
    }

    public void setDropped(DroppedItemDTO[] dropped) {
        this.dropped = dropped;
    }
}
