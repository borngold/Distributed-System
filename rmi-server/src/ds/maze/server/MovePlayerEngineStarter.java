package ds.maze.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import com.ds.maze.ChangeCoordinates;
import com.ds.maze.RmiStarter;

public class MovePlayerEngineStarter extends RmiStarter {

	public MovePlayerEngineStarter() {
		super(ChangeCoordinates.class);
	}

	@Override
	public void doCustomRmiHandling() {
		try {
			ChangeCoordinates engine = new MovePlayerImplement();
			ChangeCoordinates engineStub = (ChangeCoordinates)UnicastRemoteObject.exportObject(engine, 0);

            Registry registry = LocateRegistry.createRegistry(9000);
            registry.rebind(ChangeCoordinates.SERVICE_NAME, engineStub);
            System.out.println("Move player server started");
        }
        catch(Exception e) {
            e.printStackTrace();
        }		
	}
	public static void main(String[] args) {
        new MovePlayerEngineStarter();
    }
	}
