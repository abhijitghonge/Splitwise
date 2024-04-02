package com.ag.splitwise.commands;

public interface Command {
    public Boolean execute(String[] input);

    String getName();
}
