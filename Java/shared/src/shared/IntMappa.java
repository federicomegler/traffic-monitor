package shared;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Set;

public interface IntMappa extends Remote{

	public Set<String> generaLinee() throws RemoteException, SQLException;	
}
