package com.sourav.verthome;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TermConditions extends AppCompatActivity {
    private CheckBox agreeCheckBox;
    private Button acceptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#6c5dd2"));
        setContentView(R.layout.activity_term_conditions);
        TextView termsTextView = findViewById(R.id.termsTextView);
        agreeCheckBox = findViewById(R.id.agreeCheckBox);
        acceptButton = findViewById(R.id.acceptButton);
        termsTextView.setText(getTermsAndConditionsText());

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (agreeCheckBox.isChecked()) {
                    Intent intent = new Intent(TermConditions.this, RealTime.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(TermConditions.this, "You must agree to the terms to continue.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getTermsAndConditionsText() {
        return "Welcome to Verthome! By using this app, you agree to the following terms...\n\n" +
                "1. Introduction\n\n" +
                "Welcome to Verthome (\"the App\", \"we\", \"our\", \"us\"). The following terms and conditions\n" +
                " (the\"Terms\") govern your access to and use of the Verthome mobile application, including any\n" +
                "content, functionality, and services offered through the app (collectively, the \"Services\"). By\n" +
                "downloading, installing, or using the App, you agree to be bound by these Terms. If you do not\n" +
                "agree to these Terms, do not use the App.\n\n" +
                "2. User Eligibility\n\n" +
                "You must be at least 18 years old or have the legal capacity to enter into a binding agreement in\n" +
                "your jurisdiction to use the App. By using the App, you represent and warrant that you meet\n" +
                "these eligibility requirements.\n\n" +
                "3. Account Registration and Security\n\n" +
                "To use certain features of the App, you may be required to create an account. You agree to\n" +
                "provide accurate, current, and complete information during the registration process and to\n" +
                "update it as necessary. You are responsible for maintaining the confidentiality of your account\n" +
                "information, including your username and password. You agree to notify us immediately if you\n" +
                "become aware of any unauthorized access or use of your account.\n\n" +
                "4. Data Privacy and Protection\n\n" +
                "We value your privacy. We collect, use, and protect your personal data in accordance with our\n" +
                "Privacy Policy. By using the App, you consent to the collection, use, and storage of your data as\n" +
                "outlined in the Privacy Policy. You are responsible for ensuring the accuracy and legality of any\n" +
                "data you provide through the App.\n\n" +
                "5. Use of the App\n\n" +
                "5.1. License\n\n" +
                "We grant you a limited, non-exclusive, non-transferable license to use the App for personal, noncommercial use, subject to these Terms. You may not:\n" +
                "Modify, distribute, or create derivative works based on the App.\n" +
                "Reverse-engineer, decompile, or disassemble the App.\n" +
                "Use the App for any unlawful purpose.\n\n" +
                "5.2. Restrictions\n\n" +
                "You agree not to use the App in any manner that could damage, disable, overburden, or impair\n" +
                "the functioning of the App or interfere with any other party’s use of the App. You agree not to\n" +
                "engage in any conduct that could harm the reputation or security of the App.\n" +
                "6. IoT Device Integration\n\n" +
                "The App is designed to interact with IoT devices for home automation and security purposes. You\n" +
                "are responsible for the proper installation, configuration, and maintenance of the IoT devices\n" +
                "connected to the App. You acknowledge that improper installation or use of these devices may\n" +
                "result in malfunctions or security risks.\n" +
                "We are not liable for any damage, loss, or injury arising from the use of the IoT devices\n" +
                "connected to the App.\n\n" +
                "7. Notifications and Alerts\n\n" +
                "The App may send notifications or alerts to your device, including security alerts, system\n" +
                "updates, and activity logs. You have the ability to manage these notifications within the App’s\n" +
                "settings. You agree to receive such notifications unless you opt-out.\n\n" +
                "8. Updates and Modifications\n\n" +
                "We may periodically update or modify the App to improve performance, fix bugs, or enhance\n" +
                "functionality. You agree to allow automatic updates, or you may manually update the App from\n" +
                "the appropriate app store. We reserve the right to discontinue or suspend certain features or\n" +
                "services within the App at any time without prior notice.\n\n" +
                "9. Intellectual Property\n\n" +
                "The App and all associated content, including logos, graphics, text, images, and software, are\n" +
                "owned by us or our licensors and are protected by copyright and intellectual property laws. You\n" +
                "may not use any content from the App without our express written permission.\n\n" +
                "10. Limitation of Liability\n\n" +
                "To the fullest extent permitted by law:\n" +
                "The App is provided “as is” without any warranties or representations of any kind, either\n" +
                "express or implied.\n" +
                "We do not guarantee the accuracy, completeness, or reliability of the App or its functionality.\n" +
                "We are not liable for any direct, indirect, incidental, special, or consequential damages,\n" +
                "including but not limited to loss of data, revenue, or profits, arising from your use of or\n" +
                "inability to use the App.\n\n" +
                "11. Indemnification\n\n" +
                "You agree to indemnify, defend, and hold harmless Verthome, its affiliates, directors, employees,\n" +
                "and agents from any claims, losses, damages, liabilities, costs, or expenses (including legal fees)\n" +
                "arising from:\n" +
                "Your use of the App.\n" +
                "Your violation of these Terms.\n" +
                "Any infringement of third-party rights, including intellectual property rights, arising from the\n" +
                "content you submit or share through the App.\n\n" +
                "12. Termination of Account\n\n" +
                "We may suspend or terminate your account and access to the App at any time, without notice, if\n" +
                "you violate these Terms or engage in activities that may harm the App or its users. You may also\n" +
                "terminate your account by uninstalling the App from your device.\n" +
                "Upon termination, all provisions of these Terms that by their nature should survive termination\n" +
                "shall remain in effect, including intellectual property rights, indemnification, and limitation of\n" +
                "liability.\n\n" +
                "13. Governing Law\n\n" +
                "These Terms and any disputes arising from or related to the App shall be governed by and\n" +
                "construed in accordance with the laws of your jurisdiction, without regard to its conflict of law\n" +
                "principles. You agree to submit to the exclusive jurisdiction of the courts located in your\n" +
                "jurisdiction for the resolution of any disputes.\n\n" +
                "14. Dispute Resolution\n\n" +
                "In the event of a dispute, you agree to first attempt to resolve the issue informally by contacting\n" +
                "us. If the dispute is not resolved within 30 days, you agree to resolve the dispute through binding\n" +
                "arbitration or mediation.\n\n" +
                "15. Third-Party Links\n\n" +
                "The App may contain links to third-party websites or services that are not owned or controlled by\n" +
                "Verthome. We are not responsible for the content, privacy policies, or practices of any thirdparty sites. You acknowledge and agree that we are not liable for any damages or losses\n" +
                "resulting from your use of third-party websites or services.\n\n" +
                "16. Force Majeure\n\n" +
                "We will not be held liable for any failure or delay in performance due to circumstances beyond\n" +
                "our reasonable control, including but not limited to natural disasters, acts of government, war,\n" +
                "terrorism, labor disputes, or network failures\n\n" +
                "17. Changes to Terms\n\n" +
                "We reserve the right to update, modify, or revise these Terms at any time. Any changes will be\n" +
                "posted within the app or on our website. Your continued use of the App after the changes are\n" +
                "made constitutes your acceptance of the updated Terms.\n\n" +
                "18. Severability\n\n" +
                "If any provision of these Terms is found to be invalid or unenforceable by a court, the remaining\n" +
                "provisions shall continue in full force and effect.\n\n" +
                "19. Contact Information\n\n" +
                "If you have any questions or concerns regarding these Terms, please contact us at:\n" +
                "Email : souravpati452@gmail.com\n" +
                "LinkedIn : Sourav Kumar Pati\n\n" +
                "" +
                "... and more";
    }
}