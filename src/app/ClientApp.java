package app;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import utilitaire.Utilitaire;

public class ClientApp {
	private Socket socket;
	private Scanner scanner;
	private BufferedReader in;
	private PrintWriter out;

	public ClientApp(int port, String ip) throws IOException {
		scanner = new Scanner(System.in);
		socket = new Socket(ip, port);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
	}

	protected void connect() {
		try {
			while (true) {
				String serverMessage = Utilitaire.decrypt(in.readLine());
				System.out.println(serverMessage);
				out.println(Utilitaire.encrypt(scanner.nextLine()));
			}
		} catch (IOException e) {
			System.out.println("Connexion terminée");
		}

	}

	protected void finalize() {
		try {
			in.close();
			out.close();
			scanner.close();
		} catch (IOException e) {

		}
	}

	public static void main(String[] args) {
		int port = Integer.parseInt(args[0]);
		String ip = args[1];
		try {
			ClientApp app = new ClientApp(port, ip);
			app.connect();
		} catch (IOException e) {
			System.out.println("Impossible d'atteindre le serveur, l'offrande sacrificielle n'est pas assez importante");
		}
	}

}
