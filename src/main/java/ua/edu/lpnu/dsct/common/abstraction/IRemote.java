package ua.edu.lpnu.dsct.common.abstraction;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemote extends Remote {
    <T> T executeTask(ITask<T> t) throws RemoteException;
}
