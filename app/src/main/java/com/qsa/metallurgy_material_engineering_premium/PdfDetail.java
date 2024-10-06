package com.qsa.metallurgy_material_engineering_premium;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.icu.text.SimpleDateFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;
import com.qsa.metallurgy_material_engineering_premium.Model.Books;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import jp.wasabeef.glide.transformations.BlurTransformation;
import static com.bumptech.glide.request.RequestOptions.bitmapTransform;


public class PdfDetail extends AppCompatActivity {

    private static final int WRITE_EXT_STORAGECODE = 1;
    private static final int READ_EXT_STORAGECODE = 2;

    private InterstitialAd mInterstitialAd;
    FloatingActionButton favouriteBtn;
    FloatingActionButton downloadBtn;
    Button read;
    Button videoBtn;
    ImageView bookImageBack;
    TextView bookNameBack;
    TextView authorNameBack;
    TextView likesText;
    TextView readersText;
    TextView shareText;
    TextView bookCategory1;
    TextView downloads;
    TextView descriptionText;

    ArrayList<Books> dataListOffine;
    SharedPreferences sharedPreferences;
    //Set<String> set;

    File file;
    String str;
    Bitmap bitmap;
    ImageView backgroundImage;
    String id = "";

    String bookName, authorName, bookImage, pdfUrl, description, downloadsstring, bookCategory = "";
    private Dialog progressDialog;
    //private AdView mAdView;

    /***** my work to implement video btn ***/
    String youtubeVideoUrl, type = "";
    private Books books = new Books();

    /***** my work end ***/
    //private com.facebook.ads.InterstitialAd facebook_InterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_detail);


//        loadBannerAd();
//        loadInterstatialAd();


        progressDialog = new ProgressDialog(this);
        ((ProgressDialog) progressDialog).setMessage("Please wait\nDownloading pdf...");
        progressDialog.setCancelable(false);


        favouriteBtn = findViewById(R.id.fab);
        downloadBtn = findViewById(R.id.downloadBtn);
        bookImageBack = findViewById(R.id.bookImage);
        bookNameBack = findViewById(R.id.bookName);
        authorNameBack = findViewById(R.id.authorName);
        read = findViewById(R.id.readBtn);
        videoBtn = findViewById(R.id.videoBtn);
        backgroundImage = findViewById(R.id.background_blur);
        likesText = findViewById(R.id.likesText);
        readersText = findViewById(R.id.readerText);
        shareText = findViewById(R.id.shareText);
        bookCategory1 = findViewById(R.id.bookCategory);
        downloads = findViewById(R.id.reviewText);
        descriptionText = findViewById(R.id.description);

        dataListOffine = new ArrayList<>();

        Intent i = getIntent();
        bookName = i.getStringExtra("bookName");
        authorName = i.getStringExtra("authorName");
        bookImage = i.getStringExtra("bookImage");
        pdfUrl = i.getStringExtra("pdfUrl");
        description = i.getStringExtra("description");
        bookCategory = i.getStringExtra("bookCategory");
        downloadsstring = i.getStringExtra("bookDownloads");
        /*********/
        youtubeVideoUrl = i.getStringExtra("youtubeUrl");
        type = i.getStringExtra("type");
        /**********/


        descriptionText.setText(description);

        id = i.getStringExtra("id");

        Log.d("bookData:", "bookName " + bookName);
        Log.d("bookData:", "authorName " + authorName);
        Log.d("bookData:", "bookImage " + bookImage);
        Log.d("bookData:", "pdfUrl " + pdfUrl);
        Log.d("bookData:", "description " + description);
        Log.d("bookData:", "id " + id);
        /*****my work***/
        Log.d("bookData:", "type " + type);
        /*********/


