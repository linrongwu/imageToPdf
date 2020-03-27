import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ImageToPdf {
    private String imageFolderPath;
    private String pdfPath;
    private double width;
    private double height;
    private int page;
    private Document document;

    public void dealWith() throws IOException, DocumentException {
        if(null!=this.imageFolderPath && !"".equals(this.imageFolderPath)){
            File file = new File(imageFolderPath);
            if(file.exists() && file.listFiles().length>0){
                File[] files = file.listFiles();
                List<File> fileList =  Arrays.asList(files);
                Collections.sort(fileList, new Comparator<File>() {
                    public int compare(File o1, File o2) {
                        if (o1.isDirectory() && o2.isFile())
                            return -1;
                        if (o1.isFile() && o2.isDirectory())
                            return 1;
                        return compareTofileName(o1.getName(),o2.getName());
                    }
                });
                this.page = files.length;
                System.out.println(this.page);
                document = new Document(null, 0, 0, 0, 0);
                if(null!=pdfPath && !"".equals(pdfPath)){
                    FileOutputStream fos = new FileOutputStream(pdfPath);
                    PdfWriter.getInstance(document, fos);
                }
                BufferedImage img = null;
                Image image = null;
                if(null!=files[0]){
                    img = ImageIO.read(files[0]);
                    document.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
                }
                document.open();
                for (File imageFile:files) {
                    System.out.println(imageFile.getName());
                    img = ImageIO.read(imageFile);
                    image = Image.getInstance(imageFolderPath+imageFile.getName());
                    document.add(image);
                }
                document.close();
                this.width = document.getPageSize().getWidth();
                this.height = document.getPageSize().getHeight();
            }
        }
    }

    private int compareTofileName(String fileName1, String fileName2) {
        fileName1 = fileName1.substring(0,fileName1.lastIndexOf("."));
        fileName2 = fileName2.substring(0,fileName2.lastIndexOf("."));
        String[] name1 = fileName1.split("_");
        String[] name2 = fileName2.split("_");
        int data1 = Integer.parseInt(name1[1])-Integer.parseInt(name2[1]);
        int data2 = Integer.parseInt(name1[2])-Integer.parseInt(name2[2]);
        if(data1>0){
            return -1;
        }else if(data1<0){
            return  1;
        }else{
            if(data2>0){
                return 1;
            }else{
                return -1;
            }
        }
    }
    public String getImageFolderPath() {
        return imageFolderPath;
    }

    public void setImageFolderPath(String imageFolderPath) {
        this.imageFolderPath = imageFolderPath;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public static void main(String[] args) throws IOException, DocumentException {
        ImageToPdf imageToPdf = new ImageToPdf();
        imageToPdf.setImageFolderPath("D:\\koreancartoon\\激情分享屋\\");
        imageToPdf.setPdfPath("D:\\koreancartoon\\激情分享屋\\激情分享屋.pdf");
        long time1 = System.currentTimeMillis();
        imageToPdf.dealWith();
        long time2 = System.currentTimeMillis();
        int time = (int) ((time2 - time1)/1000);
        System.out.println("执行了："+time+"秒！");
    }
}
