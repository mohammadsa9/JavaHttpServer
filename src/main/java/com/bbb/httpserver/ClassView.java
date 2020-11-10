package com.bbb.httpserver;

import java.io.*;
import java.net.URI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Desktop;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;

public class ClassView extends JFrame implements ActionListener {

    public ClassView() {
        // set flow layout for the frame
        this.getContentPane().setLayout(new FlowLayout());
        setMinimumSize(new Dimension(300, 400));
        setTitle("BBB playback Viewer by DarkCode");
        setLayout(new GridLayout(0, 1));
        add(new JLabel(""));
        JLabel tlabel = new JLabel(
                "Use this tool to view your locally downloaded playback of BigBlueButton online session",
                SwingConstants.CENTER);
        JLabel method1 = new JLabel(
                "After clicking on \"View Playback\" you can open this link in any browser to watch the playback",
                SwingConstants.CENTER);
        JLabel method2 = new JLabel("http://127.0.0.1:9081", SwingConstants.CENTER);
        JLabel warning = new JLabel(
                "Warning: If you click on \"Method 2\" or \"Exit\", this program will close all running node processes which is not good",
                SwingConstants.CENTER);
        tlabel.setBorder(new EmptyBorder(0, 10, 0, 10));
        method1.setBorder(new EmptyBorder(10, 10, 10, 10));
        method2.setBorder(new EmptyBorder(10, 10, 10, 10));
        warning.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(tlabel);
        add(method1);
        add(method2);
        // add(warning);
        add(new JLabel("Click on \"Exit\" when you are done", SwingConstants.CENTER));
        add(new JLabel(""));
        JButton view1 = new JButton("View Playback");
        JButton view2 = new JButton("View Playback - Method 2");
        JButton exit = new JButton("Exit");
        // set action listeners for buttons
        view1.addActionListener(this);
        view2.addActionListener(this);
        exit.addActionListener(this);

        // add buttons to the frame
        add(view1);
        // add(view2);
        // add(new JLabel(""));
        add(exit);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();
        if (action.equals("View Playback")) {
            System.out.println("Yes Button pressed!");

            String port = "9081";
            String dir = System.getProperty("user.dir");

            Server server = new Server(Integer.parseInt(port));
            ResourceHandler resourceHandler = new ResourceHandler();
            resourceHandler.setDirAllowed(true);
            resourceHandler.setResourceBase(".");
            String cacheControl = "no-cache, no-store";
            resourceHandler.setCacheControl(cacheControl);
            server.setHandler(resourceHandler);
            Runnable r = new Runnable() {
                public void run() {
                    try {
                        server.start();
                        server.join();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            };
            new Thread(r).start();

            String s = "http://127.0.0.1:" + port + "/index.html";
            try {
                Desktop desktop = Desktop.getDesktop();
                desktop.browse(URI.create(s));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if (action.equals("Exit")) {
            System.exit(0);
        }
    }

    private static void createAndShowGUI() {
        // Create and set up the window.
        JFrame frame = new ClassView();
        // Display the window.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