        /*final SharedPreferences sharedPreferencesDownloads = getSharedPreferences("downloads", MODE_PRIVATE);
        set = sharedPreferencesDownloads.getStringSet("key", null);

        try {
            dataListOffine = new ArrayList<String>(set);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        Log.d("idTest: ", "Id: " + id);

        sharedPreferences = this.getSharedPreferences("favourites", Context.MODE_PRIVATE);
        boolean check = sharedPreferences.getBoolean("liked_" + id, false);

        if (check) {
            favouriteBtn.invalidate();
            favouriteBtn.setBackground(getResources().getDrawable(R.drawable.ic_thumb_up_filled));
        } else {
            favouriteBtn.setImageResource(R.drawable.ic_thumb_up);
        }

        //setting selected book data
        //setting selected book data
        Glide.with(this)
                .load(bookImage)
                .into(bookImageBack);

        Glide.with(this).load(bookImage)
                .apply(bitmapTransform(new BlurTransformation(30)))
                .into(backgroundImage);

        bookNameBack.setText(bookName);
        authorNameBack.setText(authorName);
        bookCategory1.setText(bookCategory);

        try {

            downloads.setText(downloadsstring);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //for updating likes
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Books");
        final Query query = reference.orderByChild("id").equalTo(id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Books books = snapshot1.getValue(Books.class);
                        likesText.setText(books.getLiked());
                        if (snapshot1.child("readers").exists()) {
                            readersText.setText(books.getReaders());
                        } else {
                            readersText.setText("0");
                        }

                        if (snapshot1.child("shared").exists()) {
                            shareText.setText(books.getShared());
                        } else {
                            shareText.setText("0");
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        favouriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("favourites", Context.MODE_PRIVATE);
                boolean check = sharedPreferences.getBoolean("liked_" + id, false);

                if (check) {

                    sharedPreferences = getSharedPreferences("favourites", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putBoolean("liked_" + id, false);
                    editor.commit();

                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Books");
                    final Query query = reference.orderByChild("id").equalTo(id);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot != null) {
                                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                    Books books = snapshot1.getValue(Books.class);
                                    String liked = books.getLiked();
                                    if (liked.equals("0")) {
                                        //do nothing
                                    } else {
                                        int integerLiked = (Integer.parseInt(liked)) - 1;
                                        books.setLiked(String.valueOf(integerLiked));
                                        reference.child(id).setValue(books);
                                        likesText.setText(books.getLiked());
                                    }

                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    favouriteBtn.setImageResource(R.drawable.ic_thumb_up);
                    Toast.makeText(PdfDetail.this, "Dislike", Toast.LENGTH_SHORT).show();

                } else {
                    /*saveData(id);*/
                    sharedPreferences = getSharedPreferences("favourites", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putBoolean("liked_" + id, true);
                    editor.commit();

                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Books");
                    final Query query = reference.orderByChild("id").equalTo(id);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot != null) {
                                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                    Books books = snapshot1.getValue(Books.class);
                                    String liked = books.getLiked();
                                    int integerLiked = (Integer.parseInt(liked)) + 1;
                                    books.setLiked(String.valueOf(integerLiked));
                                    reference.child(id).setValue(books);
                                    likesText.setText(books.getLiked());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    favouriteBtn.setImageResource(R.drawable.ic_thumb_up_filled);
                    Toast.makeText(PdfDetail.this, "Liked", Toast.LENGTH_SHORT).show();

                }
            }
        });

        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //storing value in array list
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED) {
                        String[] permission = {
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        };
                        requestPermissions(permission, WRITE_EXT_STORAGECODE);
                        return;
                    }
                }


                bitmap = ((BitmapDrawable) bookImageBack.getDrawable()).getBitmap();
                progressDialog.show();

                savepdf();


                Log.d("bookDataf:", "bookName " + bookName);
                Log.d("bookDataf:", "authorName " + authorName);

                Log.d("bookDataf:", "pdfUrl " + pdfUrl);
                Log.d("bookDataf:", "description " + description);
                Log.d("bookDataf:", "id " + id);


                //dataListOffine.add(bk);
                //saveData();

                /*//Set the values
                set = new HashSet<String>();
                set.addAll(dataListOffine);
                SharedPreferences.Editor editor = sharedPreferencesDownloads.edit();
                editor.putStringSet("key", set);
                editor.commit();*/
            }

            private void savepdf() {

                FileLoader.with(getApplicationContext())
                        .load(pdfUrl, false) //2nd parameter is optioal, pass true to force load from network
                        .fromDirectory("test4", FileLoader.DIR_INTERNAL)
                        .asFile(new FileRequestListener<File>() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onLoad(FileLoadRequest request, FileResponse<File> response) {

                                saveimagetoGallary();


                            }

                            @Override
                            public void onError(FileLoadRequest request, Throwable t) {
                                progressDialog.dismiss();
                            }
                        });
            }
        });


//        Toast.makeText(this, books.getPdfUrl()+"hah", Toast.LENGTH_SHORT).show();


        /***** my work to implement video btn ***/

        // watch video button click


        videoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gotoVideoLink();
            }
        });
        /*** my work end**/

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
//                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
//                    if (mInterstitialAd.isLoaded()) {
//                        mInterstitialAd.show();
//                        return;
//                    } else {
//                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
//                    }
//                }else{
                // for offline oppening of book without ads...
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Books");
                final Query query = reference.orderByChild("id").equalTo(id);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot != null) {
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                Books books = snapshot1.getValue(Books.class);

                                if (snapshot1.child("readers").exists()) {
                                    String readers = books.getReaders();
                                    int integerReader = (Integer.parseInt(readers)) + 1;
                                    books.setReaders(String.valueOf(integerReader));
                                    reference.child(id).setValue(books);
                                    readersText.setText(books.getReaders());
                                } else {
                                    books.setReaders("1");
                                    reference.child(id).setValue(books);
                                    readersText.setText(books.getReaders());
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                if (pdfUrl.equals("") || pdfUrl.equals("null")) {
                    read.setEnabled(false);
                    Toast.makeText(PdfDetail.this, "No PDF URL set for this book", Toast.LENGTH_SHORT).show();
                } else {
                    read.setEnabled(true);
                    Intent i = new Intent(PdfDetail.this, BookView.class);
                    i.putExtra("pdfUrl", pdfUrl);
                    i.putExtra("bookId", id);
                    i.putExtra("bookName", bookName);
                    startActivity(i);

                }

            }


            //  }
        });
    }  // onCreate closed

