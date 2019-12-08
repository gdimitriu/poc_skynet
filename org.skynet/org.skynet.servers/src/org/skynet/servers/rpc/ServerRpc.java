/*
 Copyright (c) 2017 Gabriel Dimitriu All rights reserved.
 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.

 This file is part of skynet project.

 skynet is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 skynet is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with skynet.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.skynet.servers.rpc;

import java.io.IOException;
import java.net.ServerSocket;

import org.skynet.servers.interfaces.IServer;
import org.skynet.servers.interfaces.IServerConstants;

public class ServerRpc extends Thread implements IServer {

	private int rpcPort;
	private ServerSocket rpcSocket;

	public ServerRpc(final int rpcPort) {
		this.rpcPort = rpcPort;
	}
	
	public ServerRpc() {
		this.rpcPort = 0;
	}
	
	@Override
	public void startServer() throws IOException {
		stopServer();
		new Thread(this).start();
	}
	
	@Override
	public void stopServer() throws IOException {
		if (rpcSocket != null && !rpcSocket.isClosed()) {
			rpcSocket.close();
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		stopServer();
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
			try {
				rpcSocket = new ServerSocket(rpcPort);
				while(!rpcSocket.isClosed()) {
					new Thread(new RpcProcess(rpcSocket.accept())).start();
					Thread.sleep(200);
				}
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
	}

	@Override
	public void setPort(final int port) {
		this.rpcPort = port;
	}

	@Override
	public int getPort() {
		return this.rpcPort;
	}

	@Override
	public String getServerKey() {
		return IServerConstants.RPC_SERVER_KEY;
	}

}
