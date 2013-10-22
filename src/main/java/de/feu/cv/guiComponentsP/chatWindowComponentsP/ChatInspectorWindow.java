package de.feu.cv.guiComponentsP.chatWindowComponentsP;


import de.feu.cv.applicationLogicP.conversationP.ChatListModel;
import de.feu.cv.applicationLogicP.conversationP.Conversation;
import de.feu.cv.applicationLogicP.conversationP.ThreadedMessage;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class ChatInspectorWindow {

    private int inspectorIndex = -1;

    Conversation conversation;
    private final JFrame frame;
    private final JTextArea inspectorTextLabel;
    private final JButton nextButton;
    private final JButton previousButton;
    private final JLabel indexLabel;
    private final JLabel sizeLabel;


    public ChatInspectorWindow(Conversation conversation) {

        this.conversation = conversation;

        //Create and set up the window.
        frame = new JFrame("Conversation Inspector");
        frame.getContentPane().setLayout(new BorderLayout());


        inspectorTextLabel = new JTextArea("Wait until a message arrives");
        inspectorTextLabel.setPreferredSize(new Dimension(500, 400));


        indexLabel = new JLabel("0");
        indexLabel.setPreferredSize(new Dimension(50, 30));

        sizeLabel = new JLabel("de 0");

        nextButton = new JButton("Next");
        nextButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                next();
            }
        });
        previousButton = new JButton("Previous");
        previousButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previous();
            }
        });


        JPanel panelBotones = new JPanel(new FlowLayout());
        frame.getContentPane().add(inspectorTextLabel, BorderLayout.CENTER);
        panelBotones.add(indexLabel);
        panelBotones.add(sizeLabel);
        panelBotones.add(previousButton);
        panelBotones.add(nextButton);
        frame.getContentPane().add(panelBotones, BorderLayout.SOUTH);


    }

    private void previous() {

        ChatListModel listModel = conversation.getBackgroundListModel();

        if (0 <= inspectorIndex && inspectorIndex < listModel.getSize()) {
            inspectorIndex = Math.max(inspectorIndex - 1, 0);
            showInspectionText(listModel);

        }


        //To change body of created methods use File | Settings | File Templates.
    }

    private void showInspectionText(ChatListModel listModel) {

        ThreadedMessage message = (ThreadedMessage) listModel.get(inspectorIndex);
        String text = "From: " + message.getOriginalJabberMessaje().getFrom() + "\r\n" +
               "To: " + message.getOriginalJabberMessaje().getTo() + "\r\n" +
                message.getText() + "\r\n";
        if (message.getOriginalJabberMessaje() != null) {
            java.util.Iterator<String> it = message.getOriginalJabberMessaje().getPropertyNames();
            while (it.hasNext()) {
                String name = it.next();
                String value = (String) message.getOriginalJabberMessaje().getProperty(name);
                text = text + "\r\n" + name + ": " + value;
            }
        }
        inspectorTextLabel.setText(text);
        indexLabel.setText("" + (inspectorIndex + 1));
        sizeLabel.setText(" de " + listModel.getSize());
    }

    private void next() {
        ChatListModel listModel = conversation.getBackgroundListModel();

        if (inspectorIndex < listModel.getSize()) {
            inspectorIndex = Math.min(inspectorIndex + 1, listModel.getSize() - 1);
            showInspectionText(listModel);

        }
    }

    public void open() {
        frame.pack();
        frame.setVisible(true);
    }

}

