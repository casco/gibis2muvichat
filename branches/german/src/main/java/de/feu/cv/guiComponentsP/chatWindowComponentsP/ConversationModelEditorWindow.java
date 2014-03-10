package de.feu.cv.guiComponentsP.chatWindowComponentsP;

import com.sun.tools.javac.comp.Flow;
import de.feu.cv.applicationLogicP.Resources;
import de.feu.cv.applicationLogicP.chatRoomP.ChatRoom;
import de.feu.cv.applicationLogicP.conversationP.Conversation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by alejandrofernandez on 3/9/14.
 */
public class ConversationModelEditorWindow {

    private final JFrame frame;
    private final JTextArea conversationTextArea;
    ChatLiveWindow chatWindow;

    public ConversationModelEditorWindow(ChatLiveWindow chatWindow) {
        this.chatWindow = chatWindow;
        frame = new JFrame("Conversation editor");
        frame.getContentPane().setLayout(new BorderLayout());


        conversationTextArea = new JTextArea("");
        conversationTextArea.setPreferredSize(new Dimension(500, 400));

        frame.getContentPane().add(conversationTextArea, BorderLayout.CENTER);
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(this.getAcceptButton());
        buttonsPanel.add(this.getCancelButton());
        frame.add(buttonsPanel, BorderLayout.SOUTH);


    }

    public void open() {
        frame.pack();
        frame.setVisible(true);
        showConversationConfiguration();
    }

    private void showConversationConfiguration() {
        conversationTextArea.setText(chatWindow.getChatroom().getConversation().getConversationModel().getConfigurationString());
    }

    public JButton getCancelButton() {
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
            }
        });
        return cancelButton;
    }

    public JButton getAcceptButton() {
        JButton acceptButton = new JButton("Accept");
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tryToChangeConversationModel();
                frame.setVisible(false);
            }
        });
        return acceptButton;
    }

    private void tryToChangeConversationModel() {
        String configuration = conversationTextArea.getText();
        chatWindow.updateConversationModel(configuration);
        frame.setVisible(false);

    }
}
