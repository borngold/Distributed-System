package abhinav.rajan.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import com.abhinav.rajan.RmiStarter;
import com.abhinav.rajan.Shares;

public class SharesEngine extends RmiStarter{

	public SharesEngine() {
		super(Shares.class);
	}

	@Override
	public void doCustomRmiHandling() {
		try {
			Shares engine = new Sharesimpl();
			Shares engineStub = (Shares)UnicastRemoteObject.exportObject(engine, 0);

            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(Shares.SERVICE_NAME, engineStub);
            System.out.println("Share operations server started");
        }
        catch(Exception e) {
            e.printStackTrace();
        }				
	}
	public static void main(String[] args) {
        new SharesEngine();
    }

}
