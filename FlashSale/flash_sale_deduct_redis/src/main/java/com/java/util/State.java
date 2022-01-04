package com.java.util;

public enum State {
    NOT_START(0), START(1), END(2);

    private int val;
    State(int value) {
        val = value;
    }

    public int getValue(){
        return val;
    }
}
