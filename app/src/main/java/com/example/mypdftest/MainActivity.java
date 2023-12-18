package com.example.mypdftest;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

import javax.security.auth.login.LoginException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView tv_write_file;
    private TextView tv_read_file;
    private File storageDir;
    private String filePath;
    // 或者
    // File storageDir = Environment.getExternalStorageDirectory(); // 获取公共存储目录

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                // You can write the files to external storage now
                Log.i(TAG,"有外部存储器的访问权");
            } else {
                Log.i(TAG,"没有外部存储器的访问权");
                // Requesting the permission from the user
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        }
    }

    private void initView() {
        //storageDir =  Environment.getExternalStorageDirectory();
        // storageDir = getExternalFilesDir(null); // 获取应用程序专属目录
        //storageDir = Environment.getExternalStoragePublicDirectory(null); // 获取应用程序专属目录

        storageDir = getFilesDir();  //内部存储
        //storageDir=Environment.getExternalStorageDirectory();   //外部存储
        //storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        //storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//        File parentDir = storageDir.getParentFile();
//        if (!parentDir.exists()) {
//            parentDir.mkdirs();
//        }
// 或者

        File file = new File(storageDir, "/shineTools/");
        if (!file.exists()){
            file.mkdirs();
        }
        tv_write_file = findViewById(R.id.tv_write_file);
        tv_read_file = findViewById(R.id.tv_read_file);
        tv_write_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isExternalStorageWritable = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
                boolean isExternalStorageReadable = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ||
                        Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY);

                Log.i(TAG,isExternalStorageWritable+"----手机的外部存储器-----"+isExternalStorageReadable);

                if (Build.VERSION.SDK_INT >= 23) {// 6.0
                    String[] perms = {
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_PHONE_STATE};
                    for (String p : perms) {
                        int f = ContextCompat.checkSelfPermission(MainActivity.this, p);
                        Log.d("---", String.format("%s - %d", p, f));
                        if (f != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(perms, 0XCF);
                            break;
                        }else {
                            // 获取ContentResolver对象
                            ContentResolver contentResolver = getContentResolver();
                            // 调用savePdfToMediaStore方法保存PDF文件
                            String fileName = "zhangjing.pdf";
                            savePdfToMediaStore(contentResolver,fileName);

//                            String formattedDate = new SimpleDateFormat("HH:mm:ss").format(new Date());
//                            File file = new File(storageDir, "/shineTools/zhangjing"+formattedDate+".txt");
//                            filePath = file.getAbsolutePath();
//                            Log.i(TAG, "我们看写入的文件目录是" + file.getAbsolutePath());
//                            try {
//                                FileOutputStream fos = new FileOutputStream(file);
//                                String data = "Hello, World!-----------";
//                                fos.write(data.getBytes());
//                                fos.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
                        }
                    }
                }
            }
        });

        tv_read_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRxJavaData();



//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                Uri uri = Uri.parse(storageDir+"/shineTools");
//                intent.setDataAndType(uri, "*/*");
//                startActivity(intent);

               String path = "/storage/emulated/0/shineTools/zhangjing.txt"; // 替换为您自己的目录路径
//                Uri uri = Uri.parse("content://com.android.externalstorage.documents/tree/primary%3A");
//                Uri treeUri = DocumentsContract.buildDocumentUriUsingTree(uri, DocumentsContract.getTreeDocumentId(uri));
//                Uri dirUri = DocumentsContract.buildChildDocumentsUriUsingTree(treeUri, DocumentsContract.getDocumentId(Uri.parse(path)));
//
//                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                intent.setType("*/*");
//                intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, dirUri);
//                startActivityForResult(intent, 1);

//                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                intent.setType("*/*");
//                intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, Uri.parse(filePath));
//                startActivityForResult(intent, 1);


//                Uri uri = Uri.parse("content://com.android.externalstorage.documents/shineTools:zhangjing.txt");
//                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                intent.setType("*/*");
//                intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, uri);
//                startActivityForResult(intent, 1);

//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                File file = new File(filePath);
//                Log.i(TAG, file.getName() + "我们看读取的这个文件是" + file.length());
//                Uri uri = FileProvider.getUriForFile(MainActivity.this, "com.example.mypdftest.fileprovider", file);
//                intent.setDataAndType(uri, "*/*");
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // 添加权限标志
//                startActivity(intent);

            }
        });
    }


    public static void savePdfToMediaStore(ContentResolver contentResolver, String fileName) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
        values.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS);

        Uri uri = contentResolver.insert(MediaStore.Files.getContentUri("external"), values);
        if (uri != null) {
            try {
                OutputStream outputStream = contentResolver.openOutputStream(uri);
                if (outputStream != null) {
                    // 写入PDF文件内容
                    File file = new File(fileName);
                    byte[] data = "PDF content".getBytes();
                    outputStream.write(data);
                    outputStream.close();
                    Log.i(TAG, "PDF文件写入成功");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void createPdf(){
        PdfDocument pdfDocument = new PdfDocument();
        File outputFile = new File(getCacheDir(), "my_pdf.pdf");
        try {
            pdfDocument.writeTo(new FileOutputStream(outputFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, "my_pdf.pdf");
        values.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS);

        ContentResolver contentResolver = getContentResolver();
        Uri uri = contentResolver.insert(MediaStore.Files.getContentUri("external"), values);

        try {
            OutputStream outputStream = contentResolver.openOutputStream(uri);
            InputStream inputStream = new FileInputStream(outputFile);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("CheckResult")
    private void createRxJavaData(){
        RxJavaHelper.openRxjavaData().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(String result) {
//                        Log.e(TAG, "custom decodeQRCode: result = " + result);
                        tv_read_file.setText("我们看得到的数据是："+result);
                    }
                    @Override
                    public void onError(Throwable e) {
//                        Log.e(TAG, "custom decodeQRCode: result err = " + e.getMessage());
                    }
                    @Override
                    public void onComplete() {

                    }
                });
    }

}