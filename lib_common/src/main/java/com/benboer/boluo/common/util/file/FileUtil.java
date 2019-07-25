package com.benboer.boluo.common.util.file;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;
import android.widget.TextView;

import com.benboer.boluo.common.app.BoLuo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Anding 文件工具类
 */

public class FileUtil {

    //格式化的模板
    private static final String TIME_FORMAT = "_yyyyMMdd_HHmmss";

    private static final String SDCARD_DIR =
            Environment.getExternalStorageDirectory().getPath();

    //默认本地上传图片目录
    public static final String UPLOAD_PHOTO_DIR = SDCARD_DIR + "/a_upload_photos/";

    //网页缓存地址
    public static final String WEB_CACHE_DIR = SDCARD_DIR + "/app_web_cache/";

    //系统相机目录
    public static final String CAMERA_PHOTO_DIR =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath() + "/Camera/";

    private static String getTimeFormatName(String timeFormatHeader) {
        final Date date = new Date(System.currentTimeMillis());
        //必须要加上单引号
        final SimpleDateFormat dateFormat = new SimpleDateFormat("'" + timeFormatHeader + "'" + TIME_FORMAT, Locale.getDefault());
        return dateFormat.format(date);
    }

    /**
     * @param timeFormatHeader 格式化的头(除去时间部分)
     * @param extension        后缀名
     * @return 返回时间格式化后的文件名
     */
    public static String getFileNameByTime(String timeFormatHeader, String extension) {
        return getTimeFormatName(timeFormatHeader) + "." + extension;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static File createDir(String sdcardDirName) {
        //拼接成SD卡中完整的dir
        final String dir = SDCARD_DIR + "/" + sdcardDirName + "/";
        final File fileDir = new File(dir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return fileDir;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File createFile(String sdcardDirName, String fileName) {
        return new File(createDir(sdcardDirName), fileName);
    }

    private static File createFileByTime(String sdcardDirName, String timeFormatHeader, String extension) {
        final String fileName = getFileNameByTime(timeFormatHeader, extension);
        return createFile(sdcardDirName, fileName);
    }

    //获取文件的MIME
    public static String getMimeType(String filePath) {
        final String extension = getExtension(filePath);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

    //获取文件的后缀名
    public static String getExtension(String filePath) {
        String suffix = "";
        final File file = new File(filePath);
        final String name = file.getName();
        final int idx = name.lastIndexOf('.');
        if (idx > 0) {
            suffix = name.substring(idx + 1);
        }
        return suffix;
    }

    /**
     * 保存Bitmap到SD卡中
     *
     * @param dir      目录名,只需要写自己的相对目录名即可
     * @param compress 压缩比例 100是不压缩,值约小压缩率越高
     * @return 返回该文件
     */
    public static File saveBitmap(Bitmap mBitmap, String dir, int compress) {

        final String sdStatus = Environment.getExternalStorageState();
        // 检测sd是否可用
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        File fileName = createFileByTime(dir, "DOWN_LOAD", "jpg");
        try {
            fos = new FileOutputStream(fileName);
            bos = new BufferedOutputStream(fos);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, compress, bos);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {

                if (bos != null) {
                    bos.flush();
                }
                if (bos != null) {
                    bos.close();
                }
                //关闭流
                if (fos != null) {
                    fos.flush();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        refreshDCIM();

        return fileName;
    }

    public static File writeToDisk(InputStream is, String dir, String name) {
        final File file = FileUtil.createFile(dir, name);
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

        try {
            bis = new BufferedInputStream(is);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);

            byte data[] = new byte[1024 * 4];

            int count;
            while ((count = bis.read(data)) != -1) {
                bos.write(data, 0, count);
            }

            bos.flush();
            fos.flush();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (fos != null) {
                    fos.close();
                }
                if (bis != null) {
                    bis.close();
                }
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }

    public static File writeToDisk(InputStream is, String dir, String prefix, String extension) {
        final File file = FileUtil.createFileByTime(dir, prefix, extension);
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

        try {
            bis = new BufferedInputStream(is);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);

            byte data[] = new byte[1024 * 4];

            int count;
            while ((count = bis.read(data)) != -1) {
                bos.write(data, 0, count);
            }

            bos.flush();
            fos.flush();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (fos != null) {
                    fos.close();
                }
                if (bis != null) {
                    bis.close();
                }
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }

    /**
     * 通知系统刷新系统相册，使照片展现出来
     */
    private static void refreshDCIM() {
        if (Build.VERSION.SDK_INT >= 19) {
            //兼容android4.4版本，只扫描存放照片的目录
            MediaScannerConnection.scanFile(BoLuo.getApplicationContext(),
                    new String[]{Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath()},
                    null, null);
        } else {
            //扫描整个SD卡来更新系统图库，当文件很多时用户体验不佳，且不适合4.4以上版本
            BoLuo.getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" +
                    Environment.getExternalStorageDirectory())));
        }
    }

    /**
     * 读取raw目录中的文件,并返回为字符串
     */
    public static String getRawFile(int id) {
        final InputStream is = BoLuo.getApplicationContext().getResources().openRawResource(id);
        final BufferedInputStream bis = new BufferedInputStream(is);
        final InputStreamReader isr = new InputStreamReader(bis);
        final BufferedReader br = new BufferedReader(isr);
        final StringBuilder stringBuilder = new StringBuilder();
        String str;
        try {
            while ((str = br.readLine()) != null) {
                stringBuilder.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                isr.close();
                bis.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }


    public static void setIconFont(String path, TextView textView) {
        final Typeface typeface = Typeface.createFromAsset(BoLuo.getApplicationContext().getAssets(), path);
        textView.setTypeface(typeface);
    }

    /**
     * 读取assets目录下的文件,并返回字符串
     */
    public static String getAssetsFile(String name) {
        InputStream is = null;
        BufferedInputStream bis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        StringBuilder stringBuilder = null;
        final AssetManager assetManager = BoLuo.getApplicationContext().getAssets();
        try {
            is = assetManager.open(name);
            bis = new BufferedInputStream(is);
            isr = new InputStreamReader(bis);
            br = new BufferedReader(isr);
            stringBuilder = new StringBuilder();
            String str;
            while ((str = br.readLine()) != null) {
                stringBuilder.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (bis != null) {
                    bis.close();
                }
                if (is != null) {
                    is.close();
                }
                assetManager.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (stringBuilder != null) {
            return stringBuilder.toString();
        } else {
            return null;
        }
    }

    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            final Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    final int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }


    /**
     * 获取缓存文件夹地址
     *
     * @return 当前APP的缓存文件夹地址
     */
    public static File getCacheDirFile(Context context) {
        return context.getCacheDir();
    }

    /**
     * 获取头像的临时存储文件地址
     *
     * @return 临时文件
     */
    public static File getPortraitTmpFile(Context context) {
        // 得到头像目录的缓存地址
        File dir = new File(getCacheDirFile(context), "portrait");
        // 创建所有的对应的文件夹
        //noinspection ResultOfMethodCallIgnored
        dir.mkdirs();

        // 删除旧的一些缓存为文件
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                //noinspection ResultOfMethodCallIgnored
                file.delete();
            }
        }

        // 返回一个当前时间戳的目录文件地址
        File path = new File(dir, SystemClock.uptimeMillis() + ".jpg");
        return path.getAbsoluteFile();
    }

    /**
     * 获取声音文件的本地地址
     *
     * @param isTmp 是否是缓存文件， True，每次返回的文件地址是一样的
     * @return 录音文件的地址
     */
    public static File getAudioTmpFile(Context context, boolean isTmp) {
        File dir = new File(getCacheDirFile(context), "audio");
        //noinspection ResultOfMethodCallIgnored
        dir.mkdirs();
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                //noinspection ResultOfMethodCallIgnored
                file.delete();
            }
        }

        // aar
        File path = new File(getCacheDirFile(context), isTmp ? "tmp.mp3" : SystemClock.uptimeMillis() + ".mp3");
        return path.getAbsoluteFile();
    }

}
