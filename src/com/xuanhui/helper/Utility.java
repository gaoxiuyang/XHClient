
package com.xuanhui.helper;

import java.io.BufferedInputStream;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import cn.buaa.myweixin.R;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


public class Utility {
    public static final int ACTIVITY_REQUEST_PICKTURE_CAMERA = 1;
    public static final int ACTIVITY_REQUEST_PICKTURE_ALBUM = 2;

    public static final int SEX_UNCERTAIN = 0;
    public static final int SEX_MAIN = 1;
    public static final int SEX_WOMAN = 2;

    public static final int PTHOTO_SIZE = 1900;
    private static FileInputStream fin;

    public static void hideKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager localInputMethodManager = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            View currentFocus = activity.getCurrentFocus();
            if (currentFocus != null) {
                localInputMethodManager.hideSoftInputFromWindow(
                        currentFocus.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public static void openKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

        }

    }

    public static boolean isSuccess() {
        Random ran = new Random();
        int t = ran.nextInt();

        if (t % 2 == 0) {
            return true;
        }
        return false;
    }

    public static void loadDataAsynctronous(final Activity activity,
            final LoadListener listener) {
        new Thread(new Runnable() {

           
            public void run() {
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // ignore
                }

                activity.runOnUiThread(new Runnable() {

                
                    public void run() {
                        if (isSuccess()) {
                            listener.onSuccess();
                        } else {
                            listener.onFailed();
                        }

                    }
                });

            }
        }).start();
    }

    public interface LoadListener {
        public void onSuccess();

        public void onFailed();
    }

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * TODO 现在头像是默认的
     * 
     * @param account
     * @param nickname
     * @param passwd
     * @return
     */
    // public static User getUser(String account, String nickname, String
    // passwd) {
    // User user = new User();
    // user.uid = "3";
    // user.account = account;
    // user.nickname = nickname;
    // user.passwd = passwd;
    // user.sex = 0;
    // user.avatarurl = "http://proimg.qiniudn.com/2.jpg";
    //
    // return user;
    // }

    public static String getSexDespByType(int type) {
        if (type == 0) {
            return "";
        } else if (type == 1) {
            return "男";
        } else {
            return "女";
        }
    }

    public static void addPhotoFromAlbums(Activity context) {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        context.startActivityForResult(i, ACTIVITY_REQUEST_PICKTURE_ALBUM);
    }

    public static void addPhotoFromCamera(Activity context) {
        Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (imageCaptureIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivityForResult(imageCaptureIntent,
                    ACTIVITY_REQUEST_PICKTURE_CAMERA);
        }
    }

    /** Fragment 使用 */
    public static void addPhotoFromAlbums(Fragment fragment) {
        if (null != fragment) {
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.setType("image/*");
            fragment.startActivityForResult(i, ACTIVITY_REQUEST_PICKTURE_ALBUM);
        }
    }

    public static void addPhotoFromCamera(Fragment fragment) {
        if (null != fragment) {
            Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            FragmentActivity fActivity = fragment.getActivity();
            if (null != fActivity
                    && imageCaptureIntent.resolveActivity(fActivity.getPackageManager()) != null) {
                fragment.startActivityForResult(imageCaptureIntent,
                        ACTIVITY_REQUEST_PICKTURE_CAMERA);
            }
        }
    }

    /**
     * 通过制定路径，从照相机添加图片
     * 
     * @param context
     * @throws Exception
     */
    public static Uri addPhotoFromCameraWithFilepath(Activity context,
            int requestCode) throws Exception {
        Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String strImgPath = getDefaultPhotoPath(context);
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US)
                .format(new Date()) + ".jpg";
        File outDir = new File(strImgPath);
        if (!outDir.exists()) {
            if (!outDir.mkdir()) {
               // Log.e(LOG_TAG, "create parent dir failed");
                throw new Exception("Can not mkdir for extrnal storage");
            }
        }

        File outFile = new File(strImgPath, fileName);
        Uri uri = Uri.fromFile(outFile);

        //Log.v(LOG_TAG, "设置的Uri" + uri);
        imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        // imageCaptureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        context.startActivityForResult(imageCaptureIntent, requestCode);

        return uri;
    }

    /**
     * 拍照默认路径
     * 
     * @param context
     * @return
     */
    private static String getDefaultPhotoPath(Context context) {
        File rootDir = context.getExternalFilesDir(null);

        if (rootDir != null) {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                if (!rootDir.exists()) {
                    rootDir.mkdir();
                }
            }

            File photoDir = new File(rootDir, "photo");
            if (!photoDir.exists()) {
                photoDir.mkdir();
            }
            try {
                return photoDir.getCanonicalPath();
            } catch (Exception e) {

            }
        }
        return "";
    }

    /**
     * 
     */
    public static Bitmap getBitmapFromUri(Context context, Uri uri) {
        try {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                    context.getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            Log.e("[Android]", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从特定的uri加载Bitmap
     * 
     * @param uri 按指定的长宽压缩
     * @param widthLimit
     * @param heightLimit
     * @return
     */
    public static Bitmap getBitmapFromUriWithCompress(Context context, Uri uri,
            int widthLimit, int heightLimit) {
        Bitmap bitmap = null;
        BitmapFactory.Options o = new BitmapFactory.Options();
        InputStream input = null;
        try {
           // Log.v(LOG_TAG, "给定的URI" + uri);
            input = context.getContentResolver().openInputStream(uri);
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(input, null, o);
            input.close();
            int realWidth = o.outWidth;
            int realHeight = o.outHeight;
            int s = 1;
            while ((realWidth / s > widthLimit)
                    || (realHeight / s > heightLimit)) {
                s *= 2;
            }

            input = context.getContentResolver().openInputStream(uri);
            o.inJustDecodeBounds = false;
            o.inSampleSize = s;
            bitmap = BitmapFactory.decodeStream(input, null, o);

        } catch (Exception e) {
           // Log.e(LOG_TAG, "Exception in decode stream", e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                   // Log.e(LOG_TAG, "IOException caught while closing stream", e);
                }
            }
        }

        return bitmap;
    }

    /**
     * 特定的URI添加图片，没有压缩
     * 那我们怎样才能对图片进行压缩呢？通过设置BitmapFactory.Options中inSampleSize的值就可以实现。比如我们有一张2048*1536像素的图片
     * ，将inSampleSize的值设置为4，就可以把这张图片压缩成512*384像素。
     * 原本加载这张图片需要占用13M的内存，压缩后就只需要占用0.75M了(假设图片是ARGB_8888类型，即每个像素点占用4个字节)。
     * 下面的方法可以根据传入的宽和高，计算出合适的inSampleSize值：
[java] view plaincopy
1.public static int calculateInSampleSize(BitmapFactory.Options options,  
2.        int reqWidth, int reqHeight) {  
3.    // 源图片的高度和宽度  
4.    final int height = options.outHeight;  
5.    final int width = options.outWidth;  
6.    int inSampleSize = 1;  
7.    if (height > reqHeight || width > reqWidth) {  
8.        // 计算出实际宽高和目标宽高的比率  
9.        final int heightRatio = Math.round((float) height / (float) reqHeight);  
10.        final int widthRatio = Math.round((float) width / (float) reqWidth);  
11.        // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高  
12.        // 一定都会大于等于目标的宽和高。  
13.        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;  
14.    }  
15.    return inSampleSize;  
16.}  
使用这个方法，首先你要将BitmapFactory.Options的inJustDecodeBounds属性设置为true，解析一次图片。然后将BitmapFactory.Options连同期望的宽度和高度一起传递到到calculateInSampleSize方法中，就可以得到合适的inSampleSize值了。之后再解析一次图片，使用新获取到的inSampleSize值，并把inJustDecodeBounds设置为false，就可以得到压缩后的图片了。
[java] view plaincopy
1.public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,  
2.        int reqWidth, int reqHeight) {  
3.    // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小  
4.    final BitmapFactory.Options options = new BitmapFactory.Options();  
5.    options.inJustDecodeBounds = true;  
6.    BitmapFactory.decodeResource(res, resId, options);  
7.    // 调用上面定义的方法计算inSampleSize值  
8.    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);  
9.    // 使用获取到的inSampleSize值再次解析图片  
10.    options.inJustDecodeBounds = false;  
11.    return BitmapFactory.decodeResource(res, resId, options);  
12.}  
下面的代码非常简单地将任意一张图片压缩成100*100的缩略图，并在ImageView上展示。
[java] view plaincopy
1.mImageView.setImageBitmap(  
2.    decodeSampledBitmapFromResource(getResources(), R.id.myimage, 100, 100));  
     * 
     * 
     * 
     * 
     * 
     * 
     */
    public static Bitmap getBitmapFromUriWithoutCompress(Context context,
            Uri uri) {
        try {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                    context.getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            Log.e("[Android]", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从 InputStream 输入流中读入字符串
     * 
     * @param inputStream
     * @return 字符串
     */
    public static String getString(InputStream inputStream) {
        InputStreamReader inputStreamReader = null;

        try {
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static void setWindowAlpha(float alpha, Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = alpha; // 0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

    @SuppressLint("NewApi")
    public static String[] copyOf(String[] original, int newLength) {
        if (Build.VERSION.SDK_INT >= 9) {
            return Arrays.copyOf(original, newLength);
        }

        String[] newArray = new String[newLength];
        int copyLength = (original.length >= newLength) ? newLength
                : original.length;
        System.arraycopy(original, 0, newArray, 0, copyLength);

        return newArray;
    }

    public static String getUTFString(String str) {
        String content = str;
        try {
            content = URLEncoder.encode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // ignore
            e.printStackTrace();
        }
        return content;
    }

    private static String getImageNameFromAccount(String account, String sex) {
        String fileName = null;
        if (account == null)
            return fileName;

        int hashName = (Math.abs(account.hashCode()) % 20 + 1);
        if (!StringUtils.isNullOrEmpty(sex)
                && Integer.parseInt(sex) == SEX_UNCERTAIN) {
            fileName = "boy" + hashName;
        } else {
            fileName = "girl" + hashName;
        }
        Log.v("TAGD", "filename :" + fileName);
        return fileName;
    }

    public static Bitmap getBitmapFromAccount(Context context, String account,
            String sex) {
        InputStream is = null;
        try {
            is = context.getAssets()
                    .open("avatar/" + getImageNameFromAccount(account, sex)
                            + ".jpeg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(is);

    }

    /**
     * @param bm
     * @return
     */
    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        return baos.toByteArray();
    }

    public static Bitmap bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    public static enum Identity {
        Teacher, Family, Chengguan
    }

    /**
     * 个人角色
     */
    public static Identity initPersonalIentity(int role_id) {
        Identity identity;
        if (role_id == 4 || role_id == 5) {
            identity = Identity.Chengguan;
        } else if (role_id == 11) {
            identity = Identity.Family;
        } else {
            identity = Identity.Teacher;
        }
        return identity;
    }

    /**
     * 读取文件
     */
    // 读SD中的文件
    public static byte[] readFileSdcardFile(String fileName) {
        // String res = "";
        byte[] buffer = null;
        try {
            fin = new FileInputStream(fileName);

            int length = fin.available();

            buffer = new byte[length];
            fin.read(buffer);

            Log.v("TAG", "语音大小" + length);
            // res = EncodingUtils.getString(buffer, "UTF-8");

            // fin.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        // return res;
        return buffer;
    }

    /**
     * 音频文件转String
     * 
     * @param voiceName
     * @return
     */
    public static String voiceFileToBase64String(Context context,String voiceName) {
        File file = new File(
                Utility.getDefaultVoicePath(context) + "/"
                        + voiceName);
        byte[] data = getBytesFromFile(file);

        Log.v("TAGD", "上传钱的文件路径" + file.getAbsolutePath());
       // data = Base64.encodeBase64(data);
        StringBuffer sb = new StringBuffer();
        for (byte bt : data) {
            sb.append((char) bt);
        }
        return sb.toString();
    }

    public static boolean writeBytesWithName(Context context, byte[] bytes, String voiceName) {
        Log.v("TAGD", "语音下载后的byte大小" + bytes.length);
        File file = new File(
                Utility.getDefaultVoicePath(context) + "/"
                        + voiceName);
        return writeBytesToFile(file, bytes);
    }

    /**
     * 文件转二进制
     * 
     * @param f
     * @return
     */
    public static byte[] getBytesFromFile(File f) {
        if (f == null) {
            return null;
        }

        InputStream fin = null;
        try {
            // byte[] bytes = new byte[(int)f.length()];
            // BufferedInputStream buf = new BufferedInputStream(new
            // FileInputStream(f));
            // buf.read(bytes, 0, bytes.length);
            // buf.close();
            // fin = new FileInputStream(f);
            // byte bytes[] = new byte[(int)f.length()];
            // fin.read(bytes);
            //
            fin = new BufferedInputStream(new FileInputStream(f));
            byte bytes[] = new byte[(int) f.length()];
            int bytesRead = fin.read(bytes);
            if (bytesRead != fin.available()) {
                Log.v("TAGD",
                        "读到的语音大小不相同" + bytesRead + " availble" + f.length());
            }
            Log.v("TAGD", "语音发送前的byte大小" + bytes.length);
            return bytes;
        } catch (IOException e) {

        } finally {
            try {
                fin.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    public static boolean writeBytesToFile(File f, byte[] fileBytes) {
        if (f == null)
            return false;
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(f));
            bos.write(fileBytes);
            bos.flush();
            return true;
        } catch (Exception e) {
            // ignore
            return false;
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * unix时间戳转换为时间字符串
     * 
     * @param context
     * @param ts 秒单位
     * @return
     */
    public static String timeStamp2String(Context context, String ts) {
        Timestamp time = new Timestamp(Long.valueOf(ts).longValue() * 1000);
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);

        String str;
        Long timeNow = System.currentTimeMillis() / 1000;
        Long timeMinus = Long.parseLong(ts);
        Long timeAfter = timeNow - timeMinus;
        if (timeAfter <= 60) {
            str = "刚刚";
        } else if (timeAfter > 60 && timeAfter <= 60 * 60) {
            str = (timeAfter / 60) + "分钟前";
        } else if (timeAfter > 60 * 60 && timeAfter < 60 * 60 * 24) {
            str = (timeAfter / (60 * 60)) + "小时前";
        } else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日",
                    Locale.getDefault());
            return format.format(time);
        }
        return str;
    }
    public static Bitmap getBitmapFromSectionId(Context context, int id) {
        return BitmapFactory.decodeResource(context.getResources(), id);

    }


    public static int getResourceByName(String name) {
        int id = R.drawable.schedule_list_item_peoplenum;
        try {
            id = R.drawable.class.getField(name).getInt(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * 拍照默认路径
     * 
     * @param context
     * @return
     */
    public static String getDefaultVoicePath(Context context) {
        File rootDir = context.getExternalFilesDir(null);
        if (rootDir == null) {
            rootDir = context.getFilesDir();
        }

        if (rootDir != null) {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                if (!rootDir.exists()) {
                    rootDir.mkdir();
                }
            }

            File photoDir = new File(rootDir, "voice");
            if (!photoDir.exists()) {
                photoDir.mkdir();
            }
            try {
                return photoDir.getCanonicalPath();
            } catch (Exception e) {

            }
        }
        return "";
    }

    public static boolean isFileExists(Context context, String name) {
        if (StringUtils.isNullOrEmpty(name)) {
            return false;
        }

        File file = new File(Utility.getDefaultVoicePath(context), name);
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public static String getFilename(String resurl) {
        String[] urls = resurl.split("/");
        if (urls.length > 0) {
            return urls[urls.length - 1];
        }
        return "";
    }
}
