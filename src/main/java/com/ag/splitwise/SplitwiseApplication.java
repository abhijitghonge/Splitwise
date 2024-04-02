package com.ag.splitwise;

import com.ag.splitwise.commands.CommandExecutor;
import com.ag.splitwise.exceptions.CommandNotFoundException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class SplitwiseApplication {

	private static CommandExecutor commandExecutor = new CommandExecutor();
	public static void main(String[] args) throws CommandNotFoundException {
		SpringApplication.run(SplitwiseApplication.class, args);
		Scanner scanner =new Scanner(System.in);
		String cmdLine = scanner.nextLine();
		commandExecutor.execute(cmdLine);
	}

}
