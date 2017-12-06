package banhang.smartbill.Fragment;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
import banhang.smartbill.DAL.OrdersAPI;
import banhang.smartbill.DAL.ProductAPI;
import banhang.smartbill.Entity.Order;
import banhang.smartbill.Entity.OrderProduct;
import banhang.smartbill.Entity.Product;
import banhang.smartbill.Entity.UnauthorizedAccessException;
import banhang.smartbill.ItemTest;
import banhang.smartbill.R;

public class OrderDetailFragment extends android.support.v4.app.Fragment {
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    SurfaceView cameraView;
    TextView tvCustomerName,tvCurrentPrice;
    ToggleButton cameraBtn;
    List<OrderProduct> arrProduct;
    OrderDetailAdapter adapter=null;
    FloatingActionButton fb_paid;
    ListView lvHoaDon=null;
    String codeDetected;
    Handler handlerPost;
    Handler handler;
    View mView;
    Runnable runnableUI;
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
        checkOrderCreated();
        getBarcode();
        fb_paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Xác nhận hóa đơn");
                alertDialog.setMessage("Xác nhận hoàn thành?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                if(MainActivity.CurrentOrder.getOrderProducts()!=null) {
                                    MainActivity.CurrentOrder.getOrderProducts().clear();
                                    MainActivity.CurrentOrder.setOrderProducts(arrProduct);
                                }
                                else MainActivity.CurrentOrder.setOrderProducts(arrProduct);
                                //Post Order len server
                                Order order = new Order();
                                order.setCustomerId(MainActivity.CurrentOrder.getCustomer().getId());
                                order.setCustomer(MainActivity.CurrentOrder.getCustomer());
                                order.setOrderProduct(arrProduct);
                                postOrder(order);

                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                            }
                        });

                alertDialog.show();

            }
        });
        return  mView;

    }
    private void initVariables(){
        cameraView = (SurfaceView)mView.findViewById(R.id.sv_camera_view);
        cameraBtn = (ToggleButton) mView.findViewById(R.id.tb_camera);
        tvCurrentPrice = (TextView)mView.findViewById(R.id.tv_current_price);
        tvCustomerName = (TextView)mView.findViewById(R.id.tv_current_customer_name);
        lvHoaDon = (ListView)mView.findViewById(R.id.lv_item);
        fb_paid = (FloatingActionButton)mView.findViewById(R.id.fb_paid);
        arrProduct = new ArrayList<OrderProduct>();

        if(MainActivity.CurrentOrder.getOrderProducts()!=null) {
            arrProduct.addAll(MainActivity.CurrentOrder.getOrderProducts());
            tvCurrentPrice.setText(Float.toString(getPrices(arrProduct))+"Đ");
        }
        adapter = new OrderDetailAdapter(getActivity(),R.layout.chitiethoadon_listview_custom, arrProduct);
        lvHoaDon.setAdapter(adapter);
        handlerPost = new Handler();


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
                    try{
                        ProductAPI api = new ProductAPI();
                        Product products = api.getProductByCode(barcodes.valueAt(0).displayValue);
                        //them OrderProduct vao danh sach Product
                        OrderProduct oProduct = new OrderProduct();
                        oProduct.setProduct(products);
                        oProduct.setProductId((products.getId()));
                        arrProduct.add(oProduct);



                        handlerPost.post(new Runnable() {
                            @Override
                            public void run() {
                                tvCurrentPrice.setText(Float.toString(getPrices(MainActivity.CurrentOrder.getOrderProducts()))+"Đ");
                                adapter.notifyDataSetChanged();
                            }
                        });

                    }catch(UnauthorizedAccessException ex){
                        MainActivity.requireLogin(getContext());

                    }


                }
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
                System.out.println("Created");

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

    public void checkOrderCreated(){
        if(MainActivity.CurrentOrder == null){
            Toast.makeText(getActivity(),"Hóa đơn chưa được tạo",Toast.LENGTH_LONG).show();
            OrderFragment fragment = new OrderFragment();
            ((MainActivity)getActivity()).showFragment(fragment);
        }
        else {
            tvCustomerName.setText(MainActivity.CurrentOrder.getCustomer().getName());
        }
    }
    public Float getPrices(List<OrderProduct> products){
        Float temp = 0f;
        if(products!=null) {
            for (OrderProduct o : products) {
                temp = o.getAmount() * o.getProduct().getUnitPrice();
            }
        }
        return temp;
    }
    public void postOrder(final Order order){
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what){
                    case 1: Toast.makeText(getContext(),"Tạo hóa đơn thành công",Toast.LENGTH_LONG).show();
                            MainActivity.CurrentOrder=null;
                            OrderFragment fragment = new OrderFragment();
                            ((MainActivity)getActivity()).showFragment(fragment);

                    case 2 : //error unauthorize
                        Toast.makeText(getContext(),R.string.unauthorize,Toast.LENGTH_LONG).show();
                        MainActivity.requireLogin(getContext());
                        break;

                }
            }
        };
        Thread postOrderThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OrdersAPI api = new OrdersAPI();
                    Order orders = api.postOrder(order);
                    Message message = handler.obtainMessage(1,orders);
                    handler.sendMessage(message);

                }catch(UnauthorizedAccessException ex){
                    Message message = handler.obtainMessage(2,"Unauthorize");
                    handler.sendMessage(message);
                }
            }
        });
        postOrderThread.start();
    }


}
