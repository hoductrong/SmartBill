package banhang.smartbill.Fragment;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import banhang.smartbill.Activity.MainActivity;
import banhang.smartbill.Adapter.OrderDetailAdapter;
import banhang.smartbill.Adapter.ProductAdapter;
import banhang.smartbill.DAL.ProductAPI;
import banhang.smartbill.Entity.OrderProduct;
import banhang.smartbill.Entity.Product;
import banhang.smartbill.Entity.UnauthorizedAccessException;
import banhang.smartbill.ItemTest;
import banhang.smartbill.R;

public class OrderDetailFragment extends android.support.v4.app.Fragment {
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    SurfaceView cameraView;
    TextView customerName;
    ToggleButton cameraBtn;
    List<OrderProduct> arrProduct;
    OrderDetailAdapter adapter=null;
    ListView lvHoaDon=null;
    String codeDetected;
    Handler handler;
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
        getBarcode();
        return  mView;

    }
    private void initVariables(){
        cameraView = (SurfaceView)mView.findViewById(R.id.sv_camera_view);
        cameraBtn = (ToggleButton) mView.findViewById(R.id.tb_camera);
        lvHoaDon = (ListView)mView.findViewById(R.id.lv_item);
        arrProduct = new ArrayList<OrderProduct>();
        adapter = new OrderDetailAdapter(getActivity(),R.layout.chitiethoadon_listview_custom, arrProduct);
        lvHoaDon.setAdapter(adapter);


        barcodeDetector =
                new BarcodeDetector.Builder(getActivity())
                        .setBarcodeFormats(Barcode.EAN_13)
                        .build();

        cameraSource = new CameraSource
                .Builder(getActivity(), barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .setAutoFocusEnabled(true)
                .build();
        initSurfaceView();


    }
    private void getBarcode(){
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {
                    getAndShowProducts(barcodes.valueAt(0).displayValue);

                }
            }
        });
        //return codeDetected;
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
    private void getAndShowProducts(final String code){


        Thread getProductThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    ProductAPI api = new ProductAPI();
                    Product products = api.getProductByCode(code);
                    Message message = handler.obtainMessage(1,products);
                    handler.sendMessage(message);
                }catch(UnauthorizedAccessException ex){
                    Message message = handler.obtainMessage(2,"Unauthorize");
                    handler.sendMessage(message);
                }
            }
        });
        getProductThread.start();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what){
                    case 1:
                        OrderProduct oProduct = new OrderProduct();
                        oProduct.setProduct((Product)msg.obj);
                        oProduct.setProductId(((Product) msg.obj).getId());
                        //van con thieu them order
                        arrProduct.add(oProduct);
                        adapter.notifyDataSetChanged();
                        break;
                    case 2 : //error unauthorize
                        Toast.makeText(getContext(),R.string.unauthorize,Toast.LENGTH_LONG).show();
                        MainActivity.requireLogin(getContext());
                        break;

                }
            }
        };
    }

}
