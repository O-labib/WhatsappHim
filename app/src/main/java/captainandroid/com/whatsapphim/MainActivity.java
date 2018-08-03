package captainandroid.com.whatsapphim;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
    }


    public void whatsAppIntent(Context c) {
        String whatsAppPackageName = "com.whatsapp";
        boolean isWhatsAppInstalled;
        PackageManager packageManager = c.getPackageManager();

        try {
            packageManager.getPackageInfo(whatsAppPackageName, 0);
            isWhatsAppInstalled = true;
        } catch (PackageManager.NameNotFoundException e) {
            isWhatsAppInstalled = false;
        }

        if (isWhatsAppInstalled) {

            String smsNumber = editText.getText().toString().trim();

            Uri uri = Uri.parse("smsto:" + smsNumber);
            Intent i = new Intent(Intent.ACTION_SENDTO);
            i.setData(uri);
            i.setPackage(whatsAppPackageName);
            moveToIntent(i, c);

        } else {
            Toast.makeText(c, "WhatsApp is not installed", Toast.LENGTH_SHORT).show();
        }
    }

    private static void moveToIntent(Intent intent, Context c) {
        if (intent.resolveActivity(c.getPackageManager()) != null) {
            c.startActivity(intent);
            ((Activity) c).overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        }

    }

    public void whatsHim(View view) {
        if (editText.getText().toString().trim().equals("")) {

            Toast.makeText(this, "Please, Enter Phone number first!", Toast.LENGTH_SHORT).show();

            return;
        }

        whatsAppIntent(this);
    }

    public void email(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "from whatsApp him");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"o.labib1995@gmail.com"});

        String gmailPackageName = "com.google.android.gm";
        try {
            getPackageManager().getPackageInfo(gmailPackageName, 0);
            intent.setPackage(gmailPackageName);
        } catch (Exception e) {
        }

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
            overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        }
    }
}
