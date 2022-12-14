package com.taquangkhoi.keisic;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.taquangkhoi.keisic.databinding.ActivityMainBinding;
import com.taquangkhoi.keisic.services.MyListener;

public class MainActivity extends AppCompatActivity implements MyListener {

    private static final String CHANNEL_ID = "1";
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private static final String TAG = "MainActivity";
    private NotificationReceiver nReceiver;
    NotificationCompat.Builder builder;
    FirebaseAuth mAuth;
    private TextView tvwEmail, tvwUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainViewModal mainViewModal = new ViewModelProvider(this).get(MainViewModal.class);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar); // binding v???i app_bar_main.xml
        mAuth = FirebaseAuth.getInstance();
        checkNotificationPermission();

        // T???o Receiver ????? nh???n th??ng b??o
        nReceiver = new NotificationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("Msg");
        registerReceiver(nReceiver, filter);

        //request notification permission
        createNotificationChannel();

        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("textTitle")
                .setContentText("textContent")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        View header = navigationView.getHeaderView(0);
        tvwEmail = header.findViewById(R.id.tvw_user_email);
        tvwUsername = header.findViewById(R.id.tvw_username);

        // get data from Login intent to show email
        Intent intentFromPreviousActivity = getIntent();
        String emailFromIntent = intentFromPreviousActivity.getStringExtra("email");
        Log.i(TAG, "onCreate: emailFromIntent " + emailFromIntent);
        if (emailFromIntent != null) {
            tvwEmail.setText(emailFromIntent);
        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        // Thi???t l???p cho NavigationUI
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_scrobbles, R.id.nav_loved, R.id.nav_charts)
                .setOpenableLayout(drawer)
                .build();

        // D??ng ????? qu???n l?? nav trong NavHost
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        // G??n AppBarConfiguration cho NavigationUI
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        // G??n NavigationView, NavController cho NavigationUI
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    /**
     * Ask user to grant notification permission
     */
    private void checkNotificationPermission() {
        if (isNotificationServiceEnabled(getApplicationContext())){
            Log.i(TAG, "Notification service is enabled");
        } else {
            Log.i(TAG, "Notification service is not enabled");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Notification Permission");
            builder.setMessage("Please enable notification permission");
            builder.setPositiveButton("OK", (dialog, which) -> {
                Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                startActivity(intent);
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            builder.show();
        }
    }

    void addEvents() {
        // n??t Floating Action Button
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // push a notification to system
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);


                // notificationId is a unique int for each notification that you must define
                notificationManager.notify(0, builder.build());
                Log.d("MainActivity", "onClick: notification pushed");

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        //add event to menu item
        MenuItem itemSettings = menu.findItem(R.id.action_settings);
        itemSettings.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Snackbar.make(binding.appBarMain.fab, "Opening Setting", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return false;
            }
        });

        MenuItem itemLogout = menu.findItem(R.id.action_logout);
        itemLogout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Snackbar.make(binding.appBarMain.fab, "Logging out", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                mAuth.signOut();
                finish();
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        Log.i(TAG, "onSupportNavigateUp: ");
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public void setValue(String packageName) {
        Log.d("MainActivity", "setValue: " + packageName);
    }

    class NotificationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String temp = intent.getStringExtra("package");
            Log.i(TAG, "NotificationReceiver onReceive: " + temp);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(nReceiver);
    }

    /**
     * Check if the notification listener service is enabled.
     *
     * @param c getApplicationContext()
     * @return
     */
    private boolean isNotificationServiceEnabled(Context c){
        String pkgName = c.getPackageName();
        final String flat = Settings.Secure.getString(c.getContentResolver(),
                "enabled_notification_listeners");
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}