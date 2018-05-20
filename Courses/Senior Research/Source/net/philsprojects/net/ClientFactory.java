package net.philsprojects.net;

/**
 * A factory which creates a client handled by the given worker and which uses
 * the given pipeline.
 * 
 * @author Philip Diffenderfer
 *
 */
public interface ClientFactory
{
	
	/**
	 * Creates a new Client.
	 * 
	 * @param pipeline
	 * 		The pipeline the client should use.
	 * @param worker
	 * 		The worker handling the client.
	 * @param server
	 * 		The server which accepted the client, or null if the client was
	 * 		created by the pipeline.
	 * @return
	 * 		A newly instantiated client with the given parameters.
	 */
	public Client newClient(Pipeline pipeline, Worker worker, Server server);
	
}
