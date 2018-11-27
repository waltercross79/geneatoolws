package com.waltercross.geneatoolcore;

public enum PersonRole {
    Mother(1), Father(2), Newborn(3), Deceased(4), Witness(5), Godparent(6), Bride(7), Groom(8);

    PersonRole(int i) {
        value = i;
    }

    private final int value;
    public int value() { return value; }
}
