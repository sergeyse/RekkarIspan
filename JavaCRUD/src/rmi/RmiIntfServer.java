package rmi;

import java.rmi.Remote;

public interface RmiIntfServer extends Remote {
	public void RunUpdate() throws RuntimeException;

}
