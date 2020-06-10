package ua.edu.lpnu.dsct.client;

import ua.edu.lpnu.dsct.client.utilities.EnumManager;
import ua.edu.lpnu.dsct.common.implementation.NumberType;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CommandParser {
    enum Command {
        PING,
        ECHO,
        GENERATE,
        PROCESS,
        HELP;
    }

    private static class Patterns {
        static final Pattern command = Pattern.compile("^([a-zA-z]+) ?.*");
        static final Pattern ping = Pattern.compile("(?i)^ping\\s*$");
        static final Pattern echo = Pattern.compile("(?i)^echo\\s+(.*)");
        static final Pattern generate = Pattern.compile("(?i)^generate\\s+\"(.+)\"\\s+(\\d+)\\s+([a-z]+)\\s+(-?\\d+)\\s+(-?\\d+)$");
        static final Pattern process = Pattern.compile("(?i)^process\\s+\"(.+)\"\\s\"(.+)\"$");
        static final Pattern plainHelp = Pattern.compile("(?i)^help\\s*$");
        static final Pattern help = Pattern.compile("(?i)^help\\s+([a-z]+)\\s*$");
    }

    private final RemoteRequestManager manager;
    private final Logger logger;

    public CommandParser(RemoteRequestManager manager) {
        this.manager = manager;
        this.logger = Logger.getGlobal();
    }

    public void callSafe(String execString) {
        try{
            call(execString);
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }

    public void call(String execString) throws IOException {
        Matcher commandMatcher = Patterns.command.matcher(execString);

        if(!commandMatcher.find()) {
            throw new IllegalArgumentException("Unknown command pattern");
        }

        String commandString = commandMatcher.group(1);
        Command command = (Command) EnumManager.parse(Command.class, commandString);

        switch (command) {
            case PING:
                if(!Patterns.ping.matcher(execString).find()) {
                    throw new IllegalArgumentException("No additional characters are allowed in PING command");
                }
                this.manager.ping();
                break;
            case ECHO:
                Matcher echoMatcher = Patterns.echo.matcher(execString);
                if(!echoMatcher.find()) {
                    throw new IllegalArgumentException("No text to send to server");
                }
                String text = echoMatcher.group(1);
                this.manager.echo(text);
                break;
            case GENERATE:
                Matcher generateMatcher = Patterns.generate.matcher(execString);
                if(!generateMatcher.find()) {
                    throw new IllegalArgumentException("Incorrect GENERATE command format. " +
                            "Use 'help generate' to get instructions.");
                }
                String filePath = generateMatcher.group(1);
                long count = Long.parseLong(generateMatcher.group(2));
                NumberType type = (NumberType) EnumManager.parse(NumberType.class,  generateMatcher.group(3));
                long min = Long.parseLong(generateMatcher.group(4));
                long max = Long.parseLong(generateMatcher.group(5));

                this.manager.generate(filePath, count, type, min, max);
                break;
            case PROCESS:
                Matcher processMatcher = Patterns.process.matcher(execString);
                if(!processMatcher.find()) {
                    throw new IllegalArgumentException("Incorrect PROCESS command format. " +
                            "Use 'help process' to get instructions.");
                }

                String inputFilePath = processMatcher.group(1);
                String outputFilePath = processMatcher.group(2);

                this.manager.sort(inputFilePath, outputFilePath);
                break;
            case HELP:
                Matcher plainHelpMatcher = Patterns.plainHelp.matcher(execString);
                StringBuilder helpStringBuilder = new StringBuilder("Executing help command.");
                if(plainHelpMatcher.find()) {
                    helpStringBuilder.append(this.help());
                } else {
                    Matcher helpMatcher = Patterns.help.matcher(execString);
                    if(!helpMatcher.find()) {
                        throw new IllegalArgumentException("Incorrect HELP command format");
                    }

                    String argString = helpMatcher.group(1);
                    Command argCommand = (Command) EnumManager.parse(Command.class, argString);
                    helpStringBuilder.append(this.help(argCommand));
                }
                logger.info(helpStringBuilder.toString());
        }
    }

    public String help() {
        StringBuilder builder = new StringBuilder();
        for (Command command : Command.values()) {
            builder.append(help(command));
        }
        return builder.toString();
    }

    public String help(Command command) {
        switch (command) {
            case PING:
                return "\n----------> PING <---------- \n" +
                        "Sends a blank message to the server to verify the connection.\n" ;
            case ECHO:
                return "\n----------> ECHO <---------- \n" +
                        "Sends the selected text to the server and returns it.\n" ;
            case GENERATE:
                return "\n----------> GENERATE <---------- \n" +
                        "Sends a request to the server to generate numbers (integers or decimals) and saves the generated numbers to a file.\n" ;
            case PROCESS:
                return "\n----------> PROCESS <---------- \n" +
                        "Sends a blank message to the server to verify the connection.\n" ;
            case HELP:
                return "\n----------> HELP <----------\n" +
                        "Print out helpful message on how to use the program.\n" ;
            default:
                return "";
        }
    }
}

