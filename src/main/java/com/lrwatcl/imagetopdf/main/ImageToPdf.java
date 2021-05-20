package com.lrwatcl.imagetopdf.main;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.lrwatcl.imagetopdf.exception.AppException;
import com.lrwatcl.imagetopdf.util.ConstantUtils;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author String
 */
public final class ImageToPdf {
    private ImageToPdf(){

    }
    public static void dealWith(String imageFolderPath,String pdfPath) throws AppException, IOException {
        File file = new File(imageFolderPath);
        if(!file.exists()){throw new AppException(imageFolderPath+ ConstantUtils.PATH_DOES_NOT_EXIST);}
        File[] files = file.listFiles();
        if(null==files|| files.length==0){throw new AppException(imageFolderPath+ConstantUtils.PATH_IS_EMPTY);}
        Arrays.sort(files,new Comparator<File>() {
            public int compare(File o1, File o2) {
                if (o1.isDirectory() && o2.isFile()) {
                    return 1;
                }else if (o1.isFile() && o2.isDirectory()) {
                    return -1;
                }else if(o1.isDirectory() && o2.isDirectory()){
                    return 0;
                }else{
                    return o1.getName().compareToIgnoreCase(o2.getName());
                }
            }
        });
        FileOutputStream fos = new FileOutputStream(pdfPath);
        Document document = new Document(null, 0, 0, 0, 0);
        try {
            PdfWriter.getInstance(document, fos);
            if(null!=files[0]){
                BufferedImage img = ImageIO.read(files[0]);
                document.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
            }
            document.open();
            for (File imageFile:files) {
                if(imageFile.isFile()){
                    document.add(Image.getInstance(imageFolderPath+imageFile.getName()));
                }
            }
        }catch (Exception e){
            throw new AppException(e);
        }finally {
            document.close();
            fos.close();
        }
    }

    public static void main(String[] args) throws IOException, AppException {
        if(null!=args && args.length==ConstantUtils.PARAMETER_NUMBER && StringUtils.isNotBlank(args[0]) && StringUtils.isNotBlank(args[1])){
            ImageToPdf.dealWith(args[0],args[1]);
        }
    }
}
