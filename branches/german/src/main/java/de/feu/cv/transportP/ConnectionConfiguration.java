package de.feu.cv.transportP;


/**
 * The data required to create a connection to a chat server.
 * @author Verena Kunz
 *
 */
public class ConnectionConfiguration {

	/**
	 * The servername or IP
	 */
	private String server;
	
	/**
	 * The tcp portnumber
	 */
	private int port = 5222;
	/**
	 * The username
	 */
	private String user;
	/**
	 * The password
	 */
	private String passwd;

	/**
	 * Getter of the property <tt>passwd</tt>.
	 * @return  Returns the passwd.
	 */
	public String getPasswd() {
		return passwd;
	}

	/**
	 * Getter of the property <tt>port</tt>.
	 * @return  Returns the port.
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Getter of the property <tt>server</tt>.
	 * @return  Returns the server.
	 */
	public String getServer() {
		return server;
	}

	/**
	 * Getter of the property <tt>user</tt>.
	 * @return  Returns the user.
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Setter of the property <tt>passwd</tt>.
	 * @param passwd  The passwd to set.
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	/**
	 * Setter of the property <tt>port</tt>.
	 * @param port  The port to set.
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Setter of the property <tt>server</tt>.
	 * @param server  The server to set.
	 */
	public void setServer(String server) {
		this.server = server;
	}

	/**
	 * Setter of the property <tt>user</tt>.
	 * @param user  The user to set.
	 */
	public void setUser(String user) {
		this.user = user;
	}

}
