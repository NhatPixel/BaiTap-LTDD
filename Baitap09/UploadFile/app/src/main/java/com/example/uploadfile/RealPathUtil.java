package com.example.uploadfile;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;

public class RealPathUtil {

    /**
     * Hàm lấy đường dẫn thực từ Uri trên Android.
     *
     * @param context Context của ứng dụng.
     * @param uri Uri cần lấy đường dẫn thực.
     * @return Đường dẫn thực của file.
     */
    @SuppressLint("NewApi")
    public static String getRealPath(Context context, Uri uri) {
        String path = null;

        // Đối với Android 4.4 (KitKat) trở lên
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // Nếu Uri là của DocumentsContract
            if (DocumentsContract.isDocumentUri(context, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    String docId = DocumentsContract.getDocumentId(uri);
                    String[] split = docId.split(":");
                    String type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        path = android.os.Environment.getExternalStorageDirectory() + "/" + split[1];
                    }
                    // Bạn có thể xử lý thêm các loại khác như non-primary storage ở đây
                }
                // DownloadsProvider
                else if (isDownloadsDocument(uri)) {
                    String id = DocumentsContract.getDocumentId(uri);
                    Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                    path = getDataColumn(context, contentUri, null, null);
                }
                // MediaProvider
                else if (isMediaDocument(uri)) {
                    String docId = DocumentsContract.getDocumentId(uri);
                    String[] split = docId.split(":");
                    String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    String selection = "_id=?";
                    String[] selectionArgs = new String[]{split[1]};
                    path = getDataColumn(context, contentUri, selection, selectionArgs);
                }
            }
        }
        // Trường hợp Uri dạng content (chẳng hạn như với các tệp media)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Kiểm tra Google Photos URI
            if (isGooglePhotosUri(uri)) {
                path = uri.getLastPathSegment();
            } else {
                path = getDataColumn(context, uri, null, null);
            }
        }
        // Trường hợp Uri là file
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            path = uri.getPath();
        }
        return path;
    }

    /**
     * Hàm lấy cột dữ liệu từ URI.
     */
    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        String column = "_data";
        String[] projection = {column};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return null;
    }

    /**
     * Kiểm tra xem URI có phải là của ExternalStorage không.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * Kiểm tra xem URI có phải là của Downloads không.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * Kiểm tra xem URI có phải là của Media không.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * Kiểm tra xem URI có phải là của Google Photos không.
     */
    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
