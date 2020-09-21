package com.groupdocs.ui.viewer.util;

import com.groupdocs.viewer.Viewer;
import com.groupdocs.viewer.options.ViewInfoOptions;
import com.groupdocs.viewer.results.Page;
import com.groupdocs.viewer.results.ViewInfo;
import org.springframework.http.MediaType;

import java.util.List;

public class ViewerUtils {

    public static MediaType detectMediaType(String fileName) {
        if (fileName.contains(".")) {
            final String extension = fileName.substring(fileName.lastIndexOf("."));
            return MediaType.parseMediaType(MediaTypes.detectMediaTypeForWeb(extension));
        }
        return MediaType.APPLICATION_OCTET_STREAM;
    }

    public static void applyWidthHeightFix(Viewer viewer, ViewInfo viewInfo) {
        // Fix to detect size, because there is a bug with detecting size in HTML mode
        // The bug is already fixed in .NET and will be fixed in the next version of Java viewer
        final ViewInfo fixViewInfo = viewer.getViewInfo(ViewInfoOptions.forPngView(false));
        final List<Page> pages = viewInfo.getPages();
        final List<Page> fixPages = fixViewInfo.getPages();
        int lastFixWidth = 0, lastFixHeight = 0;
        for (int n = 0; n < Math.min(fixPages.size(), pages.size()); n++) {
            final Page page = pages.get(n);
            final Page fixPage = fixPages.get(n);
            int fixWidth = fixPage.getWidth();
            int fixHeight = fixPage.getHeight();
            if (page.getWidth() == 0 && page.getHeight() == 0) {
                pages.set(n, new Page(page.getNumber(), page.isVisible(), (fixWidth == 0) ? lastFixWidth : fixWidth, (fixHeight == 0) ? lastFixHeight : fixHeight, page.getLines()));
            }
            lastFixWidth = pages.get(n).getWidth();
            lastFixHeight = pages.get(n).getHeight();
        }
    }
}
