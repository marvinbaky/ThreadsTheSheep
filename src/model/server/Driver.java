package model.server;

public class Driver {
	public static void main(String[] args) {
		Server server = Server.getServerInstance();
		Farm farm = new Farm();
		//server.acceptConnection(farm);
	}
}
