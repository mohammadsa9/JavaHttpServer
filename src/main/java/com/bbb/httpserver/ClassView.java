package com.bbb.httpserver;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;

public class ClassView extends JFrame implements ActionListener {
    private int port;

    public ClassView() {
        this.getContentPane().setLayout(new GridLayout(0, 1));
        setMinimumSize(new Dimension(300, 400));
        setTitle("BBB Playback Viewer");

        JLabel tlabel = new JLabel("View your locally downloaded BigBlueButton playback", SwingConstants.CENTER);
        JLabel instruction = new JLabel("Click \"View Playback\" to launch your browser", SwingConstants.CENTER);
        JLabel exitInstruction = new JLabel("Click \"Exit\" when you're done", SwingConstants.CENTER);

        tlabel.setBorder(new EmptyBorder(10, 10, 0, 10));
        instruction.setBorder(new EmptyBorder(10, 10, 10, 10));
        exitInstruction.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton viewBtn = new JButton("View Playback");
        JButton exitBtn = new JButton("Exit");

        viewBtn.addActionListener(this);
        exitBtn.addActionListener(this);

        add(new JLabel(""));
        add(tlabel);
        add(instruction);
        add(exitInstruction);
        add(new JLabel(""));
        add(viewBtn);
        add(exitBtn);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();

        if (action.equals("View Playback")) {
            if (port == 0) {
                port = findFreePort();
            }

            startHttpServer(port);
            openBrowser("http://127.0.0.1:" + port + "/index.html");

        } else if (action.equals("Exit")) {
            System.exit(0);
        }
    }

    private int findFreePort() {
        for (int p = 49152; p <= 65535; p++) {
            try (ServerSocket socket = new ServerSocket(p)) {
                return socket.getLocalPort();
            } catch (IOException ignored) {}
        }
        throw new RuntimeException("No free port found in the dynamic range (49152–65535)");
    }

    private void startHttpServer(int port) {
        Server server = new Server(port);
        ResourceHandler handler = new ResourceHandler();
        handler.setDirAllowed(true);
        handler.setResourceBase(".");
        handler.setCacheControl("no-cache, no-store");
        server.setHandler(handler);

        new Thread(() -> {
            try {
                server.start();
                server.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void openBrowser(String url) {
        try {
            Desktop.getDesktop().browse(URI.create(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createAndShowGUI() {
        JFrame frame = new ClassView();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClassView::createAndShowGUI);
    }
}
