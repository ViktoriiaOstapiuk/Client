package ua.edu.lpnu.dsct.client;

import ua.edu.lpnu.dsct.client.logging.LogFormatter;
import ua.edu.lpnu.dsct.client.logging.LoggerHandler;
import ua.edu.lpnu.dsct.client.ui.MainFrame;
import ua.edu.lpnu.dsct.client.utilities.LogSecurityManager;
import ua.edu.lpnu.dsct.common.abstraction.IRemote;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        String name = "ua.edu.lpnu.dsct.server.ServerEngine";
        int port = 2077;

        MainFrame mainFrame = new MainFrame();
        LogFormatter logFormatter = new LogFormatter();

        LoggerHandler handler = new LoggerHandler(mainFrame);
        handler.setFormatter(logFormatter);

        Logger logger = Logger.getGlobal();
        logger.addHandler(handler);

        mainFrame.setVisible(true);

        CommandParser parser = null;
        try {
            if(args.length == 2) {
                name = args[0];
                port = Integer.parseInt(args[1]);
            }

            Thread.sleep(500);
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new LogSecurityManager());
            }

            logger.info("Connecting to server [name = " + name + ", port = " + port + "]..." );
            Registry registry = LocateRegistry.getRegistry("localhost", port);
            IRemote engine = (IRemote) registry.lookup(name);

            RemoteRequestManager manager = new RemoteRequestManager(engine);
            parser = new CommandParser(manager);
        } catch (RemoteException | NotBoundException | InterruptedException e) {
            logger.severe("Could not connect to the server: " + e.getMessage());
            System.exit(1);
        }

        logger.info("Successfully connected to server.");
        logger.info("Here are some tips on how to use the program:" + parser.help());
        logger.info("Please, enter your commands.");
        mainFrame.setParser(parser);
    }
}
