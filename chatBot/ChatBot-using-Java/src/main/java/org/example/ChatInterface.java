package org.example;

import org.json.JSONException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class ChatInterface extends JFrame implements ActionListener {
    private final JTextField messageField;
    private final JTextArea chatArea;
    private final JButton sendButton;
    private final Chatbot chatbot;

    public ChatInterface(Chatbot chatbot) {
        super("Java Chatbot 🤖");

        this.chatbot = chatbot;

        // --- UI Design ---
        setLayout(new BorderLayout(10, 10));
        chatArea = new JTextArea(18, 40);
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 14));
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        messageField = new JTextField();
        messageField.setFont(new Font("Arial", Font.PLAIN, 14));
        sendButton = new JButton("Send");
        sendButton.setFont(new Font("Arial", Font.BOLD, 14));
        sendButton.setBackground(new Color(0x4CAF50));
        sendButton.setForeground(Color.WHITE);

        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        // --- Event Listeners ---
        sendButton.addActionListener(this);
        messageField.addActionListener(this);

        // --- Window Setup ---
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 450);
        setLocationRelativeTo(null);
        setVisible(true);

        chatArea.append("Chatbot: Hello! Type something to start chatting 😊\n");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String message = messageField.getText().trim();
        if (message.isEmpty()) return;

        chatArea.append("You: " + message + "\n");
        messageField.setText("");

        String response = chatbot.getResponse(message);
        chatArea.append("Chatbot: " + response + "\n\n");
    }

    public static void main(String[] args) throws IOException, JSONException {
     Chatbot chatbot = new Chatbot("ChatBot-using-Java/src/main/java/intents.json");
 new ChatInterface(chatbot);

    }
}
