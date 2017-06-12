package com.sva.common;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import org.apache.log4j.Logger;

public class TCPDesktopServer implements Runnable
{
    private static final Logger LOG = Logger.getLogger(TCPDesktopServer.class);

    private static DatagramSocket udpSocket;

    private static byte[] data = new byte[256];

    private static DatagramPacket udpPacket = new DatagramPacket(data,
            data.length);

    @Override
    public void run()
    {
        try
        {
            udpSocket = new DatagramSocket(48086);
            while (true)
            {
                
                udpSocket.receive(udpPacket);
                LOG.debug("socket is ok");
                

                if (null != udpPacket.getAddress())
                {
                    String codeString = new String(data, 0,
                            udpPacket.getLength());
                    codeString += udpPacket.getAddress();
                    LOG.debug(codeString);
                    
                    String ip = udpPacket.getAddress().toString()
                            .substring(1);
                    LOG.debug("socket IP:" + ip);
                    
                }
            }
        }
        catch (SocketException e)
        {
            LOG.error(e);
        }
        catch (IOException e)
        {
            LOG.error(e);
        }
    }

}
