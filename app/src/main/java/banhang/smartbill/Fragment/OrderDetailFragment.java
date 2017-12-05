package banhang.smartbill.Fragment;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.ArrayList;

import banhang.smartbill.Adapter.OrderDetailAdapter;
import banhang.smartbill.ItemTest;
import banhang.smartbill.R;

public class OrderDetailFragment extends android.support.v4.app.Fragment {
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    SurfaceView cameraView;
    TextView customerName;
    ToggleButton cameraBtn;
    ArrayList<ItemTest> arrItemTest;
    OrderDetailAdapter adapter=null;
    ListView lvHoaDon=null;
    View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = inflater.inflate(R.layout.activity_chitiethoadon,null);

        initVariables();

        cameraBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    startCamera();
                    // The toggle is enabled
                } else {
                    // The toggle is disabled
                    stopCamera();
                }
            }
        });
        return  mView;

    }
    private void initVariables(){
        cameraView = (SurfaceView)mView.findViewById(R.id.sv_camera_view);
        cameraBtn = (ToggleButton) mView.findViewById(R.id.tb_camera);
        lvHoaDon = (ListView)mView.findViewById(R.id.lv_item);
        arrItemTest = new ArrayList<ItemTest>();
        adapter = new OrderDetailAdapter(getActivity(),R.layout.chitiethoadon_listview_custom, arrItemTest);
        lvHoaDon.setAdapter(adapter);

        //Generate item de test
        for(int i=0;i<11;i++){
            String temp = "" ;
            temp += Integer.toString(i*1000);
            ItemTest hd = new ItemTest("Do an vat"+temp,"20000d",2+i);
            arrItemTest.add(hd);
            adapter.notifyDataSetChanged();
        }

        barcodeDetector =
                new BarcodeDetector.Builder(getActivity())
                        .setBarcodeFormats(Barcode.EAN_13)
                        .build();

        cameraSource = new CameraSource
                .Builder(getActivity(), barcodeDetector)
                .setRequestedPreviewSize(1280, 720)
                .setAutoFocusEnabled(true)
                .build();
        initSurfaceView();


    }
    public void getBarcode(){

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                /*if (barcodes.size() != 0) {
                    barcodeInfo.post(new Runnable() {    // Use the post method of the TextView
                        public void run() {
                            barcodeInfo.setText(    // Update the TextView
                                    barcodes.valueAt(0).displayValue
                            );
                        }
                    });
                }*/
            }
        });
    }
    public void startCamera(){
        try {
            if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                cameraSource.start(cameraView.getHolder());
            }
        } catch (IOException ie) {
            Log.e("CAMERA SOURCE", ie.getMessage());
        }
    }
    public void stopCamera(){
        cameraSource.stop();
    }

    public void initSurfaceView(){
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
    }

}
