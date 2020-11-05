import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import com.sun.net.httpserver.*;

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
import java.net.URI;

public class ClassView extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    public ClassView() {

        // set flow layout for the frame
        this.getContentPane().setLayout(new FlowLayout());
        setMinimumSize(new Dimension(300, 400));
        setTitle("BBB playback Viewer by DarkCode");
        setLayout(new GridLayout(0, 1));
        add(new JLabel(""));
        JLabel tlabel = new JLabel("Use this tool to view your localy downloaded playback of BBB",
                SwingConstants.CENTER);
        JLabel method1 = new JLabel(
                "Method 1 (Preferred): View playback using a java http server (Not compatible with chrome but works on any OS)",
                SwingConstants.LEFT);
        JLabel method2 = new JLabel(
                "Method 2 (Not Preferred): View playback using a node http server (Compatible with chrome but only works on Windows OS)",
                SwingConstants.LEFT);
        JLabel warning = new JLabel(
                "Warning: If you click on \"Method 2\" or \"Exit\", this program will close all running node processes which is not good",
                SwingConstants.LEFT);
        tlabel.setBorder(new EmptyBorder(0, 10, 0, 10));
        method1.setBorder(new EmptyBorder(10, 10, 10, 10));
        method2.setBorder(new EmptyBorder(10, 10, 10, 10));
        warning.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(tlabel);
        add(method1);
        add(method2);
        add(warning);
        add(new JLabel(""));
        JButton view1 = new JButton("View Playback - Method 1");
        JButton view2 = new JButton("View Playback - Method 2");
        JButton exit = new JButton("Exit");
        // set action listeners for buttons
        view1.addActionListener(this);
        view2.addActionListener(this);
        exit.addActionListener(this);

        // add buttons to the frame
        add(view1);
        add(view2);
        // add(new JLabel(""));
        add(exit);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();
        if (action.equals("View Playback - Method 1")) {
            System.out.println("Yes Button pressed!");
            try {
                String port = "8736";
                String dir = System.getProperty("user.dir");
                String directory = dir;

                System.out.println("Starting server...");
                Server server = new Server(Integer.parseInt(port), directory);
                server.startServer();
                String s = "http://127.0.0.1:" + port + "/index.html";
                Desktop desktop = Desktop.getDesktop();
                desktop.browse(URI.create(s));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (action.equals("View Playback - Method 2")) {
            System.out.println("No Button pressed!");
            String result = "";
            try {
                /*
                 * Not a safe idea to kill all node proccess but I don't have a better idea
                 * right now
                 */
                ProcessBuilder builder1 = new ProcessBuilder("cmd.exe", "/c", "taskkill /f /im node.exe");
                builder1.start();

                ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "npx http-server --port 8735");
                builder.start();
                String s = "http://localhost:" + 8735;
                Desktop desktop = Desktop.getDesktop();
                desktop.browse(URI.create(s));
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(result);
        } else if (action.equals("Exit")) {
            try {
                /*
                 * Not a safe idea to kill all node proccess but I don't have a better idea
                 * right now
                 */
                ProcessBuilder builder1 = new ProcessBuilder("cmd.exe", "/c", "taskkill /f /im node.exe");
                builder1.start();
            } catch (IOException e) {
                e.printStackTrace();

            }
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
