package com.app.mymap;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.core.AugmentedImage;
import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.core.exceptions.CameraNotAvailableException;
import com.google.ar.core.exceptions.UnavailableApkTooOldException;
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException;
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException;
import com.google.ar.core.exceptions.UnavailableSdkTooOldException;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Scene;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class ARActivity extends AppCompatActivity implements Scene.OnUpdateListener {

    private ArSceneView arView;
    private Session session;
    private boolean shouldConfigureSession=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);

        //view
        arView = (ArSceneView)findViewById(R.id.arView);
        //Request Premission
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        setupSession();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(ARActivity.this, "Permission need to display camera", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();
        initSceneView();
    }
    private void initSceneView() {
        arView.getScene().addOnUpdateListener(this);
    }
    private void setupSession() {
        if(session == null)
        {
            try {
                session = new Session(this);
            } catch (UnavailableArcoreNotInstalledException e) {
                e.printStackTrace();
            } catch (UnavailableApkTooOldException e) {
                e.printStackTrace();
            } catch (UnavailableSdkTooOldException e) {
                e.printStackTrace();
            } catch (UnavailableDeviceNotCompatibleException e) {
                e.printStackTrace();
            }
            shouldConfigureSession = true;
        }
        if(shouldConfigureSession)
        {
            configSession();
            shouldConfigureSession=false;
            arView.setupSession(session);
        }
        try {
            session.resume();
            arView.resume();
        } catch (CameraNotAvailableException e) {
            e.printStackTrace();
            session = null ;
            return;
        }
    }
    private void configSession() {
        Config config = new Config(session);
        if(!buildDatabase(config))
        {
            Toast.makeText(this, "Error database", Toast.LENGTH_SHORT).show();
        }
        config.setUpdateMode(Config.UpdateMode.LATEST_CAMERA_IMAGE);
        session.configure(config);
    }
    private boolean buildDatabase(Config config) {
        AugmentedImageDatabase augmentedImageDatabase;
       /* Bitmap bitmap = loadImage();
        if(bitmap == null)
        return false;*/
        try {
            InputStream inputStream = getAssets().open("edmtdev.imgdb");
            augmentedImageDatabase = AugmentedImageDatabase.deserialize(session,inputStream);
            config.setAugmentedImageDatabase(augmentedImageDatabase);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
    private Bitmap loadImage() {
        try {
            InputStream is = getAssets().open("cat_qr.jpeg");
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    MyARNode node = null;
    @Override
    public void onUpdate(FrameTime frameTime) {
        Frame frame = arView.getArFrame();
        Collection<AugmentedImage> updateAugmentedImg = frame.getUpdatedTrackables(AugmentedImage.class);
        if (node == null){
            for (AugmentedImage image : updateAugmentedImg) {
                if ((image.getTrackingState() == TrackingState.TRACKING)) {
                    if (image.getName().equals("cat_qr.jpeg")) {
                        node = new MyARNode(this, R.raw.superbean);
                        node.setImage(image);
                        arView.getScene().onAddChild(node);
                            Toast.makeText(ARActivity.this, "Wrong Way!!!", Toast.LENGTH_LONG).show();
                            break;
                    } else if (image.getName().equals("up.jpeg")) {
                        node = new MyARNode(this, R.raw.up);
                        node.setImage(image);
                        arView.getScene().onAddChild(node);
                        break;
                    } else if (image.getName().equals("down.jpeg")) {
                        node = new MyARNode(this, R.raw.down);
                        node.setImage(image);
                        arView.getScene().onAddChild(node);
                        break;
                    } else if (image.getName().equals("left.jpeg")) {
                        node = new MyARNode(this, R.raw.left);
                        node.setImage(image);
                        arView.getScene().onAddChild(node);
                        break;
                    } else if (image.getName().equals("right.jpeg")) {
                        node = new MyARNode(this, R.raw.right);
                        node.setImage(image);
                        arView.getScene().onAddChild(node);
                        break;
                    } else if (image.getName().equals("end.jpeg")) {
                        Intent intent = new Intent(ARActivity.this, Finish.class);
                        ARActivity.this.startActivity(intent);
                        break;
                    }
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        setupSession();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(ARActivity.this, "Permission need to display camera", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();
        initSceneView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(session != null)
        {
            arView.pause();
            session.pause();
        }
    }
    public void refresh(View view){
        if(node != null) {
            node.delete();
            finish();
            startActivity(getIntent());
        }
        Toast.makeText(getApplicationContext(), "Refresh", Toast.LENGTH_SHORT).show();
    }
}
