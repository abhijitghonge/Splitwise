package com.ag.splitwise.commands;

import com.ag.splitwise.exceptions.CommandNotFoundException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandExecutor {

    private final Map<String, Command> commands= new HashMap<>();

    public boolean execute(String cmdLine) throws CommandNotFoundException{
        String[] args = cmdLine.split("\\s+");

        if(commands.containsKey(args[0])){
            return commands.get(args[0]).execute(Arrays.copyOfRange(args, 1, args.length));
        }else{
            throw new CommandNotFoundException("Command not found:["+args[0]+"]");
        }
    }

    public Command addCommand(Command command){

        return commands.put(command.getName(), command);
    }
}
