package org.example;
import java.awt.*;
import java.awt.event.*;

import java.awt.*;
import java.awt.event.*;

public class RegistrationFormAWT extends Frame {

    Label title, nameLbl, emailLbl, passLbl, genderLbl, courseLbl, msgLbl;
    TextField nameTxt, emailTxt, passTxt;
    Checkbox male, female;
    CheckboxGroup genderGroup;
    Choice courseChoice;
    Button registerBtn, clearBtn;

    RegistrationFormAWT() {

        // Frame settings
        setTitle("Registration Form");
        setSize(450, 350);
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        /* ---------- TITLE PANEL ---------- */
        Panel titlePanel = new Panel();
        titlePanel.setBackground(new Color(70, 130, 180));

        title = new Label("User Registration Form", Label.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(Color.WHITE);

        titlePanel.add(title);
        add(titlePanel, BorderLayout.NORTH);

        /* ---------- FORM PANEL ---------- */
        Panel formPanel = new Panel();
        formPanel.setLayout(new GridLayout(6, 2, 10, 10));
        formPanel.setBackground(new Color(245, 245, 245));

        Font labelFont = new Font("Arial", Font.PLAIN, 14);

        nameLbl = new Label("Name:");
        nameLbl.setFont(labelFont);
        nameTxt = new TextField();

        emailLbl = new Label("Email:");
        emailLbl.setFont(labelFont);
        emailTxt = new TextField();

        passLbl = new Label("Password:");
        passLbl.setFont(labelFont);
        passTxt = new TextField();
        passTxt.setEchoChar('*');

        genderLbl = new Label("Gender:");
        genderLbl.setFont(labelFont);

        genderGroup = new CheckboxGroup();
        male = new Checkbox("Male", genderGroup, false);
        female = new Checkbox("Female", genderGroup, false);

        Panel genderPanel = new Panel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.add(male);
        genderPanel.add(female);

        courseLbl = new Label("Course:");
        courseLbl.setFont(labelFont);

        courseChoice = new Choice();
        courseChoice.add("Select");
        courseChoice.add("BCA");
        courseChoice.add("MCA");
        courseChoice.add("B.Tech");
        courseChoice.add("M.Tech");

        formPanel.add(nameLbl);
        formPanel.add(nameTxt);

        formPanel.add(emailLbl);
        formPanel.add(emailTxt);

        formPanel.add(passLbl);
        formPanel.add(passTxt);

        formPanel.add(genderLbl);
        formPanel.add(genderPanel);

        formPanel.add(courseLbl);
        formPanel.add(courseChoice);

        add(formPanel, BorderLayout.CENTER);

        /* ---------- BUTTON PANEL ---------- */
        Panel buttonPanel = new Panel();
        buttonPanel.setBackground(new Color(245, 245, 245));

        registerBtn = new Button("Register");
        clearBtn = new Button("Clear");

        registerBtn.setBackground(new Color(60, 179, 113));
        registerBtn.setForeground(Color.WHITE);

        clearBtn.setBackground(new Color(220, 20, 60));
        clearBtn.setForeground(Color.WHITE);

        buttonPanel.add(registerBtn);
        buttonPanel.add(clearBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        /* ---------- MESSAGE LABEL ---------- */
        msgLbl = new Label("", Label.CENTER);
        msgLbl.setForeground(Color.RED);
        add(msgLbl, BorderLayout.AFTER_LAST_LINE);

        /* ---------- WINDOW CLOSE ---------- */
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new RegistrationFormAWT();
    }
}
