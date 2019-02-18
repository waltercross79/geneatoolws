package com.waltercross.geneatoolcore;

public enum PersonRole {
    Mother(1), Father(2), Newborn(3), Deceased(4), Witness(5), 
    Godparent(6), Bride(7), Groom(8), Parent(9);

    PersonRole(int i) {
        value = i;
    }

    private final int value;
    public int value() { return value; }
    
    public static PersonRole fromInt(int i) {
        switch (i) {
            case 1:
                return PersonRole.Mother;
            case 2:
                return PersonRole.Father;
            case 3:
                return PersonRole.Newborn;
            case 4:
                return PersonRole.Deceased;
            case 5:
                return PersonRole.Witness;
            case 6:
                return PersonRole.Godparent;
            case 7:
                return PersonRole.Bride;
            case 8:
                return PersonRole.Groom;
            case 9:
                return PersonRole.Parent;
            default:
                throw new RuntimeException("Unknown person role.");
        }
    }
}
