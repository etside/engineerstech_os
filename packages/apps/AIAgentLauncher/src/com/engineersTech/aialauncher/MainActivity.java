package com.engineersTech.aialauncher;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.content.Intent;
import android.content.pm.PackageManager;

/**
 * engineersTech AI Agent Launcher
 * 
 * AI-First Home Screen replacing traditional app launcher.
 * User interacts via voice or text input to on-device AI agent.
 * 
 * @author engineersTech
 * @version 1.0
 */
public class MainActivity extends Activity {
    
    private LinearLayout chatContainer;
    private EditText userInput;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Hide system UI for immersive AI experience
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
        
        // Set up AI chat interface
        setupAIChatUI();
        
        // Request permissions for AI functions
        requestAIPermissions();
    }

    private void setupAIChatUI() {
        // Main container
        FrameLayout root = new FrameLayout(this);
        root.setLayoutParams(new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        ));
        
        // Chat messages container
        chatContainer = new LinearLayout(this);
        chatContainer.setLayoutParams(new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        ));
        chatContainer.setOrientation(LinearLayout.VERTICAL);
        
        // Header
        TextView header = new TextView(this);
        header.setText("engineersTech AI Agent OS");
        header.setTextSize(20);
        header.setPadding(16, 16, 16, 16);
        header.setLayoutParams(new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        chatContainer.addView(header);
        
        // Welcome message
        TextView welcome = new TextView(this);
        welcome.setText("Welcome to the AI-First OS.\nSpeak or type your request:");
        welcome.setTextSize(16);
        welcome.setPadding(16, 8, 16, 8);
        welcome.setLayoutParams(new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        chatContainer.addView(welcome);
        
        // Input container (bottom)
        LinearLayout inputContainer = new LinearLayout(this);
        inputContainer.setOrientation(LinearLayout.HORIZONTAL);
        inputContainer.setLayoutParams(new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        
        // Text input
        userInput = new EditText(this);
        userInput.setHint("Ask me anything...");
        userInput.setLayoutParams(new LinearLayout.LayoutParams(
            0,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            1.0f
        ));
        inputContainer.addView(userInput);
        
        // Send button
        sendButton = new Button(this);
        sendButton.setText("Send");
        sendButton.setLayoutParams(new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        sendButton.setOnClickListener(v -> handleUserInput());
        inputContainer.addView(sendButton);
        
        chatContainer.addView(inputContainer);
        root.addView(chatContainer);
        setContentView(root);
    }

    private void handleUserInput() {
        String input = userInput.getText().toString();
        if (!input.isEmpty()) {
            addChatMessage("You: " + input);
            userInput.setText("");
            // TODO: Send to AIManager service for processing
            addChatMessage("Agent: Processing...");
        }
    }

    private void addChatMessage(String message) {
        TextView msg = new TextView(this);
        msg.setText(message);
        msg.setPadding(8, 8, 8, 8);
        msg.setLayoutParams(new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        chatContainer.addView(msg);
    }

    private void requestAIPermissions() {
        // Request runtime permissions for AI operations
        String[] permissions = {
            "android.permission.RECORD_AUDIO",
            "android.permission.INTERNET",
            "android.permission.READ_CONTACTS"
        };
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String perm : permissions) {
                if (checkSelfPermission(perm) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{perm}, 1);
                }
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
    }

    @Override
    public void onBackPressed() {
        // Override back to stay in AI launcher
        moveTaskToBack(true);
    }
}
