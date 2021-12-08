import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author:xts
 * @date:Created in 2021/6/19 4:24
 * @description
 * @modified by:
 * @version: 1.0
 */
public class qrCodeTest {
    public static void main(String[] args) {
        HashMap hashMap=new HashMap();
        hashMap.put(EncodeHintType.CHARACTER_SET,"UTF-8");
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hashMap.put(EncodeHintType.MARGIN,2);
        try {
            BitMatrix bitMatrix=new MultiFormatWriter().encode("http://jsyh.natapp1.cc?id=23456&test=123", BarcodeFormat.QR_CODE,Integer.parseInt("300"),Integer.parseInt("300"),hashMap);
            //导出到指定目录
            try {
                File file= new File("D://qrCode/erweima.png");
                MatrixToImageWriter.writeToPath(bitMatrix,"png",file.toPath());
                MatrixToImageConfig matrixToImageConfig= new MatrixToImageConfig(0xFF000001,0xFFFFFFFF);
                BufferedImage matrixImage=MatrixToImageWriter.toBufferedImage(bitMatrix,matrixToImageConfig);
                //添加log
                String logPath="D:/qrCode/log/log.jpg";
                File logoFile=new File(logPath);
                /**
                 * 读取二维码图片，并构建绘图对象
                 */
                Graphics2D g2 = matrixImage.createGraphics();
                int matrixWidth = matrixImage.getWidth();
                int matrixHeigh = matrixImage.getHeight();
                /**
                 * 读取Logo图片
                 */
                BufferedImage logo = ImageIO.read(logoFile);
                //开始绘制图片
                g2.drawImage(logo,matrixWidth/5*2,matrixHeigh/5*2, matrixWidth/5, matrixHeigh/5, null);//绘制
                BasicStroke stroke = new BasicStroke(5,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
                g2.setStroke(stroke);// 设置笔画对象
                //指定弧度的圆角矩形
                RoundRectangle2D.Float round = new RoundRectangle2D.Float(matrixWidth/5*2, matrixHeigh/5*2, matrixWidth/5, matrixHeigh/5,20,20);
                g2.setColor(Color.white);
                g2.draw(round);// 绘制圆弧矩形
                //设置logo 有一道灰色边框
                BasicStroke stroke2 = new BasicStroke(1,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
                g2.setStroke(stroke2);// 设置笔画对象
                RoundRectangle2D.Float round2 = new RoundRectangle2D.Float(matrixWidth/5*2+2, matrixHeigh/5*2+2, matrixWidth/5-4, matrixHeigh/5-4,20,20);
                g2.setColor(new Color(128,128,128));
                g2.draw(round2);// 绘制圆弧矩形
                g2.dispose();
                matrixImage.flush() ;
                ImageIO.write(matrixImage,"png",file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}