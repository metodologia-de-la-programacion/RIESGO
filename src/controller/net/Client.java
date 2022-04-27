package controller.net;

import controller.controllers.net.ClientController;
import model.Player;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class Client implements Runnable{

    Thread thread;
    DatagramSocket sock;

    volatile boolean running = false;
    volatile boolean sended = false;

    ClientController controller;

    public Client(ClientController controller){
        this.controller = controller;
        try {
            sock = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public void close(){
        running = false;
        sock.close();
        sock.disconnect();
    }

    public void connect(String ip){

        NetPackages.ClientInfo ci = new NetPackages.ClientInfo();
        ci.name = controller.getPlayerConfiguration().getName();
        DatagramPacket pack = new DatagramPacket(new byte[0xfff], 0xfff);
        pack.setData(ci.getBytes());

        try {
            sock.disconnect();
            sock.connect(new InetSocketAddress(ip, Server.PORT));
            sock.send(pack);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        sended = true;
    }

    public void send(){
        DatagramPacket dp = new DatagramPacket(new byte[0xfff], 0xfff);
        dp.setData(controller.getClientUpdate().getBytes());
        try {
            sock.send(dp);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // TODO decidir si habra acciones multiples
        controller.getClientUpdate().getActions().clear();
    }

    public void listen(){

        DatagramPacket pack = new DatagramPacket(new byte[0xfff], 0xfff);
        Object rec = new Object();

        try {

            sock.receive(pack);
            rec = NetPackages.Package.bytesToObject(pack.getData());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(rec instanceof Board){
            controller.setServerBoard((Board) rec);
            //controller.actionPerformed(controller.getServerBoard().getActions().values().iterator().next());
        }
    }

    @Override
    public void run() {
        while (running){
            listen();
        }
    }

}
