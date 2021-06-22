package it.gov.pagopa.hubpa.payments.generate.paymentnotice.business.printer;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.imageio.ImageIO;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.HorizontalAlignment;

import it.gov.pagopa.hubpa.payments.generate.paymentnotice.constants.PaymentNoticeConstants;

/**
 * Management of the technical aspects of the notice payment pdf (fonts, logo
 * etc...)
 */
public class PdfPaymentNoticeManagement {

    /**
     * Creates iText template of the pdf
     * 
     * @param imgagePath
     *            path of the template to use
     * @return
     * @throws IOException
     */
    public static ImageData creaImgData(String imgagePath) throws IOException {
        return ImageDataFactory.create(Files.readAllBytes(new File(new StringBuffer(imgagePath).toString()).toPath()));
    }

    /**
     * Get robotoFontRegular font
     * 
     * @return
     * @throws IOException
     */
    public static PdfFont getRobotoFontRegular() throws IOException {
        return PdfFontFactory.createFont(
                Files.readAllBytes(
                        new File(new StringBuffer(PaymentNoticeConstants.robotoFontRegular).toString()).toPath()),
                PdfEncodings.IDENTITY_H);
    }

    /**
     * Get robotoFontBold font
     * 
     * @return
     * @throws IOException
     */
    public static PdfFont getRobotoFontBold() throws IOException {
        return PdfFontFactory.createFont(
                Files.readAllBytes(
                        new File(new StringBuffer(PaymentNoticeConstants.robotoFontBold).toString()).toPath()),
                PdfEncodings.IDENTITY_H);
    }

    /**
     * Get titilliumWebRegular font
     * 
     * @return
     * @throws IOException
     */
    public static PdfFont getTitilliumWebRegular() throws IOException {
        return PdfFontFactory.createFont(
                Files.readAllBytes(
                        new File(new StringBuffer(PaymentNoticeConstants.titilliumWebRegular).toString()).toPath()),
                PdfEncodings.IDENTITY_H);
    }

    /**
     * Get titilliumWebBold font
     * 
     * @return
     * @throws IOException
     */
    public static PdfFont getTitiilliumWebBold() throws IOException {
        return PdfFontFactory.createFont(
                Files.readAllBytes(
                        new File(new StringBuffer(PaymentNoticeConstants.titilliumWebBold).toString()).toPath()),
                PdfEncodings.IDENTITY_H);
    }

    /**
     * Get titilliumWebBlack font
     * 
     * @return
     * @throws IOException
     */
    public static PdfFont getTrilliumWebBlack() throws IOException {
        return PdfFontFactory.createFont(
                Files.readAllBytes(
                        new File(new StringBuffer(PaymentNoticeConstants.titilliumWebBlack).toString()).toPath()),
                PdfEncodings.IDENTITY_H);
    }

    /**
     * Instantiates an empty cell of the pdf
     * 
     * @param width
     *            width of the new cell
     * @return
     */
    public static Cell getEmptyCell(int width) {
        Cell emptyCell = new Cell();
        emptyCell.setWidth(width);
        emptyCell.setBorder(Border.NO_BORDER);
        Paragraph emptyPar = new Paragraph();
        emptyPar.add("  ");
        emptyCell.add(emptyPar);
        return emptyCell;
    }

    /**
     * Creates iText Image of the logo from image
     * 
     * @param byteImg
     *            byte array of the image
     * @param autoscale
     *            true if autoscale, otherwise false
     * @return
     * @throws IOException
     */
    public static Image getScaleOfGrayLogoImageFromByte(byte[] byteImg, boolean autoscale) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(byteImg));
        BufferedImage newBi = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(),
                BufferedImage.TYPE_USHORT_GRAY);
        newBi.getGraphics().drawImage(bufferedImage, 0, 0, null);
        ImageIO.write(newBi, PaymentNoticeConstants.PDF_TEXT_LOGO_EXTENSION, baos);
        ImageData imageData = ImageDataFactory.create(baos.toByteArray());
        Image img = new Image(imageData);
        img.setAutoScale(autoscale).setHorizontalAlignment(HorizontalAlignment.CENTER);
        return img;
    }
}