//    private void loadBannerAd() {
//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
//        mAdView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
//    }


    private void saveData(String id) {
        SharedPreferences sharedPreferences = getSharedPreferences("favourites", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("liked_" + id, true);
        editor.commit();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void saveimagetoGallary() {

        String time = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
        File path = Environment.getExternalStorageDirectory();
        File dir = new File(path + "/DCIM/.eBookImages");
        dir.mkdir();
        String imagename = time + ".JPEG";
        file = new File(dir, imagename);
        OutputStream out;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            Booksdb bk = new Booksdb();
            bk.setBookName(bookName);
            bk.setAuthorName(authorName);
            bk.setBookImage(String.valueOf(file));
            bk.setPdfUrl(pdfUrl);
            bk.setDescription(description);
            bk.setId(id);
            /***** my work to implement video btn ***/
            // setting the method of setYoutubeUrl from model class of Bookssdb.java     jiska paramter hy "bk" yha
            bk.setYoutubeUrl(youtubeVideoUrl);
            /** my work end */


            MainActivity.myappdatabas.myDao().addBook(bk);
            progressDialog.dismiss();


            final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Books").child(id);
            //final Query query = reference.orderByChild("id").equalTo(id);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String dwnlds1 = snapshot.child("downloads").getValue(String.class);

                    Log.e("TAGFIRE", "onDataChange: " + snapshot + ":" + dwnlds1 + "id" + id);
                    int val = Integer.parseInt(dwnlds1);
                    val = val + 1;
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("downloads", String.valueOf(val));
                    final int finalVal = val;
                    reference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            downloads.setText("" + finalVal);
                            Toast.makeText(PdfDetail.this, "Download complete ", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            // Toast.makeText(PdfDetail.this, "Saved in DCIM", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED) {
                    String[] permission = {
                            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
                    };
                    requestPermissions(permission, WRITE_EXT_STORAGECODE);
                }
            }
        }
    }

    public void saveData() {
        sharedPreferences = getSharedPreferences("saveData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(dataListOffine);
        editor.putInt("dataListSize", dataListOffine.size());
        editor.putString("downloadBookData_" + id, json);
        editor.commit();
    }

    public void gotoVideoLink(){

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            if (youtubeVideoUrl.equals("") || youtubeVideoUrl.equals("null")) {
                videoBtn.setEnabled(false);
                Toast.makeText(PdfDetail.this, "No Youtube URL or Empty for this book" + youtubeVideoUrl, Toast.LENGTH_SHORT).show();
            } else {
                videoBtn.setEnabled(true);
                Intent intent = new Intent(PdfDetail.this, VideoPlayerActivity.class);
                intent.putExtra("categoryName", bookCategory);
                intent.putExtra("youtubeUrl", youtubeVideoUrl);
                intent.putExtra("type", type);
                startActivity(intent);
            }

        } else {

            Toast.makeText(PdfDetail.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

//    private void loadFacebook_InterstitalAdVideo() {
//        AudienceNetworkAds.initialize(this);
////        facebook_InterstitialAd = new InterstitialAd(this, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
//        facebook_InterstitialAd = new com.facebook.ads.InterstitialAd(this, getResources().getString(R.string.Facebook_interstitial_ad_unit_idPlacement));
//
//        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
//            @Override
//            public void onInterstitialDisplayed(Ad ad) {
//
//            }
//
//            @Override
//            public void onInterstitialDismissed(Ad ad) {
//                gotoVideoLink();
//            }
//
//            @Override
//            public void onError(Ad ad, AdError adError) {
//
//                gotoVideoLink();
//            }
//
//            @Override
//            public void onAdLoaded(Ad ad) {
//                facebook_InterstitialAd.show();
//            }
//
//            @Override
//            public void onAdClicked(Ad ad) {
//
//            }
//
//            @Override
//            public void onLoggingImpression(Ad ad) {
//
//            }
//        };
//        facebook_InterstitialAd.loadAd(facebook_InterstitialAd.buildLoadAdConfig().withAdListener(interstitialAdListener).build());
//
//        // Create listeners for the Interstitial Ad
//    }

}