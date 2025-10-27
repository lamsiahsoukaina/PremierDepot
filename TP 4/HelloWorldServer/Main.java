package HelloWorldServer;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.*;
import org.omg.PortableServer.*;
import HelloWorldApp.*;

public class Main {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            HelloServant helloservant = new HelloServant();
            helloservant.setOrb(orb);
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(helloservant);
            Hello href = HelloHelper.narrow(ref);

            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            String name = "Hello";
            NameComponent path[] = ncRef.to_name(name);
            ncRef.rebind(path, href);

            System.out.println("HelloWorldServer ready and waiting ...");
            for(;;){
                orb.run();
            }
        }
        catch(Exception e) {
            System.out.println("Test in catch err");
            System.err.println("Error : " + e);
            e.printStackTrace(System.out);
        }
    }

}
