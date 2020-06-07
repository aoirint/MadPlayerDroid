package com.github.aoirint.madplayer.util;

import android.content.Context;
import android.net.Uri;
import android.provider.DocumentsContract;

import androidx.documentfile.provider.DocumentFile;

import java.util.ArrayList;
import java.util.List;

public class ContentUriUtil {

    public static List<DocumentFile> getFileTree(Context context, Uri contentUri) {
        Uri treeUri = DocumentsContract.buildChildDocumentsUriUsingTree(contentUri, DocumentsContract.getTreeDocumentId(contentUri));
        DocumentFile rootFile = DocumentFile.fromTreeUri(context, treeUri);

        ArrayList<DocumentFile> files = new ArrayList<>();
        if (rootFile.isDirectory())
            _getFileTree(rootFile, files);
        else
            files.add(rootFile);

        return files;
    }

    private static void _getFileTree(DocumentFile directory, List<DocumentFile> resultList) {
        for (DocumentFile file: directory.listFiles()) {
            if (file.isDirectory())
                _getFileTree(file, resultList);
            else
                resultList.add(file);
        }
    }

}
