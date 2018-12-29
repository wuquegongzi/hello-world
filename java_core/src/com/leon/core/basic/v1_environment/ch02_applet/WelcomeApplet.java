package com.leon.core.basic.v1_environment.ch02_applet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

public class WelcomeApplet extends JApplet {

    public void init(){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                setLayout(new BorderLayout());

                JLabel jLabel = new JLabel(getParameter("greeting"),SwingConstants.CENTER);
                jLabel.setFont(new Font("Serif",Font.BOLD,18));
                add(jLabel,BorderLayout.CENTER);

                JPanel panel = new JPanel();

                JButton cayButton = new JButton("bing");
                cayButton.addActionListener(makeAction("http://www.bing.com"));
                panel.add(cayButton);

                JButton garyButton = new JButton("Leon email");
                garyButton.addActionListener(makeAction("mailto:swchenminglei@163.com"));
                panel.add(garyButton);

                add(panel,BorderLayout.SOUTH);
            }


        });
    }

    private ActionListener makeAction(final String urlString) {

        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    getAppletContext().showDocument(new URL(urlString));
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }
            }
        };
    }

}
