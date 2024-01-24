package com.ag.Splitwise.commands;

import com.ag.Splitwise.exceptions.CommandNotFoundException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandExecutor {

    private Map<String, Command> commands= new HashMap<>();

    public boolean execute(String cmdLine) throws CommandNotFoundException{
        String[] args = cmdLine.split("\\s+");

        if(commands.containsKey(args[0])){
            return commands.get(args[0]).execute(Arrays.copyOfRange(args, 1, args.length));
        }else{
            throw new CommandNotFoundException("Command not found:["+args[0]+"]");
        }
    }
}
